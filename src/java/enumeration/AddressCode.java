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
public enum AddressCode 
{
    ADDRESS_FOUND("Address has been found"),
    ADDRESS_NOT_FOUND("Address has not found"),
    ADDRESS_ID_INVALID("Address ID is invalid"),
    ADDRESS_USERID_INVALID("User ID is invalid"),
    ADDRESS_ADD_SUCCESS("Address has been added"),
    ADDRESS_ADD_FAILED("Address has failed to add"),
    ADDRESS_UPDATE_SUCCESS("Address has been updated"),
    ADDRESS_UPDATE_FAILED("Address has failed to update"),
    ADDRESS_DELETE_SUCCESS("Address has been deleted"),
    ADDRESS_DELETE_FAILED("Address has failed to delete");
    
    private String message;
    
    private AddressCode(String message)
    {
        this.message = message;
    }
    
    public String getMessage()
    {
        return message;
    }
}
