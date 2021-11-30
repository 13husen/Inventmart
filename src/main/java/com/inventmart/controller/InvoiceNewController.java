package com.inventmart.controller;

import com.inventmart.model.Invoice;
import com.inventmart.model.Product;
import com.inventmart.model.ProductTransaction;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.inventmart.model.PurchaseOrder;
import com.inventmart.model.PurchaseOrderDetail;
import com.inventmart.model.Supplier;
import com.inventmart.service.InvoiceService;
import com.inventmart.service.ProductService;
import com.inventmart.service.ProductTransactionService;
import com.inventmart.util.EntityFactory;
import com.inventmart.util.ValidatorUtils;
import com.inventmart.util.WindowsUtils;

import com.inventmart.service.PurchaseOrderService;
import com.inventmart.service.SupplierService;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.commons.lang3.RandomStringUtils;

@Controller
public class InvoiceNewController extends BaseController {
	
	public static final String INVOICE_KEY = "invoice_key";
	
	public static final String PATH_FXML = "/fxml/new_invoice.fxml";
	public static final String NEW_INVOICE_TITLE_KEY = "new_invoice.title";
	public static final String PATH_ICON = WindowsUtils.ICON_APP_PATH;
	
	@FXML
	private StackPane container;


	@FXML
	private JFXTextArea noteTextArea;
	
        @FXML
	private JFXComboBox<String> termsComboBox; 
        
        @FXML
	private JFXComboBox<PurchaseOrder> poCodeComboBox;         
        
	@FXML
	private JFXTextField poCodeTextField;
	
	@FXML
	private JFXButton saveButton;
	
	@FXML
	private JFXButton cancelButton;
	
	@FXML
	private JFXButton helpButton;
	
        @FXML
        private Label invoiceCode;
        
        @FXML
        private Label poCodeLabel;
	
	private PurchaseOrder purchaseOrder;
        
        private Invoice invoice;
        
	private ProductTransaction productTransaction;


                
	@Autowired
	private ProductService productService;       
        
	@Autowired
	private SupplierService supplierService;   
        
	@Autowired
	private InvoiceService invoiceService;           
        
	@Autowired
        private PurchaseOrderService purchaseOrderService;
        
	@Autowired
        private ProductTransactionService productTransactionService;         
        
        boolean isEdit = false; 
        String code;
        
	public <T> void init(Stage stage, HashMap<String, T> parameters) {
		super.init(stage, parameters);
                purchaseOrder = new PurchaseOrder();
                productTransaction = new ProductTransaction();
		checkParameters(parameters);
		fillComboBoxs();                
                //render
                //
		watchEvents();
                code = "INV" + RandomStringUtils.randomAlphanumeric(5);
                invoiceCode.setText(isEdit ? purchaseOrder.getCode() : code);
		
	}
        
	private void fillComboBoxs() {
		WindowsUtils.addComboBoxItens(poCodeComboBox, purchaseOrderService);     
	}        


	@Override
	protected void onClose() {
		purchaseOrderService.onClose();
	}
	
	private <T> void checkParameters( HashMap<String, T> parameters) {
		if (parameters != null) {
			this.invoice = (Invoice) parameters.get(INVOICE_KEY);
                        isEdit = true;
			updateTextFields();
		}else{
                    isEdit = false;
                    this.invoice = null;
                }
	}

        

        
        
	private void updateTextFields() {
                   
		WindowsUtils.setTextInTextArea(noteTextArea, invoice.getNote());
                termsComboBox.setValue(invoice.getTerms());
                if(isEdit){
                        poCodeComboBox.setDisable(true);
                }else{
                    poCodeComboBox.setDisable(false);
                }
	}
	

	
	private void watchEvents() {
		WindowsUtils.watchEvents(poCodeComboBox, v -> watch());            
	}

        

	private void watch() {
		if (!(WindowsUtils.isComboBoxSelected(poCodeComboBox))) {
			saveButton.setDisable(false);
		} else {
			saveButton.setDisable(true);
		}
		
	}
	
	@FXML
	public void onSave() {
		try {
                        PurchaseOrder po = (PurchaseOrder) WindowsUtils.getSelectedComboBoxItem(poCodeComboBox);
                    
                        String date = new Date().toString();
			invoiceService.save(EntityFactory.createInvoice(invoice, 
                            isEdit ? invoice.getInvoiceCode() : code, 
                            WindowsUtils.getSelectedComboBoxItem(termsComboBox), 
                            WindowsUtils.getTextFromTextArea(noteTextArea),
                            date,
                            po), e -> {
                                        WindowsUtils.createDefaultDialog(container, "Sucess", "Invoice data berhasil di simpan.", () -> { stage.close(); });
                            }, null);

                        
		} catch (Exception e) {
                        e.printStackTrace();                    
			WindowsUtils.createDefaultDialog(container, "Error", "Error saving Purchase Order, try again.", () -> {});
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
