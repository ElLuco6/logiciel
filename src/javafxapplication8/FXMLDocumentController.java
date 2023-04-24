package javafxapplication8;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
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
    private AnchorPane AnchorPane2;
    

     @FXML
    private ChoiceBox<String> choiceBox;

    private String selectedFirmware;
    
    
    @FXML
    private void handleDownloadButton(ActionEvent event) {
        FirmwareDownloader firmwareDownloader = new FirmwareDownloader();
        try {
            firmwareDownloader.main(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        
        
        String firmwareFolderPath = "D:\\Simon\\Ynov\\2022_2023\\DEV_DESKTOP\\jsons\\";
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
