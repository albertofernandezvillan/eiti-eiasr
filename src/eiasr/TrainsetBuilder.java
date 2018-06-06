package eiasr;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class TrainsetBuilder {
	public static final String ROOT = "GTSDB/";
	
	public static final String TRAIN_PATH = "train/";
    public static final String TEST_PATH = "test/";
    
    public static int COUNT = 70;

	public static void main(String[] args) {
		// Load the native library.
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		System.out.println(Core.NATIVE_LIBRARY_NAME.toString());
		
		TrainsetBuilder builder = new TrainsetBuilder();
		
		List<File> files = builder.getFiles(ROOT+TEST_PATH + "localization");
		
		Random r = new Random();
		
		// Read from file
		Mat img = Imgcodecs.imread(files.get(1).getAbsolutePath(), CvType.CV_32F); 
		
		for( int k = 1; k<=COUNT; k++) {
			
			// draw size of subimage
			int randSize = 16 + r.nextInt(128-16);
			System.out.println("size: " + randSize + "x" + randSize);
			
			// draw x coord of subimage
			int x = r.nextInt(img.width() - randSize);
			System.out.println("x: " + x);
			
			// draw y coord of subimage
			int y = r.nextInt(img.height() - randSize);
			System.out.println("y: " + y);
			
			Size size = new Size(randSize, randSize);
			
			Point p1 = new Point(x, y);
			Point p2 = new Point(x + size.width, y + size.height);
			Rect rect = new Rect(p1, p2); 
			
			Mat submat = img.submat(rect);
			Imgcodecs.imwrite(ROOT + TRAIN_PATH + "no/" + x + "-" + y + "-" + randSize + ".ppm", submat);

		}
	}
	
	public List<File> getFiles(String dirPath){
        List<File> list = new ArrayList<File>();

        File file = new File(dirPath);

        if (!file.exists()) {
            System.out.println("Error : " + dirPath + " folder is not exist!");
            return list;
        }

        if (!file.isDirectory()) {
            System.out.println("Error : " + dirPath + "  is not a folder!");
            return list;
        }

        File[] files = file.listFiles();
        if (files.length == 0) {
            System.out.println("Error : " + dirPath + "  folder is empty!");
            return list;
        }

        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            list.add(f);
        }
        return list;
    }

}
