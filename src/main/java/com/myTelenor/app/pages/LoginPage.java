package com.myTelenor.app.pages;

import java.util.List;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.touch.offset.PointOption;

public class LoginPage extends BaseWidget {
    // Android
    private static final String phoneIdAndroid = "new UiSelector().resourceId(\"phone\")";
    private static final String nextIdAndroid = "new UiSelector().resourceId(\"next\")";
    private static final String passwordIdAndroid = "new UiSelector().resourceId(\"enterPassword\")";
    private static final String loginButtonIdAndroid = "new UiSelector().resourceId(\"se.telenor.mytelenor:id/loginButton\")";
    private static final String errorViewTryAgainButtonIdAndroid = "new UiSelector().resourceId(\"se.telenor.mytelenor:id/errorViewTryAgainButton\")";
    private static final String chooseButtonIdAndroid = "new UiSelector().resourceId(\"se.telenor.mytelenor:id/selectAccountButton\")";

    // iOS
    private static final String connectIDIdiOS = "main";
    private static final String iOScontinuePredicateiOS = "type == 'XCUIElementTypeButton' AND label == 'Continue'";
    private static final String loginButtonIdiOS = "Sign in";
    private static final String buttonsIdiOS = "XCUIElementTypeButton";
    private static final String chooseButtonIdiOS = "Select";

    @iOSXCUITFindBy(iOSNsPredicate = "type == 'XCUIElementTypeApplication' AND name == 'My Telenor'")
    private MobileElement connectIDBS;

    @iOSXCUITFindBy(accessibility = connectIDIdiOS)
	private MobileElement connectID;
	
    @AndroidFindBy(uiAutomator = phoneIdAndroid)
    private MobileElement phone;
	
    @AndroidFindBy(uiAutomator = nextIdAndroid)
    private MobileElement next;	

    @AndroidFindBy(uiAutomator = passwordIdAndroid)
    private MobileElement password;
    
    @iOSXCUITFindBy(iOSNsPredicate = iOScontinuePredicateiOS)
    private MobileElement iOScontinue;

    @AndroidFindBy(uiAutomator = loginButtonIdAndroid)
    @iOSXCUITFindBy(accessibility = loginButtonIdiOS)
    private MobileElement  loginButton;
    
    @iOSXCUITFindBy(className = buttonsIdiOS)
    private List<MobileElement> buttons;
    
    @AndroidFindBy(uiAutomator = errorViewTryAgainButtonIdAndroid)
    private MobileElement errorViewTryAgainButton;	
    
    @AndroidFindBy(uiAutomator = chooseButtonIdAndroid)
    @iOSXCUITFindBy(accessibility = chooseButtonIdiOS)
    private MobileElement chooseButton;	

    private String env;

    public LoginPage(AppiumDriver<MobileElement> driver, boolean iOS,
                     PointOption<?> m, PointOption<?> t, PointOption<?> b, PointOption<?> l, PointOption<?> r, PointOption<?> bs, PointOption<?> ts,
                     String env) {
        super(driver, iOS, m, t, b, l, r, bs, ts);
        this.env = env;
    }

    public void loginButton() {
    	waitForElementToAppear(loginButton);
        loginButton.click();
    }
    
    public void clickErrorViewTryAgainButton() {
    	waitForElementToAppear(errorViewTryAgainButton);
    	errorViewTryAgainButton.click();
    }
    
    public void checkLoginButton() {
    	waitForElementToAppear(loginButton);
    }
    
    public void enterPhone(String number) {
        waitForElementToAppear(phone);
        phone.sendKeys(number);
		waitForElementToAppear(next);
		next.click();
    }

    public void enterPassword(String pass) {
        waitForElementToAppear(password);
        password.sendKeys(pass);
		waitForElementToAppear(next);
		next.click();
    }
    
    public void clickChooseButton() {
    	waitForElementToAppear(chooseButton);
    	chooseButton.click();
    }
}
