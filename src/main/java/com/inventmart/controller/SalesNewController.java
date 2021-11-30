package com.inventmart.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.inventmart.model.Client;
import com.inventmart.model.Fone;
import com.inventmart.model.Item;
import com.inventmart.model.Product;
import com.inventmart.model.ProductTransaction;
import com.inventmart.model.PurchaseOrder;
import com.inventmart.model.Sale;
import com.inventmart.model.Supplier;
import com.inventmart.service.ClientService;
import com.inventmart.service.ProductService;
import com.inventmart.service.ProductTransactionService;
import com.inventmart.service.PurchaseOrderService;
import com.inventmart.service.SaleService;
import com.inventmart.service.SupplierService;
import com.inventmart.util.EntityFactory;
import com.inventmart.util.ValidatorUtils;
import com.inventmart.util.WindowsUtils;
import com.jfoenix.controls.JFXButton;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class SalesNewController extends BaseController {
	
	public static final String SALE_KEY = "sale_key";
	public static final String PURCHASE_ORDER_KEY = "purchase_order_key";
	public static final String NEW_PURCHASE_ORDER_TITLE_KEY = "new_po.title";        
        
	public static final String PATH_FXML = "/fxml/new_sale_order.fxml";
	public static final String NEW_SALE_TITLE_KEY = "new_sale.title";
	public static final String PATH_ICON = WindowsUtils.ICON_APP_PATH;
	
	@FXML
    private StackPane container;
	

	@FXML
	private JFXTextArea noteTextArea;
	
        @FXML
        private Label purchaseCode;
	
    
	@FXML
	private Button saveButton;
	
	@FXML
	private VBox itemsContainer;
        
        @FXML
	private JFXComboBox<Supplier> supplierComboBox;       
        
        @FXML
	private JFXComboBox<String> statusComboBox;           
	
	@FXML
	private Pane paneBottomItems;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private SaleService saleService;
        
	@Autowired
	private SupplierService supplierService;           
	
        @FXML
        private JFXButton addItemButton;
                
	private int numUnits;
	private double total;
	
	private List<ItemBaseController> itemsControllers;
	private List<Item> items;
	private Sale sale;
        String code;
        boolean isEdit = false; 
	private PurchaseOrder purchaseOrder;     
        
	@Autowired
        private PurchaseOrderService purchaseOrderService;
        
	@Autowired
        private ProductService productService;        
        
	@Autowired
        private ProductTransactionService productTransactionService;     
        
	private ProductTransaction productTransaction;
        
        HashMap<String, Supplier> supplierItems;
	
	@Override
	public <T> void init(Stage stage, HashMap<String, T> parameters) {
		super.init(stage, parameters);
                isEdit=false;
		supplierItems = new HashMap<String, Supplier>();
		this.itemsControllers = new ArrayList<>();
		this.items = new ArrayList<>();
		this.total = 0;	
		fillComboBoxes();
		checkParameters(parameters);
                enableAll();
                if(isEdit){
                    disableAll();
                    if(purchaseOrder != null){
                        statusComboBox.setVisible(true);
                        if(purchaseOrder.getStatus().equals("Sukses") || purchaseOrder.getStatus().equals("Gagal")){
                            toogleStatus(true,true);
                        }else{
                            toogleStatus(true,false);                            
                        }
                    }
                }else{
                    statusComboBox.setVisible(false);                
                }                
//		validateTextFields();
		watchEvents();
                
               code = "PO" + RandomStringUtils.randomAlphanumeric(5);
               purchaseCode.setText(isEdit ? purchaseOrder.getCode() : code);                
	}
	
       public void disableAll(){
           System.out.println("KETRIGGER DONGGG");
            noteTextArea.setDisable(true);
            supplierComboBox.setDisable(true);
            statusComboBox.setDisable(true);        
            saveButton.setDisable(true);
            addItemButton.setVisible(false);

        }
        
        public void enableAll(){
            noteTextArea.setDisable(false);
            supplierComboBox.setDisable(false);
            statusComboBox.setDisable(false);        
            addItemButton.setVisible(true);
        }
        
        public void toogleStatus(boolean withStatusInvisible,boolean withStatusDisable){
                statusComboBox.setVisible(withStatusInvisible);
                statusComboBox.setDisable(withStatusDisable);
        }
        
        
	@Override
	protected void onClose() {
		clientService.onClose();
		saleService.onClose();
		purchaseOrderService.onClose();
		productTransactionService.onClose();
 		productService.onClose();               
                
	}
	
	private void fillComboBoxes() {
//		WindowsUtils.addComboBoxItens(clientComboBox, clientService);
		WindowsUtils.addComboBoxItens(supplierComboBox, supplierService);    
	}


	private void watchEvents() {
		WindowsUtils.onComboBoxItemSelected(supplierComboBox, item -> onSelectSupplier(item));
		WindowsUtils.onComboBoxItemSelected(statusComboBox, item -> {
                    saveButton.setDisable(false);
                });                
//		WindowsUtils.onComboBoxItemSelected(clientComboBox, item -> onSelectClient(item));
	}
        
	private void onSelectSupplier(Supplier supplier) {
                supplierItems.put(ItemBaseController.SUPPLIER_KEY, supplier);
                supplierComboBox.setDisable(true);
//		productService.findBySupplierId(supplier.getId(),e -> {
//                        if(!isEdit){
//                            itemsComboBox.getSelectionModel().clearSelection();
//                            itemsComboBox.getItems().clear();
//                            itemsComboBox.getItems().addAll((List<Product>) e.getSource().getValue());                            
//                        }else{
//                            itemsComboBox.getSelectionModel().clearSelection();
//                            itemsComboBox.getItems().clear();                            
//                            itemsComboBox.getItems().addAll((List<Product>) e.getSource().getValue());
//                        }
//                        
//                }, null);            
	}        
        

	
	private <T> void checkParameters(HashMap<String, T> parameters) {
		if (parameters != null) {
			this.purchaseOrder = (PurchaseOrder) parameters.get(PURCHASE_ORDER_KEY);
                        isEdit = true;
			updateTextFields();
		} else {
			this.purchaseOrder = null;
		}
	}
	
	private void updateTextFields() {
		
//		WindowsUtils.setTextInLabel(saleCode, sale.getSaleCode());
//		WindowsUtils.setTextInLabel(totalUnitsLabel, sale.getTotalUnits());
//		WindowsUtils.setTextInLabel(totalLabel, sale.getTotal());
		
//		WindowsUtils.setTextInTextField(emailTextField, sale.getEmail());
//		WindowsUtils.setTextInTextField(referenceTextField, sale.getReference());
//		WindowsUtils.setTextInTextArea(messageTextArea, sale.getMessage());
		
//		if (sale.getFone() != null) {
//			WindowsUtils.setTextInTextField(phoneTextField, sale.getFone().getNumber());
//		}
		
//		if (sale.getCliente() != null) {
//			WindowsUtils.setSelectedComboBoxItem(clientComboBox, sale.getCliente());
//		}
		
		WindowsUtils.setTextInTextArea(noteTextArea, purchaseOrder.getNote());
		WindowsUtils.setSelectedComboBoxItem(supplierComboBox, purchaseOrder.getItems().get(0).getProduct().getSupplier());   
                if(isEdit){
                    if(purchaseOrder.getStatus().equals("Sukses") || purchaseOrder.getStatus().equals("Gagal"))
                    statusComboBox.setValue(purchaseOrder.getStatus());
                }

		if (purchaseOrder.getItems() != null) {
			updateItems();
		}
	}
	
	private void updateItems() {
		
		purchaseOrder.getItems().forEach(item -> {
			HashMap<String, Item> parameters = new HashMap<String, Item>();
			parameters.put(ItemBaseController.ITEM_KEY, item);
			addPane(parameters);
		});
	}

	@FXML
	private void onAddAnotherItem() {
            if(!supplierItems.isEmpty()){
                addPane(null);
            }else{
                WindowsUtils.createDefaultDialog(container, "Error", "Mohon di pilih supplier terlebih dahulu !.", () -> {});                
            }
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
			controller.init(this, parameters, supplierItems);
			
			itemsControllers.add(controller);
		} catch (IOException e) {
			e.printStackTrace();
		}

                if(items.size() > 0){
                    saveButton.setDisable(false);
                }else{
                    saveButton.setDisable(true);
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
		
//		totalLabel.setText(String.format("%.2f", this.total));
//		totalUnitsLabel.setText(Integer.toString(this.numUnits));
	}
	
	public void addItem(Item item) {
		this.items.add(item);
	}
	
	
	@FXML
	public void onSave() {
                try{
                        String date = new Date().toString();
			if(!isEdit){
			purchaseOrderService.save(EntityFactory.createPurchaseOrder(
                            purchaseOrder, 
                            isEdit ? purchaseOrder.getCode() : code, 
                            WindowsUtils.getTextFromTextArea(noteTextArea), 
                            date,
                            isEdit ? (String) WindowsUtils.getSelectedComboBoxItem(statusComboBox) : "Pending",                            
                            items), e -> {
                                        WindowsUtils.createDefaultDialog(container, "Sucess", "Purchase Order data berhasil di simpan.", () -> { stage.close(); });                           
                            }, null);
                        }else{
                            purchaseOrderService.setPurchaseOrderStatus(
                                    isEdit ? (String) WindowsUtils.getSelectedComboBoxItem(statusComboBox) : "Pending", 
                                    purchaseOrder.getCode(), 
                                    els -> {
//                                                if(WindowsUtils.getSelectedComboBoxItem(statusComboBox).equals("Sukses")){
//                                                    try {
////                                                        purchaseOrder.getItems().forEach(item -> {
////                                                            long totalQty = ((long) item.getProduct().getInitialStock() + (long) item.getQuantity());
////                                                            try {
////                                                                productTransactionService.save(EntityFactory.createProductTransaction(productTransaction,
////                                                                        (long) item.getProduct().getInitialStock(),
////                                                                        totalQty,
////                                                                        item.getQuantity(),
////                                                                        WindowsUtils.getTextFromTextArea(noteTextArea),
////                                                                        date,
////                                                                        purchaseOrder), b -> {
//////                                                                        productService.setProductQuantity(
//////                                                                                (double)totalQty, 
//////                                                                                (long)item.getProduct().getId(), 
//////                                                                                elas -> {
//////                                                                                        try {
//////                                                                                            WindowsUtils.createDefaultDialog(container, "Sucess", "Purchase Order save with success.", () -> { stage.close(); });
//////                                                                                            stage.close();
//////
//////                                                                                        } catch (Exception e1) {
//////                                                                                                e1.printStackTrace();
//////                                                                                        }
//////
//////                                                                                }, null);                                                                            
////                                                                        }, null);
////                                                            } catch (Exception exs) {
////                                                                exs.printStackTrace();       
////                                                            }
////                                                        });
//                                                    } catch (Exception ex) {
//                                                            ex.printStackTrace();                                                       
//                                                    }
//                                                }else{
                                                    WindowsUtils.createDefaultDialog(container, "Sucess", "Sukses mengubah status Purchase Order.", () -> { stage.close(); });
//                                                }                                                    
         
                                    }, null);                           
                                                        
                        }
                        
		} catch (Exception e) {
                        e.printStackTrace();                    
			WindowsUtils.createDefaultDialog(container, "Error", "Error saving Purchase Order, try again.", () -> {});
		}		
            
            
//		if (sale == null)
//			sale = new Sale();
		
//		Fone phone = null;
//		if (!WindowsUtils.isTextFieldEmpty(phoneTextField)) {
//			phone = EntityFactory.createPhone(WindowsUtils.getTextFromTextField(phoneTextField));
//		}
		
//		try {
//			saleService.save(EntityFactory.createSale(sale, 
//					WindowsUtils.getTextFromLabel(saleCode), 
//					convertToCalendar(shipmentDateDatePicker.getValue()), 
//					convertToCalendar(issueDateDatePicker.getValue()), 
//					WindowsUtils.getTextFromTextField(referenceTextField), 
//					WindowsUtils.getTextFromTextField(emailTextField), 
//					WindowsUtils.getTextFromTextArea(messageTextArea), 
//					(String) WindowsUtils.getSelectedComboBoxItem(stateComboBox), 
//					Integer.valueOf(numUnits), 
//					Double.valueOf(total), 
//					phone, 
//					(Client) WindowsUtils.getSelectedComboBoxItem(clientComboBox), 
//					items, 
//					null), e -> {
//						WindowsUtils.createDefaultDialog(container, "Sucess", "Client data berhasil di simpan.", () -> { stage.close(); });
//					}, null);
//		} catch (Exception e) {
//			e.printStackTrace();
//			WindowsUtils.createDefaultDialog(container, "Error", "Error saving client, try again.", () -> {});
//		}
		
	}
	
	public static Calendar convertToCalendar(LocalDate localDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
		
		return calendar;
	  }
	
	@FXML
	public void onCancel() {
		stage.close();
	}
	
	@FXML
	public void onHelp() {
		
	}
}
