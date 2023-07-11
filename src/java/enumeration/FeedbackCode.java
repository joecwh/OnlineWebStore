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
public enum FeedbackCode 
{
    PENDING("Feedback is pending to be process."),
    REPLIED("Feedback has been replied."),
    RESOLVED("Feedback has been resolved.");

    private String message;
    
    private FeedbackCode(String message)
    {
        this.message = message;
    }
    
    public String getMessage()
    {
        return message;
    }
}
