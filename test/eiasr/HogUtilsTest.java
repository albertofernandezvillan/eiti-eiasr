package eiasr;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class HogUtilsTest {

	private Hist[][] hists;

	@Test
	void testNormHist() {
		
		double[] bins1 = {75.0, 200.0, 0.0, 0.0, 0.0, 
				0.0, 0.0, 0.0, 50.0, 250.0, 
				0.0, 0.0, 0.0, 0.0, 0.0, 
				0.0, 0.0, 25.0};
		
		double[] bins2 = {150.0, 400.0, 0.0, 0.0, 0.0, 
				0.0, 0.0, 0.0, 100.0, 500.0, 
				0.0, 0.0, 0.0, 0.0, 0.0, 
				0.0, 0.0, 50.0};
		
		Hist h1 = new Hist(bins1);
		Hist h2 = new Hist(bins2);
		
		Hist resultHist1 = HogUtils.normHist(h1);
		Hist resultHist2 = HogUtils.normHist(h2);
		
		assertArrayEquals(resultHist1.getBins(),resultHist2.getBins(), 0.01);

	}
	
	@Test
	void testNormHist2() {
		
		double[] b1 = new double[3], b2 = new double[3], b3 = new double[3], b4 = new double[3],
				b5 = new double[3], b6 = new double[3], b7 = new double[3], b8 = new double[3];
		Hist[][] h1 = new Hist[2][2];
		Hist[][] h2 = new Hist[2][2];
		
		for(int i=0; i<b1.length; i++) {
			b1[i] = i; b5[i] = 2*b1[i]; 
			b2[i] = 2*i; b6[i] = 2*b2[i]; 
			b3[i] = 3*i; b7[i] = 2*b3[i]; 
			b4[i] = 4*i; b8[i] = 2*b4[i];
		}
		
		h1[0][0]=new Hist(b1); h2[0][0]=new Hist(b5);
		h1[0][1]=new Hist(b2); h2[0][1]=new Hist(b6);
		h1[1][0]=new Hist(b3); h2[1][0]=new Hist(b7);
		h1[1][1]=new Hist(b4); h2[1][1]=new Hist(b8);
		
		Hist[][] resultHists1 = HogUtils.normHist(h1, 2, 2);
		Hist[][] resultHists2 = HogUtils.normHist(h2, 2, 2);

		
		assertArrayEquals(resultHists1[0][0].getBins(),resultHists2[0][0].getBins(), 0.01);
		assertArrayEquals(resultHists1[0][1].getBins(),resultHists2[0][1].getBins(), 0.01);
		assertArrayEquals(resultHists1[1][0].getBins(),resultHists2[1][0].getBins(), 0.01);
		assertArrayEquals(resultHists1[1][1].getBins(),resultHists2[1][1].getBins(), 0.01);

	}

}
