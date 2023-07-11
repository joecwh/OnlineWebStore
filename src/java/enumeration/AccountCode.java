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
public enum AccountCode 
{
    SUCCESS("Success"),
    FAILED("Failed"),
    
    ACCOUNT_ACTIVE("Your account is active."),
    ACCOUNT_PENDING("Please verify your account in the email."),
    ACCOUNT_DELETED("Your account has been deleted."),
    ACCOUNT_SUSPENDED("Your account has been suspended. Contact with administrator for further assistance."),
    ACCOUNT_LOCKED("Your account has been locked. Contact with administrator for further assistance."),
    ACCOUNT_NOT_FOUND("User account is not found."),
    TOKEN_EXPIRED("Verify token is expired. Please request again and verify in 15 minutes."),
    ERROR("Something went wrong. Please try again later.");
    
    private String message;

    private AccountCode(String message) 
    {
        this.message = message;
    }

    public String getMessage() 
    {
        return message;
    }
}
