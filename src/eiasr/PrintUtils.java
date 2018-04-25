package eiasr;

import java.awt.Image;

import org.opencv.core.Mat;

public class PrintUtils {
	
	public static void printImageInfo(String name, Mat mat) {

		System.out.println("Mat " + name + " size:" + mat.size());
		System.out.println("Mat " + name + " channels:" + mat.channels());
		
		// get type number and decode most popular types
		int type = mat.type();
		String typeName = "";
		
		switch(type) {
			case 0: typeName = "CV_8UC1";
					break;
			case 1: typeName = "CV_8SC1";
					break;
			case 5: typeName = "CV_32F";
					break;
			case 16: typeName = "CV_8UC3";
					break;
			default: typeName = "check for type name in opencv doc";
					break;
		}
		System.out.println("Mat " + name + " type:" + mat.type() + " (" + typeName + ")");
		
		System.out.println("Mat " + name + " depth:" + mat.depth());
	    System.out.println("===========================================");
	}
	
}
