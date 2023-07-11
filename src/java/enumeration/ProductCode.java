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
public enum ProductCode 
{
    //find product
    PRODUCT_FOUND("Product has been found"),
    PRODUCT_NOT_FOUND("Product is not found"),
    PRODUCT_ID_INVALID("Product ID is invalid"),
    
    //add product
    PRODUCT_ADD_SUCCESS("Product has been added"),
    PRODUCT_ADD_FAILED("Product has failed to add"),
    
    //update product
    PRODUCT_UPDATE_SUCCESS("Product has been updated"),
    PRODUCT_UPDATE_FAILED("Product has failed to update"),
    
    //delete product
    PRODUCT_DELETE_SUCCESS("Product has been deleted"),
    PRODUCT_DELETE_FAILED_CART_EXIST("Product has failed to delete due to customer cart having this product."),
    PRODUCT_DELETE_FAILED("Product has failed to delete");
    
    private String message;
    
    ProductCode(String message)
    {
        this.message = message;
    }
    
    public String getMessage()
    {
        return message;
    }
}
