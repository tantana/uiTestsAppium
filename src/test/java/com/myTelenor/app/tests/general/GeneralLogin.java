package com.myTelenor.app.tests.general;

import com.myTelenor.app.pages.LoginPage;
import com.myTelenor.app.tests.BaseScenario;

public class GeneralLogin extends BaseScenario {
	private LoginPage loginPage;
	
	public GeneralLogin() {
		loginPage = new LoginPage(driver, iOS, point_xy_mid, point_xy_top, point_xy_bot, point_xy_left, point_xy_right, point_xy_botSSI, point_xy_topSSI, env);
	}	

	public void testLogin(String number, String pass) {
    	loginPage.loginButton();
   		loginPage.enterPhone(number); 		    		
   		loginPage.enterPassword(pass);
    	loginPage.isPageLoaded();
	}
	
	public void testTryLoginAgain() {
		loginPage.clickErrorViewTryAgainButton();
	}
	
	public void navigateToSubscriptionManager(int i) {
		loginPage.scrollHorizontal(true, i);
		loginPage.clickChooseButton();
	}
	
	public void navigateTheMostLeft() {
		loginPage.scrollHorizontal(false);
	}
}
