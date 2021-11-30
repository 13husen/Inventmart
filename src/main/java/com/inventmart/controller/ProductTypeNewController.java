package com.inventmart.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.inventmart.model.Brand;
import com.inventmart.model.ProductType;
import com.inventmart.service.BrandService;
import com.inventmart.service.ProductTypeService;
import com.inventmart.util.EntityFactory;
import com.inventmart.util.ValidatorUtils;
import com.inventmart.util.WindowsUtils;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

@Controller
public class ProductTypeNewController extends BaseController {
	
	public static final String PRODUCT_TYPE_KEY = "producttype_key";
	
	public static final String PATH_FXML = "/fxml/new_product_type.fxml";
	public static final String NEW_PRODUCT_TYPE_TITLE_KEY = "new_prodtype.title";
	public static final String PATH_ICON = WindowsUtils.ICON_APP_PATH;

	@FXML
	private StackPane container;
	
	@FXML
	private JFXTextField nameTextField;
	
	
	@FXML
	private JFXTextArea descriptionTextArea;

	@FXML
	private JFXButton saveButton;
	
	@Autowired
	private ProductTypeService productTypeService;
	
	private ProductType productType;
	
	@Override
	public <T> void init(Stage stage, HashMap<String, T> parameters) {
		super.init(stage, parameters);
		
		checkParameters(parameters);
		validateTextFields();
		watchEvents();
	}

	@Override
	protected void onClose() {
		productTypeService.onClose();
	}
	
	private <T> void checkParameters( HashMap<String, T> parameters) {
		if (parameters != null) {
			this.productType = (ProductType) parameters.get(PRODUCT_TYPE_KEY);
			updateTextFields();
		}else{
                    this.productType = null;
                }
	}

	private void updateTextFields() {
		
		WindowsUtils.setTextInTextField(nameTextField, productType.getName());
		WindowsUtils.setTextInTextArea(descriptionTextArea, productType.getDeskripsi());
	}

	private void watchEvents() {
		WindowsUtils.watchEvents(nameTextField, v -> watch());
	}

	private void validateTextFields() {
		ValidatorUtils.addRequiredValidator(nameTextField, "Brand Name is Required!");
		
		WindowsUtils.validateTextField(nameTextField);
	}
	
	private void watch() {
		if (isRequiredTextFieldsFilled()) {
			saveButton.setDisable(false);
		} else {
			saveButton.setDisable(true);
		}
		
	}
	
	private boolean isRequiredTextFieldsFilled() {
		return  !WindowsUtils.isTextFieldEmpty(nameTextField);
	}
	
	@FXML
	public void onSave() {
		
		try {
			productTypeService.save(EntityFactory.createProductType(productType, WindowsUtils.getTextFromTextField(nameTextField), 
														WindowsUtils.getTextFromTextArea(descriptionTextArea)), e -> {
															WindowsUtils.createDefaultDialog(container, "Sucess", "Sukses insert Tipe Produk.", () -> { stage.close(); });
														}, null);
		} catch (Exception e) {
			e.printStackTrace();
			WindowsUtils.createDefaultDialog(container, "Error", "Error saving product type, try again.", () -> {});
		}
	}
	
	@FXML
	public void onCancel() {
		stage.close();
	}
	
	@FXML
	public void onHelp() {
		
	}

}
