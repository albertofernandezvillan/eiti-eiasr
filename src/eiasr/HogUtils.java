package eiasr;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

public class HogUtils {
	
	static final int ddepth = CvType.CV_32F;
	
	/**
	 * Given Gradient object containing matrixes of gradient magnitude and direction (angle)
	 * for every pixel, returning Histogram of Oriented Gradients
	 * @param grad	Gradient (Grad) object 
	 * @return 		Histogram (Hist) object
	 * @see Hist
	 */
	public static Hist calculateHist(Grad grad) {
		return new Hist(grad);
	}
	
	public static Hist[][] calculateHist(Grad[][] grad) {
		Hist[][] result = new Hist[grad.length][grad[0].length];
		for(int i=0; i<grad.length; i++) {
			for(int j=0; j<grad[0].length; j++) result[i][j] = new Hist(grad[i][j]);
		}
		return result;
	}
	
	/**
	 * 
	 * @param hists
	 * @param blockWidth
	 * @param blockHeight
	 * @param blockPosX
	 * @param blockPosY
	 * @return
	 */
	public static Hist[][] normHistOverBlock(Hist[][] hists, int blockWidth, int blockHeight, int blockPosX, int blockPosY) {
		Hist[][] result = new Hist[blockWidth][blockHeight];
		for(int i=0; i<blockWidth; i++) {
			for(int j=0; j<blockHeight; j++) result[i][j] = new Hist();
		}
		
		int sum = 0;
		for(int j=blockPosX; j<blockPosX+blockWidth; j++) {
			for(int k=blockPosY; k<blockPosY+blockHeight; k++) {
				for(int i=0; i<hists[j][k].getBins().length; i++) sum += hists[j][k].getBinVal(i);
				//System.out.println(hists[j][k].toString());
			}
		}
		
		for(int j=blockPosX; j<blockPosX+blockWidth; j++) {
			for(int k=blockPosY; k<blockPosY+blockHeight; k++) {
				
				for(int i=0; i<hists[j][k].getBins().length; i++) result[j-blockPosX][k-blockPosY].setBinVal(hists[j][k].getBinVal(i)/sum, i);
				//System.out.println(result[j-blockPosX][k-blockPosY].toString());
			}
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param hist
	 * @return
	 */
	public static Hist normHist(Hist hist) {
		Hist result = new Hist();
		int sum = 0;
		for(int i=0; i<hist.getBins().length; i++) sum += hist.getBinVal(i);
		
		for(int i=0; i<hist.getBins().length; i++) result.setBinVal(hist.getBinVal(i)/sum, i);
		//System.out.println(result.toString());
		
		return result;
	}
	
	public static Grad calculateGradient(Mat img, Rect rect){
//		Mat mag = new Mat();
//		Mat angle = new Mat();
		
		// calculate gradient for whole image
		Grad grad = calculateGradient(img);
		
		//Grad grad = new Grad(mag, angle);
        grad.submat(rect);
		
		return grad;
	}

	public static Grad calculateGradient(Mat img){
		
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
		Mat angle = new Mat();
		Mat tempMag = new Mat();
		Core.cartToPolar(gx, gy, tempMag, angle, true); 
		
		Mat mag = new Mat();
		Core.convertScaleAbs(tempMag, mag);
		
		Grad grad = new Grad(mag, angle);
		
		return grad;
		
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
