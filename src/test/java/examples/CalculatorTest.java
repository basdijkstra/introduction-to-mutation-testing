package examples;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
	
	@Test
	public void testAddition() {
		
		Calculator calculator = new Calculator();
		calculator.add(2);
		assertEquals(2, calculator.getResult());
	}
	
	@Test
	public void testPower() {
		
		Calculator calculator = new Calculator(2);
		calculator.power(3);
		assertEquals(8, calculator.getResult());
	}
	
	@Test
	public void testConditionalSetTrue() {
		
		Calculator calculator = new Calculator();
		assertTrue(calculator.setConditional(2, true));
	}
	
	@Test
	public void testConditionalSetFalse() {
		
		Calculator calculator = new Calculator();
		assertFalse(calculator.setConditional(3, false));
	}
}
