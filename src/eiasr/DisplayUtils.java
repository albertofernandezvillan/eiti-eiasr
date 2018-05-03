package eiasr;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class DisplayUtils {

	public static void displayGradAngle(Mat mat, Rect roi, int x, int y, Size displaySize) {
		// calculate Gradient magnitude and angle and print
		Grad grad = HogUtils.calculateGradient(mat, roi);

		Mat roiMat = mat.submat(roi);

		// Printing
		System.out.println("Gradient magnitude:");
		// System.out.println(grad.getMag().dump());
		PrintUtils.printImageInfo("grad.getMag()", grad.getMag());

		System.out.println("Gradient angle:");
		// System.out.println(grad.getAngle().dump());
		PrintUtils.printImageInfo("grad.getAngle()", grad.getAngle());

		Size matSize = roiMat.size();
		Mat displayMat = new Mat();

		Imgproc.resize(roiMat, displayMat, displaySize); // resize to draw a vector on every pixel

		int maxMag = 255;
		int maxDisplayMag = (int) (displaySize.width / matSize.width); // max grad magnitude do draw

		double[] angleBuffer = new double[1];
		double[] magBuffer = new double[1];

		for (int i = 0; i < (int) matSize.height; i++) {
			for (int j = 0; j < (int) matSize.width; j++) {

				angleBuffer = grad.getAngle().get(i, j);
				double a = angleBuffer[0];

				magBuffer = grad.getMag().get(i, j);
				double m = magBuffer[0] * (double) (maxDisplayMag) / (double) (maxMag);

				int gx = (int) ((Math.round(m * Math.cos(Math.toRadians(a)))));
				int gy = (int) ((Math.round(m * Math.sin(Math.toRadians(a)))));

				Point pp1 = new Point(maxDisplayMag * j + (maxDisplayMag - gx) / 2,
						maxDisplayMag * i + (maxDisplayMag - gy) / 2);
				Point pp2 = new Point(maxDisplayMag * j + (maxDisplayMag + gx) / 2,
						maxDisplayMag * i + (maxDisplayMag + gy) / 2);

				Imgproc.line(displayMat, pp1, pp2, new Scalar(0, 0, 255));
			}
		}

		// Displaying
		DisplayUtils.displayImage(displayMat, x, y, displaySize);
	}

	public static void displayGradAngle(Mat mat, int x, int y, Size displaySize) {
		// calculate Gradient magnitude and angle and print
		Grad grad = HogUtils.calculateGradient(mat);

		// Printing
		System.out.println("Gradient magnitude:");
		// System.out.println(grad.getMag().dump());
		PrintUtils.printImageInfo("grad.getMag()", grad.getMag());

		System.out.println("Gradient angle:");
		// System.out.println(grad.getAngle().dump());
		PrintUtils.printImageInfo("grad.getAngle()", grad.getAngle());

		Size matSize = mat.size();
		Mat displayMat = new Mat();

		Imgproc.resize(mat, displayMat, displaySize); // resize to draw a vector on every pixel

		int maxMag = 255;
		int maxDisplayMag = (int) (displaySize.width / matSize.width); // max grad magnitude do draw

		double[] angleBuffer = new double[1];
		double[] magBuffer = new double[1];

		for (int i = 0; i < (int) matSize.height; i++) {
			for (int j = 0; j < (int) matSize.width; j++) {

				angleBuffer = grad.getAngle().get(i, j);
				double a = angleBuffer[0];

				magBuffer = grad.getMag().get(i, j);
				double m = magBuffer[0] * (double) (maxDisplayMag) / (double) (maxMag);

				int gx = (int) ((Math.round(m * Math.cos(Math.toRadians(a)))));
				int gy = (int) ((Math.round(m * Math.sin(Math.toRadians(a)))));

				Point pp1 = new Point(maxDisplayMag * j + (maxDisplayMag - gx) / 2,
						maxDisplayMag * i + (maxDisplayMag - gy) / 2);
				Point pp2 = new Point(maxDisplayMag * j + (maxDisplayMag + gx) / 2,
						maxDisplayMag * i + (maxDisplayMag + gy) / 2);

				Imgproc.line(displayMat, pp1, pp2, new Scalar(0, 0, 255));
			}
		}

		// Displaying
		DisplayUtils.displayImage(displayMat, x, y, displaySize);
	}

	public static void displayImage(Image img) {

		displayImageInFrame(img); // display image
	}

	public static void displayImage(Image img, int x, int y) {

		JFrame frame = displayImageInFrame(img); // display image
		frame.setLocation(x, y); // set position on screen in pixels
	}

	public static void displayImage(Mat mat) {

		Image img = MatToImage(mat); // convert Mat to Image
		displayImageInFrame(img); // display image
	}

	public static void displayImage(Mat mat, int x, int y) {

		Image img = MatToImage(mat); // convert Mat to Image
		JFrame frame = displayImageInFrame(img); // display image
		frame.setLocation(x, y); // set position on screen in pixels
	}

	public static void displayImage(Mat mat, int x, int y, Size size) {

		Mat display = new Mat();
		Imgproc.resize(mat, display, size); // resize to display
		Image img = MatToImage(display); // convert Mat to Image
		JFrame frame = displayImageInFrame(img); // display image
		frame.setLocation(x, y); // set position on screen in pixels
	}

	private static JFrame displayImageInFrame(Image img) {

		ImageIcon icon = new ImageIcon(img); // create icon filled with Image
		JFrame frame = new JFrame(); // create frame

		// set FlowLayout for Frame and fill it
		frame.setLayout(new FlowLayout());
		frame.setSize(img.getWidth(null) + 50, img.getHeight(null) + 50);
		JLabel lbl = new JLabel();
		lbl.setIcon(icon);
		frame.add(lbl);

		// display frame
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		return frame;
	}

	private static BufferedImage MatToImage(Mat mat) {

		// detect whether image is grayscale or colour
		int type = BufferedImage.TYPE_BYTE_GRAY;
		if (mat.channels() > 1) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}

		// create buffer - one byte per every pixel (per channel)
		int bufferSize = mat.channels() * mat.cols() * mat.rows();
		byte[] buffer = new byte[bufferSize];

		mat.get(0, 0, buffer); // get all the pixels from Mat and write values to Buffer

		// create BufferedImage and fill with data from buffer
		BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(buffer, 0, targetPixels, 0, buffer.length);

		// output can be assigned either to a BufferedImage or to an Image
		return image;
	}

}
