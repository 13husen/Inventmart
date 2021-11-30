package com.inventmart.controller;

import com.inventmart.model.Role;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.inventmart.model.User;
import com.inventmart.service.RoleService;
import com.inventmart.service.UserService;
import com.inventmart.util.ValidatorUtils;
import com.inventmart.util.WindowsUtils;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class LoginController extends BaseController {

	public static final String PATH_FXML = "/fxml/login.fxml";
	public static final String LOGIN_TITLE_KEY = "login.title";
	public static final String PATH_ICON = WindowsUtils.ICON_APP_PATH;
	
	@FXML
	private JFXTextField emailTextField;
	
	@FXML
	private JFXPasswordField passwordTextField;
        
	@FXML
	private JFXTextField passwordTextFieldShow;        
	
        
	@FXML
	private JFXCheckBox showPasswordCheckBox;        
	
	@FXML
	private JFXButton loginButton;
	
	@FXML
	private JFXComboBox<Role> roleComboBox;
        
	@FXML
	private Label errorLabel;
	
	@Autowired
	private UserService userService;
        
	@Autowired
	private RoleService roleService;        
	
	public <T> void init(Stage stage, HashMap<String, T> parameters) {
		super.init(stage, parameters);
                passwordTextFieldShow.setManaged(false);
                passwordTextFieldShow.setVisible(false);		
                
                fillComboBoxes();
		validateTextFields();
		watchEvents();
	}
	
	private void fillComboBoxes() {
		WindowsUtils.addComboBoxItens(roleComboBox, roleService);
	}        
        
	private void validateTextFields() {
		
		ValidatorUtils.addRequiredValidator(emailTextField, "E-mail is Required!");
		ValidatorUtils.addRequiredValidator(passwordTextField, "Password is Required!");
		
		ValidatorUtils.addEmailValidator(emailTextField, "Email does not match");
		
		WindowsUtils.validateTextField(emailTextField);
		WindowsUtils.validateTextField(passwordTextField);
	}

	private void watchEvents() {
                passwordTextFieldShow.managedProperty().bind(showPasswordCheckBox.selectedProperty());
                passwordTextFieldShow.visibleProperty().bind(showPasswordCheckBox.selectedProperty());

                passwordTextField.managedProperty().bind(showPasswordCheckBox.selectedProperty().not());
                passwordTextField.visibleProperty().bind(showPasswordCheckBox.selectedProperty().not());

                // Bind the textField and passwordField text values bidirectionally.
                passwordTextFieldShow.textProperty().bindBidirectional(passwordTextField.textProperty());                
//                showPasswordCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
//                @Override
//                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//                        showPasswordCheckBox.setSelected(!newValue);
//                        if(newValue){
//                        }else{
//                            
//                        }
//                    }
//                });
            
		WindowsUtils.watchEvents(emailTextField, v -> watch());
		WindowsUtils.watchEvents(passwordTextField, v -> watch());
	}
	
	private void watch() {
		if (isRequiredTextFieldsFilled() && (passwordTextField.validate() && emailTextField.validate())) {
			loginButton.setDisable(false);
		} else {
			loginButton.setDisable(true);
		}
		
	}

	private boolean isRequiredTextFieldsFilled() {
		return  !(WindowsUtils.isTextFieldEmpty(emailTextField)) &&
				!(WindowsUtils.isTextFieldEmpty(passwordTextField));
	}

	@Override
	protected void onClose() {
		userService.onClose();
	}
	
	@FXML
	public void onLogin() {
		
		userService.findByEmailAndPassword(WindowsUtils.getTextFromTextField(emailTextField), 
										   WindowsUtils.getTextFromTextField(passwordTextField), 
										   e -> {
											   User user = (User) e.getSource().getValue();
											   Role roleNow = (Role) WindowsUtils.getSelectedComboBoxItem(roleComboBox);
											   if (user != null) {
                                                                                               if(user.getRoles().size() == 1){
                                                                                                if(user.getRoles().get(0).getRole().equals(roleNow.getRole())){
                                                                                                    onSucessLogin(user);
                                                                                                }else{
                                                                                                    onErrorLogin();
                                                                                                }                                                                                                   
                                                                                               }else{
                                                                                                if(user.getRoles().get(0).getRole().equals(roleNow.getRole()) ||
                                                                                                        user.getRoles().get(1).getRole().equals(roleNow.getRole())){
                                                                                                    onSucessLogin(user);
                                                                                                }else{
                                                                                                    onErrorLogin();
                                                                                                }                                                                                                   
                                                                                               }

											   } else {
												   onErrorLogin();
											   }
										   }, null);
		
	}
	
	private void onSucessLogin(User user) {
		errorLabel.setVisible(false);
		
		userService.setUserAsSignin(user.getEmail(), 
									user.getPassword(), 
									e -> {
										try {
											WindowsUtils.openNewWindow(RootController.PATH_FXML, getWindowTitle(RootController.ROOT_TITLE_KEY), RootController.PATH_ICON, null, Modality.WINDOW_MODAL);
											stage.close();
										} catch (Exception e1) {
											e1.printStackTrace();
										}
										
									}, null);
		
	}
	
	private void onErrorLogin() {
		errorLabel.setVisible(true);
	}
}
