package com.inventmart.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.inventmart.model.Address;
import com.inventmart.model.Client;
import com.inventmart.model.ClientType;
import com.inventmart.model.Fone;
import com.inventmart.model.Role;
import com.inventmart.model.User;
import com.inventmart.service.ClientService;
import com.inventmart.service.RoleService;
import com.inventmart.service.UserService;
import com.inventmart.util.EntityFactory;
import com.inventmart.util.ValidatorUtils;
import com.inventmart.util.WindowsUtils;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

@Controller
public class ClientNewController extends BaseController {
	
	public static final String PRODUCT_KEY = "client_key";
	public static final String PATH_FXML = "/fxml/new_client.fxml";
	public static final String NEW_CLIENT_TITLE_KEY = "new_client.title";
	public static final String PATH_ICON = WindowsUtils.ICON_APP_PATH;
	
	@FXML
	private StackPane container;
	
	@FXML
	private JFXTextField nameTextField;
	
	@FXML
	private JFXTextField emailTextField;
	

        
	@FXML
	private JFXTextField cellPhoneTextField;
	
	@FXML
	private JFXTextField streetTextField;
        
	@FXML
	private JFXTextField cepTextField;
        
	@FXML
	private JFXTextField districtTextField;

	@FXML
	private JFXTextField cityTextField;

	@FXML
	private JFXTextField stateTextField;

	@FXML
	private JFXPasswordField passwordTextField;

	@FXML
	private JFXPasswordField confirmPasswordTextField;

	@FXML
	private JFXComboBox<String> countryComboBox;

	@FXML
	private JFXComboBox<Role> roleComboBox;
	
	
	@FXML
	private JFXButton saveButton;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private UserService userService;
	
	private Client client;
	
        private User user; 
        boolean isEdit = false;
        
	public <T> void init(Stage stage, HashMap<String, T> parameters) {
		super.init(stage, parameters);
		
		fillComboBoxes();
		checkParameters(parameters);
                //render
                if(isEdit){
                    emailTextField.setVisible(false);
                }else{
                    emailTextField.setVisible(true);                    
                }
		validateTextFields();
		watchEvents();
	}

	@Override
	protected void onClose() {
		roleService.onClose();
		clientService.onClose();
		userService.onClose();
	}
	
	private <T> void checkParameters(HashMap<String, T> parameters) {
		if (parameters != null) {
			this.client = (Client) parameters.get(PRODUCT_KEY);
                        this.user = this.client.getUser();
                        isEdit = true;
			updateTextFields();
		}else{
                    isEdit = false;
                    this.client = null;
                }
	}
	
	private void updateTextFields() {
		if (client.getPhones() != null) {
			WindowsUtils.setTextInTextField(cellPhoneTextField, client.getPhones().get(0).getNumber());
		}
		
		if (client.getAddress() != null) {
			WindowsUtils.setTextInTextField(streetTextField, client.getAddress().getStreet());
			WindowsUtils.setTextInTextField(districtTextField, client.getAddress().getSuburb());
			WindowsUtils.setTextInTextField(cityTextField, client.getAddress().getCity());
			WindowsUtils.setTextInTextField(stateTextField, client.getAddress().getState());
			WindowsUtils.setTextInTextField(cepTextField, client.getAddress().getCep());			
			WindowsUtils.setSelectedComboBoxItem(countryComboBox, client.getAddress().getCountry());
		}
		
		if (client.getUser() != null) {
			WindowsUtils.setSelectedComboBoxItem(roleComboBox, client.getUser().getRoles().get(0));
			WindowsUtils.setTextInTextField(nameTextField, client.getUser().getName());
			WindowsUtils.setTextInTextField(emailTextField, client.getUser().getEmail());
		}
		
	}
	
	private void validateTextFields() {
		ValidatorUtils.addRequiredValidator(nameTextField, "Employee Name is Required!");
		ValidatorUtils.addRequiredValidator(emailTextField, "E-mail is Required!");
		ValidatorUtils.addRequiredValidator(passwordTextField, "Password is Required!");
		ValidatorUtils.addRequiredValidator(confirmPasswordTextField, "Confirm Password is Required!");
		ValidatorUtils.addPasswordAndConfirmPasswordValidator(passwordTextField, confirmPasswordTextField, "Password does not match the confirm password");
		
		
		ValidatorUtils.addEmailValidator(emailTextField, "Email does not match");
		
		ValidatorUtils.addDuplicateUserValidator(emailTextField, "An account for the specified email address already exists", userService);
		
		WindowsUtils.validateTextField(cellPhoneTextField);
		WindowsUtils.validateTextField(nameTextField);
		WindowsUtils.validateTextField(emailTextField);
		WindowsUtils.validateTextField(passwordTextField);
		WindowsUtils.validateTextField(confirmPasswordTextField);
	}
	
