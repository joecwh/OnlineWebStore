/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository;

import dto.*;
import model.Result;

/**
 *
 * @author Lenovo
 */
public interface IAuthRepository 
{
    public Result<UserDto> UserLogin(String email, String password);
    public boolean LoginWithTempass(String email, String tempass);
    public Result<UserDto> CheckUserAccountStatus(String userid);
}
