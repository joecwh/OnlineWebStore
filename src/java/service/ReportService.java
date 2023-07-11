/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Repository.IReportRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;

import static enumeration.ReportCode.*;
import static enumeration.UserCode.MANAGER;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import model.Product;
import model.Report;
import model.Result;


/**
 *
 * @author Lenovo
 */
public class ReportService implements IReportRepository
{
    @PersistenceContext EntityManager em;
    @Resource UserTransaction utx;
    
    public ReportService(
        UserTransaction utx,
        EntityManager em)
    {
        this.utx = utx;
        this.em = em;
    }
    
    @Override
    public Result<Report> GetReports() {
        List<Report> reports = (List<Report>) em.createNamedQuery("Report.findAll").getResultList();
        if(reports.isEmpty())
            return new Result<>(null, MANAGER.name(), false, REPORT_NOT_FOUND.getMessage());
        
        return new Result<>(reports, MANAGER.name(), true, REPORT_FOUND.getMessage());
    }

    @Override
    public Result<Report> GetReport(String reportid) {
        Report report = em.find(Report.class, reportid);
        if(report == null)
            return new Result<>(null, MANAGER.name(), false, REPORT_NOT_FOUND.getMessage());

        return new Result<>(report, MANAGER.name(), true, REPORT_FOUND.getMessage());
    }

    @Override
    public Result<Report> DeleteReport(String reportid) {
        Result<Report> report = GetReport(reportid);
        if(!report.getStatus())
            return new Result<>(null, MANAGER.name(), false, report.getMessage());

        try
        {
            utx.begin();
            Report removeReport = em.merge(report.getResult());
            em.remove(removeReport);
            utx.commit();
            
            return new Result<>(report.getResult(), MANAGER.name(), true, REPORT_DELETE_SUCCESS.getMessage());
        }
        catch(Exception ex)
        {
            return new Result<>(null, MANAGER.name(), false, REPORT_DELETE_FAILED.getMessage());
        }
    }
    
