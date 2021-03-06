package com.inventmart.controller;

import com.inventmart.model.User;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.inventmart.service.UserService;
import com.inventmart.util.WindowsUtils;
import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


@Controller
public class RootController extends BaseController {

	public static final String PATH_FXML = "/fxml/root.fxml";
	public static final String ROOT_TITLE_KEY = "root.title";
	public static final String PATH_ICON = WindowsUtils.ICON_APP_PATH;
	
	@FXML
	private AnchorPane rootPane;
	
        @FXML
        private JFXButton dashboardButton;

        @FXML
        private JFXButton inventoryButton;

        @FXML
        private JFXButton transactionButton;

        @FXML
        private JFXButton reportButton;
        
        @FXML
        private JFXButton clientButton;
        
        @FXML
        private ImageView dashboardImage;
        
        @FXML
        private ImageView transactionImage;

        @FXML
        private ImageView clientImage;

        @FXML
        private ImageView reportImage;
         
        @FXML
        private ImageView inventoryImage;
        
	@Autowired
	private UserService userService;
        
        String user_name;
        String user_role;
        
	public <T> void init(Stage stage, HashMap<String, T> parameters) {
		super.init(stage, parameters);

		try {
                    	userService.finUserSignIn(e -> {
    			User user = (User) e.getSource().getValue();
        		if (user != null) {
                            user_name = user.getName();
                            if(user.getRoles().size() > 0){
                                user_role = user.getRoles().get(0).getName();
                                String role = user.getRoles().get(0).getRole();
                                if(role.equals("ADMIN")){
                                    dashboardButton.setVisible(true);
                                    dashboardImage.setVisible(true);
                                    transactionButton.setVisible(true);
                                    transactionImage.setVisible(true);
                                    reportButton.setVisible(true);
                                    reportImage.setVisible(true);
                                    inventoryButton.setVisible(true);
                                    inventoryImage.setVisible(true);
                                    clientButton.setVisible(true);
                                    clientImage.setVisible(true);
                                    
                                }else if(role.equals("USER")) {
                                    dashboardButton.setVisible(true);
                                    dashboardImage.setVisible(true);
                                    transactionButton.setVisible(false);
                                    transactionImage.setVisible(false);
                                    reportButton.setVisible(true);
                                    reportImage.setVisible(true);
                                    inventoryButton.setVisible(false);
                                    inventoryImage.setVisible(false);
                                    clientButton.setVisible(false);
                                    clientImage.setVisible(false);
                                }
                            }
        		}
    		
                        }, null);                    
                        
                        HashMap<String, String> parameterSend = new HashMap<String, String>();
                        parameterSend.put("user_name", user_name); 
                        parameterSend.put("user_role", user_role);                        
                        
			WindowsUtils.replaceFxmlOnWindow(rootPane, DashboardController.PATH_FXML, stage, parameterSend);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
        
//	public <T> void init(Stage stage, HashMap<String, T> parameters) {
//		super.init(stage, parameters);
//		

//	}
	
	@Override
	protected void onClose() {
		stage.close();
	}
	
	@FXML
	private void onInvetoryAction() throws Exception {
		WindowsUtils.replaceFxmlOnWindow(rootPane, InventoryController.PATH_FXML, stage, null);
	}
	
	@FXML
	private void onDashboardAction() throws Exception {
		WindowsUtils.replaceFxmlOnWindow(rootPane, DashboardController.PATH_FXML, stage, null);
	}
	
	@FXML
	private void onTransactionAction() throws Exception {
		WindowsUtils.replaceFxmlOnWindow(rootPane, TransactionController.PATH_FXML, stage, null);
	}
	
	@FXML
	private void onClientsAction() throws Exception {
		WindowsUtils.replaceFxmlOnWindow(rootPane, ClientsController.PATH_FXML, stage, null);
	}
	
	@FXML
	private void onReportsAction() throws Exception {
		WindowsUtils.replaceFxmlOnWindow(rootPane, ReportsController.PATH_FXML, stage, null);
	}
	
	@FXML
	private void onAbout() throws Exception {
		WindowsUtils.openNewWindow(AboutController.PATH_FXML, getWindowTitle(AboutController.ABOUT_TITLE_KEY), AboutController.PATH_ICON, null, Modality.APPLICATION_MODAL);
	}
	
	@FXML
	private void onSettings() throws Exception {
		WindowsUtils.openNewWindow(SettingsController.PATH_FXML, getWindowTitle(SettingsController.SETTINGS_TITLE_KEY), SettingsController.PATH_ICON, null, Modality.WINDOW_MODAL);
		stage.close();
	}
	
	@FXML
	private void onLogout() throws Exception {
		
		userService.setUserAsSignOut(e -> {
			try {
				WindowsUtils.openNewWindow(LoginController.PATH_FXML, getWindowTitle(LoginController.LOGIN_TITLE_KEY), LoginController.PATH_ICON, null, Modality.WINDOW_MODAL);
				onClose();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}, null);
		
	}
	
}
