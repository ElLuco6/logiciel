package javafxapplication8;

import com.fazecast.jSerialComm.SerialPort;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javax.swing.JFileChooser;
import netscape.javascript.JSObject;



public class FXMLDocumentController implements Initializable {
    private BorderPane rootLayout;
    @FXML
    private Tab tab1;
    
    @FXML
    private Tab tab2;

    @FXML
    private Label label;
    
    @FXML
    private Button MAJ;
    
    @FXML
    private Button flash;

    @FXML
    private AnchorPane AnchorPane2;
    

     @FXML
    private ChoiceBox<String> choiceBox;

    private String selectedFirmware;
    private String port ;
    
    
    @FXML
    private void handleDownloadButton(ActionEvent event) {
        FirmwareDownloader firmwareDownloader = new FirmwareDownloader();
        try {
            firmwareDownloader.main(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void checkPorts() {
                File[] roots = File.listRoots();
                File usbDevice = null;

                for (File roott : roots) {
                    if (roott.getPath().startsWith("/media/")) {  // Modify this condition as needed
                        usbDevice = roott;
                        break;
                    }
                }
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
  
            }
    @FXML
    private void flash(){
        checkPorts();
        String exePath = System.getProperty("user.dir") + "\\esptool\\esptool.exe";
        String port = "COM1"; 
        String addr = "0x00000"; 
        String firmwareFile = System.getProperty("user.dir") + "\\bins\\"+selectedFirmware;
        System.out.println(exePath);
        String[] command = {exePath, "--port", port, "write_flash", addr, firmwareFile};
        System.out.println("command"+command);
        try {
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.redirectErrorStream(true);
            Process process = builder.start();
            InputStream is = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = process.waitFor();
            System.out.println("Command exited with code " + exitCode);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public File getFile() {
    // Create a file chooser dialog box
    JFileChooser fileChooser = new JFileChooser();

    // Show the dialog box and wait for the user's response
    int result = fileChooser.showOpenDialog(null);

    // If the user clicked the "Open" button
    if (result == JFileChooser.APPROVE_OPTION) {
        // Get the selected file
        File file = fileChooser.getSelectedFile();
        return file;
    }
    return null;
}

    private void loadFXML() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Button.fxml"));
        rootLayout = loader.load();

        // Récupérer le bouton depuis le contrôleur
        ButtonController controller = loader.getController();
        Button myButton = controller.myButton;

        
        BorderPane.setAlignment(myButton, Pos.TOP_RIGHT);
        // Ajouter le bouton au layout racine
        rootLayout.setTop(myButton);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadFXML();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        // Charge la page web
        webEngine.load("https://play.thingz.co/galaxia");

        // Ajouter un écouteur pour détecter quand la page est prête
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                String changeName = "La page est chargée !";

                JSObject title = (JSObject) webEngine.executeScript("document.querySelector('.orange.header')");
                title.setMember("innerHTML", changeName);
            }
        });

        AnchorPane AnchorPane2 = new AnchorPane();
        AnchorPane2.getChildren().add(webView);
        AnchorPane.setTopAnchor(webView, 0.0);
        AnchorPane.setRightAnchor(webView, 0.0);
        AnchorPane.setBottomAnchor(webView, 0.0);
        AnchorPane.setLeftAnchor(webView, 0.0);
        tab2.setContent(AnchorPane2);
        
        
        String firmwareFolderPath = System.getProperty("user.dir") + "\\bins\\";
        String[] fileNames = FirmwareDownloader.getBinFileNames(firmwareFolderPath);
        ObservableList<String> items = FXCollections.observableArrayList(fileNames);
        choiceBox.setItems(items);
        
        choiceBox.setOnAction(event -> {
        selectedFirmware = choiceBox.getSelectionModel().getSelectedItem();
        System.out.println("Selected firmware: " + selectedFirmware);
        
        
        
        
        
    });
    }

    // Add any necessary methods or event handlers here
}
