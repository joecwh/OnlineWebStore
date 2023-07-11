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
public enum CardCode 
{
    CARD_FOUND("Card has been found."),
    CARD_NOT_FOUND("Card is not found."),
    CARD_ID_INVALID("Card ID is invalid."),
    CARD_NUMBER_EXIST("Card number has been existed. Try another number."),
    CARD_ADD_SUCCESS("New card has been added."),
    CARD_ADD_FAIL("Card has fail to add."),
    CARD_UPDATE_SUCCESS("Card has been updated."),
    CARD_UPDATE_FAIL("Card has fail to update."),
    CARD_DELETE_SUCCESS("Card has been deleted."),
    CARD_DELETE_FAIL("Card has fail to delete.");
    
    private String message;
    
    private CardCode(String message)
    {
        this.message = message;
    }
    
    public String getMessage()
    {
        return message;
    }
}
