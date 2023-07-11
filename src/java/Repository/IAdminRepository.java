/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository;

import dto.UserDto;
import java.util.List;
import model.*;

/**
 *
 * @author Lenovo
 */
public interface IAdminRepository 
{
    public Result<UserDto> AddStaff(Users user);
    public Result<Users> GetStaff(String id);
    public Result<UserDto> GetStaffs();
    public Result<UserDto> UpdateStaff(Users user);
    public Result<UserDto> SuspendStaff(String id);
    public Result<UserDto> DeleteStaff(String id);
    public String GetTotalWithSuffix(double total);

}
