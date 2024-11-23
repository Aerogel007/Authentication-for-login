package demo;

import java.io.File;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;

public class Controller {
    File selectedFile;
    public static Label firstName;
    public static Label lastName;
    public static Label email;
    public static ImageView image;

    // Register.fxml
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private ImageView profileImageView;
    @FXML
    private Button chooseFileButton;
    @FXML
    private Button registerButton;
    @FXML
    private Label matchingPassword;

    // Login.fxml
    @FXML
    private TextField loginEmailField;
    @FXML
    private PasswordField loginPasswordField;
    @FXML
    private Button loginButton;

    // Confirmation.fxml
    @FXML
    private Button confirmationLoginButton;

    // Profile.fxml
    @FXML
    Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label emailLabel;

    // Main-stage.fxml
    @FXML
    private Button mainLoginButton;
    @FXML
    private Button mainRegisterButton;

    @FXML
    private void initialize() {
        firstName = firstNameLabel;
        lastName = lastNameLabel;
        email = emailLabel;
                
        //note that initialize method gets called after the controller object is initialized creating all the fxml objects and linking them to the annotated field.
        //and initialize method gets called for each FXML Loader so if we don't check if confirmPasswordField is null, the below case happens
        //we run initialize method for the controller of Loader that loads main-stage here. @FXML confirmPassowrd doesn't get instatiated. so we get a null pointer exception. because, it doesnot exist yet.
        //when confirmPasswordField is not null, it mean we are calling the initialize method of registerLoader
        
        if (confirmPasswordField != null) {
           System.out.print("listner code block gets executed");
            /*
             * adding listners so i can check if passwords match in real time, while
             * registaring.
             */
            //Add listeners to password fields
            confirmPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.equals(passwordField.getText())) {
                    Platform.runLater(() -> matchingPassword.setText("Passwords do not match"));
                    System.out.println("Passwords do not match");
                } else {
                    Platform.runLater(() -> matchingPassword.setText(""));
                }
            });
        }

    }

    @FXML
    private void handleKeyReleased(KeyEvent keyEvent) {
        // if (passwordField.getText().equals(confirmPasswordField.getText())) {
        //     matchingPassword.setText("");
        // } else {
        //     matchingPassword.setText("Passwords do not match");
        // }
    }

    @FXML
    private void clickOnRegister() {
        AuthenticationLogic.clickOnRegister();

    }

    @FXML
    private void handleRegister() {
        AuthenticationLogic.handleRegister(firstNameField.getText(), lastNameField.getText(), emailField.getText(),
                passwordField.getText(), confirmPasswordField.getText(), selectedFile.getAbsolutePath());
    }

    @FXML
    private void handleLogin() {
        AuthenticationLogic.handleLogin(loginEmailField.getText(), loginPasswordField.getText());
    }

    @FXML
    private void clickOnLogin() {
        AuthenticationLogic.clickOnLogin();

    }

    @FXML
    private void handleLoginAction() {
        AuthenticationLogic.handleLoginAction();
    }

    /*
     * here we use method chaining to first,
     * create a filechooser window.
     * the use .getExtensionFilters() to add a filter to the filechooser(it means we
     * define what type of files we want to be able to choose)
     * then we use .addAll() to add multiple filters to the filechooser.
     * --------------------------------------------------
     * we store the reference to file object in selected file
     * which is slected using filechooser.showOpenDialog(null);
     * filechooser.showOpenDialog(null) will open the filechooser window and return
     * the file object that is selected.
     */
    @FXML
    private void handleChooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Picture");
        // Set extension filter
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        selectedFile = fileChooser.showOpenDialog(null);

        // Return the file path if a file is selected, or null otherwise
        if (selectedFile != null) {
            // AuthenticationLogic.path = selectedFile.getAbsolutePath();
            System.out.println("file path is" + selectedFile.getAbsolutePath());
            Image image = new Image(selectedFile.toURI().toString());
            profileImageView.setImage(image);
            profileImageView.setPreserveRatio(true);
        } else {
            // Indicates no file was selected
            System.out.println("No file selected");
        }
    }
}
