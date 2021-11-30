package com.inventmart.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.inventmart.model.Address;
import com.inventmart.model.Brand;
import com.inventmart.model.Client;
import com.inventmart.model.ClientType;
import com.inventmart.model.Fone;
import com.inventmart.model.Image;
import com.inventmart.model.Invoice;
import com.inventmart.model.Item;
import com.inventmart.model.Product;
import com.inventmart.model.ProductTransaction;
import com.inventmart.model.ProductType;
import com.inventmart.model.Role;
import com.inventmart.model.Sale;
import com.inventmart.model.Supplier;
import com.inventmart.model.Tag;
import com.inventmart.model.User;
import com.inventmart.model.PurchaseOrder;
import com.inventmart.model.PurchaseOrderDetail;
public class EntityFactory {
	
	public static Supplier createSupplier(String companyName, String email, Address address) {
		return createSupplier(new Supplier(), companyName, email, address);
	}
	
	public static Supplier createSupplier(Supplier supplier, String companyName, String email, Address address) {
		
		if (supplier == null) {
			supplier = new Supplier();
		}
		
		supplier.setCompanyName(companyName);
		supplier.setEmail(email);
		supplier.setAddres(address);
		
		return supplier;
	}
	
	
	public static Product createProduct(String sku, String name, String description, double initialCostPrice, double buyPrice,
										double wholesalePrice, double retailPrice, double weight, double initialStock, Date createdAt,
										Date updatedAt, Supplier supplier, Brand brand, ProductType productType, List<Image> images,
										List<Tag> tags) {
		return createProduct(new Product(), sku, name, description, initialCostPrice, buyPrice, wholesalePrice, retailPrice, weight, initialStock, createdAt, updatedAt, supplier, brand, productType, images, tags);
	}
	
	public static Product createProduct(Product product, String sku, String name, String description, double initialCostPrice, double buyPrice,
			double wholesalePrice, double retailPrice, double weight, double initialStock, Date createdAt,
			Date updatedAt, Supplier supplier, Brand brand, ProductType productType, List<Image> images,
			List<Tag> tags) {
		
		if (product == null) {
			product = new Product();
		}
		
		product.setSku(sku);
		product.setName(name);
		product.setDescription(description);
		product.setInitialCostPrice(initialCostPrice);
		product.setBuyPrice(buyPrice);
		product.setWholesalePrice(wholesalePrice);
		product.setRetailPrice(retailPrice);
		product.setWeight(weight);
		product.setInitialStock(initialStock);
		product.setCreatedAt(createdAt);
		product.setUpdatedAt(updatedAt);
		
		product.setBrand(brand);
		product.setProductType(productType);
		product.setSupplier(supplier);
		
		product.setTags(tags);
		product.setImages(images);
		
		return product;
	}
        
        
	public static PurchaseOrder createPurchaseOrder(PurchaseOrder purchaseOrder, String code, String note, String timestamp,String status,List<Item> items) {
		if (purchaseOrder == null) {
			purchaseOrder = new PurchaseOrder();
		}
		purchaseOrder.setCode(code);
		purchaseOrder.setNote(note);
		purchaseOrder.setTimestamp(timestamp);
                purchaseOrder.setStatus(status);
		purchaseOrder.setItems(items);		
		return purchaseOrder;
	}    
        
	public static Invoice createInvoice(Invoice invoice, String code, String terms, String note, String timestamp, PurchaseOrder purchaseOrder) {
		if (invoice == null) {
			invoice = new Invoice();
		}
		invoice.setInvoiceCode(code);
		invoice.setNote(note);
                invoice.setTerms(terms);
		invoice.setTimestamp(timestamp);
                invoice.setPurchaseOrder(purchaseOrder);
		
		return invoice;
	}    
                
        
	public static ProductTransaction createProductTransaction(ProductTransaction productTransaction, long stockBefore, long stockAfter, int qty, String note, String timestamp,PurchaseOrder purchaseOrder) {
		if (productTransaction == null) {
			productTransaction = new ProductTransaction();
		}
		productTransaction.setStockBefore(stockBefore);
		productTransaction.setStockAfter(stockAfter);
		productTransaction.setQty(qty);
		productTransaction.setNote(note);
		productTransaction.setTimestamp(timestamp);                
                productTransaction.setPurchaseOrder(purchaseOrder);
		
		return productTransaction;
	}            
	
