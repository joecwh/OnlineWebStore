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
public interface IUserRepository 
{
    public Result<UserDto> GetAllUser();
    public Result<UserDto> GetCustomers();
    public Result<UserDto> CustomerRegister(Users user);
    public Result<UserDto> GetCustomerById(String userid);
    public Result<UserDto> UpdateCustomer(Users user);
    public Users GetUserByEmail(String email);
    public Verification GetVerificationByToken(String token);
    public Temporarypassword CheckTemporaryPasswordExistByEmail(String email);
    public boolean AddTemporaryPassword(Temporarypassword tempass);
    public boolean VerifyUser(String token);
    public boolean AddVerification(Verification verification);
    public boolean ChangePassword(String userid, String oldpassword, String newpassword);
    public boolean ForgetPassword(String email);
    public boolean UserExistsByUsername(String username);
    public boolean UserExistsByEmail(String email);
    public void VisitorCount();
    public int GetDailyVisitorCount(int day);
    public List<Integer> GetMonthlyVisitorCount();
}
