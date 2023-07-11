/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.UUID;

/**
 *
 * @author Lenovo
 */
public class Email 
{
    private String subject;
    private String message;
    private String recipient;
    
    public Email(){}
    
    public Email(String subject, String message, String recipient)
    {
        this.subject = subject;
        this.message = message;
        this.recipient = recipient;
    }
    
    public void setSubject(String subject)
    {
        this.subject = subject;
    }
    
    public String getSubject()
    {
        return subject;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public void setRecipient(String recipient)
    {
        this.recipient = recipient;
    }
    
    public String getRecipient()
    {
        return recipient;
    }
}
