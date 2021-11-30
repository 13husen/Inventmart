package com.inventmart.controller;

// dependency maven
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.inventmart.model.Brand;
import com.inventmart.service.BrandService;
import com.inventmart.util.EntityFactory;
import com.inventmart.util.ValidatorUtils;
import com.inventmart.util.WindowsUtils;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**********************************************/
//   CONTROLLER BRAND (untuk halaman brand)
/**********************************************/


@Controller
public class BrandNewController extends BaseController {
	// deklarasi variable global
	public static final String BRAND_KEY = "brand_key";
	
	public static final String PATH_FXML = "/fxml/new_brand.fxml";
	public static final String NEW_BRAND_TITLE_KEY = "new_brand.title";
	public static final String PATH_ICON = WindowsUtils.ICON_APP_PATH;

	@FXML
	private StackPane container;
	
	@FXML
	private JFXTextField nameTextField;
	
	@FXML
	private JFXTextField emailTextField;
	
	@FXML
	private JFXTextArea additionalInfoTextArea;

	@FXML
	private JFXButton saveButton;
	
	@Autowired
	private BrandService brandService;
	
	private Brand brand;
	
        // init controller
	@Override
	public <T> void init(Stage stage, HashMap<String, T> parameters) {
		super.init(stage, parameters);
		
		checkParameters(parameters);
		validateTextFields();
		watchEvents();
	}

        // function saat tampilan  berubah atau menutup
	@Override
	protected void onClose() {
		brandService.onClose();
	}
	
        // mengecek parameter yang di terima dari model brand (kebutuhan edit)
	private <T> void checkParameters( HashMap<String, T> parameters) {
		if (parameters != null) {
			this.brand = (Brand) parameters.get(BRAND_KEY);
			updateTextFields();
		}else{
                    this.brand = null;
                }
	}
        
        // mengupdate textfield
	private void updateTextFields() {
		
		WindowsUtils.setTextInTextField(nameTextField, brand.getName());
		WindowsUtils.setTextInTextField(emailTextField, brand.getEmail());
		WindowsUtils.setTextInTextArea(additionalInfoTextArea, brand.getAdditionalInformation());
	}

        // memantau perubahan text field
	private void watchEvents() {
		WindowsUtils.watchEvents(nameTextField, v -> watch());
		WindowsUtils.watchEvents(emailTextField, v -> watch());
	}

        // validasi text field
	private void validateTextFields() {
		ValidatorUtils.addRequiredValidator(nameTextField, "Brand Name is Required!");
		ValidatorUtils.addRequiredValidator(emailTextField, "Email is Required!");
		
		ValidatorUtils.addEmailValidator(emailTextField, "Email does not match");
		
		WindowsUtils.validateTextField(nameTextField);
		WindowsUtils.validateTextField(emailTextField);
	}
	
        // mengecek validasi text field , untuk mentoggle tombol simpan, aktif atau nonaktif
	private void watch() {
		if (isRequiredTextFieldsFilled() && (emailTextField.validate())) {
			saveButton.setDisable(false);
		} else {
			saveButton.setDisable(true);
		}
		
	}
	
	private boolean isRequiredTextFieldsFilled() {
		return  !WindowsUtils.isTextFieldEmpty(nameTextField) ||
				!WindowsUtils.isTextFieldEmpty(emailTextField);
	}
	
        // dari tampilan UI, untuk membuat brand baru
	@FXML
	public void onSave() {
		
		try {
                    // servis untuk menyimpan brand
			brandService.save(EntityFactory.createBrand(brand, WindowsUtils.getTextFromTextField(nameTextField), 
														WindowsUtils.getTextFromTextField(emailTextField),  // mengambil text dari UI textfield email
														WindowsUtils.getTextFromTextArea(additionalInfoTextArea)), e -> {  // mengambil text dari text area additional text
															WindowsUtils.createDefaultDialog(container, "Sucess", "Brand data berhasil di simpan.", () -> { stage.close(); }); // membuat dialog keterangan sukses
														}, null);
		} catch (Exception e) {
			e.printStackTrace();
                        // membuat dialog gagal buat brand
			WindowsUtils.createDefaultDialog(container, "Error", "Error saving brand, try again.", () -> {});
		}
	}
	
        // function jika berubah scene atau tampilan, atau ketika di tutup
	@FXML
	public void onCancel() {
		stage.close();
	}
	
        // on help masih blank dan progress
	@FXML
	public void onHelp() {
		
	}

}
