package eiasr;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

class HogUtilsTest {
	
	@Test
	void testCalculateHist() {
		
		// Load the native library.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		// creates magnitude
		int row = 0, col = 0;
		Mat mag = new Mat( 3, 3, CvType.CV_8UC1);
		mag.put(row ,col, 0, 100, 0, 100, 200, 100, 0, 100, 0 );
		//System.out.println(mag.dump());
		
		// creates angles
		Mat angle = new Mat( 3, 3, CvType.CV_32F);
		angle.put(row ,col, 0, 20.0, 0, 20.0, 180.0, 170.0, 0, 355.0, 100.0);
		//System.out.println(angle.dump());
		
		// creates gradient object
		Grad[][] grad = new Grad[2][2];
		for(int i=0; i<grad.length; i++) {
			for(int j=0; j<grad[0].length; j++) grad[i][j] = new Grad(mag, angle);
		}
		
		// create build histogram from gradient
		Hist[][] hist = HogUtils.calculateHist(grad);
		
		double[] resultBins = {75.0, 200.0, 0.0, 0.0, 0.0, 
								0.0, 0.0, 0.0, 50.0, 250.0, 
								0.0, 0.0, 0.0, 0.0, 0.0, 
								0.0, 0.0, 25.0};
		
		assertArrayEquals(resultBins, hist[0][0].getBins(), 0.01);
		assertArrayEquals(resultBins, hist[0][1].getBins(), 0.01);
		assertArrayEquals(resultBins, hist[1][0].getBins(), 0.01);
		assertArrayEquals(resultBins, hist[1][1].getBins(), 0.01);

	}

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
	void testNormHist3() {
		
		int sizeX=4;
		int sizeY=4;
		int blockSizeX=2;
		int blockSizeY=2;
		
		Hist h1[][] = new Hist[sizeX][sizeY];
		Hist h2[][] = new Hist[sizeX][sizeY];
		
		for(int i=0; i<sizeX; i++) {
			for(int j=0; j<sizeY; j++) {
				double[] b1 = new double[3];
				double[] b2 = new double[3];
				for(int k=0; k<b1.length; k++){b1[k]=i+j+k; b2[k]=2*b1[k]; }
				h1[i][j] = new Hist(b1);
				h2[i][j] = new Hist(b2);
			}
		}
		
		Hist[][] resultHists1 = HogUtils.normHistOverBlock(h1, blockSizeX, blockSizeY, 2, 2);
		Hist[][] resultHists2 = HogUtils.normHistOverBlock(h2, blockSizeX, blockSizeY, 2, 2);

		for(int i=0; i<blockSizeX; i++) {
			for(int j=0; j<blockSizeY; j++) {
				assertArrayEquals(resultHists1[i][j].getBins(),resultHists2[i][j].getBins(), 0.01);
			}
		}

	}

}
