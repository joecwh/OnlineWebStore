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
public enum ReportCode 
{
    REPORT_FOUND("Report has found"),
    REPORT_NOT_FOUND("Report not found"),
    REPORT_ID_INVALID("Report ID is invalid"),
    REPORT_GENERATE_SUCCESS("New report has been generated"),
    REPORT_GENERATE_FAILED("Report has failed to generate"),
    REPORT_DELETE_SUCCESS("Report has been deleted"),
    REPORT_DELETE_FAILED("Report has failed to delete");
    
    private String message;
    
    private ReportCode(String message)
    {
        this.message = message;
    }
    
    public String getMessage()
    {
        return message;
    }
}
