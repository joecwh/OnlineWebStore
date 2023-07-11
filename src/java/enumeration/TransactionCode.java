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
public enum TransactionCode 
{
    TRANSACTION_SUCCESS("Transaction has been success."),
    TRANSACTION_FAIL("Transaction has been failed.");
    
    private String message;
    
    private TransactionCode(String message)
    {
        this.message = message;
    }
    
    public String getMessage()
    {
        return message;
    }

}
