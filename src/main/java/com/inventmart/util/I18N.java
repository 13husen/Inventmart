package com.inventmart.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18N {
        // Utilitas Bahasa, variable konstan untuk menentukan bahasa
	public static Locale INDONESIAN = new Locale("id", "ID");
	public static Locale ENGLISH = new Locale("en", "US");
	
        // variable default bahasa
	private Locale defaultLocale;
        
        // bundle resource
	private ResourceBundle bundle;
	
        // update bahasa default menjadi bahasa indonesia
	public I18N() {
		this.updateResourceBundle ( updateLocale(INDONESIAN) );
	}
	
        
        // update jika data locale terisi (ID atau USA)
	public I18N(Locale locale) {
		this.updateResourceBundle ( updateLocale(locale) );
	}
	
        // function update locale/ bahasa
	public Locale updateLocale(Locale locale) {
		this.defaultLocale = locale;
		
		return defaultLocale;
	}
	// function untuk update locale / bahasa
	public Locale updateLocale(String language, String country) {
		return updateLocale(new Locale(language, country));
	}
	
        // update resource bundle
	public ResourceBundle updateResourceBundle(ResourceBundle bundle) {
		this.bundle = bundle;
		
		return bundle;
	}

        // ambil string dari bundle yang di terima
	public String getString(String key) {
		return bundle.getString(key);
	}
	// update resource bundle dengan locale yang ada di parameter function
	public ResourceBundle updateResourceBundle(Locale locale) {
		return this.updateResourceBundle( ResourceBundle.getBundle("i18n/i18n", locale) );
	}
        
        // getter default locale/ bahasa
	public Locale getDefaultLocale() {
		return defaultLocale;
	}

        // update default locale/ bahasa        
	public void updateDefaultLocale(Locale defaultLocale) {
		this.updateResourceBundle ( updateLocale(defaultLocale) );
	}
        
        // getter resource bundle
	public ResourceBundle getBundle() {
		return bundle;
	}
        
        // setter resourdce bundle
	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}
	
	
}
