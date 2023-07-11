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
public enum OrderCode 
{
    COMPLETED("Order has been completed."),
    INCOMPLETE("Order has been failed");
    
    private String message;
    
    private OrderCode(String message)
    {
        this.message = message;
    }
    
    public String getMessage()
    {
        return message;
    }
}
