package eiasr;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;

public class HOGcalculator {
    public static void main(String[] args){

        // Load the native library.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println(Core.NATIVE_LIBRARY_NAME.toString());
        
        // Read from file
        Mat frame = Imgcodecs.imread("GTSRB/Final_Training/Images/00000/00000_00029.ppm", CvType.CV_32F);
        
        // Print Info and Image
        PrintUtils.printImageInfo("frame", frame); 
        DisplayUtils.displayImage(frame);
        
        Size size = frame.size();
        int displayOffsetX = (int)(size.width + 50.0);
        
        // calculate Gradient X (horizontal) and print
        Mat gradX = HogUtils.calculateGradientX(frame);
        PrintUtils.printImageInfo("gradX", gradX);
        DisplayUtils.displayImage(gradX, displayOffsetX, 0);
        
        
        displayOffsetX += displayOffsetX; // move offset for the next frame to display
        
        // calculate Gradient Y (vertical) and print
        Mat gradY = HogUtils.calculateGradientY(frame);
        PrintUtils.printImageInfo("gradY", gradY);
        DisplayUtils.displayImage(gradY, displayOffsetX, 0);
        
 
        displayOffsetX += displayOffsetX; // move offset for the next frame to display
        
        // calculate Gradient magnitude and angle and print
        Mat grad = new Mat(); 	// gradient magnitude
        Mat angle = new Mat(); 	// gradient angle
        
        HogUtils.calculateGradient(frame, grad, angle);
        PrintUtils.printImageInfo("grad", grad);
        DisplayUtils.displayImage(grad, displayOffsetX, 0);
   
        
    }
    
    
    
    
    
    
    
    
    

}