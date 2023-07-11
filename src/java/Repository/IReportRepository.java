/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository;

import java.util.List;
import model.*;

/**
 *
 * @author Lenovo
 */
public interface IReportRepository 
{
    public Result<Report> GetReports();
    public Result<Report> GetReport(String reportid);
    public boolean GenerateProductDemandReport(String templatePath);
    public Result<Report> DeleteReport(String reportid);
    public List<Report> GetReportByName(String reportname);
//    public void generateProductDemandReport(String htmlTemplate, String outputPdfPath);
}
