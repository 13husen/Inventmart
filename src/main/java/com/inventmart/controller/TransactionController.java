package com.inventmart.controller;

import com.inventmart.model.ProductTransaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
//import com.inventmart.model.Employee;
import com.inventmart.model.PurchaseOrder;
import com.inventmart.model.dto.ProductTransactionTableDTO;
import com.inventmart.model.dto.PurchaseOrderTableDTO;
import com.inventmart.service.ProductTransactionService;
import com.inventmart.service.PurchaseOrderService;
import com.inventmart.service.ReportsService;
import com.inventmart.service.TableService;
import com.inventmart.util.EntityReportFactory;
import com.inventmart.util.TableUtils;
import com.inventmart.util.WindowsUtils;
import com.jfoenix.controls.JFXSpinner;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Pagination;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperPrint;

@SuppressWarnings("unchecked")
@Controller
public class TransactionController extends BaseController {
	
	public static final String PATH_FXML = "/fxml/transaction.fxml";
	public static final String INVENTORY_TITLE_KEY = "transcation.title";
	public static final String PATH_ICON = WindowsUtils.ICON_APP_PATH;

	@FXML
	private StackPane container;
	
	@FXML
	private JFXTreeTableView<PurchaseOrderTableDTO> purchaseOrderTable;
        
	@FXML
	private JFXTreeTableView<ProductTransactionTableDTO> productTransactionTable;	
	
	@FXML
    private JFXTreeTableColumn<PurchaseOrderTableDTO, String> purchaseOrderCodeColumn;
	
	
//	@FXML
//    private JFXTreeTableColumn<PurchaseOrderTableDTO, String> purchaseOrderIdProductColumn;
	
	@FXML
    private JFXTreeTableColumn<PurchaseOrderTableDTO, String> purchaseOrderNoteColumn;
        
	@FXML
    private JFXTreeTableColumn<PurchaseOrderTableDTO, String> purchaseOrderStatusColumn;        
	
	@FXML
    private JFXTreeTableColumn<PurchaseOrderTableDTO, String> purchaseOrderTimestampColumn;
        
        // Column Product Transaction
	@FXML
    private JFXTreeTableColumn<ProductTransactionTableDTO, String> productTransactionCodeColumn;
	
        
	@FXML
    private JFXTreeTableColumn<ProductTransactionTableDTO, String> productTransactionQtyColumn;        
	
	@FXML
    private JFXTreeTableColumn<ProductTransactionTableDTO, String> productTransactionStockBeforeColumn;

 	@FXML
    private JFXTreeTableColumn<ProductTransactionTableDTO, String> productTransactionStockAfterColumn;
        
	@FXML
    private JFXTreeTableColumn<ProductTransactionTableDTO, String> productTransactionNoteColumn;
	
	@FXML
    private JFXTreeTableColumn<ProductTransactionTableDTO, String> productTransactionTimestampColumn;
        
        
	@FXML
	private Pagination purchaseOrderPagination;
        
	@FXML
	private Pagination productTransactionPagination;        
	
	@FXML
        private JFXTextField purchaseOrderSearchTextField;
        
        
	@FXML
        private JFXTextField productTransactionSearchTextField;
        	
	@Autowired
	private ReportsService reportsService;
        
        
	@FXML
        private JFXButton purchaseOrderEditButton;
        
	@FXML
	private JFXButton poReportGenerate;        
        
        @FXML
        private JFXButton purchaseOrderRemoveButton;
        
    
	@Autowired
	private PurchaseOrderService purchaseOrderService;
      
	private ObservableList<PurchaseOrderTableDTO> purchaseOrderData;
        
	@FXML
	private JFXSpinner poSpinner;           
        
	@Autowired
	private ProductTransactionService productTransactionService;
      
	private ObservableList<ProductTransactionTableDTO> productTransactionData;        
        
      
	private List<TableService> tableService;
	
	public <T> void init(Stage stage, HashMap<String, T> parameters) {
		super.init(stage, parameters);
		
		configureTables();
		configureSearchs();
	}

	@Override
	protected void onClose() {
		purchaseOrderService.onClose();
		productTransactionService.onClose();                
	}
	
	private void configureTables() {
		tableService = new ArrayList<TableService>();
		
		tableService.add(new TableService(() -> configurePurchaseOrderTable()));
//		tableService.add(new TableService(() -> configureProductTransactionTable()));                
		tableService.forEach(s -> { s.start(); });
		
		TableUtils.configureEditAndRemoveState(purchaseOrderTable, purchaseOrderEditButton, purchaseOrderRemoveButton);
	}
	
