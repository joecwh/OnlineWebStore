/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Mapper.Mapper;
import Repository.IUserRepository;
import dto.UserDto;
import static enumeration.AccountCode.*;
import static enumeration.UserCode.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import model.Result;
import model.Role;
import model.Roles;
import model.Temporarypassword;
import model.Users;
import model.Verification;
import model.Visitorcount;

/**
 *
 * @author Lenovo
 */


public class UserService implements IUserRepository
{
    @PersistenceContext EntityManager em;
    @Resource UserTransaction utx;
    private Mapper _mapper;
    
    public UserService(
            UserTransaction utx, 
            EntityManager em,
            Mapper mapper)
    {
        this.utx = utx;
        this.em = em;
        this._mapper = mapper;
    }

    @Override
    public Result<UserDto> CustomerRegister(Users user) 
    {
        if(UserExistsByUsername(user.getUsername()))
            return new Result<>(null, CUSTOMER.name(), false, USER_USERNAME_ALREADY_EXISTS.getMessage());
        
        if(UserExistsByEmail(user.getEmail()))
            return new Result<>(null, CUSTOMER.name(), false, USER_EMAIL_ALREADY_EXISTS.getMessage());

        List<Roles> roles = em.createNamedQuery("Roles.findByRolename").setParameter("rolename", CUSTOMER.name()).getResultList();
        if(roles.isEmpty())
            return new Result<>(null, CUSTOMER.name(), false, "Role not found");

        Role userRole = new Role(roles.get(0).getRolesid(), user.getUserid());
        try
        {
            utx.begin();
            em.persist(user);
            em.persist(userRole);
            utx.commit();

            return new Result<>(_mapper.fromUserToUserDto(user), CUSTOMER.name(), true, USER_REGISTER_SUCCESS.getMessage());
        }
        catch(Exception ex)
        {
            return new Result<>(_mapper.fromUserToUserDto(user), CUSTOMER.name(), false, USER_REGISTER_FAILED.getMessage() + "\nDetails message: " + ex.getMessage());
        }
    }

    @Override
    public Result<UserDto> GetCustomerById(String userid) 
    {
        List<Users> users = em.createNamedQuery("Users.findByUserid").setParameter("userid", userid).getResultList();
        Users user = new Users();
        if(!users.isEmpty())
        {
            user = users.get(0);
            return new Result<>(_mapper.fromUserToUserDto(user), CUSTOMER.name(), true, USER_FOUND.getMessage());
        }
        
        return new Result<>(null, CUSTOMER.name(), false, USER_ID_INVALID.getMessage());
    }

    @Override
    public Result<UserDto> UpdateCustomer(Users user) {
        if(UserExistsByUsername(user.getUsername()))
            return new Result<>(_mapper.fromUserToUserDto(user), CUSTOMER.name(), false, USER_USERNAME_ALREADY_EXISTS.getMessage());
        
        if(UserExistsByEmail(user.getEmail()))
            return new Result<>(_mapper.fromUserToUserDto(user), CUSTOMER.name(), false, USER_EMAIL_ALREADY_EXISTS.getMessage());

        try
        {
            utx.begin();
            em.merge(user);
            utx.commit();
        }
        catch(Exception ex)
        {
            return new Result<>(_mapper.fromUserToUserDto(user), CUSTOMER.name(), false, USER_UPDATE_FAILED.getMessage() + "\nDetails message: " + ex.getMessage());
        }
        return new Result<>(_mapper.fromUserToUserDto(user), CUSTOMER.name(), true, USER_UPDATE_SUCCESS.getMessage());
    }

