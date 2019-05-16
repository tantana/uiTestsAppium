package com.myTelenor.app.pages;

import io.appium.java_client.touch.offset.PointOption;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class HomePage extends BaseWidget {
    // Android
    private static final String homeMenuXpathAndroid = "//android.widget.TextView[@text = 'Home']";
    private static final String navigationbarIdAndroid = "new UiSelector().resourceId(\"se.telenor.mytelenor:id/toolbarRightButton\")";
    private static final String logoutXpathAndroid = "//android.widget.TextView[@text = 'Sign out']";
    private static final String bubbleIdAndroid = "new UiSelector().resourceId(\"se.telenor.mytelenor:id/connectedAccountHomeOverlayBubble\")";
    private static final String freeIdAndroid = "new UiSelector().resourceId(\"se.telenor.mytelenor:id/toolbar\")";

    // iOS
    private static final String homeMenuIdiOS = "Home";
    private static final String notificationPredicateiOS = "type == 'XCUIElementTypeStaticText' AND label == 'Allow notifications?'";
    private static final String navigationbarIdiOS = "switch account icon";
    private static final String logoutIdiOS = "Sign out";
    private static final String bubbleIdiOS = "balloon-image";
    private static final String freeIdiOS = "balloon-image";

    private static final String noPredicateiOS = "type == 'XCUIElementTypeButton' AND label == 'NO'";
    private static final String yesPredicateiOS = "type == 'XCUIElementTypeButton' AND label == 'YES'";
    private static final String allowPredicateiOS = "type == 'XCUIElementTypeButton' AND label == 'Allow'";

    @AndroidFindBy(xpath = homeMenuXpathAndroid)
    @iOSXCUITFindBy(accessibility = homeMenuIdiOS)
    private MobileElement homeMenu;
    
	@AndroidFindBy(uiAutomator = navigationbarIdAndroid)
    @iOSXCUITFindBy(accessibility = navigationbarIdiOS)
    private MobileElement navigationbar;
    
    @AndroidFindBy(xpath = logoutXpathAndroid)
    @iOSXCUITFindBy(accessibility = logoutIdiOS)
    private MobileElement logout;

    @AndroidFindBy(uiAutomator = bubbleIdAndroid)
    @iOSXCUITFindBy(accessibility = bubbleIdiOS)
    private MobileElement bubble;
    
    @AndroidFindBy(uiAutomator = freeIdAndroid)
    @iOSXCUITFindBy(accessibility = freeIdiOS)
    private MobileElement free;    

    @iOSXCUITFindBy(iOSNsPredicate = notificationPredicateiOS)
    private MobileElement notification;

    @iOSXCUITFindBy(iOSNsPredicate = noPredicateiOS)
    private MobileElement no;

    @iOSXCUITFindBy(iOSNsPredicate = yesPredicateiOS)
    private MobileElement yes;

    @iOSXCUITFindBy(iOSNsPredicate = allowPredicateiOS)
    private MobileElement allow;
    
    public HomePage(AppiumDriver<MobileElement> driver, boolean envB,
                    PointOption<?> m, PointOption<?> t, PointOption<?> b, PointOption<?> l, PointOption<?> r, PointOption<?> bs, PointOption<?> ts) {
        super(driver, envB, m, t, b, l, r, bs, ts);
    }

    public void navigateToHome() {
    	waitForElementToAppear(homeMenu);
    	homeMenu.click();
    }

    public MobileElement getNotification() {
    	waitForElementToAppear(notification);
    	return notification;
    }
    
    public void toolbarClick() {
    	waitForElementToAppear(navigationbar);
    	navigationbar.click();
    }
    
    public void logoutButton() {
    	waitForElementToAppear(logout);
        logout.click();
    }
  
    public void clickBubble() {
    	waitForElementToAppear(bubble);
    	bubble.click();
    }
    
    public void freeClick() {
    	waitForElementToAppear(free);
    	free.click();
    }
        
    public void clickYes() {
    	waitForElementToAppear(yes);
		yes.click();
    }
    
    public void clickAllow() {
    	waitForElementToAppear(allow);
		allow.click();
    }

    public void clickNo() {
        waitForElementToAppear(no);
        no.click();
    }
}