	public static Brand createBrand(String name, String email, String additionalInformation) {
		return createBrand(new Brand(), name, email, additionalInformation);
	}
	
	public static Brand createBrand(Brand brand, String name, String email, String additionalInformation) {
		
		if (brand == null) {
			brand = new Brand();
		}
		
		brand.setName(name);
		brand.setEmail(email);
		brand.setAdditionalInformation(additionalInformation);
		
		return brand;
	}
	
        public static ProductType createProductType(String name,String description) {
		return createProductType(new ProductType(), name, description);
	}
	
	public static ProductType createProductType(ProductType productType, String name, String description) {
		
		if (productType == null) {
			productType = new ProductType();
		}
		
		productType.setName(name);
		productType.setDeskripsi(description);
		
		return productType;
	}
	
        
	public static Sale createSale(String saleCode, Calendar issueDate, Calendar shipmentDate, String reference, String email,
			String message, String state, int totalUnits, double total, Fone fone, Client cliente, List<Item> items,
			List<Tag> tags) {
		return createSale(new Sale(), saleCode, issueDate, shipmentDate, reference, email, message, state, totalUnits, total, fone, cliente, items, tags);
	}
	
	public static Sale createSale(Sale sale, String saleCode, Calendar issueDate, Calendar shipmentDate, String reference, String email,
			String message, String state, int totalUnits, double total, Fone fone, Client cliente, List<Item> items,
			List<Tag> tags) {
		
		if (sale == null) {
			sale = new Sale();
		}
		
		sale.setSaleCode(saleCode);
		sale.setIssueDate(issueDate);
		sale.setShipmentDate(shipmentDate);
		sale.setReference(reference);
		sale.setEmail(email);
		sale.setMessage(message);
		sale.setState(state);
		sale.setTotalUnits(totalUnits);
		sale.setTotal(total);
		sale.setFone(fone);
		sale.setCliente(cliente);
		sale.setItems(items);
		
		return sale;
	}
	
	
	
	public static Client createClient(String cpf, Address address,
											List<Fone> phones, User user) {
		return createClient(new Client(), cpf, address, phones, user);
	}
	
	public static Client createClient(Client client, String cpf,  Address address,
									List<Fone> phones, User user) {
		
		if (client == null) {
			client = new Client();
		}
		
		client.setAddress(address);
		client.setCpf(cpf);
		client.setPhones(phones);
		client.setUser(user);
		
		return client;
	}
	
	public static ClientType createClientType(String name) {
		ClientType clientType = new ClientType();
		
		clientType.setName(name);
		
		return clientType;
	}
	
	public static ProductType createProductType(String name) {
		ProductType productType = new ProductType();
		
		productType.setName(name);
		
		return productType;
	}
	
	public static Image createImage(String path) {
		Image image = new Image();
		
		image.setPath(path);
		
		return image;
	}
	
	public static Tag createTag(String name) {
		Tag tag = new Tag();
		
		tag.setName(name);
		
		return tag;
	}
        
        


	public static Address createAddress(String street, int number, String complement, String suburb, String city, String state,
			String country, String cep) {
		Address address = new Address();
		
		address.setStreet(street);
		address.setNumber(number);
		address.setComplement(complement);
		address.setSuburb(suburb);
		address.setCity(city);
		address.setState(state);
		address.setCountry(country);
		address.setCep(cep);
		
		return address;
	}
	
	public static User createUser(User user,String email, String name, String photoPath, String password, List<Role> role) {
		if (user == null) {
			user = new User();
		}		
		user.setName(name);
		user.setEmail(email);
		user.setPhotoPath(photoPath);
		user.setPassword(password);
		user.setRoles(role);
		
		return user;
	}


	public static Role createRole(String role, String roleName) {
		Role r = new Role();
		
		r.setName(roleName);
		r.setRole(role);
		
		return r;
	}
	
	public static Fone createPhone(String number) {
		Fone phone = new Fone();
		
		phone.setNumber(number);
		
		return phone;
	}
	
}
