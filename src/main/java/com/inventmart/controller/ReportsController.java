package com.inventmart.controller;

import com.inventmart.model.Invoice;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.inventmart.model.Product;
import com.inventmart.model.Sale;
import com.inventmart.model.Supplier;
import com.inventmart.model.User;
import com.inventmart.model.dto.InvoiceTableDTO;
import com.inventmart.model.dto.ProductTableDTO;
import com.inventmart.model.dto.SalesTableDTO;
import com.inventmart.model.dto.SupplierTableDTO;
import com.inventmart.service.InvoiceService;
import com.inventmart.service.ProductService;
import com.inventmart.service.ReportsService;
import com.inventmart.service.SaleService;
import com.inventmart.service.SupplierService;
import com.inventmart.service.TableService;
import com.inventmart.service.UserService;
import com.inventmart.util.EntityReportFactory;
import com.inventmart.util.TableUtils;
import com.inventmart.util.WindowsUtils;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperPrint;

@SuppressWarnings("unchecked")
@Controller
public class ReportsController extends BaseController{

	public static final String PATH_FXML = "/fxml/reports.fxml";
	public static final String REPORTS_TITLE_KEY = "reports.title";
	public static final String PATH_ICON = WindowsUtils.ICON_APP_PATH;
	
	@FXML
	private JFXTreeTableView<SalesTableDTO> salesTable;
	
	@FXML
	private JFXTreeTableView<ProductTableDTO> productsTable;
	
	@FXML
	private JFXTreeTableView<SupplierTableDTO> suppliersTable;

	@FXML
	private JFXTreeTableView<InvoiceTableDTO> invoiceTable;        
        
        
	@FXML
	private JFXTreeTableColumn<SalesTableDTO, String> saleCodeColumn;
	
	@FXML
	private JFXTreeTableColumn<SalesTableDTO, String> saleIssueDateColumn;
	
	@FXML
	private JFXTreeTableColumn<SalesTableDTO, String> saleShipmentDateColumn;
	
	@FXML
	private JFXTreeTableColumn<SalesTableDTO, String> saleTotalColumn;
	
	@FXML
	private JFXTreeTableColumn<SalesTableDTO, String> saleNumItemsColumn;
	
	@FXML
	private JFXTreeTableColumn<SalesTableDTO, String> saleClientNameColumn;
	
	@FXML
    private JFXTreeTableColumn<ProductTableDTO, String> productSkuColumn;
	
	@FXML
    private JFXTreeTableColumn<ProductTableDTO, String> productSupplierColumn;
	
	@FXML
    private JFXTreeTableColumn<ProductTableDTO, String> productBuyPriceColumn;
	
	@FXML
    private JFXTreeTableColumn<ProductTableDTO, String> productProductTypeColumn;
	
	@FXML
    private JFXTreeTableColumn<ProductTableDTO, String> productDescriptionColumn;
	
	@FXML
    private JFXTreeTableColumn<SupplierTableDTO, String> supplierNameColumn;
	
	@FXML
    private JFXTreeTableColumn<SupplierTableDTO, String> supplierEmailColumn;
	
	@FXML
    private JFXTreeTableColumn<SupplierTableDTO, String> supplierAddresColumn;
        
        
	@FXML
    private JFXTreeTableColumn<InvoiceTableDTO, String> invoiceInvoiceCodeColumn;

	@FXML
    private JFXTreeTableColumn<InvoiceTableDTO, String> invoiceSupplierIdColumn;        
        

	@FXML
    private JFXTreeTableColumn<InvoiceTableDTO, String> invoiceTermsColumn; 
        
	@FXML
    private JFXTreeTableColumn<InvoiceTableDTO, String> invoiceNoteColumn;
        
	@FXML
    private JFXTreeTableColumn<InvoiceTableDTO, String> invoiceTimestampColumn;
        
	@FXML
    private JFXTreeTableColumn<InvoiceTableDTO, String> invoicePurchaseOrderCodeColumn;                
        
        
        
	@FXML
	private Pagination salesPagination;
	
	@FXML
	private Pagination productsPagination;
	
	@FXML
	private Pagination employeesPagination;
	
	@FXML
	private Pagination suppliersPagination;
        
	@FXML
	private Pagination invoicePagination;        
	
	@FXML
	private Label totalProductLabel;
	
	@FXML
	private Label totalInvoiceLabel;
	
	@FXML
	private Label totalEmployeeLabel;
	
	@FXML
	private Label totalSuppliersLabel;
	
	@FXML
	private JFXSpinner salesSpinner;
	
	@FXML
	private JFXSpinner productsSpinner;
        
	@FXML
	private JFXSpinner invoiceSpinner;        
	
	@FXML
	private JFXSpinner employeesSpinner;
	
