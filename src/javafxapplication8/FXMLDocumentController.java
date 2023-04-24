package javafxapplication8;

import java.io.File;
import java.io.FilenameFilter;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class FXMLDocumentController implements Initializable {

    @FXML
    private Tab tab1;
    
    @FXML
    private Tab tab2;

    @FXML
    private Label label;
    
    @FXML
    private Button MAJ;

    
     @FXML
    private ChoiceBox<String> choiceBox;

    private String selectedFirmware;

    @FXML
    private WebView webView;
    
    @FXML
    private void handleDownloadButton(ActionEvent event) {
        FirmwareDownloader firmwareDownloader = new FirmwareDownloader();
        try {
            firmwareDownloader.main(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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