	private void fillComboBoxes() {
		WindowsUtils.addComboBoxItens(roleComboBox, roleService);
	}
	
	private void watchEvents() {
		WindowsUtils.watchEvents(nameTextField, v -> watch());
		WindowsUtils.watchEvents(emailTextField, v -> watch());
		WindowsUtils.watchEvents(passwordTextField, v -> watch());
		WindowsUtils.watchEvents(confirmPasswordTextField, v -> watch());
	}
	
	private void watch() {
                if(!isEdit){
                    if (isRequiredTextFieldsFilled() && (passwordTextField.validate() && confirmPasswordTextField.validate())) {
                            saveButton.setDisable(false);
                    } else {
                            saveButton.setDisable(true);
                    }
                }else{
                    if (isRequiredTextFieldsFilled()) {
                            saveButton.setDisable(false);
                    } else {
                            saveButton.setDisable(true);
                    }                    
                }
		
	}
	
	private boolean isAddressFilled() {
		return  !WindowsUtils.isTextFieldEmpty(streetTextField) ||
				!WindowsUtils.isTextFieldEmpty(districtTextField) ||
				!WindowsUtils.isTextFieldEmpty(cityTextField) ||
				!WindowsUtils.isTextFieldEmpty(stateTextField) ||
				WindowsUtils.isComboBoxSelected(roleComboBox);
	}
	
	private boolean isRequiredTextFieldsFilled() {
                boolean status = false;
                if(!isEdit){
                    status = !(WindowsUtils.isTextFieldEmpty(nameTextField)) &&
                    !(WindowsUtils.isTextFieldEmpty(emailTextField)) &&
                    !(WindowsUtils.isTextFieldEmpty(passwordTextField)) &&
                    !(WindowsUtils.isTextFieldEmpty(confirmPasswordTextField));
                }else{
                    status = !(WindowsUtils.isTextFieldEmpty(nameTextField));
                }
                
                return status;

	}
	
	@SuppressWarnings("unchecked")
	@FXML
	public void onSave() {
            if(!isEdit){
		userService.findUserByEmail(WindowsUtils.getTextFromTextField(emailTextField),e -> {
                        User user = (User) e.getSource().getValue();
                        if (user != null) {
                            WindowsUtils.createDefaultDialog(container, "Error", "Data user sudah ada di dalam sistem.", () -> {});                            
                        } else {
                            insertData();
                        }
                }, null);                
            }else{
                insertData();
            }
   
	}
	
        public void insertData(){
		Role roleSelected = WindowsUtils.getSelectedComboBoxItem(roleComboBox);
		List<Fone> phones = Arrays.asList(EntityFactory.createPhone(WindowsUtils.getTextFromTextField(cellPhoneTextField)));
//EntityFactory.createPhone(WindowsUtils.getLongFromTextField(residentialPhoneTextField))                
		
		roleService.findByRole(roleSelected.getRole(), e -> {
			List<Role> role = (List<Role>) e.getSource().getValue();
                        user = EntityFactory.createUser(
                                             user,
                                             WindowsUtils.getTextFromTextField(emailTextField), 
                                             WindowsUtils.getTextFromTextField(nameTextField), 
                                             null, 
                                             isEdit ? client.getUser() != null ? client.getUser().getPassword() : WindowsUtils.getTextFromTextField(passwordTextField) : WindowsUtils.getTextFromTextField(passwordTextField), 
                                             role);                            
			
			Address address = null;
			if (isAddressFilled()) {
				address = EntityFactory.createAddress(
                                WindowsUtils.getTextFromTextField(streetTextField), 
                                    0,
                                    "",
                                    WindowsUtils.getTextFromTextField(districtTextField), 
                                    WindowsUtils.getTextFromTextField(cityTextField), 
                                    WindowsUtils.getTextFromTextField(stateTextField), 
                                    WindowsUtils.getSelectedComboBoxItem(countryComboBox),
                                    WindowsUtils.getTextFromTextField(cepTextField)
                                );
			}
			
			try {
				clientService.save(EntityFactory.createClient(client, "", address, phones, user), en -> {
															WindowsUtils.createDefaultDialog(container, "Sucess", "Client data berhasil di simpan.", () -> { stage.close(); });
														}, null);
			
			} catch (Exception error) {
				error.printStackTrace();
				WindowsUtils.createDefaultDialog(container, "Error", "Error saving client, try again.", () -> {});
			}
		}, null);             
        }
        
        
	@FXML
	public void onCancel() {
		stage.close();
	}
	
	@FXML
	public void onHelp() {
		
	}

}