	private void configureSearchs() {
		TableUtils.configureTableSearch(purchaseOrderSearchTextField, purchaseOrderTable, (purchaseOrderProp, newVal) -> configurePurchaseOrderSearchTest(purchaseOrderProp, newVal));
//		TableUtils.configureTableSearch(productTransactionSearchTextField, productTransactionTable, (productTransactionProp, newVal) -> configureProductTransactionSearchTest(productTransactionProp, newVal));                
	}
	
	
	private boolean configurePurchaseOrderSearchTest(TreeItem<PurchaseOrderTableDTO> purchaseOrderProp, String value) {
		final PurchaseOrderTableDTO po = purchaseOrderProp.getValue();
        return po.getCode().get().contains(value)
            || po.getTimestamp().get().contains(value)
            || po.getNote().get().contains(value);        
	}
        
	private boolean configureProductTransactionSearchTest(TreeItem<ProductTransactionTableDTO> productTransactionProp, String value) {
		final ProductTransactionTableDTO productTrans = productTransactionProp.getValue();
        return productTrans.getIdPo().get().contains(value)
            || productTrans.getNote().get().contains(value);
	}
        
	
	private void configurePurchaseOrderTable() {
		TableUtils.setupColumn(purchaseOrderCodeColumn, PurchaseOrderTableDTO::getCode);
//		TableUtils.setupColumn(purchaseOrderIdProductColumn, PurchaseOrderTableDTO::getIdProduct);
		TableUtils.setupColumn(purchaseOrderNoteColumn, PurchaseOrderTableDTO::getNote);
		TableUtils.setupColumn(purchaseOrderStatusColumn, PurchaseOrderTableDTO::getStatus);                
		TableUtils.setupColumn(purchaseOrderTimestampColumn, PurchaseOrderTableDTO::getTimestamp);
		
		purchaseOrderService.findAll(e -> {
			TableUtils.configureTable((List<PurchaseOrder>) e.getSource().getValue(), purchaseOrderData, purchaseOrderTable, purchaseOrderPagination, en -> createPurchaseOrderData(en));
		}, null);
	}
        
	private void configureProductTransactionTable() {
		TableUtils.setupColumn(productTransactionCodeColumn, ProductTransactionTableDTO::getCode);
		TableUtils.setupColumn(productTransactionQtyColumn, ProductTransactionTableDTO::getQty);                             
		TableUtils.setupColumn(productTransactionStockBeforeColumn, ProductTransactionTableDTO::getStockBefore);
		TableUtils.setupColumn(productTransactionStockAfterColumn, ProductTransactionTableDTO::getStockAfter);                
		TableUtils.setupColumn(productTransactionNoteColumn, ProductTransactionTableDTO::getNote);                
		TableUtils.setupColumn(productTransactionTimestampColumn, ProductTransactionTableDTO::getTimestamp);                

		
		productTransactionService.findAll(e -> {
			TableUtils.configureTable((List<ProductTransaction>) e.getSource().getValue(), productTransactionData, productTransactionTable, productTransactionPagination, en -> createProductTransactionData(en));
		}, null);
	}        

	
	private PurchaseOrderTableDTO createPurchaseOrderData(PurchaseOrder purchaseOrder) {
		PurchaseOrderTableDTO purchaseOrderTableDTO = new PurchaseOrderTableDTO();
		
		purchaseOrderTableDTO.setCode(purchaseOrder.getCode());
		purchaseOrderTableDTO.setQty(purchaseOrder.getQty());
		purchaseOrderTableDTO.setStatus(purchaseOrder.getStatus());
                
//		purchaseOrderTableDTO.setIdProduct(purchaseOrder.getIdProduct());
		purchaseOrderTableDTO.setNote(purchaseOrder.getNote());
		purchaseOrderTableDTO.setTimestamp(purchaseOrder.getTimestamp());
                
//		if (purchaseOrder.getSupplier() != null) {
//			productTableDTO.setSupplier(product.getSupplier().getCompanyName());
//		} else {
//			productTableDTO.setSupplier("--");
//		}

		purchaseOrderTableDTO.setOriginalPurchaseOrder(purchaseOrder);
		
		return purchaseOrderTableDTO;
	}
        
        
	private ProductTransactionTableDTO createProductTransactionData(ProductTransaction productTransaction) {
		ProductTransactionTableDTO productTransactionTableDTO = new ProductTransactionTableDTO();
		
		productTransactionTableDTO.setCode(productTransaction.getPurchaseOrder().getCode());
		productTransactionTableDTO.setQty(productTransaction.getQty());
		productTransactionTableDTO.setStockBefore(productTransaction.getStockBefore());
		productTransactionTableDTO.setStockAfter(productTransaction.getStockAfter());
		productTransactionTableDTO.setNote(productTransaction.getNote());                
		productTransactionTableDTO.setTimestamp(productTransaction.getTimestamp());                
//		if (purchaseOrder.getSupplier() != null) {
//			productTableDTO.setSupplier(product.getSupplier().getCompanyName());
//		} else {
//			productTableDTO.setSupplier("--");
//		}

		productTransactionTableDTO.setOriginalProductTransaction(productTransaction);
		
		return productTransactionTableDTO;
	}        
        

	
	
