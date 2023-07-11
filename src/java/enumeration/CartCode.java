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
public enum CartCode 
{
    CART_FOUND("Cart has been found"),
    CART_NOT_FOUND("Cart not found"),
    CART_ID_INVALID("Cart ID is invalid"),
    CART_LIMIT_EXCEED("Cart can only store maximum 6 items"),
    ERROR("Something went wrong, please try again later."),
    LOGIN_TO_ADD_CART("You have to login to add product to the cart."),
    
    CART_ADD_SUCCESS("Cart has added successfully"),
    CART_ADD_FAIL("Cart has failed to add"),
    
    CART_UPDATE_SUCCESS("Cart has been updated"),
    CART_UPDATE_FAIL("Cart has failed to update"),
    
    CART_DELETE_SUCCESS("Cart has been deleted successfully"),
    CART_DELETE_FAIL("Cart has failed to delete"),
    
    CART_PLUS_SUCCESS("Cart has been added by 1"),
    CART_PLUS_FAIL("Cart failed to plus 1"),
    
    CART_MINUS_SUCCESS("Cart has been minus by 1"),
    CART_MINUS_FAIL("Cart failed to minus 1");
    
    private String message;
    
    private CartCode(String message)
    {
        this.message = message;
    }
    
    public String getMessage()
    {
        return message;
    }
}