    @Override
    public boolean UserExistsByUsername(String username) {
        try
        {
            List<Users> users = (List<Users>) em.createNamedQuery("Users.findByUsername").setParameter("username", username).getResultList();
            if(!users.isEmpty())
                return true;
        }
        catch(Exception ex)
        {
            System.out.println("error occur on checking username existence" + ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean UserExistsByEmail(String email) {
        try
        {
            List<Users> users = (List<Users>) em.createNamedQuery("Users.findByEmail").setParameter("email", email).getResultList();
            if(!users.isEmpty())
                return true;
        }
        catch(Exception ex)
        {
            System.out.println("error occur on checking email existence" + ex.getMessage());
        }
        return false;
    }

    @Override
    public Result<UserDto> GetCustomers() {
        try
        {
            List<Roles> roles = em.createNamedQuery("Roles.findByRolename").setParameter("rolename", CUSTOMER.name()).getResultList();
            if(roles.isEmpty())
                return new Result<>(new ArrayList(), CUSTOMER.name(), false, "Role not found");

            List<Role> userRoleList = em.createNamedQuery("Role.findByRolesid").setParameter("rolesid", roles.get(0).getRolesid()).getResultList();
            if(userRoleList.isEmpty())
                return new Result<>(new ArrayList(), CUSTOMER.name(), false, USER_NOT_FOUND.getMessage());

            String userids = "";
            for(Role u : userRoleList)
            {
                userids += "'" + u.getUserid()+ "',";
            }
            userids = userids.substring(0, userids.length() - 1);

            List<Users> userList = em.createQuery("SELECT u FROM Users u WHERE u.userid IN (" + userids + ")").getResultList();

            return new Result<>(_mapper.fromUserListToUserDtoList(userList), CUSTOMER.name(), true, USER_FOUND.getMessage());
        }
        catch(Exception ex)
        {
            System.out.println("no customer found");
        }
        return new Result<>(new ArrayList(), null, true, USER_NOT_FOUND.getMessage());
    }

    @Override
    public Result<UserDto> GetAllUser() {
        try
        {
            List<Users> users = (List<Users>) em.createNamedQuery("Users.findAll").getResultList();
            if(users.isEmpty())
                return new Result<>(null, null, false, USER_NOT_FOUND.getMessage());

            List<Roles> roles = em.createNamedQuery("Roles.findByRolename").setParameter("rolename", CUSTOMER.name()).getResultList();
            List<Role> customerList = em.createNamedQuery("Role.findByRolesid").setParameter("rolesid", roles.get(0).getRolesid()).getResultList();

            String cid = "";
            List<UserDto> custLists = new ArrayList();
            if(!customerList.isEmpty())
            {
                for(Role r : customerList)
                {
                    cid += "'" + r.getUserid() + "',";
                }
                cid = cid.substring(0, cid.length() - 1);
                custLists = _mapper.fromUserListToUserDtoList(em.createQuery("SELECT u FROM Users u WHERE u.userid IN (" + cid + ")").getResultList());
                for (UserDto u : custLists) 
                {
                    u.setRolename(CUSTOMER.name());
                }
            }

            List<Roles> adminrole = em.createNamedQuery("Roles.findByRolename").setParameter("rolename", ADMIN.name()).getResultList();
            List<Role> adminList = em.createNamedQuery("Role.findByRolesid").setParameter("rolesid", adminrole.get(0).getRolesid()).getResultList();
            String aid = "";
            List<UserDto> adminLists = new ArrayList();
            if(!adminList.isEmpty())
            {
                for(Role r : adminList)
                {
                    aid += "'" + r.getUserid() + "',";
                }
                aid = aid.substring(0, aid.length() - 1);
                adminLists = _mapper.fromUserListToUserDtoList(em.createQuery("SELECT u FROM Users u WHERE u.userid IN (" + aid + ")").getResultList());
                for (UserDto u : adminLists) 
                {
                    u.setRolename(ADMIN.name());
                }
            }

            List<UserDto> allusers = new ArrayList();
            if(!custLists.isEmpty())
                allusers.addAll(custLists);
            if(!adminLists.isEmpty())
                allusers.addAll(adminLists);

            return new Result<>(allusers, ADMIN.name(), true, USER_FOUND.getMessage());
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return new Result<>(null, null, false, USER_NOT_FOUND.getMessage());
    }

    @Override
    public boolean ChangePassword(String userid, String oldpassword, String newpassword) {
        try
        {
            Users user = em.find(Users.class, userid);
            if(user != null)
            {
                if(user.getPassword().equals(oldpassword))
                {
                    LocalDateTime currentDateTime = LocalDateTime.now();
                    Timestamp currentTimestamp = Timestamp.valueOf(currentDateTime);
                    user.setPassword(newpassword);
                    user.setModifiedtime(currentTimestamp);
                    user.setModifiedby(userid);
                    
                    utx.begin();
                    em.merge(user);
                    utx.commit();

                    return true;
                }
                else
                    return false;
            }
            else
                return false;
        }catch(Exception ex)
        {
            System.out.println("change password fail : " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean ForgetPassword(String email) {
        if(UserExistsByEmail(email))
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean VerifyUser(String token) 
    {
        try
        {
            Verification userToken = em.find(Verification.class, token);

            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            if(userToken != null && (currentTimestamp.before(userToken.getExpiredat())))
            {
                Users user = em.find(Users.class, userToken.getUserid());
                user.setAccountstatus(ACCOUNT_ACTIVE.name());
                utx.begin();
                em.merge(user);
                utx.commit();
                
                return true;
            }
        }
        catch(Exception ex)
        {
            System.out.println("verify user failed : " + ex.getMessage());
        }
        return false;
    }

    @Override
    public Verification GetVerificationByToken(String token) {
        Verification verification = em.find(Verification.class, token);
        return verification == null ? null : verification;
    }

    @Override
    public boolean AddVerification(Verification verification) {
        try
        {
            utx.begin();
            em.merge(verification);
            utx.commit();
            
            return true;
        }
        catch(Exception ex)
        {
            System.out.println("add verification fail : " + ex.getMessage());
        }
        return false;
    }

    

    @Override
    public Users GetUserByEmail(String email) {
        List<Users> user = em.createNamedQuery("Users.findByEmail")
                .setParameter("email", email)
                .getResultList();
        
        return user.isEmpty() ? null : user.get(0);
    }

    @Override
    public boolean AddTemporaryPassword(Temporarypassword tempass) {
        try
        {
            if(CheckTemporaryPasswordExistByEmail(tempass.getEmail()) == null && GetUserByEmail(tempass.getEmail()) != null)
            {
                utx.begin();
                em.persist(tempass);
                utx.commit();

                return true;
            }
        }
        catch(Exception ex)
        {
            System.out.println("add feedback  fail : " + ex.getMessage());
        }
        return false;
    }

    @Override
    public Temporarypassword CheckTemporaryPasswordExistByEmail(String email) {
        List<Temporarypassword> tempass = em.createNamedQuery("Temporarypassword.findByEmail")
                .setParameter("email", email)
                .getResultList();
        
        if(!tempass.isEmpty())
        {
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            java.util.Date expiredDate = tempass.get(tempass.size() - 1).getExpiredat();
            Timestamp expiredat = new Timestamp(expiredDate.getTime());
            if(currentTimestamp.before(expiredDate))
            {            
                return tempass.get(tempass.size() - 1);
            }
        }
        
        return null;
    }

    @Override
    public void VisitorCount() {
        try
        {
            Visitorcount c = new Visitorcount();
            utx.begin();
            em.persist(c);
            utx.commit();
        }
        catch(Exception ex)
        {
            System.out.println("add visitor count  fail : " + ex.getMessage());
        }
    }

    @Override
    public int GetDailyVisitorCount(int day) {
        List<Visitorcount> visitorCount = em.createQuery("SELECT v FROM Visitorcount v WHERE EXTRACT(DAY FROM v.time) = :day AND EXTRACT(YEAR FROM v.time) = EXTRACT(YEAR FROM CURRENT_TIMESTAMP)")
                .setParameter("day", day)
                .getResultList();
        return visitorCount.isEmpty() ? 0 : visitorCount.size();
    }

    @Override
    public List<Integer> GetMonthlyVisitorCount() {
        List<Integer> visitorCount = new ArrayList();
        for(int i = 1; i < 32; i++)
        {
            visitorCount.add(GetDailyVisitorCount(i));
        }
        return visitorCount;
    }
}
