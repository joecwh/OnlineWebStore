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
public enum ShippingCode 
{
    PENDING("Package is pending to process."),
    PACKAGING("Package is pending to be delivery."),
    SHIPPING("Package has already been shipped."),
    RECEIVED("Package has been successfully delivered."),
    MISSING("Package is missing.");
    
    private String message;
    
    private ShippingCode(String message)
    {
        this.message = message;
    }
    
    public String getMessage()
    {
        return message;
    }
}
