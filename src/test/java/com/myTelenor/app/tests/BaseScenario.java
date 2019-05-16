package com.myTelenor.app.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.myTelenor.app.pages.BaseWidget;
import com.myTelenor.app.tests.resources.CustomizedCapabilities;
import com.myTelenor.app.tests.resources.TestData;
import com.myTelenor.app.tests.resources.XmlFile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class BaseScenario extends XmlFile{
	public static String refDir;
	public static String tempDir;
	public static String dmy;

	protected static int runsCount = 0;
    protected static AppiumDriver<MobileElement> driver;
    protected static CustomizedCapabilities dc;
    protected static TestData testData;
    protected static String env;
    protected static String app;
    protected static String bs_username;
    protected static String bs_accesskey;
    protected static String nightly;
    protected static String lang;
    protected static boolean mode; 			// true - Create ref files and test;	false - Only create ref files
    protected static boolean screenShots; 	// true - Create screenShots;	false - No screenShots	
	
    protected static boolean iOS; // true - iOS; false - Android
	protected static String sysS; // true - iOS; false - Android

	private static int touchDuration = 1300;
    protected static PointOption<?> point_xy_mid;
    protected static PointOption<?> point_xy_bot;
    protected static PointOption<?> point_xy_top;
    protected static PointOption<?> point_xy_left;
    protected static PointOption<?> point_xy_right;    

	protected static PointOption<?> point_xy_botSSI;
	protected static PointOption<?> point_xy_topSSI;

	protected static PointOption<?> webDoneButton;

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";	
	
	public static BaseWidget baseWidget;

	protected final HashMap<String, String> webStrings = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{	put("GetRidOfPaper",	"Du kan slippa kostnaden f\u00F6r pappersfaktura och ist\u00E4llet betala fakturan f\u00F6r ditt mobilabonnemang med e-faktura. Den skickas elektroniskt till din internetbank och du f\u00E5r godk\u00E4nna den varje m\u00E5nad f\u00F6r att den ska betalas.");
		 	put("InvoicesFaq",	"Vad g\u00E4ller fakturafr\u00E5gan?");
		 	put("Login", "F\u00F6r att logga in till Mitt Telenor anger du mobilnummer eller e-post.");
		 	put("Integritetspolicy",	"Integritetspolicy");
		 	put("ForAttKommuniceraMedDig",	"F\u00F6r att kommunicera med dig om tj\u00E4nsterna");		 	
		 	put("AddNewUser",	"L\u00E4gg till anv\u00E4ndare");
		 	put("AddExtraSim", "L\u00E4gg till extra sim-kort f\u00F6r surf till Telenor Familj");
		 	put("ChooseNewMobile", "V\u00E4lj en ny mobil");		 	
		 } };	

	public void getPageScreenShot(String fn, boolean mode, boolean ss, boolean vertical) throws IOException {
		if (!iOS || ss)
	    	baseWidget.scroll(false);
		assertTrue(saveScreenShot(fn, false, ss, vertical));
		if (mode)		
			assertEquals(getXmlFile(refDir, fn), getXmlFile(tempDir, fn));
	}
	
	public void getPageScreenShot(String fn, boolean mode, boolean ss, boolean vertical, int count) throws IOException {
		if (!iOS || ss)
			baseWidget.scroll(false);
		assertTrue(saveScreenShot(fn, false, ss, vertical));
		if (mode)		
			for (int i=0; i<count; i++)
				assertEquals(getXmlFile(refDir, fn+i), getXmlFile(tempDir, fn+i));
	}

	public void getPageScreenShot(String fn, boolean mode, boolean ss, boolean vertical, boolean modal) throws IOException {
		if (!iOS || ss)
			baseWidget.scroll(false, modal);
		assertTrue(saveScreenShot(fn, modal, ss, vertical));
		if (mode)
			assertEquals(getXmlFile(refDir, fn), getXmlFile(tempDir, fn));
	}

	public void testWebPage(String str) {
		if (!baseWidget.isPageLoaded())
			fail("Page was not loaded in time");
		
		String pageSource = driver.getPageSource();
		
		assertTrue("Page doesn't contain: "+str, 
				pageSource.contains(str));
	}
	
	@BeforeClass
    public static void setup() throws Exception {
        System.out.println("Executing run# "+runsCount);
		
    	env = System.getProperty("env");
        System.out.println("Running on "+env);
        
    	app = System.getProperty("app");
        System.out.println("Running app: "+app);
                
        bs_username = System.getProperty("bs_username");
        System.out.println("BrowserStack user name: "+bs_username);
        
        bs_accesskey = System.getProperty("bs_accesskey");
        System.out.println("BrowserStack access key: "+bs_accesskey);
        
        nightly = System.getProperty("nightly");
        System.out.println("Running nightly: "+nightly);
        
        lang = System.getProperty("lang");
        System.out.println("Running lang: "+lang);
        
        mode = Boolean.valueOf(System.getProperty("mode"));
        System.out.println("Running mode: "+mode);
        
        screenShots = Boolean.valueOf(System.getProperty("screenShots"));
        System.out.println("Running screenShots: "+screenShots);

        if (runsCount == 0) {
        	dmy = (new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss")).format(new Date());
        
        	if (nightly.equals("true"))
        		nightly="nightly-";
        	else
        		nightly="manual-";
        
        	dmy = nightly+dmy;
        }
		
		dc = new CustomizedCapabilities(env, app, runsCount, dmy, bs_username, bs_accesskey);

        if (env.contains("Android")) {
        	driver = new AndroidDriver<MobileElement>(new URL(dc.url), dc.dc);
        	iOS = false;
        	sysS = "Android";
        }
        else {
        	driver = new IOSDriver<MobileElement>(new URL(dc.url), dc.dc);
        	iOS = true;
        	sysS = "iOS";
        }
                
        testData = new TestData();

		int tempY = driver.manage().window().getSize().height/2;
		int tempX = driver.manage().window().getSize().width/2;

        point_xy_mid = PointOption.point(tempX, tempY);
        point_xy_top = PointOption.point(tempX, Math.toIntExact(Math.round(tempY*0.1)));
        point_xy_bot = PointOption.point(tempX, Math.toIntExact(Math.round(tempY*1.8)));

		point_xy_botSSI = point_xy_bot;
		point_xy_topSSI = point_xy_top;

        point_xy_left = PointOption.point(0, tempY);
        point_xy_right = PointOption.point(tempX*2-2, tempY);

		webDoneButton = null;

		refDir="ReferenceScreenShots/"+sysS+"/"+lang+"/";

    	if (runsCount == 0) {
    		System.out.println("Update the existing temp folder");
    		
    		tempDir = "temp"+dmy+"/";
            createDir(tempDir.replaceAll("///", ""));
            tempDir = tempDir+sysS+"/";
            createDir(tempDir);  
            tempDir = tempDir+lang+"/";
            createDir(tempDir);
    	}
    	else
    		tempDir = "temp"+dmy+"/"+sysS+"/"+lang+"/";
        
        baseWidget = new BaseWidget(driver, iOS, point_xy_mid, point_xy_top, point_xy_bot, point_xy_left, point_xy_right, point_xy_botSSI, point_xy_topSSI);

		runsCount++;
    }

    public int getRunsCount() {
    	return runsCount;
    }
    
    protected static String getPass() {
    	return testData.PASSWORD;
    }
        
    protected boolean saveScreenShot(String ref, boolean modal, boolean ss, boolean vertical) throws IOException {
    	return saveScreenShotG(ref, ss, vertical);    		
    }
        
    @SuppressWarnings("rawtypes")
    protected boolean saveScreenShotG(String ref, boolean ss, boolean vertical) throws IOException {    	
    	if (!baseWidget.isPageLoaded()) {
    		System.out.println("Executing saveScreenShot("+ref+" . Page is not loaded.");
    		return false;
    	}
    	String lastScreenShot = driver.getPageSource();
		String currentScreenShot = "";

		PointOption<?> xy1 = point_xy_mid;
		PointOption<?> xy2 = point_xy_top;
		PointOption<?> xy3 = point_xy_bot;

    	if (!vertical) {
    		xy2 = point_xy_left;
    		xy3 = point_xy_right;
    	}

    	// Android: Process and save screen shots one by one (can get only visible parts)
		// Take screen shots if ordered
    	int i=0;
    	while (!lastScreenShot.equals(currentScreenShot)) {    		
    		currentScreenShot = lastScreenShot;
    		processScreenShot(currentScreenShot, screenShotName+"P"+i, tempDir, iOS);
    		
    		if (ss) {
    			File file  = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    			FileUtils.copyFile(file, new File(tempDir+"ScreenShots/"+screenShotName+ref+"_"+i+".jpg"));
    		}
    		
    		new TouchAction(driver)
    		.press(xy1)
    		.waitAction(WaitOptions.waitOptions(Duration.ofMillis(touchDuration)))
    		.moveTo(xy2)
    		.release().perform();
    		
        	if (!baseWidget.isPageLoaded()) {
        		System.out.println("Executing saveScreenShot("+ref+" . Page is not loaded.");
        		return false;    		
        	}
        	
    		lastScreenShot = driver.getPageSource();
    		i++;
    	}
    	
		int scrools = --i;
				
    	while (i>0) {
    		new TouchAction(driver)
    		.press(xy1)
    		.waitAction(WaitOptions.waitOptions(Duration.ofMillis(touchDuration)))
    		.moveTo(xy3)
    		.release().perform();    
    		i--;
    	}
    	
    	if (scrools > 0) {
    		i=0;
    		String n= screenShotName+"P"+i;
   			while (i<scrools) {
   	    		if (vertical) {
    				mergeTwoScreenShots(n, screenShotName+"P"+(i+1), screenShotName+"T"+i, tempDir);
    				n="ScreenShotT"+i;
   	    		}
   	    		else
   	    			renameFile(tempDir+screenShotName+"P"+i+".xml", tempDir+screenShotName+ref+i+".xml");
				i++;
    		}
	    	if (vertical)
	    		renameFile(tempDir+screenShotName+"T"+(i-1)+".xml", tempDir+screenShotName+ref+".xml");
	    	else
	    		renameFile(tempDir+screenShotName+"P"+i+".xml", tempDir+screenShotName+ref+i+".xml");
    	}
    	else {
    		renameFile(tempDir+screenShotName+"P0.xml", tempDir+screenShotName+ref+".xml");
    	}
    	return true;
    }           

    @AfterClass
    public static void tearDown() throws Exception {
    	driver.quit();
	}

    public void setRefDir(String newRefDir) {
		System.out.println("Set a reference dir as "+newRefDir);
    	refDir = newRefDir;
    }
    
    public boolean setTempDir(String fam, String rol) {
    	System.out.println("setTempDir "+fam+" "+rol);
    	tempDir = tempDir+fam+"/";
    	boolean res = true;
    	if (!dirExists(tempDir))
    		res = createDir(tempDir);    	
    	
    	tempDir = tempDir+rol+"/";
    	if (!dirExists(tempDir))
			res = res && createDir(tempDir);
    	
    	if (!dirExists(tempDir+"ScreenShots/"))
    		res = res && createDir(tempDir+"ScreenShots/");
    	
    	return res;
    }
    

    public boolean setTempDir(String fam, String rol, int connectedAccountIndex) {
    	tempDir = tempDir+fam+"/";
    	boolean res = true;
    	
    	if (!dirExists(tempDir))
    		res = createDir(tempDir);
    	
    	tempDir = tempDir+rol+"/";
    	if (!dirExists(tempDir))
    		res = res && createDir(tempDir);
    	
    	tempDir = tempDir+connectedAccountIndex+"/";
    	if (!dirExists(tempDir))
    		res = res && createDir(tempDir);    	
    	
    	if (!dirExists(tempDir+"ScreenShots/"))
    		res = res && createDir(tempDir+"ScreenShots/");
    	
    	return res;
    }
}
