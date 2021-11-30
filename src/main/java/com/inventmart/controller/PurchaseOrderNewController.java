package com.inventmart.controller;

import com.inventmart.model.Item;
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
import com.inventmart.model.Sale;
import com.inventmart.model.Supplier;
import com.inventmart.service.ClientService;
import com.inventmart.service.ProductService;
import com.inventmart.service.ProductTransactionService;
import com.inventmart.util.EntityFactory;
import com.inventmart.util.ValidatorUtils;
import com.inventmart.util.WindowsUtils;

import com.inventmart.service.PurchaseOrderService;
import com.inventmart.service.SaleService;
import com.inventmart.service.SupplierService;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.lang3.RandomStringUtils;

@Controller
public class PurchaseOrderNewController extends BaseController {
	
	public static final String PURCHASE_ORDER_KEY = "purchase_order_key";
	
	public static final String PATH_FXML = "/fxml/new_purchase_order.fxml";
	public static final String NEW_PURCHASE_ORDER_TITLE_KEY = "new_po.title";
	public static final String PATH_ICON = WindowsUtils.ICON_APP_PATH;
	
	@FXML
	private StackPane container;

        @FXML
	private JFXTextField qtyTextField;
	
	
	
	@FXML
	private JFXTextArea noteTextArea;
	
        @FXML
	private JFXComboBox<Product> itemsComboBox; 
        
        @FXML
	private JFXComboBox<Supplier> supplierComboBox;
        
        @FXML
	private JFXComboBox<String> statusComboBox;        

	@FXML
	private JFXTextField priceTextField;
	
	@FXML
	private JFXButton saveButton;
	
	@FXML
	private JFXButton cancelButton;
	
	@FXML
	private JFXButton helpButton;
	
        @FXML
        private Label purchaseCode;
        
        @FXML
        private Label totalLabel;        
        
       
	
	private PurchaseOrder purchaseOrder;
        
	private ProductTransaction productTransaction;
        
	private PurchaseOrderDetail purchaseOrderDetail;

        String code;
        double priceUnit;
        double priceTotal;        
                
	@Autowired
	private ProductService productService;       
        
	@Autowired
	private SupplierService supplierService;   
        
	@Autowired
        private PurchaseOrderService purchaseOrderService;
        
	@Autowired
        private ProductTransactionService productTransactionService;         
        
        //
	@FXML
	private VBox itemsContainer;
	
	@FXML
	private Pane paneBottomItems;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private SaleService saleService;
	
	private int numUnits;
	private double total;
	
	private List<ItemBaseController> itemsControllers;
	private List<Item> items;
	private PurchaseOrder po;        
        
        boolean isEdit = false; 
	
	public <T> void init(Stage stage, HashMap<String, T> parameters) {
		super.init(stage, parameters);
                purchaseOrder = new PurchaseOrder();
                purchaseOrderDetail = new PurchaseOrderDetail();
                productTransaction = new ProductTransaction();
		checkParameters(parameters);
                //render
                enableAll();
                if(isEdit){
                    if(purchaseOrder != null){
                        statusComboBox.setVisible(true);
                        if(purchaseOrder.getStatus().equals("Sukses") || purchaseOrder.getStatus().equals("Gagal")){
                            disableAll();
                        }
                    }
                }else{
                    statusComboBox.setVisible(false);                
                }
                //
		fillComboBoxs();
		validateTextFields();
		watchEvents();
                code = "PO" + RandomStringUtils.randomAlphanumeric(5);
                purchaseCode.setText(isEdit ? purchaseOrder.getCode() : code);
		
	}
        
        public void disableAll(){
            qtyTextField.setDisable(true);
            noteTextArea.setDisable(true);
            itemsComboBox.setDisable(true); 
            supplierComboBox.setDisable(true);
            statusComboBox.setDisable(true);        
            priceTextField.setDisable(true);
            saveButton.setDisable(true);                   
        }
        
        public void enableAll(){
            qtyTextField.setDisable(false);
            noteTextArea.setDisable(false);
            itemsComboBox.setDisable(false); 
            supplierComboBox.setDisable(false);
            statusComboBox.setDisable(false);        
            priceTextField.setDisable(false);
            saveButton.setDisable(false);                   
        }

	@Override
	protected void onClose() {
		purchaseOrderService.onClose();
	}
	
	private <T> void checkParameters( HashMap<String, T> parameters) {
		if (parameters != null) {
			this.purchaseOrder = (PurchaseOrder) parameters.get(PURCHASE_ORDER_KEY);
                        isEdit = true;
			updateTextFields();
		}else{
                    isEdit = false;
                    this.purchaseOrder = null;
                }
	}

        
	private void fillComboBoxs() {
                if(isEdit){
                    onSelectSupplier(purchaseOrder.getItems().get(0).getProduct().getSupplier());
                }else{
                    WindowsUtils.addComboBoxItens(itemsComboBox, productService);

                }
		WindowsUtils.addComboBoxItens(supplierComboBox, supplierService);     
	}

        
        
	private void updateTextFields() {
                   
		WindowsUtils.setTextInTextField(qtyTextField, purchaseOrder.getQty());
		WindowsUtils.setSelectedComboBoxItem(itemsComboBox, purchaseOrder.getProduct());
		WindowsUtils.setTextInTextArea(noteTextArea, purchaseOrder.getNote());
                if(purchaseOrder.getItems().size() > 0){
                    WindowsUtils.setSelectedComboBoxItem(supplierComboBox, purchaseOrder.getItems().get(0).getProduct().getSupplier());   
                }
                if(isEdit){
                    if(purchaseOrder.getStatus().equals("Sukses") || purchaseOrder.getStatus().equals("Gagal"))
                    statusComboBox.setValue(purchaseOrder.getStatus());
                }
	}

        
	private void updateItems() {
		po.getItems().forEach(item -> {
			HashMap<String, Item> parameters = new HashMap<String, Item>();
			parameters.put(ItemBaseController.ITEM_KEY, item);
			
			addPane(parameters);
		});
	}

