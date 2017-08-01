package com.linkedin;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;


public class utils {
    public static void scrollDown(WebDriver drv) {
        JavascriptExecutor jse = (JavascriptExecutor) drv;
        jse.executeScript("window.scrollBy(0,2500)", "");

    }

    public static void sleepThread(int waitMs) {
        try {
            Thread.sleep(waitMs);
        } catch (Exception ex) {
            System.out.println("Problem inside a thread " + ex.getMessage());
        }
    }

    public static void writeTextToFile(List<String> content, String fileName, String outputFolder) {
        PrintWriter pw = null;

        File output;
        try {
            File dir = new File(outputFolder + '/' + "");
            if (!dir.exists()) {
                boolean success = dir.mkdirs();
                if (!success) {
                    try {
                        throw new Exception(dir + " could not be created");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            output = new File(dir + "/" + fileName);
            if (output.exists()) {
                if (!output.delete()) {
                    throw new Exception(dir + "/" + fileName + " could not be deleted");
                }
            } else {
                try {
                    if (!output.createNewFile()) {
                        throw new Exception(dir + "/" + fileName + " could not be created");
                    }
                } catch (Exception ioe) {
                    ioe.printStackTrace();
                }
            }
            pw = new PrintWriter(new FileWriter(output, true));
            for (String line : content) {
                pw.print(line);
                pw.print("\n");
            }
        } catch (Exception ioe) {
            ioe.printStackTrace();
        } finally {
            pw.close();
        }

    }

}
