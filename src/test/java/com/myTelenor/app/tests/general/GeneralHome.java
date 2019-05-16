package com.myTelenor.app.tests.general;

import java.io.IOException;

import com.myTelenor.app.pages.HomePage;
import com.myTelenor.app.pages.LoginPage;
import com.myTelenor.app.tests.BaseScenario;

public class GeneralHome extends BaseScenario {
	private HomePage homePage;
	
	public GeneralHome() {
		homePage = new HomePage(driver, iOS, point_xy_mid, point_xy_top, point_xy_bot, point_xy_left, point_xy_right, point_xy_botSSI, point_xy_topSSI);
	}

	public void testBlueBubble(String fn, boolean mode, boolean ss, boolean vertical) throws IOException {
		homePage.clickBubble();
	}
		
	public void testLogout() {
    	homePage.navigateToHome();

		homePage.toolbarClick();

    	homePage.logoutButton();

    	LoginPage loginPage = new LoginPage(driver, iOS, point_xy_mid, point_xy_top, point_xy_bot, point_xy_left, point_xy_right, point_xy_botSSI, point_xy_topSSI, env);

    	loginPage.checkLoginButton();

		try { Thread.sleep(1000); } catch(InterruptedException ex) { Thread.currentThread().interrupt(); }
	}
}
