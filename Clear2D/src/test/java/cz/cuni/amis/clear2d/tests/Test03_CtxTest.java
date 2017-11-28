package cz.cuni.amis.clear2d.tests;

import org.junit.Test;

public class Test03_CtxTest {
	
	
	
	@Test
	public void test() {
		System.out.println("Starting Clear2D...");
		CtxTest.init();
		System.out.println("Stopping Clear2D...");
		CtxTest.die();
	}
	
	public static void main(String[] args) {
		new Test03_CtxTest().test();		
	}
	
}
