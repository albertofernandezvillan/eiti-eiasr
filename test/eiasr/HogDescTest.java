package eiasr;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

class HogDescTest {

	@Test
	void testGetHogDescriptor() {
		// Load the native library.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		
		Mat mat = Mat.eye(20, 20, CvType.CV_8UC3);
		
		HogDesc hog = new HogDesc();
		
		double[] resultHog = hog.getHogDescriptor(mat);
		
		assertTrue(3528==resultHog.length);
	}

}
