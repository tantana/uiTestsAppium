package com.myTelenor.app.tests.resources;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.remote.MobileCapabilityType;

public class CustomizedCapabilities {
	private String SERVER = "hub-cloud.browserstack.com";
	
	public DesiredCapabilities dc;
	public String url = "";
	
	public CustomizedCapabilities(String env, String app, int i, String timeStamp,
			String USERNAME, String ACCESS_KEY) {
	//public CustomizedCapabilities(String env, String app) {
		dc = new DesiredCapabilities();
        if (env.equals("AndroidBS")) {
			 	// Android BrowserStack
        		String deviceName = "Samsung Galaxy S6";
				dc.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
				dc.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
				dc.setCapability("build", "Android-"+timeStamp);
				dc.setCapability("name", deviceName+" run_"+i);
				dc.setCapability("browserstack.debug", true);
				dc.setCapability("app", app);
				dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
		        //dc.setCapability("language", "en");
				//dc.setCapability("real_mobile", "true");
				url = "http://"+USERNAME+":"+ACCESS_KEY+"@"+SERVER+"/wd/hub";
        }
        else if (env.equals("AndroidL")) {
			// Android local
        	dc.setCapability(MobileCapabilityType.DEVICE_NAME, "Samsung Galaxy S7");
    		dc.setCapability(MobileCapabilityType.UDID, "ce11160b95278c1505");
        	//dc.setCapability(MobileCapabilityType.DEVICE_NAME, "G8441");
    		//dc.setCapability(MobileCapabilityType.UDID, "BH901GJL9E");
			dc.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
			dc.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.0.0");
			dc.setCapability("appPackage", "se.telenor.mytelenor");
			dc.setCapability("appActivity", "com.telenor.app.mytelenor.ui.LoginActivity");
			dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
	        //dc.setCapability("language", "en");
			url = "http://127.0.0.1:4723/wd/hub";        	
    	}
        else if (env.equals("iOSBS")) {
        	/*
{"os":"ios","os_version":"12","device":"iPhone XS","realMobile":true},
{"os":"ios","os_version":"12","device":"iPhone XS Max","realMobile":true},
{"os":"ios","os_version":"12","device":"iPhone XR","realMobile":true},
{"os":"ios","os_version":"11","device":"iPhone X","realMobile":true},
{"os":"ios","os_version":"12","device":"iPhone 8","realMobile":true},
{"os":"ios","os_version":"11","device":"iPhone 8","realMobile":true},
{"os":"ios","os_version":"11","device":"iPhone 8 Plus","realMobile":true},
{"os":"ios","os_version":"10","device":"iPhone 7","realMobile":true},
{"os":"ios","os_version":"10","device":"iPhone 7 Plus","realMobile":true},
{"os":"ios","os_version":"11","device":"iPhone 6S","realMobile":true},
{"os":"ios","os_version":"11","device":"iPhone 6S Plus","realMobile":true},
{"os":"ios","os_version":"11","device":"iPhone 6","realMobile":true},
        	 */
				// iOS BrowserStack
			String deviceName = "iPhone 7 Plus";
			String platformVersion = "10.0";

				dc.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        		dc.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
        		dc.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        		dc.setCapability(MobileCapabilityType.APP, app);
        		dc.setCapability("bundleId", "com.telenor.se.MyTelenor");
        		dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
        		dc.setCapability("noRest", true);
        		dc.setCapability("build", "iOS-"+timeStamp);
        		dc.setCapability("name", deviceName+" "+platformVersion+" run_"+i);
        		System.out.println(deviceName+" "+platformVersion+" "+i);
        		dc.setCapability("browserstack.debug", true);
				dc.setCapability("browserstack.appium_version", "1.9.1");
        		url = "http://"+USERNAME+":"+ACCESS_KEY+"@"+SERVER+"/wd/hub";
        }
        else {
        		// iOS local
				//dc.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 6s Plus");
				dc.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 5s");
				dc.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
				//dc.setCapability(MobileCapabilityType.PLATFORM_VERSION, "12.2");
				dc.setCapability(MobileCapabilityType.PLATFORM_VERSION, "12.1.4");
				//dc.setCapability(MobileCapabilityType.UDID, "127655f1685adccef8ffcb3381f195a623c0ec6a");
				dc.setCapability(MobileCapabilityType.UDID, "5af71c83ccd0425cf004391cc51e1c822fe2f89d");
				dc.setCapability(MobileCapabilityType.APP, "/Users/annaj/Downloads/MyTelenor20190327.ipa");
				dc.setCapability("bundleId", "com.telenor.se.MyTelenor");
				dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
				dc.setCapability("noRest", true);
				url = "http://127.0.0.1:4723/wd/hub";
		}        
	}	
}
