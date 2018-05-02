package eiasr;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

public class HogUtils {
	
	static final int ddepth = CvType.CV_32F;
	
	public static Grad calculateGradient(Mat img, Rect rect){
		Mat mag = new Mat();
		Mat angle = new Mat();
		
		// calculate gradient for whole image
		calculateGradient(img, mag, angle);
		
//		//Printing
//        System.out.println("Gradient magnitude:");
//        System.out.println(mag.dump());
//        PrintUtils.printImageInfo("mag", mag);
//             
//        System.out.println("Gradient angle:");
//        System.out.println(angle.dump());
//        PrintUtils.printImageInfo("angle", angle);
		
		// trim matrixes of gradient magnitudes and angles to given rect
		Grad grad = new Grad(mag, angle);
        grad.submat(rect);
		
//		//Printing
//        System.out.println("Gradient magnitude:");
//        System.out.println(grad.getMag().dump());
//        PrintUtils.printImageInfo("grad.getMag()", grad.getMag());
//             
//        System.out.println("Gradient angle:");
//        System.out.println(grad.getAngle());
//        PrintUtils.printImageInfo("grad.getAngle()", grad.getAngle());
		return grad;
	}

	public static void calculateGradient(Mat img, Mat mag, Mat angle){
		
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
		Mat tempMag = new Mat();
		Core.cartToPolar(gx, gy, tempMag, angle, true); 

		Core.convertScaleAbs(tempMag, mag);
		
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
