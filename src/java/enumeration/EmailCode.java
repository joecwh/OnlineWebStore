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
public enum EmailCode 
{
    COMPANY_EMAIL("YOUR_GMAIL"), //Enter your gmail email, eg: smtp@gmail.com
    COMPANY_SMTP_PASSWORD("APP_PASSWORD"), // Enter your app password

    VERIFY_EMAIL_SUBJECT("Account Verification"),
    VERIFY_EMAIL_SENT("Verify email has been sent. Please check your inbox."),
    VERIFY_EMAIL_SENT_FAIL("Verify email has failed to sent, please try again later."),
    
    SUBSCRIBE_EMAIL_SUBJECT("Thank You for Subscribing"),
    SUBSCRIBE_EMAIL_SENT("Thank you for subscribe. We will send our latest news to you."),
    SUBSCRIBE_EMAIL_EXIST("You have already subscribed to our news."),
    
    FEEDBACK_EMAIL_SUBJECT("Thank you for your feedback"),
    FEEDBACK_EMAIL_SENT("Thank you for your feedback. We will response to you soon."),

    RESET_PASSWORD_EMAIL_SENT("Temporary password has been sent to your email"),
    
    PURCHASE_EMAIL_SUBJECT("Thank you for your purchase."),
    PURCHASE_EMAIL_SENT("Thank you for your purchase. We will send email to you soon."),
    
    EMAIL_NOT_FOUND("Email address is not found. Please enter a valid email or register an account."),
    ERROR("Something went wrong. Please try again later.");
    
    private String message;
    
    private EmailCode(String message)
    {
        this.message = message;
    }
    
    public String getMessage()
    {
        return message;
    }
}