	@FXML
	private void onAddAnotherItem() {
		addPane(null);
	}
	
	private void addPane(HashMap<String, Item> parameters) {
		itemsContainer.getChildren().add(createPane(parameters));
		paneBottomItems.setLayoutY(paneBottomItems.getLayoutY() + 63.0);
	}
	
	private Pane createPane(HashMap<String, Item> parameters) {
		Scene scene = null;
		
		try {
			FXMLLoader loader = WindowsUtils.loadFxml("/fxml/item_base.fxml");
			scene = new Scene(loader.load());
			ItemBaseController controller = (ItemBaseController) loader.getController();
//			controller.init(this, parameters);
			itemsControllers.add(controller);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return (Pane) scene.lookup("#itemContainer");
	}

 	public void updateValues() {
		this.total = 0;
		this.numUnits = 0;
		
		itemsControllers.forEach(controller -> {
			this.total += controller.getTotal();
			this.numUnits += controller.getQuantity();
		});
	}
	
	public void addItem(Item item) {
		this.items.add(item);
	}       
	
	private void validateTextFields() {
		ValidatorUtils.addNumberOnlyValidator(qtyTextField);                
	}
	

	
	private void watchEvents() {
                WindowsUtils.onComboBoxItemSelected(itemsComboBox, item -> onSelect(item));            
		WindowsUtils.onComboBoxItemSelected(supplierComboBox, item -> onSelectSupplier(item));
		WindowsUtils.onTextFieldTextChange(qtyTextField, (observable, oldValue, newValue) -> onQuantityChange(newValue));                
		WindowsUtils.watchEvents(qtyTextField, v -> watch());            
	}

	private void onSelectSupplier(Supplier supplier) {
		productService.findBySupplierId(supplier.getId(),e -> {
                        if(!isEdit){
                            itemsComboBox.getSelectionModel().clearSelection();
                            itemsComboBox.getItems().clear();
                            itemsComboBox.getItems().addAll((List<Product>) e.getSource().getValue());                            
                        }else{
                            itemsComboBox.getSelectionModel().clearSelection();
                            itemsComboBox.getItems().clear();                            
                            itemsComboBox.getItems().addAll((List<Product>) e.getSource().getValue());
                        }
                        
                }, null);            
	}        
        
        
	private void onSelect(Product product) {
		if (WindowsUtils.isTextFieldEmpty(qtyTextField)) {
                purchaseOrderDetail.setQty(1);
                qtyTextField.setText("1");
		}
                if(product != null){
                    priceUnit = product.getBuyPrice();
                    priceTextField.setText(String.format("Rp %.2f", priceUnit));                    
                }

	}
	
	private void onQuantityChange(String value) {
		if (!value.trim().isEmpty()) {
			purchaseOrderDetail.setQty(Integer.valueOf(value));
                        totalLabel.setText(String.format("Rp %.2f", priceUnit * Integer.parseInt(value)));                        
		}
	}
        
	private void watch() {
		if (!(WindowsUtils.isTextFieldEmpty(qtyTextField))) {
			saveButton.setDisable(false);
		} else {
			saveButton.setDisable(true);
		}
		
	}
	
	@FXML
	public void onSave() {
		
		try {
                        purchaseOrderDetail.setTimestamp(new Date() + "");
                        purchaseOrderDetail.setNote(WindowsUtils.getTextFromTextArea(noteTextArea));
                        String date = new Date().toString();
			purchaseOrderService.save(EntityFactory.createPurchaseOrder(
                            purchaseOrder, 
                            isEdit ? purchaseOrder.getCode() : code, 
                            WindowsUtils.getTextFromTextArea(noteTextArea), 
                            date,
                            isEdit ? (String) WindowsUtils.getSelectedComboBoxItem(statusComboBox) : "Pending",                            
                            items), e -> {
                                    if(isEdit){
                                    System.out.println("=================================== masuk sini");
                                        
                                        if(WindowsUtils.getSelectedComboBoxItem(statusComboBox).equals("Sukses")){
                                            try {
//                                                productTransactionService.save(EntityFactory.createProductTransaction(productTransaction,
//                                                        (long) productSelected.getInitialStock(),
//                                                        ((long) productSelected.getInitialStock() + (long) WindowsUtils.getIntegerFromTextField(qtyTextField)),
//                                                        WindowsUtils.getIntegerFromTextField(qtyTextField),
//                                                        WindowsUtils.getTextFromTextArea(noteTextArea),
//                                                        date,
//                                                        purchaseOrder), b -> {
                                                            WindowsUtils.createDefaultDialog(container, "Sucess", "Purchase Order save with success.", () -> { stage.close(); });
//                                                        }, null);
                                            } catch (Exception ex) {
                                                Logger.getLogger(PurchaseOrderNewController.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }                            
                                    }else{
                                        System.out.println("=================================== masuk sinsssssi");                                        
                                        WindowsUtils.createDefaultDialog(container, "Sucess", "Purchase Order data berhasil di simpan.", () -> { stage.close(); });
                                    }                                
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
