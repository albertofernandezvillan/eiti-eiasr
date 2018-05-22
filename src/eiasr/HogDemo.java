package eiasr;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class HogDemo {
	final static Size cellSize = new Size(8, 8);
	final static Size size = new Size(64, 64); // image size for calculation
	final static Size displaySize = new Size(512, 512); // image size for displaying
	final static int displayOffsetX = (int) (displaySize.width + 50.0);

	public static void main(String[] args) {

		// Load the native library.
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		System.out.println(Core.NATIVE_LIBRARY_NAME.toString());

		// ********************* LOAD IMAGE *******************************

		// Read from file
		Mat input = Imgcodecs.imread("GTSRB/train/00002/00002_00027.ppm", CvType.CV_32F);
		//Mat input = Imgcodecs.imread("GTSRB/train/00000/00004_00020.ppm", CvType.CV_32F);
		//Mat input = Imgcodecs.imread("GTSRB/train/00000/00002_00027.ppm", CvType.CV_32F);

		// Print Info and Image
		PrintUtils.printImageInfo("input", input);
		DisplayUtils.displayImage(input);

		Size inputSize = input.size();
		int displayPositionX = (int) (inputSize.width + 50.0);

		// ********************* PREPROCESSING **************************
		// Mat inputBlur = new Mat();
		// Imgproc.GaussianBlur(input, inputBlur, new Size(5, 5) ,1 ,1);
		Mat inputBlur = input;

		// ********************* RESIZE IMAGE ***************************
		Mat mat = new Mat();
		Imgproc.resize(inputBlur, mat, size);

		// ******************** DISPLAY CELL WITH GRADIENT VECTORS ******
	
		DisplayUtils.displayGradAngle(mat, displayPositionX, 0, displaySize);
		
		displayPositionX += displayOffsetX;
		

		// ********************* DISPLAY 8x8 CELLS **********************
		Mat displayMat = new Mat(); // make a copy for displaying
		mat.copyTo(displayMat);
		
		for(int x=0; x<size.width; x+=cellSize.width) {
			for(int y=0; y<size.height; y+=cellSize.height) {
				Point p1 = new Point(x, y);
				Point p2 = new Point(x + cellSize.height, y + cellSize.width);
				Imgproc.rectangle(displayMat, p1, p2, new Scalar(0, 255, 0)); // mark selected cell to display
			}
		}
		PrintUtils.printImageInfo("displayMatt", displayMat);
		DisplayUtils.displayImage(displayMat, displayPositionX, 0, displaySize); // display image

		displayPositionX += displayOffsetX;
		
		// ******************** VISUALIZE HOG **************************
		
		DisplayUtils.displayHog(mat, cellSize, displayPositionX, 0, displaySize); // display image
		
		// *************CALC AND PRINT HOG DESCRIPTOR ********************
		HogDesc hogDesc = new HogDesc();
		double[] hog = hogDesc.getHogDescriptor(mat);
		System.out.println("FULL HOG DESCRIPTOR (length = " + hog.length + "):" );
		for(int i =0; i<hog.length; i++) {
			if (i%18==0) System.out.println(" ");
			System.out.print(hog[i] + " ");
		}
			

	}

}
