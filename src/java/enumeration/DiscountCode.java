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
public enum DiscountCode 
{
    DISCOUNT_ADD_SUCCESS("Discount has been added"),
    DISCOUNT_ADD_FAIL("Discount has failed to add. Make sure discount code is different"),
    
    DISCOUNT_FOUND("Discount has found"),
    DISCOUNT_NOT_FOUND("Discount not found"),
    
    DISCOUNT_UPDATE_SUCCESS("Discount has been updated"),
    DISCOUNT_UPDATE_FAIL("Discount has failed to update. Make sure discount code is different"),
    
    DISCOUNT_DELETE_SUCCESS("Discount has been deleted"),
    DISCOUNT_DELETE_FAIL("Discount has failed to delete"),
    
    DISCOUNT_CODE_APPLY_SUCCESS("Discount code has been applied"),
    DISCOUNT_CODE_APPLY_FAIL("Discount code has failed to apply"),
    DISCOUNT_CODE_NOT_FOUND("Discount code not found");
    
    private String message;
    
    private DiscountCode(String message)
    {
        this.message = message;
    }
    
    public String getMessage()
    {
        return message;
    }
}
