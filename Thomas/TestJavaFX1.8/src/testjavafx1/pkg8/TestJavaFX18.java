package testjavafx1.pkg8;

import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import java.io.IOException;

import javafx.stage.Stage;

public class TestJavaFX18 extends Application {

    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Charge le fichier XML et le controlleur
        loadFXML();

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

        AnchorPane webPane = new AnchorPane();
        webPane.getChildren().add(webView);
        AnchorPane.setTopAnchor(webView, 0.0);
        AnchorPane.setRightAnchor(webView, 0.0);
        AnchorPane.setBottomAnchor(webView, 0.0);
        AnchorPane.setLeftAnchor(webView, 0.0);
        

        rootLayout.setCenter(webPane);

        Scene scene = new Scene(rootLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadFXML() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(TestJavaFX18.class.getResource("Button.fxml"));
        rootLayout = loader.load();

        // Récupérer le bouton depuis le contrôleur
        ButtonController controller = loader.getController();
        Button myButton = controller.myButton;

        
        BorderPane.setAlignment(myButton, Pos.TOP_RIGHT);
        // Ajouter le bouton au layout racine
        rootLayout.setTop(myButton);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
