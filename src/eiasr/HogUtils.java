package eiasr;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class HogUtils {
	
	static final int ddepth = CvType.CV_32F;
//  Mat mag = new Mat();
//  Mat angle = new Mat(); 
//  Core.cartToPolar(gx, gy, mag, angle); 
//  
//  Mat grad = new Mat();
//  Core.convertScaleAbs(mag, grad);

	public static void calculateGradient(Mat img, Mat grad, Mat angle){
		
		Mat grayImg = new Mat();
		Mat gx = new Mat();
		Mat abs_gx = new Mat();
		Mat gy = new Mat();
		Mat abs_gy = new Mat();
		
		// convert to grayscale
		Imgproc.cvtColor(img, grayImg, Imgproc.COLOR_BGR2GRAY);

		// Gradient X
		Imgproc.Sobel(grayImg, gx, ddepth, 1, 0);
		Core.convertScaleAbs(gx, abs_gx);

		// Gradient Y
		Imgproc.Sobel(grayImg, gy, ddepth, 0, 1);
		Core.convertScaleAbs(gy, abs_gy);
		
		// convert cartesian gradients to magnitude and angle
		Mat mag = new Mat();
		Core.cartToPolar(gx, gy, mag, angle); 

		Core.convertScaleAbs(mag, grad);
	}
	
	
	public static Mat calculateGradientX(Mat img){
		
		Mat grayImg = new Mat();
		Mat gx = new Mat();
		Mat abs_gx = new Mat();
		
		// convert to grayscale
		Imgproc.cvtColor(img, grayImg, Imgproc.COLOR_BGR2GRAY);

		// Gradient X
		Imgproc.Sobel(grayImg, gx, ddepth, 1, 0);
		Core.convertScaleAbs(gx, abs_gx);
		
		return abs_gx;
	}
	
	public static Mat calculateGradientY(Mat img){
		
		Mat grayImg = new Mat();
		Mat gy = new Mat();
		Mat abs_gy = new Mat();
		
		// convert to grayscale
		Imgproc.cvtColor(img, grayImg, Imgproc.COLOR_BGR2GRAY);

		// Gradient Y
		Imgproc.Sobel(grayImg, gy, ddepth, 0, 1);
		Core.convertScaleAbs(gy, abs_gy);
		
		return abs_gy;
	}
	
	
	
	
    
}
