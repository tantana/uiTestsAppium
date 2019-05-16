package com.myTelenor.app.pages;

import java.time.Duration;
import java.util.List;

import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;

public class BaseWidget {

    protected AppiumDriver<MobileElement> driver;
    protected boolean iOS;
    protected WebDriverWait wait;
    //protected WebDriverWait waitPdf;

	private static PointOption<?> point_xy_mid;
	private static PointOption<?> point_xy_bot;
	private static PointOption<?> point_xy_top;
	private static PointOption<?> point_xy_left;
	private static PointOption<?> point_xy_right;
	private static PointOption<?> point_xy_botSSI;
	private static PointOption<?> point_xy_topSSI;

	private static int touchDuration = 1300;

	private String spinnerClassName;

	@iOSXCUITFindBy(accessibility = "Cancel")
	private MobileElement alertCancelButton;

    public BaseWidget(AppiumDriver<MobileElement> driver, boolean iOS,
					  PointOption<?> m, PointOption<?> t, PointOption<?> b,
					  PointOption<?> l, PointOption<?> r,
					  PointOption<?> bs, PointOption<?> ts) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30, 100);
        //waitPdf = new WebDriverWait(driver, 60, 100);
        
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);

        if (iOS)
			spinnerClassName = "UIActivityIndicatorView";
        else
			spinnerClassName = "com.telenor.app.mytelenor.ui.widgets.ColoredProgressBar";
        this.iOS = iOS;

		point_xy_mid = m;
		point_xy_top = t;
		point_xy_bot = b;
		point_xy_left = l;
		point_xy_right = r;
		point_xy_botSSI = bs;
		point_xy_topSSI = ts;
	}

    protected void waitForElementToAppear(WebElement element) {
    	if (isPageLoaded())
    		wait.until(ExpectedConditions.visibilityOf(element));
    }
    
    public boolean isPageLoaded() {
    	String androidProgressBarClassName = "android.widget.ProgressBar";
		//protected String androidFragmentLoadingContainer = "se.telenor.mytelenor:id/fragment_loading_progress_bar";
		//protected String androidProgressBar = "se.telenor.mytelenor:id/progressBar";

    	List<MobileElement> temp = driver.findElements(By.className(spinnerClassName));
   		temp.addAll(driver.findElements(By.className(androidProgressBarClassName)));
    	//temp.addAll(driver.findElements(By.className(androidFragmentLoadingContainer)));
    	//temp.addAll(driver.findElements(By.id(androidProgressBar)));
    	
    	long t= System.currentTimeMillis();
    	long end = t+15000;
    	
    	while (!temp.isEmpty() && System.currentTimeMillis()<end ) {
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) { e.printStackTrace(); }

    		temp = driver.findElements(By.className(spinnerClassName));
        	temp.addAll(driver.findElements(By.className(androidProgressBarClassName)));
    		//temp.addAll(driver.findElements(By.className(androidFragmentLoadingContainer)));
        	//temp.addAll(driver.findElements(By.id(androidProgressBar)));    		
    	}

    	return temp.isEmpty();
    }

    public void scrollToElement(String elementId, boolean scrollDown) {
    	scrollToElementG(elementId.substring(elementId.indexOf("\"")+1, elementId.lastIndexOf("\"")), scrollDown, false);
    }

    public void scrollToElement(String elementId, boolean scrollDown, boolean modal) {
    	scrollToElementG(elementId.substring(elementId.indexOf("\"")+1, elementId.lastIndexOf("\"")), scrollDown, modal);
    }

    public void scrollToElement(String elementIdAndroid, String elementIdiOS, boolean scrollDown) {
 		scrollToElementG(elementIdAndroid.substring(elementIdAndroid.indexOf("\"")+1, elementIdAndroid.lastIndexOf("\"")), scrollDown, false);
	}

	public void scrollToElement(String elementIdAndroid, String elementIdiOS, boolean scrollDown, boolean modal) {
		scrollToElementG(elementIdAndroid.substring(elementIdAndroid.indexOf("\"")+1, elementIdAndroid.lastIndexOf("\"")), scrollDown, modal);
	}

	public void scrollToElement(MobileElement element, String className, boolean scrollDown) {
		scrollToElementG(element, className, scrollDown, false);
	}

	public void scrollToElement(MobileElement element, String className, boolean scrollDown, boolean modal) {
		scrollToElementG(element, className, scrollDown, modal);
	}

	@SuppressWarnings("rawtypes")
	public void scrollToElementG(MobileElement element, String className, boolean scrollDown, boolean modal) {
		List<MobileElement> temp = element.findElements(By.className(className));

		PointOption<?> xy1 = point_xy_mid;
		PointOption<?> xy2;

		if (modal && !iOS && !scrollDown)
			xy2 = point_xy_bot;
		else if (modal && !iOS && scrollDown)
			xy2 = point_xy_top;
		else if (!modal && iOS && !scrollDown)
			xy2 = point_xy_botSSI;
		else
			xy2 = point_xy_topSSI;

		boolean flag = temp.isEmpty();

		while (flag) {
			new TouchAction(driver)
					.press(xy1)
					.waitAction(waitOptions(Duration.ofMillis(touchDuration)))
					.moveTo(xy2)
					.release().perform();

			if (!this.isPageLoaded())
				System.out.println("Executing scrollToElement("+className+" . Page is not loaded.");

			temp = element.findElements(By.className(className));
			flag = temp.isEmpty();
		}
	}

	@SuppressWarnings("rawtypes")
	public void scrollToElementG(String elementId, boolean scrollDown, boolean modal) {

		if (!this.isPageLoaded())
			System.out.println("Executing scrollToElement("+elementId+" . Page is not loaded.");
		
		List<MobileElement> temp = driver.findElements(By.id(elementId));

		PointOption<?> xy1 = point_xy_mid;
		PointOption<?> xy2;

		if (modal && !iOS && !scrollDown)
			xy2 = point_xy_bot;
		else if (modal && !iOS && scrollDown)
			xy2 = point_xy_top;
		else if (!modal && iOS && !scrollDown)
			xy2 = point_xy_botSSI;
		else
			xy2 = point_xy_topSSI;

		boolean flag = temp.isEmpty();	

		while (flag) {
			new TouchAction(driver)
					.press(xy1)
					.waitAction(waitOptions(Duration.ofMillis(touchDuration)))
					.moveTo(xy2)
					.release().perform();

			if (!this.isPageLoaded())
				System.out.println("Executing scrollToElement("+elementId+" . Page is not loaded.");

			temp = driver.findElements(By.id(elementId));
			flag = temp.isEmpty();
		}
	}

	public void scroll(boolean scrollDown, boolean modal) {
		scrollG(scrollDown, modal);
	}

	public void scroll(boolean scrollDown) {
    	scrollG(scrollDown, false);
	}

	@SuppressWarnings("rawtypes")
	public void scrollG(boolean scrollDown, boolean modal) {
		String lastScreenShot = driver.getPageSource();
		String currentScreenShot = "";
		PointOption<?> xy1 = point_xy_mid;
		PointOption<?> xy2 = point_xy_top;

		if (!iOS && !scrollDown)
			xy2 = point_xy_bot;
		else if (iOS && !scrollDown && modal)
			xy2 = point_xy_bot;
		else if (iOS && scrollDown && !modal)
			xy2 = point_xy_topSSI;
		else if (iOS && !scrollDown && !modal)
			xy2 = point_xy_botSSI;	
		
		while (!lastScreenShot.equals(currentScreenShot)) {
			currentScreenShot = lastScreenShot;

			new TouchAction(driver)
					.press(xy1)
					.waitAction(waitOptions(Duration.ofMillis(touchDuration)))
					.moveTo(xy2)
					.release().perform();

			lastScreenShot = driver.getPageSource();
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void scrollHorizontal(boolean scrollRight) {
		String lastScreenShot = driver.getPageSource();
		String currentScreenShot = "";
		PointOption<?> xy1 = point_xy_mid;
		PointOption<?> xy2 = point_xy_right;
		if (scrollRight) {
			xy2 = point_xy_left;
		}
		while (!lastScreenShot.equals(currentScreenShot)) {
			currentScreenShot = lastScreenShot;

			new TouchAction(driver)
					.press(xy1)
					.waitAction(waitOptions(Duration.ofMillis(touchDuration)))
					.moveTo(xy2)
					.release().perform();

			lastScreenShot = driver.getPageSource();
		}
	}	

	@SuppressWarnings("rawtypes")
	public void scrollHorizontal(boolean scrollRight, int steps) {
		PointOption<?> xy1 = point_xy_mid;
		PointOption<?> xy2 = point_xy_right;
		if (scrollRight) {
			xy2 = point_xy_left;
		}
		while ((steps--)>0) {

			new TouchAction(driver)
					.press(xy1)
					.waitAction(waitOptions(Duration.ofMillis(touchDuration)))
					.moveTo(xy2)
					.release().perform();
		}
	}		
	
	@SuppressWarnings("rawtypes")
	public void clickElement(MobileElement e) {
		new TouchAction(driver)
					.tap(point((e.getLocation().getX()+e.getSize().getWidth()/2),
							(e.getLocation().getY()+e.getSize().getHeight()/2)))
					.waitAction(waitOptions(Duration.ofMillis(250))).perform();
	}

	@SuppressWarnings("rawtypes")
	public void clickElement(PointOption<?> b) {
		new TouchAction(driver)
				.tap(b)
				.waitAction(waitOptions(Duration.ofMillis(250))).perform();
	}

	public void clickAlertCancelButton() {
		waitForElementToAppear(alertCancelButton);
		alertCancelButton.click();
	}
	
    protected void comeBackToApp(String e1, String e2) {
		String e = e1;		
    	List<MobileElement> temp = driver.findElements(By.id(e));
    	while (temp.isEmpty()) {
    		driver.navigate().back();
    		temp = driver.findElements(By.id(e));
    	}
    }
}