/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enumeration;

/**
 *
 * @author Lenovo
 */
public enum UserCode 
{
    //User type
    CUSTOMER("Customer credential"),
    ADMIN("Admin credential"),
    MANAGER("MANAGER"),
    ERROR("Something went wrong. Please try again later."),
    CHANGE_PASSWORD_SUCCESS("Password has been changed"),
    CHANGE_PASSWORD_FAIL("Password has failed to change"),
    PASSWORD_NOT_MATCH("New password and confirm password is not match"),
    CREDENTIAL_NOT_ALLOW("Your credential are not allow to access this page."),
    SESSION_EXPIRED("Session has been expired. Please login again."),
    
    //User login status & message
    USER_LOGIN_SUCCESS("Login success"),
    USER_LOGIN_FAILED("Username or password is invalid. Please try again"),
    USER_LOGIN_LOCKED("You have exceeded the login attempts. Please try again after 5 minutes"),
    
    //Check user existence
    USER_USERNAME_ALREADY_EXISTS("This username already exists. Please try again with another username"),
    USER_EMAIL_ALREADY_EXISTS("This email already exists. Please try again with another email"),
    USER_FOUND("User has been retrieved"),
    USER_NOT_FOUND("User not found"),
    USER_ID_INVALID("User ID is invalid"),
    
    //User CRUD
    USER_REGISTER_SUCCESS("Account has been created. Please verify account in your email within 15 minutes and try to login."),
    USER_REGISTER_FAILED("User account has failed to create. Please try again later."),
    USER_UPDATE_SUCCESS("User has been updated successfully"),
    USER_UPDATE_FAILED("User update has been failed"),
    USER_SUSPEND_SUCCESS("User has been suspended successfully"),
    USER_SUSPEND_FAILED("User has failed to suspend"),
    USER_DELETE_SUCCESS("User has been deleted"),
    USER_DELETE_FAILED("User has failed to delete");
    
    private String message;

    private UserCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
