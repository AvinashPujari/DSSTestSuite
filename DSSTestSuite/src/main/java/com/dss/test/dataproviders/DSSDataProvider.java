package com.dss.test.dataproviders;

import org.testng.annotations.DataProvider;

/**
 * ------- DSSDataProvider ------- 
 * Author: QA-DART 
 * Created on: 20-May-2016
 * History of Changes: Data Provider for Subscription Tests
 */
public class DSSDataProvider {

    @DataProvider(name="dssDataProviderOutsideAreaZIPNonSSOR")
    public static Object[][] getDSSTestDataOutsideAreaZIPWithNonSSOR() {

	return new Object[][] {
		{ "90012", "tribune1", "TestCreditCard", "4111 1111 1111 1111", "05 - May", "2018", "James", "Hogger",
			"125 nimes rd st church", "", "32801", "Orlando", "FL", "651-628-4488" },
		
		 };
    }
    
    @DataProvider(name="dssDataProviderOutsideAreaZIPSSOR")
    public static Object[][] getDSSTestDataOutsideAreaZIPWithSSOR() {

	return new Object[][] {
		{ "90012", "jan12@gmail.com", "USSA", "1236547895", "071923909", "James", "Hogger",
			"125 nimes rd st church", "", "32801", "Orlando", "FL", "651-628-4488", "tribune1" },
		 };
    }
    
    
    @DataProvider(name="dssDataProviderWithInAreaZIPSSOR")
    public static Object[][] getDSSTestDataForWithInAreaZIPWithSSOR() {

	return new Object[][] {
		{ "32801", "jan12@gmail.com", "TestCreditCard", "4111 1111 1111 1111", "05 - May", "2018", "James", "Hogger",
			"125 nimes rd st church", "", "32801", "Orlando", "FL", "651-628-4488", "tribune1" },
		 };
    }
    
    @DataProvider(name="dssDataProviderWithInAreaZIPNonSSOR")
    public static Object[][] getDSSTestDataForWithInAreaZIPWithNonSSOR() {

	return new Object[][] {
		{ "32801", "tribune1", "USSA", "1236547895", "071923909", "James", "Hogger",
			"125 nimes rd st church", "", "32801", "Orlando", "FL", "651-628-4488" },
		 };
    }
    
    
    @DataProvider(name="dssDataProviderDigitalPlusSSOR")
    public static Object[][] getDSSTestDataForDigitalPlusWithSSOR() {

	return new Object[][] {
		{ "Digital Super Saver", "jan12@gmail.com", "TestCreditCard", "4111 1111 1111 1111", "05 - May", "2018", "James", "Hogger",
			"125 nimes rd st church", "", "32801", "Orlando", "FL", "651-628-4488", "tribune1" },
		 };
    }
    
    @DataProvider(name="dssDataProviderDigitalPlusNonSSOR")
    public static Object[][] getDSSTestDataForDigitalPlusWithNonSSOR() {

	return new Object[][] {
		{ "Digital Saver", "tribune1", "USSA", "1236547895", "071923909", "James", "Hogger",
			"125 nimes rd st church", "", "32801", "Orlando", "FL", "651-628-4488" },
		 };
    }

}