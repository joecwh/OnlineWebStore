/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository;

import dto.UserDto;
import java.util.List;
import model.Result;
import model.Role;
import model.Roles;

/**
 *
 * @author Lenovo
 */
public interface IRoleRepository 
{
    public List<Roles> GetRoles();
    public Roles GetRolesById(String rolesid);
    public String GetIdByRoleName(String rolename);
    public boolean AddRole(String rolename);
    
    public List<Role> GetRole();
    public List<Role> GetUsersByRolesId(String rolesid);
    public Role GetUserRole(String userid);
    
    //configure role / roles
    public boolean CheckAllRoles();
    public boolean AddAllRoles();
    public Result<UserDto> GetManager();
    public Result<UserDto> AddManager();
}
