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

public class GradientDemo extends DisplayUtils {

	public static void main(String[] args) {
		// Load the native library.
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		System.out.println(Core.NATIVE_LIBRARY_NAME.toString());

		showGradFull();
		
		//showGradMag();
		
	}

	private static void showGradFull() {
		Size cellSize = new Size(8, 8);
		Size size = new Size(64, 64); // image size for calculation
		Size displaySize = new Size(256, 256); // image size for displaying
		int displayOffsetX = (int) (displaySize.width + 50.0);

		// ********************* LOAD IMAGE *******************************
		// ****************************************************************

		// Read from file
		Mat input = Imgcodecs.imread("GTSRB/Final_Training/Images/00000/00002_00023.ppm", CvType.CV_32F);

		// Print Info and Image
		PrintUtils.printImageInfo("input", input);
		DisplayUtils.displayImage(input);

		Size inputSize = input.size();
		int displayPositionX = (int) (inputSize.width + 50.0);

		// ********************* PREPROCESSING ***********************
		// ************************************************************
		// Mat inputBlur = new Mat();
		// Imgproc.GaussianBlur(input, inputBlur, new Size(5, 5) ,1 ,1);
		Mat inputBlur = input;

		// ********************* RESIZE IMAGE ***************************
		// **************************************************************
		Mat mat = new Mat();
		Imgproc.resize(inputBlur, mat, size);

		// ********************* CHOOSE THE 8x8 CELL *******************
		// *************************************************************

		Point p1 = new Point(24, 24);
		Point p2 = new Point(24 + cellSize.height, 24 + cellSize.width);
		Rect cellRect = new Rect(p1, p2); // 8x8 cell definition

		Mat displayMat = new Mat(); // make a copy for displaying
		mat.copyTo(displayMat);

		Imgproc.rectangle(displayMat, p1, p2, new Scalar(0, 255, 0)); // mark selected cell to display

		PrintUtils.printImageInfo("mat", mat);
		DisplayUtils.displayImage(displayMat, displayPositionX, 0, displaySize); // display image

		displayPositionX += displayOffsetX;

		Mat cell = mat.submat(cellRect); // extract the cell
		PrintUtils.printImageInfo("cell", cell);
		DisplayUtils.displayImage(cell, displayPositionX, 0, displaySize); // display cell

		displayPositionX += displayOffsetX;

		// ***************** CALCULATE GRADIENT IN CELL *****************************
		// ************************************************************************

		// calculate Gradient magnitude and angle and print
		Grad grad = HogUtils.calculateGradient(mat, cellRect);

		// Displaying
		DisplayUtils.displayImage(grad.getMag(), displayPositionX, 0, displaySize);

		displayPositionX += displayOffsetX;

		// ******************** DISPLAY CELL WITH GRADIENT VECTORS *********************
		// ****************************************************************************
		DisplayUtils.displayGradAngle(mat, cellRect, displayPositionX, 0, displaySize);
		
		// ******************** DISPLAY CELL WITH GRADIENT VECTORS *********************
		// ****************************************************************************
		Size displaySize2 = new Size(512, 512);
		DisplayUtils.displayGradAngle(mat, 200, (int)displaySize.height+100, displaySize2);
	}

	private static void showGradMag() {
		// Read from file
		Mat input = Imgcodecs.imread("GTSRB/Final_Training/Images/00000/00002_00023.ppm", CvType.CV_32F);

		// Print Info and Image
		PrintUtils.printImageInfo("input", input);
		DisplayUtils.displayImage(input);

		Size inputSize = input.size();
		int displayPositionX = (int) (inputSize.width + 50.0);

		// Resize input frame
		Size size = new Size(128, 128);
		Mat frame = new Mat();
		Imgproc.resize(input, frame, size);

		PrintUtils.printImageInfo("frame", frame);
		DisplayUtils.displayImage(frame, displayPositionX, 0);

		int displayOffsetX = (int) (size.width + 50.0);
		displayPositionX += displayOffsetX;

		// calculate Gradient X (horizontal) and print
		Mat gradX = HogUtils.calculateGradientX(frame);
		PrintUtils.printImageInfo("gradX", gradX);
		DisplayUtils.displayImage(gradX, displayPositionX, 0);

		displayPositionX += displayOffsetX; // move offset for the next frame to display

		// calculate Gradient Y (vertical) and print
		Mat gradY = HogUtils.calculateGradientY(frame);
		PrintUtils.printImageInfo("gradY", gradY);
		DisplayUtils.displayImage(gradY, displayPositionX, 0);

		displayPositionX += displayOffsetX; // move offset for the next frame to display

		// calculate Gradient magnitude and angle and print
		Grad grad = HogUtils.calculateGradient(frame);
		PrintUtils.printImageInfo("grad.getMag()", grad.getMag());
		DisplayUtils.displayImage(grad.getMag(), displayPositionX, 0);
	}

}
