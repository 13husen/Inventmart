package com.inventmart.controller;

import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.inventmart.model.Brand;
import com.inventmart.model.Product;
import com.inventmart.model.ProductType;
import com.inventmart.model.Supplier;
import com.inventmart.service.BrandService;
import com.inventmart.service.ProductService;
import com.inventmart.service.ProductTypeService;
import com.inventmart.service.SupplierService;
import com.inventmart.util.EntityFactory;
import com.inventmart.util.ValidatorUtils;
import com.inventmart.util.WindowsUtils;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

@Controller
public class ProductNewController extends BaseController {
	
	public static final String PRODUCT_KEY = "product_key";
	
	public static final String PATH_FXML = "/fxml/new_product.fxml";
	public static final String NEW_PRODUCT_TITLE_KEY = "new_product.title";
	public static final String PATH_ICON = WindowsUtils.ICON_APP_PATH;
	
	@FXML
	private StackPane container;
	
	@FXML
	private JFXTextField productNameTextField;
	
	@FXML
	private JFXTextField skuTextField;
	
	@FXML
	private JFXTextArea descriptionTextArea;
	
	@FXML
	private JFXComboBox<Supplier> supplierComboBox;
	
	@FXML
	private JFXComboBox<ProductType> productTypeComboBox;
	
	@FXML
	private JFXComboBox<Brand> brandComboBox;
	
	
	@FXML
	private JFXTextField initialCostPriceTextField;
	
	@FXML
	private JFXTextField buyPriceTextField;
	
	@FXML
	private JFXTextField wholesalePriceTextField;
	
	@FXML
	private JFXTextField retailPriceTextField;
	
	@FXML
	private JFXTextField weightTextField;
	
	@FXML
	private JFXTextField initialStockTextField;
	
	@FXML
	private JFXButton saveButton;
	
	@FXML
	private JFXButton cancelButton;
	
	@FXML
	private JFXButton helpButton;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private BrandService brandService;
	
	@Autowired
	private ProductTypeService productTypeService;
	
	private Product product;
	
	public <T> void init(Stage stage, HashMap<String, T> parameters) {
		super.init(stage, parameters);
		
		fillComboBoxes();
		checkParameters(parameters);
		validateTextFields();
		watchEvents();
		
	}

	@Override
	protected void onClose() {
		productService.onClose();
		supplierService.onClose();
		brandService.onClose();
		productTypeService.onClose();
	}
	
	private <T> void checkParameters( HashMap<String, T> parameters) {
		if (parameters != null) {
			this.product = (Product) parameters.get(PRODUCT_KEY);
                        initialStockTextField.setDisable(true);
			updateTextFields();
		}else{
                    this.product = null;
                }
	}
	
	private void updateTextFields() {
		
		WindowsUtils.setTextInTextField(skuTextField, product.getSku());
		WindowsUtils.setTextInTextField(productNameTextField, product.getName());
		WindowsUtils.setTextInTextArea(descriptionTextArea, product.getDescription());
		WindowsUtils.setTextInTextField(initialCostPriceTextField, product.getInitialCostPrice());
		WindowsUtils.setTextInTextField(buyPriceTextField, product.getBuyPrice());
		WindowsUtils.setTextInTextField(wholesalePriceTextField, product.getWholesalePrice());
		WindowsUtils.setTextInTextField(retailPriceTextField, product.getRetailPrice());
		WindowsUtils.setTextInTextField(weightTextField, product.getWeight());
		WindowsUtils.setTextInTextField(initialStockTextField, product.getInitialStock());
		
		WindowsUtils.setSelectedComboBoxItem(brandComboBox, product.getBrand());
		WindowsUtils.setSelectedComboBoxItem(productTypeComboBox, product.getProductType());
		WindowsUtils.setSelectedComboBoxItem(supplierComboBox, product.getSupplier());
	}
	
	private void validateTextFields() {
		ValidatorUtils.addRequiredValidator(productNameTextField, "Product Name is Required!");
		ValidatorUtils.addRequiredValidator(skuTextField, "SKU is Required!");
		ValidatorUtils.addRequiredValidator(initialCostPriceTextField, "Initial Cost Price is Required!");
		ValidatorUtils.addRequiredValidator(buyPriceTextField, "Buy Price is Required!");
		ValidatorUtils.addRequiredValidator(initialStockTextField, "Initial Stock On Hand is Required!");
		
		ValidatorUtils.addNumberOnlyValidator(initialCostPriceTextField);
		ValidatorUtils.addNumberOnlyValidator(buyPriceTextField);
		ValidatorUtils.addNumberOnlyValidator(wholesalePriceTextField);
		ValidatorUtils.addNumberOnlyValidator(retailPriceTextField);
		ValidatorUtils.addNumberOnlyValidator(weightTextField);
		ValidatorUtils.addNumberOnlyValidator(initialStockTextField);

		WindowsUtils.validateTextField(productNameTextField);
		WindowsUtils.validateTextField(skuTextField);
		WindowsUtils.validateTextField(initialStockTextField);
		WindowsUtils.validateTextField(initialStockTextField);
	}
	
	private void watchEvents() {
		WindowsUtils.watchEvents(productNameTextField, v -> watch());
		WindowsUtils.watchEvents(initialCostPriceTextField, v -> watch());
		WindowsUtils.watchEvents(initialStockTextField, v -> watch());
	}
	
	
	private void fillComboBoxes() {
		WindowsUtils.addComboBoxItens(brandComboBox, brandService);
		WindowsUtils.addComboBoxItens(productTypeComboBox, productTypeService);
		WindowsUtils.addComboBoxItens(supplierComboBox, supplierService);
	}
	
	private void watch() {
		if (!(WindowsUtils.isTextFieldEmpty(productNameTextField)) &&
			!(WindowsUtils.isTextFieldEmpty(initialStockTextField)) &&
			!(WindowsUtils.isTextFieldEmpty(initialCostPriceTextField))) {
			saveButton.setDisable(false);
		} else {
			saveButton.setDisable(true);
		}
		
	}
	
	@FXML
	public void onSave() {
		
		try {
			productService.save(EntityFactory.createProduct(product, WindowsUtils.getTextFromTextField(skuTextField), 
															WindowsUtils.getTextFromTextField(productNameTextField), 
															WindowsUtils.getTextFromTextArea(descriptionTextArea), 
															WindowsUtils.getDoubleFromTextField(initialCostPriceTextField), 
															WindowsUtils.getDoubleFromTextField(buyPriceTextField), 
															WindowsUtils.getDoubleFromTextField(wholesalePriceTextField), 
															WindowsUtils.getDoubleFromTextField(retailPriceTextField), 
															WindowsUtils.getDoubleFromTextField(weightTextField), 
															WindowsUtils.getDoubleFromTextField(initialStockTextField), 
															product == null ? new Date() : product.getCreatedAt(), 
															new Date(), 
															(Supplier) WindowsUtils.getSelectedComboBoxItem(supplierComboBox), 
															(Brand) WindowsUtils.getSelectedComboBoxItem(brandComboBox), 
															(ProductType) WindowsUtils.getSelectedComboBoxItem(productTypeComboBox), 
															null, 
															null), e -> {
																WindowsUtils.createDefaultDialog(container, "Sucess", "Product data berhasil di simpan.", () -> { stage.close(); });
															}, null);
		} catch (Exception e) {
			WindowsUtils.createDefaultDialog(container, "Error", "Error saving product, try again.", () -> {});
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
