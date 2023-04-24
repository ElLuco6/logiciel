/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package javafxapplication8;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import com.fazecast.jSerialComm.SerialPort;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Utilisateur
 */
public class JavaFXApplication8 extends Application {
    
    
    
    Scene scene1, scene2;
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        //Variables
        primaryStage.setTitle("PROJET_DESKTOP"); 
       String saveDirectory = System.getProperty("user.dir") + "\\bins\\";

        Button buttonHtmlFile = new Button("Get files");
        Button buttonAccess = new Button("Access Device");
        
        FirmwareDownloader myClass = new FirmwareDownloader();
        String url = "https://play.thingz.co/galaxia";
        
        Parent root = FXMLLoader.load(getClass().getResource ("FXMLDocument.fxml"));
        Scene scene0 = new Scene (root,800,600);
        primaryStage.setScene(scene0);
        primaryStage.show();
        
        
        myClass.main(new String[]{});
       
    
 
        File[] roots = File.listRoots();
        File usbDevice = null;
        
        for (File roott : roots) {
            if (roott.getPath().startsWith("/media/")) {  // Modify this condition as needed
                usbDevice = roott;
                break;
            }
        }
        buttonHtmlFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

     
            }
        });
        buttonAccess.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                System.out.println("sans la librairie ");
                for (int i = 0; i < roots.length; i++) {
                    File root = roots[i];
                    System.out.println("Root " + i + ": " + root.getAbsolutePath());
                    System.out.println("Total space: " + root.getTotalSpace());
                    System.out.println("Free space: " + root.getFreeSpace());
                }
                System.out.println("Avec la librairie ");
                
                SerialPort[] ports = SerialPort.getCommPorts();
                System.out.println(ports.length);
                for (SerialPort port : ports) {
                        System.out.println("dans boucle ");
                        System.out.println("System PortName : "+port.getSystemPortName());
                        System.out.println("System PortPath: "+port.getSystemPortPath()); System.out.println("Port Location: "+port.getPortLocation()); System.out.println("Port Description: "+port.getPortDescription());
                        System.out.println("Descriptive PortName : "+port.getDescriptivePortName());
                        System.out.println("BaudRate : "+port.getBaudRate()); 
                        System.out.println("‒‒‒‒‒‒‒");
                }
                System.out.println("Avec la librairie ");    
            }
        });
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
