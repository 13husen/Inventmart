package com.inventmart.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.inventmart.model.Item;
import com.inventmart.model.Product;
import com.inventmart.model.Supplier;
import com.inventmart.service.ProductService;
import com.inventmart.util.ValidatorUtils;
import com.inventmart.util.WindowsUtils;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

@Controller
@Scope("prototype")
public class ItemBaseController implements IItemBaseController {
	
	public static final String ITEM_KEY = "item_key";
	public static final String SUPPLIER_KEY = "supplier_key";
        
	public static final String PATH_FXML = "/fxml/item_base.fxml";
	
	@FXML
	private JFXComboBox<Product> itemsComboBox;
	
	@FXML
	private JFXTextField quantityTextField;
	
	@FXML
	private JFXTextField priceTextField;
        

	
	
	@FXML
	private Label totalLabel;
	
	private Item item;
	private Supplier supplier;        
	private SalesNewController salesNewController;
	
	@Autowired
	private ProductService productService;
	
	public void init(SalesNewController salesNewController, HashMap<String, Item> parameters, HashMap<String, Supplier> params) {
		this.salesNewController = salesNewController;
		
		checkParameters(parameters,params);
		validateTextFields();
		watchEvents();
	}
	
	private void checkParameters(HashMap<String, Item> parameters, HashMap<String, Supplier> params ) {
		if (parameters != null) {
			this.item = (Item) parameters.get(ITEM_KEY);
                        disableAll();
			updateTextFields();
		}else{
                        enableAll();
                }
                
                if (params != null){
                        this.supplier = (Supplier) params.get(SUPPLIER_KEY);
                        if(supplier != null){
                            System.out.println("====================" + supplier.getId());
                            productService.findBySupplierId(supplier.getId(),e -> {
    //                                if(!isEdit){
                                        System.out.println("====================" + (List<Product>) e.getSource().getValue());
                                        itemsComboBox.getSelectionModel().clearSelection();
                                        itemsComboBox.getItems().clear();
                                        itemsComboBox.getItems().addAll((List<Product>) e.getSource().getValue());                            
    //                                }else{
    //                                    itemsComboBox.getSelectionModel().clearSelection();
    //                                    itemsComboBox.getItems().clear();                            
    //                                    itemsComboBox.getItems().addAll((List<Product>) e.getSource().getValue());
    //                                }
                            }, null);                                    
                        }else{
                            fillComboBoxs();
                            WindowsUtils.setSelectedComboBoxItem(itemsComboBox, item.getProduct());
                        }
                }
	}
        
        
        public void disableAll(){
            quantityTextField.setDisable(true);
            itemsComboBox.setDisable(true); 
            priceTextField.setDisable(true);
        }
        
        public void enableAll(){
            quantityTextField.setDisable(false);
            itemsComboBox.setDisable(false); 
            priceTextField.setDisable(false);
        }
        
	private void updateTextFields() {
		
		WindowsUtils.setTextInTextField(quantityTextField, item.getQuantity());
		WindowsUtils.setTextInTextField(priceTextField, item.getProduct().getBuyPrice());
		
		WindowsUtils.setTextInLabel(totalLabel, item.getTotalPrice());
                
		
	}
        
        

	private void validateTextFields() {
		ValidatorUtils.addNumberOnlyValidator(quantityTextField);
	}
	
	private void fillComboBoxs() {
		WindowsUtils.addComboBoxItens(itemsComboBox, productService);
	}
	
	private void watchEvents() {
		
		WindowsUtils.onTextFieldTextChange(quantityTextField, (observable, oldValue, newValue) -> onQuantityChange(newValue));
		
		WindowsUtils.onComboBoxItemSelected(itemsComboBox, item -> onSelect(item));
	}
	
	private void onQuantityChange(String value) {
		if (!value.trim().isEmpty()) {
			item.setQuantity(Integer.valueOf(value));
			onUpdate();
		}
		
		
	}

	private void onSelect(Product product) {
		if (item == null)
			item = new Item();
		
		item.setProduct(product);
		
		if (WindowsUtils.isTextFieldEmpty(quantityTextField)) {
			item.setQuantity(1);
			quantityTextField.setText("1");
		}
		
		priceTextField.setText(String.format("RP %.2f", item.getProduct().getBuyPrice()));
		
		salesNewController.addItem(item);
		onUpdate();
	}
	
	private void onUpdate() {
		item.setTotalPrice( (item.getProduct().getBuyPrice() * item.getQuantity()) );
		
		totalLabel.setText(String.format("RP %.2f", item.getTotalPrice()));
		
		salesNewController.updateValues();
	}
	
	@Override
	public double getTotal() {
		return item.getTotalPrice();
	}

	@Override
	public double getQuantity() {
		return item.getQuantity();
	}

}