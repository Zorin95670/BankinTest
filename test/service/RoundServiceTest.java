package service;

import org.junit.Assert;
import org.junit.Test;

public class RoundServiceTest {

	private static double DELTA = 1e-2;
	
	@Test
	public final void getTest() {
		Assert.assertEquals("1", 1.00f, RoundService.get(1.00f), DELTA);
		Assert.assertEquals("2", 1.00f, RoundService.get(0.75f), DELTA);
		Assert.assertEquals("3", 2.00f, RoundService.get(1.01f), DELTA);
		Assert.assertEquals("4", 4.00f, RoundService.get(4.00f), DELTA);
		Assert.assertEquals("5", 9.00f, RoundService.get(9.00f), DELTA);
		Assert.assertEquals("6", 11.00f, RoundService.get(11.00f), DELTA);
		Assert.assertEquals("7", 16.00f, RoundService.get(16.00f), DELTA);
		Assert.assertEquals("8", 20.00f, RoundService.get(19.00f), DELTA);
		Assert.assertEquals("8", 60.00f, RoundService.get(57.00f), DELTA);
		Assert.assertEquals("8", 58.00f, RoundService.get(57.01f), DELTA);
	}
}
