package com.inventmart.util;

import com.inventmart.model.Invoice;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.inventmart.model.Product;
import com.inventmart.model.PurchaseOrder;
import com.inventmart.model.Sale;
import com.inventmart.model.Supplier;
import com.inventmart.model.dto.EmployeeReportDTO;
import com.inventmart.model.dto.InvoiceReportDTO;
import com.inventmart.model.dto.ProductReportDTO;
import com.inventmart.model.dto.PurchaseOrderReportDTO;
import com.inventmart.model.dto.SalesReportDTO;
import com.inventmart.model.dto.SupplierReportDTO;

public class EntityReportFactory {
	
	public static List<SalesReportDTO> createSales(List<Sale> sales) {
		List<SalesReportDTO> salesReport = new ArrayList<>();
		
		for (Sale sale : sales) {
			salesReport.add(createSale(sale));
		}
		
		return salesReport;
	}
	
	public static SalesReportDTO createSale(Sale sale) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		
		SalesReportDTO salesReportDTO = new SalesReportDTO();
		
		salesReportDTO.setCode(isEmpty(sale.getSaleCode()));
		salesReportDTO.setIssueDate(isEmpty(sale.getIssueDate(), formatter.format(sale.getIssueDateFormatter().getTime())));
		salesReportDTO.setShipmentDate(isEmpty(sale.getShipmentDate(), formatter.format(sale.getShipmentDateFormatter().getTime())));
		salesReportDTO.setTotalUnits(isEmpty(sale.getTotalUnits()));
		salesReportDTO.setTotal(isEmpty(sale.getTotal()));
		salesReportDTO.setClient(isEmpty(sale.getCliente(), sale.getCliente().getUser().getName()));
		
		return salesReportDTO;
	}
	
	public static List<ProductReportDTO> createProducts(List<Product> products) {
		List<ProductReportDTO> productsReport = new ArrayList<>();
		
		for (Product p : products) {
			productsReport.add(createProduct(p));
		}
		
		return productsReport;
	}
	
	public static ProductReportDTO createProduct(Product product) {
		ProductReportDTO productReportDTO = new ProductReportDTO();
		
		productReportDTO.setSku(isEmpty(product.getSku()));
		productReportDTO.setDescription(isEmpty(product.getDescription()));
		productReportDTO.setBuyPrice(isEmpty(product.getBuyPrice()));
		productReportDTO.setSupplier(product.getSupplier() == null ? "---" : isEmpty(product.getSupplier().getCompanyName()));
		productReportDTO.setProductType(product.getProductType() == null ? "---" : isEmpty(product.getProductType().getName()));
		
		return productReportDTO;
	}
	
        
	public static InvoiceReportDTO createInvoice(Invoice invoice) {
		InvoiceReportDTO invoiceReportDTO = new InvoiceReportDTO();
		
		invoiceReportDTO.setCode(isEmpty(invoice.getInvoiceCode()));
                try{
        		invoiceReportDTO.setPoCode(isEmpty(invoice.getPurchaseOrder().getCode()));
                }catch(Exception e){
        		invoiceReportDTO.setPoCode("-");                        
                }
		invoiceReportDTO.setTerms(isEmpty(invoice.getTerms()));
		invoiceReportDTO.setNote(isEmpty(invoice.getNote()));                
		invoiceReportDTO.setTimestamp(isEmpty(invoice.getTimestamp()));
                
		return invoiceReportDTO;
	}
        
	public static PurchaseOrderReportDTO createPo(PurchaseOrder po) {
		PurchaseOrderReportDTO purchaseOrderReportDTO = new PurchaseOrderReportDTO();
		
		purchaseOrderReportDTO.setCode(isEmpty(po.getCode()));
		purchaseOrderReportDTO.setQty(isEmpty(po.getQty()));
		purchaseOrderReportDTO.setNote(isEmpty(po.getNote()));
		purchaseOrderReportDTO.setStatus(isEmpty(po.getStatus()));                
		purchaseOrderReportDTO.setTimestamp(isEmpty(po.getTimestamp()));
                
		return purchaseOrderReportDTO;
	}        
	        
	
	
	public static List<SupplierReportDTO> createSuppliers(List<Supplier> suppliers) {
		List<SupplierReportDTO> supplierReport = new ArrayList<>();
		
		for (Supplier s : suppliers) {
			supplierReport.add(createSupplier(s));
		}
		
		return supplierReport;
	}
        
	public static List<InvoiceReportDTO> createInvoices(List<Invoice> invoice) {
		List<InvoiceReportDTO> invoiceReport = new ArrayList<>();
		
		for (Invoice s : invoice) {
			invoiceReport.add(createInvoice(s));
		}
		
		return invoiceReport;
	}
        
	public static List<PurchaseOrderReportDTO> createPos(List<PurchaseOrder> po) {
		List<PurchaseOrderReportDTO> poReport = new ArrayList<>();
		
		for (PurchaseOrder s : po) {
			poReport.add(createPo(s));
		}
		
		return poReport;
	}        
        
	
	public static SupplierReportDTO createSupplier(Supplier supplier) {
		SupplierReportDTO supplierReportDTO = new SupplierReportDTO();
		
		supplierReportDTO.setAdress(supplier.getAddres() == null ? "---" : 
								    isEmpty(String.format("%s - %d", supplier.getAddres().getStreet(), supplier.getAddres().getNumber())));
		supplierReportDTO.setCompanyName(supplier.getCompanyName());
		supplierReportDTO.setEmail(supplier.getEmail());
		
		return supplierReportDTO;
	}
	
	private static String isEmpty(String value) {
		return value == null ? "---" : value;
	}
	
	private static String isEmpty(Object object, String value) {
		return object == null ? "---" : isEmpty(value);
	}
	
	private static String isEmpty(double value) {
		return value == 0.0 ? "---" : String.format("%.2f", value);
	}
	
	private static String isEmpty(int value) {
		return value == 0 ? "---" : String.valueOf(value);
	}
}
