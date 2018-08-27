package jtm.testSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import jtm.activity01.ConfigurationTest;
import jtm.activity02.HelloTest;
import jtm.activity03.ArrayTest;
import jtm.activity03.BlackKnightTest;
import jtm.activity03.RandomPersonTest;
import jtm.activity04.TrafficManagementTest;
import jtm.activity05.EncapsulationTests;
import jtm.activity06.InterfaceTests;
import jtm.activity07.AnimalTests;
import jtm.activity08.SimpleCalcTests;
import jtm.activity09.OrdersTests;
import jtm.activity10.StreamEditorTest;
import jtm.activity11.ArrayFillerManagerTest;
import jtm.activity12.ChatServerTest;
import jtm.activity13.DatabaseTest;
import jtm.activity14.DatabaseUnitTest;
import jtm.activity15.ColorSliderTest;
import jtm.activity17.JettyApplicationTest;
import jtm.activity17.SeleniumWebDriverTest;
import jtm.activity18.GitMergeTest;
import jtm.extra01.GetOneTest;
import jtm.extra01.ZodiacTest;
import jtm.extra02.ArrayListMethodsTest;
import jtm.extra02.LetsRideTest;
import jtm.extra03.PracticalNumbersTest;
import jtm.extra04.StringTokenizerExerciseTest;
import jtm.extra04.StudentTest;
import jtm.extra05.XMLCarsTest;
import jtm.extra06.GenericsTest;
import jtm.extra06.HolidayTest;
import jtm.extra06.RegExTest;
import jtm.extra07.ChatClientTest;
import jtm.extra08.InvoiceManagerTest;
import jtm.extra09.BoardTest;
import jtm.extra09.CrocodileTest;
import jtm.extra09.GameTest;
import jtm.extra10.LogTest;
import jtm.extra11.PersonMatcherTests;
import jtm.extra12.JNIClassTest;

@RunWith(JtmSuite.class)
@SuiteClasses({

		// First week

		ConfigurationTest.class, // 1
		HelloTest.class, // 2
		ArrayTest.class, // 3
		BlackKnightTest.class, // 3
		RandomPersonTest.class, // 3
		TrafficManagementTest.class, // 4
		EncapsulationTests.class, // 5

		InterfaceTests.class, // 6
		AnimalTests.class, // 7
		SimpleCalcTests.class, // 8
		OrdersTests.class, // 9
		StreamEditorTest.class, // 10

		// Second week

		ArrayFillerManagerTest.class, // 11
		ChatServerTest.class, // 12
		DatabaseTest.class, // 13
		DatabaseUnitTest.class, // 14
		ColorSliderTest.class, // 15
								// 16 — external project — Android application
		JettyApplicationTest.class, // 17
		SeleniumWebDriverTest.class, // 17
		GitMergeTest.class, // 18

		// extra activities

		GetOneTest.class, // 1
		ZodiacTest.class, // 1
		ArrayListMethodsTest.class, // 2
		LetsRideTest.class, // 2
		PracticalNumbersTest.class, // 3
		StringTokenizerExerciseTest.class, // 4
		StudentTest.class, // 4
		XMLCarsTest.class, // 5
		GenericsTest.class, // 6
		HolidayTest.class, // 6
		RegExTest.class, // 6
		ChatClientTest.class, // 7
		InvoiceManagerTest.class, // 8
		CrocodileTest.class, // 9
		GameTest.class, // 9
		LogTest.class, // 10
		PersonMatcherTests.class, // 11
		JNIClassTest.class // 12

})
public class AllTests {

}
