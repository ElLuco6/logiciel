/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxapplication8;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 *
 * @author Utilisateur
 */
public class FirmwareDownloader {
    public static void main(String[] args) throws Exception {
        // Specify the URL of the JSON data and the directory to save the firmware files to
        String jsonUrl = "https://thingz-mutiny.s3.eu-central-1.amazonaws.com/galaxia-firmware/versions.json";
//        String saveDirectory = "D:\\Simon\\Ynov\\2022_2023\\DEV_DESKTOP\\jsons\\";
        String saveDirectory = System.getProperty("user.dir") + "\\bins\\";
        System.out.println(saveDirectory);
        // Retrieve the JSON data from the URL and parse it
        String jsonText = readUrl(jsonUrl);
//        System.out.println("text" +jsonText);
        
        String[] jsonArrayStrings = jsonText.split("[\\[\\]]");
//        System.out.println("array" +jsonArrayStrings[1]);
        JSONObject firmwareObj = new JSONObject(jsonText);
        
        
        JSONArray firmwareArray = new JSONArray("["+jsonArrayStrings[1]+"]");
        System.out.println("array" +firmwareArray);
 
        
        // Loop through each firmware item in the JSON array
        for (int i = 0; i < firmwareArray.length(); i++) {
            JSONObject firmware = firmwareArray.getJSONObject(i);
            String firmwareUrl = firmware.getString("url");
            String firmwareName = firmwareUrl.substring(firmwareUrl.lastIndexOf("/") + 1);
            System.out.println("firmwareName"+firmwareName);
            
            // Check if the firmware file has already been downloaded
            File firmwareFile = new File(saveDirectory + firmwareName);
            if (firmwareFile.exists()) {
                System.out.println("Firmware file " + firmwareName + " already exists.");
            } else {
                // Download the firmware file from the URL and save it to the specified directory
                System.out.println("Downloading firmware file " + firmwareName + " from URL " + firmwareUrl);
                saveUrl(firmwareUrl, saveDirectory + firmwareName);
            }
        }
    }
    
    // Read data from a URL and return it as a string
    public static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder buffer = new StringBuilder();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read); 

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }
    
    // Download data from a URL and save it to a file
    public static void saveUrl(String urlString, String fileName) throws Exception {
        BufferedInputStream in = null;
        FileOutputStream out = null;
        try {
            URL url = new URL(urlString);
            in = new BufferedInputStream(url.openStream());
            out = new FileOutputStream(fileName);
            byte[] data = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(data, 0, 1024)) != -1) {
                out.write(data, 0, bytesRead);
            }
        } finally {
            if (in != null)
                in.close();
            if (out != null)
                out.close();
        }
    }
    public static String[] getBinFileNames(String firmwareFolderPath) {
    File folder = new File(firmwareFolderPath);
    File[] files = folder.listFiles(new FilenameFilter() {
        public boolean accept(File dir, String name) {
            return name.toLowerCase().endsWith(".bin");
        }
    });
    String[] fileNames = new String[files.length];
    for (int i = 0; i < files.length; i++) {
        fileNames[i] = files[i].getName();
    }
    return fileNames;
}
}
