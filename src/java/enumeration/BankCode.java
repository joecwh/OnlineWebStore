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
public enum BankCode
{
    CASH_ON_DELIVERY("CASH"),
    ONLINE_BANKING(""),
    CREDIT_OR_DEBIT_CARD("CARD"),
    
    BANK_ID_NOT_FOUND("Bank ID is invalid."),
    BANK_NOT_FOUND("Bank is not found"),
    BANK_ADD_SUCCESS("Bank has been added."),
    BANK_ADD_FAIL("Bank has failed to add."),
    BANK_DELETE_SUCCESS("Bank has been deleted."),
    BANK_DELETE_FAIL("Bank has failed to delete.");
    
    private String message;
    
    private BankCode(String message)
    {
        this.message = message;
    }
    
    public String getMessage()
    {
        return message;
    }
}