	@FXML
	private JFXSpinner suppliersSpinner;
	
	@FXML
	private JFXButton salesReportGenerate;
        
	
	@FXML
	private JFXButton productsReportGenerate;
	
	@FXML
	private JFXButton employeesReportGenerate;
	
	@FXML
	private JFXButton supplierReportGenerate;
	
	@FXML
	private JFXButton invoiceReportGenerate;        
        
        @FXML
        private JFXButton invoice_add; 
        
	@Autowired
	private UserService userService;
        
	@Autowired
	private InvoiceService invoiceService;        
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private SaleService saleService;
        
	
	@Autowired
	private ReportsService reportsService;
	
	private ObservableList<ProductTableDTO> productsData;
	private ObservableList<SupplierTableDTO> suppliersData;
	private ObservableList<SalesTableDTO> salesData;
	private ObservableList<InvoiceTableDTO> invoiceData;        
	
	private List<TableService> tableService;
	
	@Override
	public <T> void init(Stage stage, HashMap<String, T> parameters) {
		super.init(stage, parameters);
                    	userService.finUserSignIn(e -> {
    			User user = (User) e.getSource().getValue();
        		if (user != null) {
                            if(user.getRoles().size() > 0){
                                String role = user.getRoles().get(0).getRole();
                                if(role.equals("ADMIN")){                           
                                      invoice_add.setVisible(true);
                                }else if(role.equals("USER")) {
                                      invoice_add.setVisible(false);                                    
                                }
                            }
        		}
    		
                        }, null);                    		
                
		configureLabels();
		configureTables();
	}

	@Override
	protected void onClose() {
                invoiceService.onClose();                
		productService.onClose();
		supplierService.onClose();
		saleService.onClose();
		userService.onClose();
		reportsService.onClose();
	}
	
	private void configureLabels() {
		productService.getTotalProduct(e -> { configureLabel(totalProductLabel, (Long) e.getSource().getValue()); }, null);
		invoiceService.getTotalInvoice(e -> { configureLabel(totalInvoiceLabel, (Long) e.getSource().getValue()); }, null);
		supplierService.getTotalSuppliers(e -> { configureLabel(totalSuppliersLabel, (Long) e.getSource().getValue()); }, null);
	}
	
	private void configureLabel(Label label, long value) {
		WindowsUtils.setTextInLabel(label, String.valueOf(value));
	}
	
	private void configureTables() {
		
		tableService = new ArrayList<TableService>();
		
		tableService.add(new TableService(() -> configureProductTable()));
		tableService.add(new TableService(() -> configureSupplierTable()));
		tableService.add(new TableService(() -> configureInvoiceTable()));                
		
		tableService.forEach(s -> { s.start(); });
	}
	
	private void configureProductTable() {
		TableUtils.setupColumn(productSkuColumn, ProductTableDTO::getSku);
		TableUtils.setupColumn(productSupplierColumn, ProductTableDTO::getSupplier);
		TableUtils.setupColumn(productBuyPriceColumn, ProductTableDTO::getBuyPrice);
		TableUtils.setupColumn(productProductTypeColumn, ProductTableDTO::getProductType);
		TableUtils.setupColumn(productDescriptionColumn, ProductTableDTO::getDescription);
		
		productService.findAll(e -> {
			TableUtils.configureTable((List<Product>) e.getSource().getValue(), productsData, productsTable, productsPagination, en -> createProductData(en));
		}, null);
	}
        
	private void configureInvoiceTable() {
		TableUtils.setupColumn(invoiceInvoiceCodeColumn, InvoiceTableDTO::getInvoiceCode);
		TableUtils.setupColumn(invoicePurchaseOrderCodeColumn, InvoiceTableDTO::getPurchaseOrderCode);                
		TableUtils.setupColumn(invoiceTermsColumn, InvoiceTableDTO::getTerms);
		TableUtils.setupColumn(invoiceNoteColumn, InvoiceTableDTO::getNote);
		TableUtils.setupColumn(invoiceTimestampColumn, InvoiceTableDTO::getTimestamp);
		
		invoiceService.findAll(e -> {
			TableUtils.configureTable((List<Invoice>) e.getSource().getValue(), invoiceData, invoiceTable, invoicePagination, en -> createInvoiceData(en));
		}, null);
	}
        
	
	private void configureSupplierTable() {
		TableUtils.setupColumn(supplierNameColumn, SupplierTableDTO::getCompanyName);
		TableUtils.setupColumn(supplierEmailColumn, SupplierTableDTO::getEmail);
		TableUtils.setupColumn(supplierAddresColumn, SupplierTableDTO::getAdress);
		
		supplierService.findAll(e -> {
			TableUtils.configureTable((List<Supplier>) e.getSource().getValue(), suppliersData, suppliersTable, suppliersPagination, en -> createSupplierData(en));
		}, null);
	}
	

	
	private ProductTableDTO createProductData(Product product) {
		ProductTableDTO productTableDTO = new ProductTableDTO();
		
		productTableDTO.setSku(product.getSku());
		productTableDTO.setDescription(product.getDescription());
		productTableDTO.setBuyPrice(product.getBuyPrice());
		
		if (product.getSupplier() != null) {
			productTableDTO.setSupplier(product.getSupplier().getCompanyName());
		} else {
			productTableDTO.setSupplier("--");
		}
		
		if (product.getProductType() != null) {
			productTableDTO.setProductType(product.getProductType().getName());
		} else {
			productTableDTO.setProductType("--");
		}
		
		productTableDTO.setOriginalProduct(product);
		
		return productTableDTO;
	}

