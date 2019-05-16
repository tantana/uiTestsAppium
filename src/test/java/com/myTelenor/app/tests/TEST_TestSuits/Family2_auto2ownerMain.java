package com.myTelenor.app.tests.TEST_TestSuits;

import static org.junit.Assume.assumeTrue;

import java.io.IOException;

import org.junit.FixMethodOrder;
import org.junit.Test;

import com.myTelenor.app.tests.BaseScenario;
import com.myTelenor.app.tests.general.GeneralHome;
import com.myTelenor.app.tests.general.GeneralLogin;

import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Family2_auto2ownerMain extends BaseScenario  {
    
    private static String rol = "auto2owner";
    private static int connectedAccountIndex = 1;
    private static String fam = "Family2";
	private static String pass = getPass();
	private static String msisdn = testData.ACCOUNTS_TEST_AT.get(fam).get(rol);
	private GeneralLogin testLogin = new GeneralLogin();
	private GeneralHome testHome = new GeneralHome();

	@Test
	public void a_aModeTest() {
		setRefDir(refDir+fam+"/"+rol+"/"+connectedAccountIndex+"/");

		if (mode && !dirExists(refDir))
			testData.setTestStatusFlagMain(false);
		else {
			testData.setTestStatusFlagMain(true);
			testData.setTestStatusFlagMain(setTempDir(fam, rol, connectedAccountIndex));
		}
	}

	
	// --- LOG IN --- //	
    @Test
    public void a_login() {
    	assumeTrue(testData.getTestStatusFlagMain());
    	testData.setTestStatusFlagLogin(false);
    	System.out.println("\tLogin for user "+msisdn+" ");

    	testLogin.testLogin(msisdn, pass);
    	testData.setTestStatusFlagLogin(true);
    }
   
    	@Test 
    	public void ab_login() throws IOException {
    		assumeTrue(testData.getTestStatusFlagMain());
    		assumeTrue(testData.getTestStatusFlagLogin());
    		testLogin.navigateTheMostLeft();
    		testLogin.getPageScreenShot("LoginPage", mode, screenShots, false, 3);
    	}
    
    	@Test 
    	public void ac_navigateToSubscriptionManager() {
    		assumeTrue(testData.getTestStatusFlagLogin());
    		testLogin.navigateToSubscriptionManager(connectedAccountIndex);
    	}    
    	
    	// --- HOME SCREEN BLUE BUBBLE --- //
    @Test
    public void b_BlueBubble() throws IOException {
		assumeTrue(testData.getTestStatusFlagLogin());
    	testHome.testBlueBubble("HomePageBlueBubble", mode, screenShots, true);
    }
    
    	// --- HOME --- //
	@Test
	public void ca_HomePageMainView() throws IOException {
		assumeTrue(testData.getTestStatusFlagLogin());
		testHome.getPageScreenShot("HomePage", mode, screenShots, true);
	}

    	// --- LOG OUT --- //    
    @Test
    public void z_logout() {
		assumeTrue(testData.getTestStatusFlagLogin());
    	testHome.testLogout();
    }
   
}
