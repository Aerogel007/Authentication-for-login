package demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Kiran jung Shah
 * @version 1.0
 * this is the main class of the project.
 * in this project i have used java fx to create a registration and login system.
 * first, the user has to register.and it is strored into a .csv file.
 * then the user can login using the email and password.
 */
public class Main extends Application {
    public static Stage mainStage = new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the main-stage FXML file
        Parent rootMainStage = FXMLLoader.load(getClass().getResource("/main-stage.fxml"));
        Scene sceneMainStage = new Scene(rootMainStage, 200, 200);
        
         // Set the initial scene
        primaryStage.setScene(sceneMainStage);
        primaryStage.setTitle("Authentication Program");
        primaryStage.show();
        mainStage = primaryStage;

        AuthenticationLogic authenticationLogic = new AuthenticationLogic();
        authenticationLogic.setStages();
    }

    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}