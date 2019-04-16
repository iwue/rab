package calculation.rab.local;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import local.rab.controller.calculation.CalculationAngels;

class CalculationAnglesTest {
	private CalculationAngels calcAngels;
	
	@Before
	void init() {
		calcAngels = new CalculationAngels();
	}
	
	@Test
	void xIsPositiveAndYisZero() {
		double result = CalculationAngels.calcTheta1(200, 0);
		assertEquals(result, 0);
	}
	
	@Test
	void xIsPositiveAndYisPositive() {
		double result = CalculationAngels.calcTheta1(200, 200);
		assertEquals(result, 45);
	}
	
	@Test
	void xIsZeroAndYisPositive() {
		double result = CalculationAngels.calcTheta1(0, 200);
		assertEquals(result, 90);
	}
	
	@Test
	void xIsNegativeAndYisZero() {
		double result = CalculationAngels.calcTheta1(-200, 200);
		assertEquals(result, 135);
	}
	
	@Test
	void xIsNegativeAndYisNegative() {
		double result = CalculationAngels.calcTheta1(-200, -200);
		assertEquals(result, 225);
	}
	
	@Test
	void xIsZeroAndYisNegative() {
		double result = CalculationAngels.calcTheta1(0, -200);
		assertEquals(result, 270);
	}

	@Test
	void xIsPositiveAndYisNegative() {
		double result = CalculationAngels.calcTheta1(200, -200);
		assertEquals(result, 315);
	}
	
	@Test
	void xIsZeroAndYisZero() {
		double result = CalculationAngels.calcTheta1(0, 0);
		assertEquals(result, 0);
	}
}
