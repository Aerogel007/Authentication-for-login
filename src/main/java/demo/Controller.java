package demo;

import java.io.File;
import javafx.fxml.FXML;
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
    @FXML Label firstNameLabel;
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
        
        // /*adding listners so i can check if passwords match in real time, while registaring. */
        // // Add listeners to password fields
        // confirmPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
        //     if(!newValue.equals(Controller.password.getText())) {
        //         Platform.runLater(() -> Controller.matchingPassword.setText("Passwords do not match"));
        //         System.out.println("Passwords do not match");
        //     } 
        //     else 
        //     {
        //         Platform.runLater(() -> Controller.matchingPassword.setText(""));
        //     }
        // });

    }

    @FXML
    private void handleKeyReleased(KeyEvent keyEvent) {
        if(passwordField.getText().equals(confirmPasswordField.getText())) {
            matchingPassword.setText("");
        } else {
            matchingPassword.setText("Passwords do not match");
        }
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
