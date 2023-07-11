/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Mapper.Mapper;
import Repository.IAuthRepository;
import Repository.IRoleRepository;
import dto.UserDto;
import static enumeration.AccountCode.*;
import static enumeration.UserCode.CUSTOMER;
import static enumeration.UserCode.USER_LOGIN_FAILED;
import static enumeration.UserCode.USER_LOGIN_SUCCESS;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import model.*;
/**
 *
 * @author Lenovo
 */
public class AuthService implements IAuthRepository
{
    @PersistenceContext 
    private EntityManager em;
    @Resource 
    private UserTransaction utx;
    IRoleRepository _roleRepository;
    private final Mapper _mapper;
    
    public AuthService(
            UserTransaction utx,
            EntityManager em,
            IRoleRepository roleRepository,
            Mapper mapper)
    {
        this.utx = utx;
        this.em = em;
        this._roleRepository = roleRepository;
        this._mapper = mapper;
    }

    @Override
    public Result<UserDto> UserLogin(String email, String password) 
    {
        try
        {
            List<Users> users = (List<Users>) em.createNamedQuery("Users.findByEmail")
                    .setParameter("email", email)
                    .getResultList();
            
            if(!users.isEmpty())
            {
                Users user = users.get(0);
                if(user.getPassword().equals(password) || LoginWithTempass(email, password))
                {
                    Result result = CheckUserAccountStatus(user.getUserid());
                    if(result.getStatus())
                    {
                        List<Role> userRole = em.createNamedQuery("Role.findByUserid").setParameter("userid", user.getUserid()).getResultList();
                        List<Roles> roleName = em.createNamedQuery("Roles.findByRolesid").setParameter("rolesid", userRole.get(0).getRolesid()).getResultList();

                        LocalDateTime currentDateTime = LocalDateTime.now();
                        Timestamp currentTimestamp = Timestamp.valueOf(currentDateTime);
                        user.setLastlogin(currentTimestamp);
                        utx.begin();
                        em.merge(user);
                        utx.commit();
                        
                        UserDto userDto = _mapper.fromUserToUserDto(user);
                        userDto.setRolename(roleName.get(0).getRolename());
                        return new Result<>(userDto, roleName.get(0).getRolename(), true, USER_LOGIN_SUCCESS.getMessage());
                    }
                    else
                        return new Result<>(null, null, false, result.getMessage());
                }
            }
        }
        catch(Exception ex)
        {
            System.out.println("Login failed : " + ex.getMessage());
        }
        return new Result<>(new UserDto(), null, false, USER_LOGIN_FAILED.getMessage());
    }
    
    @Override
    public boolean LoginWithTempass(String email, String tempass) {
        List<Users> user = em.createNamedQuery("Users.findByEmail")
                .setParameter("email", email)
                .getResultList();
        
        List<Temporarypassword> temporarypassword = em.createNamedQuery("Temporarypassword.findByTempass")
                .setParameter("tempass", tempass)
                .getResultList();
        
        if(!user.isEmpty() && !temporarypassword.isEmpty())
        {
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            if(temporarypassword.get(0).getEmail().equals(email) && temporarypassword.get(0).getExpiredat().after(currentTimestamp))
            {
                return true;
            }
        }
        
        return false;
    }

    @Override
    public Result<UserDto> CheckUserAccountStatus(String userid) {
        try
        {
            Users user = em.find(Users.class, userid);
            if(user != null)
            {
                String message = "";
                boolean status = false;
                if (user.getAccountstatus().equals(ACCOUNT_ACTIVE.name())) {
                    status = true;
                    message = ACCOUNT_ACTIVE.getMessage();
                } else if (user.getAccountstatus().equals(ACCOUNT_PENDING.name())) {
                    message = ACCOUNT_PENDING.getMessage();
                } else if (user.getAccountstatus().equals(ACCOUNT_DELETED.name())) {
                    message = ACCOUNT_DELETED.getMessage();
                } else if (user.getAccountstatus().equals(ACCOUNT_SUSPENDED.name())) {
                    message = ACCOUNT_SUSPENDED.getMessage();
                } else if (user.getAccountstatus().equals(ACCOUNT_LOCKED.name())) {
                    message = ACCOUNT_LOCKED.getMessage();
                }
                
                return new Result<>(Mapper.fromUserToUserDto(user), CUSTOMER.name(), status, message);
            }
            else
                return new Result<>(null, CUSTOMER.name(), false, ACCOUNT_NOT_FOUND.getMessage());
        }
        catch(Exception ex)
        {
            System.out.println("User check status fail : " + ex.getMessage());
        }
        return new Result<>(null, CUSTOMER.name(), false, ERROR.getMessage());
    }

}
    
