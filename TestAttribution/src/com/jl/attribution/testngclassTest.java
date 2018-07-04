package com.jl.attribution;

import org.testng.Assert;
import org.testng.annotations.Test;

public class testngclassTest {

	String message = "Hello World";
	testngclass messageUtil = new testngclass(message);

	@Test
	public void testPrintMessage() {
		Assert.assertEquals(message, messageUtil.printMessage());
	}
}
