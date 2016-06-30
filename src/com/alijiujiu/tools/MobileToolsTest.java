package com.alijiujiu.tools;

import org.junit.Before;
import org.junit.Test;

import android.test.AndroidTestCase;
import junit.framework.Assert;

public class MobileToolsTest extends AndroidTestCase {

	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testIsMobileNoMatch() {
		String phone = "18764233176";
		boolean result = MobileTools.isMobileNoMatch(phone);
		Assert.assertEquals("Succeed!", true, result);
	}
	@Test
	public void test(){
		String phone = "12364233176";
		boolean result = MobileTools.isMobileNoMatch(phone);
		Assert.assertEquals("Succeed!", true, result);
	}
}
