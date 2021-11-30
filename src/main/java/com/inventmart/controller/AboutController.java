package com.inventmart.controller;

// import dependency/ kebutuhan dari maven
import java.util.HashMap;

import org.springframework.stereotype.Controller;

import com.inventmart.MainApplication;
import com.inventmart.util.WindowsUtils;

import javafx.fxml.FXML;
import javafx.stage.Stage;

/**********************************************/
//   CONTROLLER ABOUT (untuk halaman about)
/**********************************************/

@Controller
public class AboutController extends BaseController {
	
        // setup variable layout, title, icon, dan url
	public static final String PATH_FXML = "/fxml/about.fxml";
	public static final String ABOUT_TITLE_KEY = "about.title";
	public static final String PATH_ICON = WindowsUtils.ICON_APP_PATH;
	
	public static final String FACCEBOOK_URL = "https://www.facebook.com/tiago.henrique.16";
	public static final String GITHUB_URL = "https://github.com/tiagohs";
	public static final String LINKEDIN_URL = "https://br.linkedin.com/in/tiago-henrique-395868b7";
	public static final String EMAIL_URL = "mailto:tiago.hsilva@al.infnet.edu.br";
	
	@Override
	public <T> void init(Stage stage, HashMap<String, T> parameters) {
		super.init(stage, parameters);
	}

        // abstract function
	@Override
	protected void onClose() {
		
	}
	
        // membuka web facebook
	@FXML
	public void onFacebook() {
		MainApplication.hostServices.showDocument(FACCEBOOK_URL);
	}

        // membuka akun github sesuai url
	@FXML
	public void onGithub() {
		MainApplication.hostServices.showDocument(GITHUB_URL);
	}
        
        // mengirimkan email menggunakan softwere email yang tersedia
	@FXML
	public void onEmail() {
		MainApplication.hostServices.showDocument(EMAIL_URL);
	}

        // membuka akun linkedin url
	@FXML
	public void onLinkedin() {
		MainApplication.hostServices.showDocument(LINKEDIN_URL);
	}
	
        // menutup tampilan about
	public void onOk() {
		stage.close();
	}

}
