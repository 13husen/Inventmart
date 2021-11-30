package com.inventmart.controller;

import java.util.HashMap;

import com.inventmart.MainApplication;
import com.inventmart.util.I18N;

import javafx.stage.Stage;

/**********************************************/
//   CONTROLLER BASE(sebagai parent class yang di pakai untuk implement controller lain)
/**********************************************/


public abstract class BaseController {
	// deklarasi variable stage dan language (I18N)
	protected Stage stage;
	protected I18N i18N;
	
        // init variable global
	public <T> void init(Stage stage, HashMap<String, T> parameters) {
		this.stage = stage;
		this.i18N = MainApplication.i18n;
		
		this.stage.setOnHiding(e -> { onClose(); });
		this.stage.setOnHidden(e -> { onClose(); });
	}
	
        // get stage atau keadaan layar
	public Stage getStage() {
		return stage;
	}
	
        // function untuk mendapatkan bahasa yang di gunakan (ID / US)
	public I18N getI18N() {
		return i18N;
	}
	
        // mendapatkan judul window
	public String getWindowTitle(String titleKey) {
		return MainApplication.i18n.getString(titleKey);
	}
	
	protected abstract void onClose();
}
