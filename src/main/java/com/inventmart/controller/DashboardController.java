package com.inventmart.controller;

import com.inventmart.model.User;
import com.inventmart.service.PurchaseOrderService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.inventmart.service.SaleService;
import com.inventmart.service.UserService;
import com.inventmart.util.WindowsUtils;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;

@Controller
public class DashboardController extends BaseController {
	
	public static final String PATH_FXML = "/fxml/dashboard.fxml";
	public static final String DASHBOARD_TITLE_KEY = "dashboard.title";
	public static final String PATH_ICON = WindowsUtils.ICON_APP_PATH;
	
	@FXML
	private LineChart<String, Number> salesChart;

	@FXML
	private Label usersLabel;
	
	@FXML
	private Label salesLabel;
	
	@FXML
	private Label employeesLabel;
        
	@FXML
	private Label dashboard_user;
        
	@FXML
	private Label dashboard_role;
        
	@Autowired
	private UserService userService;
	
	@Autowired
	private SaleService saleService;
        
	@Autowired
	private PurchaseOrderService purchaseOrderService;        
	
	
	@Override
	public <T> void init(Stage stage, HashMap<String, T> parameters) {
		super.init(stage, parameters);
                checkParameters(parameters);
//		configureSalesChart();
		configureLabels();
	}
	private <T> void checkParameters(HashMap<String, T> parameters) {
                String userName = "";
                String userRole = "";
		if (parameters != null) {
			userName = (String) parameters.get("user_name");
                        userRole = (String) parameters.get("user_role");
                        
                        dashboard_user.setText("Hello " + userName);
                        dashboard_role.setText(userRole);                        
                        
		}
	}
        
	@Override
	protected void onClose() {
		userService.onClose();
		saleService.onClose();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void configureSalesChart() {
		SimpleDateFormat month = new SimpleDateFormat("MMM-yyyy");
		
		salesChart.getXAxis().setLabel(getI18N().getString("date.month"));
        
		XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        series.setName(getI18N().getString("sales.pathTitle"));
        
        List<Calendar> allMonths = new ArrayList<>();
        
		try {
			allMonths = getLast12Months();
		} catch (ParseException e1) {
		}
        
		
		
        for (Calendar calendar : allMonths) {
        	System.out.println(calendar.getTime().toString());
        	System.out.println(saleService.getTotalSalesByMonth(calendar));
        	series.getData().add(new XYChart.Data(month.format(calendar.getTime()), saleService.getTotalSalesByMonth(calendar)));
        }
        
        salesChart.getData().add(series);
	}
	
	private List<Calendar> getLast12Months() throws ParseException {
		List<Calendar> allDates = new ArrayList<>();
		
		Calendar cal = Calendar.getInstance();
		allDates.add(Calendar.getInstance());
		
		for (int i = 1; i <= 12; i++) {
			Calendar c = Calendar.getInstance();
		    cal.add(Calendar.MONTH, -1);
		    c.setTime(cal.getTime());
		    allDates.add(c);
		}
		
		return reverse(allDates);
	}
	
	public List<Calendar> reverse(List<Calendar> list) {
	    for(int i = 0, j = list.size() - 1; i < j; i++) {
	        list.add(i, list.remove(j));
	    }
	    
	    return list;
	}
	
	private void configureLabels() {
		
                    userService.finUserSignIn(e -> {
                    User user = (User) e.getSource().getValue();
                    if (user != null) {
                        String role = user.getRoles().size() > 0 ? user.getRoles().get(0).getName() : "Pengunjung";
                        dashboard_user.setText("Hello " + user.getName());
                        dashboard_role.setText(role);
                    }else{
                        dashboard_user.setText("Hello Pengunjung");
                        dashboard_role.setText("Not Found");                        
                    }

                    }, null);  
                
		userService.getTotalUsers(e -> {
			configureLabel(usersLabel, (Long) e.getSource().getValue());
		}, null);                
		
		purchaseOrderService.getTotalPo(e -> {
			configureLabel(salesLabel, (Long) e.getSource().getValue());		
		}, null);
	}
	
	private void configureLabel(Label label, long value) {
		WindowsUtils.setTextInLabel(label, String.valueOf(value));
	}
	
}