	@FXML
	private void onReloadPurchaseOrderTable() {
		TableUtils.reloadTable(() -> configurePurchaseOrderTable());
		TableUtils.updateEditAndRemoveButtonState(purchaseOrderTable, purchaseOrderEditButton, purchaseOrderRemoveButton);
	}
        
//	@FXML
//	private void onReloadProductTransactionTable() {
//		TableUtils.reloadTable(() -> configureProductTransactionTable());
//	}
                
	
	
	@FXML
	private void onEditPurchaseOrderTable() throws Exception {
		PurchaseOrderTableDTO purchaseOrderTableValue = purchaseOrderTable.getSelectionModel().selectedItemProperty().get().getValue();
//                                WindowsUtils.openNewWindow(SalesNewController.PATH_FXML, getWindowTitle(SalesNewController.NEW_SALE_TITLE_KEY), SalesNewController.PATH_ICON, null, Modality.APPLICATION_MODAL); 
		TableUtils.editItemFromTable(purchaseOrderTable, purchaseOrderTableValue.getOriginalPurchaseOrder(), SalesNewController.PURCHASE_ORDER_KEY, SalesNewController.PATH_FXML, getWindowTitle(SalesNewController.NEW_PURCHASE_ORDER_TITLE_KEY), PurchaseOrderNewController.PATH_ICON);
	}
        
        
	@FXML
	private void onRemovePurchaseOrderTable() {
		PurchaseOrderTableDTO purchaseOrderTableValue = purchaseOrderTable.getSelectionModel().selectedItemProperty().get().getValue();
		WindowsUtils.createDefaultDialog(container, 
										 "Hapus Purchase Order", "Anda yakin hapus " + purchaseOrderTableValue.getOriginalPurchaseOrder().getCode() + " ?", 
										 () -> { 
                                                                                        TableUtils.removeItemFromTable(purchaseOrderService, purchaseOrderTableValue.getOriginalPurchaseOrder().getId(), purchaseOrderTable, purchaseOrderData, container);
                                                                                        WindowsUtils.createDefaultDialog(container, "Sukses", "Berhasil hapus data.", () -> { onReloadPurchaseOrderTable(); });
                                                                                 });
	}
        
	@FXML
	public void onNewPurchaseOrder() throws Exception {
                WindowsUtils.openNewWindow(SalesNewController.PATH_FXML, getWindowTitle(SalesNewController.NEW_SALE_TITLE_KEY), SalesNewController.PATH_ICON, null, Modality.APPLICATION_MODAL);            
//		WindowsUtils.openNewWindow(PurchaseOrderNewController.PATH_FXML, getWindowTitle(PurchaseOrderNewController.NEW_PURCHASE_ORDER_TITLE_KEY), PurchaseOrderNewController.PATH_ICON, null, Modality.APPLICATION_MODAL);
	}
        
	@FXML
	public void poReport() throws Exception {
		purchaseOrderService.findAll(e -> {
			createReport(EntityReportFactory.createPos((List<PurchaseOrder>) e.getSource().getValue()), "/reports/po_template.jrxml", poReportGenerate, poSpinner);
		}, null);
	}
        
        
	private <T> void createReport(List<T> data, String reportTemplatePath, JFXButton reportGenerate, JFXSpinner spinner) {
		reportsService.createJasperPrint(reportTemplatePath, data, e -> {
			reportGenerate.setDisable(false);
			spinner.setVisible(false);
			
			JasperPrint jasperPrint = (JasperPrint) e.getSource().getValue();
			
			try {
				HashMap<String, JasperPrint> parameters = new HashMap<String, JasperPrint>();
				parameters.put(ReportViewerController.JASPER_PRINT, jasperPrint);
				
				WindowsUtils.openNewWindow(ReportViewerController.PATH_FXML, getWindowTitle(ReportViewerController.REPORT_VIEWER_TITLE_KEY), ReportViewerController.PATH_ICON, parameters, Modality.APPLICATION_MODAL);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		},
		e -> {
			reportGenerate.setDisable(true);
			spinner.setVisible(true);
		});
	}
	        	
        
}
