package demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AuthenticationLogic {
    private static Stage registerStage = new Stage();
    public static Stage loginStage = new Stage();
    public static Stage confirmationStage = new Stage();
    public static String path;
    public static Stage profileStage = new Stage();
   

    public void setStages() throws Exception {

        // Load the register FXML file
        Parent rootRegister = FXMLLoader.load(getClass().getResource("/register.fxml"));
        Scene sceneRegister = new Scene(rootRegister, 400, 400);
        sceneRegister.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        registerStage.setScene(sceneRegister);
        registerStage.setTitle("Authentication");

        // Load the login FXML file
        Parent rootLogin = FXMLLoader.load(getClass().getResource("/login.fxml"));
        Scene sceneLogin = new Scene(rootLogin, 300, 200);
        loginStage.setScene(sceneLogin);
        loginStage.setTitle("Login");

        // Load the second FXML file
        Parent rootConfirmation = FXMLLoader.load(getClass().getResource("/confirmation.fxml"));
        Scene sceneConfirmatin = new Scene(rootConfirmation, 300, 200);
        confirmationStage.setScene(sceneConfirmatin);
        confirmationStage.setTitle("Confirmation");

        // Load the profile FXML file
        Parent rootProfile = FXMLLoader.load(getClass().getResource("/profile.fxml"));
        Scene sceneProfile = new Scene(rootProfile, 300, 300);
        profileStage.setScene(sceneProfile);
        profileStage.setTitle("Profile");

    }

    /* a method to read csv file */
    public static String[] readCSV(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                return data;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /* a method to write to csv file */
    public static void writeToCSV(String[] dataList, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // using String.join() to put the , in between. aka delimiter
            String string = String.join(",", dataList);
            // Write the row to the file
            writer.write(string);
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clickOnRegister() {
        Main.mainStage.close();
        registerStage.show();
        
    }

    public static void handleRegister(String firstName, String lastName, String email, String password,
            String confirmPassword, String profileImageFilePath) {
                
        if (password.equals(confirmPassword)) {
            String[] data = { firstName, lastName, email, password, profileImageFilePath };
            writeToCSV(data, "src/main/resources/users.csv");
            registerStage.close();
            confirmationStage.show();
        } else {
            System.out.println("Passwords do not match");

        }

    }

    public static void handleLogin(String email, String password) {

        String[] data = readCSV("src/main/resources/users.csv");
        System.out.println("data on csv file at index one is " + data[2] + data[3]);
        String passwordInRegistry = data[3];
        if (passwordInRegistry.equals(password) && data[2].equals(email)) {
            profileStage.show();
            loginStage.close();
            parseProfile();


        } else {
            System.out.println("Login failed");
        }
    }

    public static void handleConfirmation() {
        confirmationStage.show();
    }

    public static void clickOnLogin() {
        loginStage.show();
        Main.mainStage.close();

    }

    public static void handleLoginAction() {
        Main.mainStage.show();
        confirmationStage.hide();
    }

    private static void parseProfile() {
        String[] data = readCSV("src/main/resources/users.csv");
        String firstName = data[0];
        String secondName = data[1];
        String email = data[2];
        String imagePath = data[4];
       Platform.runLater(() -> {
            Controller.firstName.setText(firstName);
            Controller.lastName.setText(secondName);
            Controller.email.setText(email);
            // Load the image using the file path
            Image image = new Image(new File(imagePath).toURI().toString());
            Controller.image.setImage(image);
            Controller.image.setPreserveRatio(true);
            System.out.println(".......profile updated");
        });

    }
    
}
