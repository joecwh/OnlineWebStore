/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Mapper.Mapper;
import Repository.IRoleRepository;
import dto.UserDto;
import static enumeration.AccountCode.ACCOUNT_ACTIVE;
import static enumeration.UserCode.*;
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
public class RoleService implements IRoleRepository
{
    @PersistenceContext EntityManager em;
    @Resource UserTransaction utx;
    
    public RoleService(
        UserTransaction utx,
        EntityManager em)
    {
        this.utx = utx;
        this.em = em;
    }
    
    @Override
    public List<Roles> GetRoles() {
        return em.createNamedQuery("Roles.findAll").getResultList();
    }

    @Override
    public Roles GetRolesById(String rolesid) {
        List<Roles> roles = em.createNamedQuery("Roles.findByRolesid").setParameter("rolesid", rolesid).getResultList();
        return roles.get(0);
    }

    @Override
    public List<Role> GetRole() {
        return em.createNamedQuery("Role.findAll").getResultList();
    }

    @Override
    public Role GetUserRole(String userid) {
        List<Role> userRoles = em.createNamedQuery("Role.findByUserid").setParameter("userid", userid).getResultList();
        return userRoles.get(0);
    }

    @Override
    public String GetIdByRoleName(String rolename) {
        try
        {
            List<Roles> role  = (List<Roles>) em.createNamedQuery("Roles.findByRolename").setParameter("rolename", rolename).getResultList();
            return role.get(0).getRolesid();
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Role> GetUsersByRolesId(String rolesid) {
        return em.createNamedQuery("Role.findByRolesid").getResultList();
    }

    @Override
    public boolean AddRole(String rolename) {
        try
        {
            Roles newrole = new Roles(rolename);
            utx.begin();
            em.persist(newrole);
            utx.commit();
            
            return true;
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean CheckAllRoles() {
        try
        {
            List<Roles> roles = GetRoles();
            if(roles.size() != 3)
            {
                utx.begin();
                if(!roles.isEmpty())
                {
                    for(Roles r : roles)
                    {
                        Roles rolesRemover = em.merge(r);
                        em.remove(rolesRemover);
                    }
                }
                utx.commit();
                
                if(AddAllRoles())
                    if(AddManager().getStatus())
                        return true;
            }
            else
                return true;
        }
        catch(Exception ex)
        {
            System.out.println("check roles failed : " + ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean AddAllRoles() {
        try
        {
            Roles customer = new Roles(CUSTOMER.name());
            Roles admin = new Roles(ADMIN.name());
            Roles manager = new Roles(MANAGER.name());

            utx.begin();
            em.persist(customer);
            em.persist(admin);
            em.persist(manager);
            utx.commit();
            
            return true;
        }
        catch(Exception ex)
        {
            System.out.println("add all roles failed : " + ex.getMessage());
        }
        return false;
    }
    
    @Override
    public Result<UserDto> GetManager() {
        try
        {
            Roles rolesid = em.createNamedQuery("Roles.findByRolename", Roles.class)
                    .setParameter("rolename", MANAGER.name())
                    .getSingleResult();
            
            Role managerRole = em.find(Role.class, rolesid.getRolesid());
            Users manager = em.find(Users.class, managerRole.getUserid());
            if(manager != null)
            {
                return new Result<>(Mapper.fromUserToUserDto(manager), MANAGER.name(), true, USER_FOUND.getMessage());
            }
            else
            {
                Result addManager = AddManager();
                if(addManager.getStatus())
                {
                    return addManager;
                }
            }
        }
        catch(Exception ex)
        {
            System.out.println("get manager failed : " + ex.getMessage());
        }
        return new Result<>(null, null, false, USER_NOT_FOUND.getMessage());
    }
    
    @Override
    public Result<UserDto> AddManager() {
        try
        {
            Users manager = new Users(MANAGER.name(), MANAGER.name(), MANAGER.getMessage());
            manager.setPassword(MANAGER.name());
            manager.setAccountstatus(ACCOUNT_ACTIVE.name());
            
            String managerId = GetIdByRoleName(MANAGER.name());
            Role managerRole = new Role(managerId, manager.getUserid());
            
            utx.begin();
            em.persist(manager);
            em.persist(managerRole);
            utx.commit();
            
            return new Result<>(Mapper.fromUserToUserDto(manager), MANAGER.name(), true, USER_REGISTER_SUCCESS.getMessage());
        }
        catch(Exception ex)
        {
            System.out.println("add manager failed : " + ex.getMessage());
        }
        return new Result<>(null, null, false, USER_REGISTER_FAILED.getMessage());
    }
}
