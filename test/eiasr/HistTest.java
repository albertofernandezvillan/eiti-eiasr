package eiasr;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

class HistTest {
	
	private Hist h;

	@Test
	void testHistGrad() {
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
		Grad grad = new Grad(mag, angle);
		
		// create build histogram from gradient
		h = new Hist(grad);
		
		double[] resultBins = {75.0, 200.0, 0.0, 0.0, 0.0, 
								0.0, 0.0, 0.0, 50.0, 250.0, 
								0.0, 0.0, 0.0, 0.0, 0.0, 
								0.0, 0.0, 25.0};
		
		assertArrayEquals(resultBins, h.getBins(), 0.01);
	}

}
