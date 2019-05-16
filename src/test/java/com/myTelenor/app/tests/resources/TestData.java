package com.myTelenor.app.tests.resources;

import java.util.HashMap;

import com.google.common.collect.ImmutableMap;

public class TestData {  
	public String EFTERNAMN = "Telenor Sverige AB";
	public String PASSWORD = "test1337";
	
	private boolean testStatusFlagMain = false;
	private boolean testStatusFlagLogin = false;
	private boolean testStatusFlagMenu = false;
	private boolean testStatusFlagIteam = false;
	private boolean testStatusFlagSubIteam = false;
	
	public String API_URL = "http://api.bredband.local/idmanagement";
			
	public final ImmutableMap<String, HashMap<String, String>> ACCOUNTS_TEST_AT = ImmutableMap.<String, HashMap<String, String>>builder()
			//Fam2
			.put("Family2",	new HashMap<String, String>() {
				private static final long serialVersionUID = 1L;
				{	put("auto2owner",	"+46733070763"); } })			
			
			.build();
	
	public void setTestStatusFlagMain(boolean s) {
		testStatusFlagMain = s;
	}
	
	public boolean getTestStatusFlagMain() {
		return testStatusFlagMain;
	}
	
	/*public boolean getTestStatusFlagMenu() {
		return testStatusFlagMenu;
	}*/
	
	public void setTestStatusFlagMenu(boolean s) {
		testStatusFlagMenu = s;
	}	
	
	public boolean getTestStatusFlagLogin() {
		return testStatusFlagLogin;
	}
	
	public void setTestStatusFlagLogin(boolean s) {
		testStatusFlagLogin = s;
	}	
	
	public boolean getTestStatusFlagIteam() {
		return testStatusFlagIteam;
	}
	
	public void setTestStatusFlagIteam(boolean s) {
		testStatusFlagIteam = s;
	}
	
	public boolean getTestStatusFlagSubIteam() {
		return testStatusFlagSubIteam;
	}
	
	public void setTestStatusFlagSubIteam(boolean s) {
		testStatusFlagSubIteam = s;
	}	
	
	public boolean getTestStatusFlagLoginMenu() {
		return (testStatusFlagMain && testStatusFlagLogin && testStatusFlagMenu);
	}
	
	public boolean getTestStatusFlagLoginMenuIteam() {
		return (testStatusFlagMain && testStatusFlagLogin && testStatusFlagMenu && testStatusFlagIteam);
	}
	
	public boolean getTestStatusFlagLoginMenuIteamSubIteam() {
		return (testStatusFlagMain && testStatusFlagLogin && testStatusFlagMenu && testStatusFlagIteam && testStatusFlagSubIteam);
	}
}
