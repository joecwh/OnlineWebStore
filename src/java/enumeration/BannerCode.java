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
public enum BannerCode 
{
    BANNER_FOUND("Banner is found"),
    BANNER_NOT_FOUND("Banner is not found"),
    BANNER_ID_INVALID("Banner ID is invalid"),
    BANNER_ADD_SUCCESS("Banner has been added"),
    BANNER_ADD_FAILED("Banner has failed to add"),
    BANNER_UPDATE_SUCCESS("Banner has been updated"),
    BANNER_UPDATE_FAILED("Banner has failed to update"),
    BANNER_DELETE_SUCCESS("Banner has been deleted"),
    BANNER_DELETE_FAILED("Banner has failed to delete");
    
    private String message;
    
    private BannerCode(String message)
    {
        this.message = message;
    }
    
    public String getMessage()
    {
        return message;
    }
}
