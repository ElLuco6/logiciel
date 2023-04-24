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
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Utilisateur
 */
public class JavaFXApplication8 extends Application {
    
    
    
//    private BorderPane rootLayout;
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        //Variables
        primaryStage.setTitle("PROJET_DESKTOP"); 

        
        FirmwareDownloader myClass = new FirmwareDownloader();
        myClass.main(new String[]{});
        
        
        Parent root = FXMLLoader.load(getClass().getResource ("FXMLDocument.fxml"));
        Scene scene0 = new Scene (root,800,600);
        primaryStage.setScene(scene0);
        primaryStage.show();
        
//        loadFXML();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
