/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

/**
 *
 * @author Lenovo
 */
public class testingReportServie {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String currentDirectoryPath = System.getProperty("user.dir");
        File currentDirectory = new File(currentDirectoryPath);
        File parentDirectory = currentDirectory.getParentFile();

        if (parentDirectory != null) {
            String parentPath = parentDirectory.getPath();
            String reportFilePath = parentPath + File.separator + "Den's Toy" + File.separator + "web" + File.separator + "ReportFile" + File.separator;

            System.out.println("Parent Directory Path: " + parentPath);
            System.out.println("Report File Path: " + reportFilePath);
        } else {
            System.out.println("Parent directory not found.");
        }
    }

    private static String readFileContent(String filePath) {
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

    private static void writeFileContent(String filePath, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