	private InvoiceTableDTO createInvoiceData(Invoice invoice) {
		InvoiceTableDTO invoiceTableDTO = new InvoiceTableDTO();
		invoiceTableDTO.setInvoiceCode(invoice.getInvoiceCode());
                try{
                    invoiceTableDTO.setPurchaseOrderCode(invoice.getPurchaseOrder().getCode());
                }catch(Exception es){
                    invoiceTableDTO.setPurchaseOrderCode("-");                    
                }
		invoiceTableDTO.setTerms(invoice.getTerms());
		invoiceTableDTO.setNote(invoice.getNote());
		invoiceTableDTO.setTimestamp(invoice.getTimestamp());

                invoiceTableDTO.setOriginalInvoice(invoice);
		
		return invoiceTableDTO;
		
	}
	        
        
	private SupplierTableDTO createSupplierData(Supplier supplier) {
		SupplierTableDTO supplierTableDTO = new SupplierTableDTO();
		
		supplierTableDTO.setCompanyName(supplier.getCompanyName());
		supplierTableDTO.setEmail(supplier.getEmail());
		
		if (supplier.getAddres() != null) {
			supplierTableDTO.setAdress(supplier.getAddres());
		} else {
			supplierTableDTO.setAdress("--");
		}
		
		supplierTableDTO.setOriginalSupplier(supplier);
		
		return supplierTableDTO;
		
	}
	

	
	private <T> void createReport(List<T> data, String reportTemplatePath, JFXButton reportGenerate, JFXSpinner spinner) {
		reportsService.createJasperPrint(reportTemplatePath, data, e -> {
			reportGenerate.setDisable(false);
			spinner.setVisible(false);
			
			JasperPrint jasperPrint = (JasperPrint) e.getSource().getValue();
			
			try {
				HashMap<String, JasperPrint> parameters = new HashMap<String, JasperPrint>();
				parameters.put(ReportViewerController.JASPER_PRINT, jasperPrint);
				
				System.out.println(jasperPrint);
				
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
	
	@FXML
	public void salesReport() throws Exception {
		
		saleService.findAll(e -> {
			createReport(EntityReportFactory.createSales((List<Sale>) e.getSource().getValue()), "/reports/sales_template.jrxml", salesReportGenerate, salesSpinner);
		}, null);
	}
	
	@FXML
	public void productsReport() throws Exception {
		productService.findAll(e -> {
			createReport(EntityReportFactory.createProducts((List<Product>) e.getSource().getValue()), "/reports/products_template.jrxml", productsReportGenerate, productsSpinner);
		}, null);
	}
	

	@FXML
	public void suppliersReport() throws Exception {
		supplierService.findAll(e -> {
			createReport(EntityReportFactory.createSuppliers((List<Supplier>) e.getSource().getValue()), "/reports/suppliers_template.jrxml", supplierReportGenerate, suppliersSpinner);
		}, null);
	}
        

	@FXML
	public void invoiceReport() throws Exception {
		invoiceService.findAll(e -> {
			createReport(EntityReportFactory.createInvoices((List<Invoice>) e.getSource().getValue()), "/reports/invoice_template.jrxml", invoiceReportGenerate, invoiceSpinner);
		}, null);
	}
        
	
	@FXML
	public void onReloadProductTable() {
		TableUtils.reloadTable(() -> configureProductTable());
	}
	
	
	@FXML
	public void onReloadSupplierTable() {
		TableUtils.reloadTable(() -> configureSupplierTable());
	}
        
	@FXML
	public void onReloadInvoiceTable() {
		TableUtils.reloadTable(() -> configureInvoiceTable());
	}  
        
	@FXML
	public void onNewInvoice() throws Exception {
		WindowsUtils.openNewWindow(InvoiceNewController.PATH_FXML, getWindowTitle(InvoiceNewController.NEW_INVOICE_TITLE_KEY), InvoiceNewController.PATH_ICON, null, Modality.APPLICATION_MODAL);
	}
        
        
}