    @Override
    public boolean GenerateProductDemandReport(String templatePath) 
    {
        try
        {
            // Read the HTML template
            String reportTemplate = templatePath + "productDemandReport_template.html";
            String templateContent = readFileContent(reportTemplate);

            // Create the output HTML file
            LocalDate currentDate = LocalDate.now();
            String reportname = "DemandProductReport" + currentDate;
            List<Report> checkReportNameExists = GetReportByName(reportname);
            if(!checkReportNameExists.isEmpty())
                reportname += "(" + checkReportNameExists.size() + ")" + ".html";
            else
                reportname += ".html";
            String outputFilePath = templatePath + reportname;

            // Replace placeholders with data
            String year = String.valueOf(LocalDate.now().getYear());

            String outputContent = templateContent
                    .replace("{{currentYear}}", year)
                    .replace("{{homelogo}}", templatePath + "homelogo.png");

            //top 5 products
            List<Object[]> results = em.createQuery(
                "SELECT c.productid, SUM(c.quantity) "
                + "FROM Cart c "
                + "WHERE c.ispaid = true "
                + "GROUP BY c.productid "
                + "HAVING c.productid IS NOT NULL AND SUM(c.quantity) IS NOT NULL "
                + "ORDER BY SUM(c.quantity) DESC", Object[].class)
                .setMaxResults(5)
                .getResultList();
            
            List<Product> product = new ArrayList();
            for(Object[] o : results)
            {
                int i = 0;
                product.add(em.find(Product.class, o[i]));
                i++;
            }

            if (results.isEmpty()) 
            {
                for (int i = 0; i < 5; i++) 
                {
                    String topProductPlaceholder = "{{topProduct" + (i + 1) + "}}";
                    String topQuantityPlaceholder = "{{topQuantity" + (i + 1) + "}}";
                    outputContent = outputContent
                            .replace(topProductPlaceholder, "-")
                            .replace(topQuantityPlaceholder, "0");
                }
            } 
            else 
            {
                for (int i = 0; i < 5; i++) 
                {
                    String topProductPlaceholder = "{{topProduct" + (i + 1) + "}}";
                    String topQuantityPlaceholder = "{{topQuantity" + (i + 1) + "}}";
                    if (i < results.size()) 
                    {
                        Object[] result = results.get(i);
                        outputContent = outputContent
                            .replace(topProductPlaceholder, (result[0] == null || result[0].equals("")) ? "-" : product.get(i).getProductname())
                            .replace(topQuantityPlaceholder, (result[1] == null || Objects.equals(result[1], 0)) ? "0" : result[1].toString());
                    } 
                    else 
                    {
                        outputContent = outputContent
                            .replace(topProductPlaceholder, "-")
                            .replace(topQuantityPlaceholder, "0");
                    }
                }
            }

            //least 5 products
            results = em.createQuery(
                "SELECT c.productid, SUM(c.quantity) "
                + "FROM Cart c "
                + "WHERE c.ispaid = true "
                + "GROUP BY c.productid "
                + "HAVING c.productid IS NOT NULL AND SUM(c.quantity) IS NOT NULL "
                + "ORDER BY SUM(c.quantity) ", Object[].class)
                .setMaxResults(5)
                .getResultList();
            
            product = new ArrayList();
            for(Object[] o : results)
            {
                int i = 0;
                product.add(em.find(Product.class, o[i]));
                i++;
            }

            if (results.isEmpty()) 
            {
                for (int i = 0; i < 5; i++) 
                {
                    String topProductPlaceholder = "{{leastProduct" + (i + 1) + "}}";
                    String topQuantityPlaceholder = "{{leastQuantity" + (i + 1) + "}}";
                    outputContent = outputContent
                            .replace(topProductPlaceholder, "-")
                            .replace(topQuantityPlaceholder, "0");
                }
            } 
            else 
            {
                for (int i = 0; i < 5; i++) 
                {
                    String leastProductPlaceholder = "{{leastProduct" + (i + 1) + "}}";
                    String leastQuantityPlaceholder = "{{leastQuantity" + (i + 1) + "}}";
                    if (i < results.size()) 
                    {
                        Object[] result = results.get(i);
                        outputContent = outputContent
                            .replace(leastProductPlaceholder, (result[0] == null || result[0].equals("")) ? "-" : product.get(i).getProductname())
                            .replace(leastQuantityPlaceholder, (result[1] == null || Objects.equals(result[1], 0)) ? "0" : result[1].toString());
                    } 
                    else 
                    {
                        outputContent = outputContent
                            .replace(leastProductPlaceholder, "-")
                            .replace(leastQuantityPlaceholder, "0");
                    }
                }
            }

            // Write the output HTML file
            writeFileContent(outputFilePath, outputContent);
            HtmlToPdfConverter(reportname, templatePath);
            System.out.println("Output HTML file generated successfully!");

            return true;
        }
        catch(Exception ex)
        {
            System.out.println("generate report failed : " + ex.getMessage());
        }
        return false;
    }
    
    public void HtmlToPdfConverter (String filename, String templatePath) 
    {
        try
        { 
            String pdfFile = removeExtension(filename) + ".pdf";
            String pdfReport = templatePath + pdfFile;

            Document document = new Document();
            document.setPageSize(PageSize.LETTER);
            document.setMargins(36, 36, 72, 72);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfReport));
            document.open();
            HTMLWorker htmlWorker = new HTMLWorker(document);
            FileReader reader = new FileReader(templatePath + filename);
            htmlWorker.parse(reader);
            document.close();
            writer.close();
            
            File file = new File(pdfReport);
            FileInputStream inputStream = new FileInputStream(file);
            byte[] reportfile = new byte[(int) file.length()];
            inputStream.read(reportfile);
            inputStream.close();
            
            Report report = new Report(removeExtension(filename), reportfile);
            utx.begin();
            em.persist(report);
            utx.commit();
        }
        catch(Exception ex)
        {
            System.out.println("convert and persist failed : " + ex.getMessage());
        }
    }

    private String readFileContent(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    private void writeFileContent(String filePath, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String removeExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf('.');
        if (lastIndex != -1) {
            fileName = fileName.substring(0, lastIndex);
        }
        return fileName;
    }

    @Override
    public List<Report> GetReportByName(String reportname) {
        List<Report> reports = em.createQuery("SELECT r FROM Report r WHERE r.reportname LIKE :pattern")
                .setParameter("pattern", "%" + reportname + "%")
                .getResultList();
        
        return reports.isEmpty() ? new ArrayList() : reports;
    }
}