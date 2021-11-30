package com.inventmart;

import java.util.Locale;

// boiler plate spring application, import dasar untuk aplikasi spring boot
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

// controller login dan root controller untuk jembatan antara model logic database & service dengan UI 
import com.inventmart.controller.LoginController;
import com.inventmart.controller.RootController;

// model database untuk bahasa dan data user (interaksi langsung)
import com.inventmart.model.Language;
import com.inventmart.model.User;

// servis bahasa dan user untuk interaksi dengan repository yang menghubungkan dengan database
import com.inventmart.service.LanguageService;
import com.inventmart.service.UserService;

// utilitas atau alat bantu untuk kebutuhan bahasa
import com.inventmart.util.I18N;

// utilitas atau alat bantu untuk kebutuhan load fxml dan UI rendering
import com.inventmart.util.WindowsUtils;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class MainApplication extends Application  {
        // deklarasi spring context
	public static ConfigurableApplicationContext springContext;
        // deklarasi utilitas bahasa
	public static I18N i18n;
        // deklarasi service host
	public static HostServices hostServices;
	
        // deklarasi service user
	private UserService userService;
        // deklarasi service bahasa
	private LanguageService languageService;
	
    @Override
    public void init() throws Exception {
        // context program spring menjalankan scene MainApplication class
        SpringApplicationBuilder builder = new SpringApplicationBuilder(MainApplication.class);

        builder.headless(false);        
        
//    	springContext = SpringApplication.run(MainApplication.class);
        springContext = builder.run();        
        // pemberian data UserService class pada variable userService
    	userService = springContext.getBean(UserService.class);
        // pemberian data LanguageService class / service bahasa pada variable userService        
    	languageService = springContext.getBean(LanguageService.class);
    	
    	initI18N();
    }
    
    // inisialisasi Bahasa
    private void initI18N() {
    	Language languageDefault = languageService.findDefaultLanguage();
    	
        // jika default bahasa tidak kosong di language service
    	if (languageDefault != null) {
                // set global bahasa sesuai kode : ID(Indonesia) atau US (English USA)
    		i18n = new I18N(new Locale(languageDefault.getLanguageCode(), languageDefault.getCountryCode()));
    	} else {
    		i18n = new I18N(I18N.INDONESIAN);
    	}
    	
    	
    }
   
    @Override
	public void start(Stage primaryStage) throws Exception {
    	hostServices = this.getHostServices();
    	
    	userService.finUserSignIn(e -> {
    		try {
    			User user = (User) e.getSource().getValue();
        		
        		if (user == null) {
        			WindowsUtils.openNewWindow(primaryStage, LoginController.PATH_FXML, i18n.getString(LoginController.LOGIN_TITLE_KEY), LoginController.PATH_ICON, null);
        		} else {
        			WindowsUtils.openNewWindow(primaryStage, RootController.PATH_FXML, i18n.getString(RootController.ROOT_TITLE_KEY), RootController.PATH_ICON, null);
        		}
    		} catch(Exception ex) {
    			ex.printStackTrace();
    		}
    		
    	}, null);
    	
	}
    
	public static void main(String[] args) {
		launch(args);
	}
	
}