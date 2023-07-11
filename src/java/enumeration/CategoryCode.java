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
public enum CategoryCode 
{
    CATEGORIES_FOUND("Categories has been found"),
    CATEGORIES_NOT_FOUND("Categories not found"),
    CATEGORIES_ID_INVALID("Categories ID is invalid"),
    CATEGORIES_ADD_SUCCESS("Categories has been added"),
    CATEGORIES_ADD_FAILED("Categories has failed to add"),
    CATEGORIES_UPDATE_SUCCESS("Categories has been updated"),
    CATEGORIES_UPDATE_FAILED("Categories has failed to update"),
    CATEGORIES_DELETE_SUCCESS("Categories has been deleted"),
    CATEGORIES_DELETE_FAILED("Categories has failed to delete"),

    CATEGORY_FOUND("Category has been found"),
    CATEGORY_NOT_FOUND("Category not found"),
    CATEGORY_ID_INVALID("Category ID is invalid"),
    CATEGORY_ADD_SUCCESS("Category has been added"),
    CATEGORY_ADD_FAILED("Category has failed to add"),
    CATEGORY_UPDATE_SUCCESS("Category has been updated"),
    CATEGORY_UPDATE_FAILED("Category has failed to update"),
    CATEGORY_DELETE_SUCCESS("Category has been deleted"),
    CATEGORY_DELETE_FAILED("Category has failed to delete");
    
    private String message;
    
    private CategoryCode(String message)
    {
        this.message = message;
    }
    
    public String getMessage()
    {
        return message;
    }
}
