/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Mapper.Mapper;
import Repository.*;
import dto.UserDto;
import static enumeration.AccountCode.*;
import static enumeration.UserCode.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import model.Result;
import model.Role;
import model.Roles;
import model.Users;

/**
 *
 * @author Lenovo
 */

public class AdminService implements IAdminRepository
{
    @PersistenceContext EntityManager em;
    @Resource UserTransaction utx;
    private final Mapper _mapper;
    
    public AdminService(
            UserTransaction utx, 
            EntityManager em,
            Mapper mapper)
    {
        this.em = em;
        this.utx = utx;
        this._mapper = mapper;
    }

    @Override
    public Result<UserDto> AddStaff(Users user) 
    {
        List<Users> unameExists = em.createNamedQuery("Users.findByUsername").setParameter("username", user.getUsername()).getResultList();
        List<Users> emailExists = em.createNamedQuery("Users.findByEmail").setParameter("email", user.getEmail()).getResultList();
        List<Roles> rolesExists = em.createNamedQuery("Roles.findByRolename").setParameter("rolename", ADMIN.name()).getResultList();

        if(!unameExists.isEmpty())
            return new Result<>(null, ADMIN.name(), false, USER_USERNAME_ALREADY_EXISTS.getMessage());
        
        if(!emailExists.isEmpty())
            return new Result<>(null, ADMIN.name(), false, USER_EMAIL_ALREADY_EXISTS.getMessage());
        
        if(rolesExists.isEmpty())
            return new Result<>(null, ADMIN.name(), false, "Role not found");

        Role userRole = new Role(rolesExists.get(0).getRolesid(), user.getUserid());

        try
        {
            utx.begin();
            em.persist(user);
            em.flush();
            em.persist(userRole);
            utx.commit();

            return new Result<>(null, ADMIN.name(), true, USER_REGISTER_SUCCESS.getMessage());
        }
        catch(Exception ex)
        {
            return new Result<>(null, ADMIN.name(), false, USER_REGISTER_FAILED.getMessage() + "\nDetails message: " + ex.getMessage());
        }
    }

    @Override
    public Result<UserDto> GetStaffs() {
        List<Roles> role = em.createNamedQuery("Roles.findByRolename").setParameter("rolename", ADMIN.name()).getResultList();
        if(role.isEmpty())
            return new Result<>(null, ADMIN.name(), false, "Role not found");

        List<Role> userRoleList = em.createNamedQuery("Role.findByRolesid").setParameter("rolesid", role.get(0).getRolesid()).getResultList();
        if(userRoleList.isEmpty())
            return new Result<>(null, ADMIN.name(), false, USER_NOT_FOUND.getMessage());

        String userids = "";
        for(Role u : userRoleList)
        {
            userids += "'" + u.getUserid()+ "',";
        }
        userids = userids.substring(0, userids.length() - 1);
        List<Users> userList = em.createQuery("SELECT u FROM Users u WHERE u.userid IN (" + userids + ")").getResultList();
        return new Result<>(_mapper.fromUserListToUserDtoList(userList), ADMIN.name(), true, USER_FOUND.getMessage());
    }

    @Override
    public Result<Users> GetStaff(String id) {
        List<Users> user = em.createNamedQuery("Users.findByUserid").setParameter("userid", id).getResultList();
        if(user.isEmpty())
            return new Result<>(null, ADMIN.name(), false, USER_NOT_FOUND.getMessage());

        return new Result<>(user.get(0), ADMIN.name(), true, USER_FOUND.getMessage());
    }

    @Override
    public Result<UserDto> UpdateStaff(Users user) {
        try
        {
            Users staffExists = em.find(Users.class, user.getUserid());

            List<Users> unameExists = new ArrayList();
            List<Users> emailExists = new ArrayList();
            if(!staffExists.getUsername().equals(user.getUsername()))
                unameExists = em.createNamedQuery("Users.findByUsername").setParameter("username", user.getUsername()).getResultList();

            if(!staffExists.getEmail().equals(user.getEmail()))
                emailExists = em.createNamedQuery("Users.findByEmail").setParameter("email", user.getEmail()).getResultList();

            if(!unameExists.isEmpty())
                return new Result<>(null, ADMIN.name(), false, USER_USERNAME_ALREADY_EXISTS.getMessage());

            if(!emailExists.isEmpty())
                return new Result<>(null, ADMIN.name(), false, USER_EMAIL_ALREADY_EXISTS.getMessage());
        
            utx.begin();
            em.merge(user);
            utx.commit();

            return new Result<>(_mapper.fromUserToUserDto(user), ADMIN.name(), true, USER_UPDATE_SUCCESS.getMessage());
        }
        catch(Exception ex)
        {
            System.out.println("update user failed" + ex.getMessage());
            return new Result<>(null, ADMIN.name(), false, USER_UPDATE_FAILED.getMessage() + "\nDetails message: " + ex.getMessage());
        }
    }

    @Override
    public Result<UserDto> DeleteStaff(String id) {
        Users user = em.find(Users.class, id);
        if(user == null)
            return new Result<>(null, ADMIN.name(), false, USER_ID_INVALID.getMessage());
        
        try
        {
            utx.begin();
            Users staff = em.merge(user);
            em.remove(staff);
            utx.commit();
            
            return new Result<>(_mapper.fromUserToUserDto(user), ADMIN.name(), true, USER_DELETE_SUCCESS.getMessage());
        }
        catch(Exception ex)
        {
            return new Result<>(null, ADMIN.name(), false, USER_DELETE_FAILED.getMessage() + "\nDetails message: " + ex.getMessage());
        }
    }

    @Override
    public Result<UserDto> SuspendStaff(String id) {
        try
        {
            Users getStaff = GetStaff(id).getResult();
            if(getStaff == null)
                return new Result<>(null, null, false, USER_NOT_FOUND.getMessage());

            UserDto staff = _mapper.fromUserToUserDto(getStaff);
            staff.setAccountstatus(ACCOUNT_SUSPENDED.name());
            utx.begin();
            em.merge(staff);
            utx.commit();
            return new Result<>(staff, ADMIN.name(), true, USER_SUSPEND_SUCCESS.getMessage());
        }
        catch(Exception ex)
        {
            System.out.println("Suspend admin failed" + ex.getMessage());
        }
        return new Result<>(null, null, false, USER_SUSPEND_FAILED.getMessage());
    }
    
    @Override
    public String GetTotalWithSuffix(double total) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        total = Math.floor(total);
        String suffix = "";
        
        if(total >= 1000000000000.00)
        {
            total = total/1000000000000.00;
            suffix = "t";
        }
        else if(total >= 1000000000.00)
        {
            total = total/1000000000.00;
            suffix = "b";
        }
        else if(total >= 1000000.00)
        {
            total = total/1000000.00;
            suffix = "m";
        }
        else if(total >= 1000.00)
        {
            total = total/1000.00;
            suffix = "k";
        }
    
        return decimalFormat.format(total) + suffix;
    }
}
