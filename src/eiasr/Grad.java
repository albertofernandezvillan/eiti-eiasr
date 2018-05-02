package eiasr;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

public class Grad {
	Mat mag;	// magnitude
	Mat angle;	// angle
	
	public Grad(Mat mag, Mat angle) {
		super();
		this.mag = mag;
		this.angle = angle;
	}
	
	public Mat getMag() {
		return mag;
	}
	public void setMag(Mat mag) {
		this.mag = mag;
	}
	public Mat getAngle() {
		return angle;
	}
	public void setAngle(Mat angle) {
		this.angle = angle;
	}
	
	public void submat(Rect rect) {
//		//Printing
//        System.out.println("Gradient magnitude:");
//        System.out.println(this.getMag().dump());
//        PrintUtils.printImageInfo("this.getMag()", this.getMag());
        
        //System.out.println(rect.height);
		this.angle = this.angle.submat(rect);
		this.mag = this.mag.submat(rect);
		
//		//Printing
//        System.out.println("Gradient magnitude:");
//        System.out.println(this.getMag().dump());
//        PrintUtils.printImageInfo("this.getMag()", this.getMag());
	}
	
	

}
