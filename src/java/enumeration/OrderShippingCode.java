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
public enum OrderShippingCode 
{
    ORDER_STATUS_UPDATE("Order status has been updated."),
    ORDER_STATUS_UPDATE_FAIL("Order status has failed to update."),
    
    SHIPPING_STATUS_UPDATE("Shipping status has been updated."),
    SHIPPING_STATUS_UPDATE_FAIL("Shipping status has failed to update.");

    
    private String message;
    
    private OrderShippingCode(String message)
    {
        this.message = message;
    }
    
    public String getMessage()
    {
        return message;
    }
}
