package eiasr;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.core.TermCriteria;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.ml.Ml;
import org.opencv.ml.SVM;
import org.opencv.ml.TrainData;

public class Classification {
	public static final String SVM_MODEL_FILE_PATH = "resources/b_process/svm/model/svm.xml";

    public static final String ROOT = "GTSRB/";

    public static final String TEST_PATH = "test/";
    public static final String TRAIN_PATH = "train/";
    public static final String SVM_MODEL_PATH = "model/svm.xml";

	public static void main(String[] args) {
		// Load the native library.
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		Classification c1 = new Classification();
		
		c1.train();
		
	}
	
	
    void train(){

        List<File> c1files = getFiles(ROOT+TRAIN_PATH+ "00015/" );
        List<File> c2files = getFiles(ROOT+TRAIN_PATH+ "00020/" );
        
        int c1Count = c1files.size();
        int c2Count = c2files.size();
        
        System.out.println("c1Count = " + c1Count);
        System.out.println("c2Count = " + c2Count);

        Mat samples = new Mat();
        Mat labels = new Mat();


        for (int i = 0; i < c1Count; i++) {
        	Mat img = Imgcodecs.imread(c1files.get(i).getAbsolutePath(), CvType.CV_32F);   
            //DisplayUtils.displayImage(img);          
            HogDesc hogDesc = new HogDesc();
            double[] hog = hogDesc.getHogDescriptor(img);     
            Mat hogMat = new Mat(new Size(hog.length,1),CvType.CV_32F);
            //PrintUtils.printImageInfo("hogMat", hogMat);
            hogMat.put(0, 0, hog);    
            samples.push_back( hogMat );
            labels.push_back( Mat.ones( new Size( 1, 1 ), CvType.CV_32S ) );
        }
        
        for (int i = 0; i < c2Count; i++) {
        	Mat img = Imgcodecs.imread(c2files.get(i).getAbsolutePath(), CvType.CV_32F);
            //DisplayUtils.displayImage(img);
            HogDesc hogDesc = new HogDesc();
            double[] hog = hogDesc.getHogDescriptor(img);
            Mat hogMat = new Mat(new Size(hog.length,1),CvType.CV_32F);
            //PrintUtils.printImageInfo("hogMat", hogMat);
            hogMat.put(0, 0, hog);            
            samples.push_back( hogMat );
            labels.push_back( Mat.zeros( new Size( 1, 1 ), CvType.CV_32S ) );
        }
        
        PrintUtils.printImageInfo("samples", samples);
        PrintUtils.printImageInfo("labels", labels);

        Mat trainingData = new Mat();
        Mat classes = new Mat();

        samples.copyTo(trainingData);
        trainingData.convertTo( trainingData, CvType.CV_32F);
        labels.copyTo(classes);
        
      //SVM
        SVM svm = SVM.create();
//        svm.setType(SVM.C_SVC);
//        svm.setKernel(SVM.LINEAR);
//        svm.setDegree(0.1);
//        // 1.4 bug fix: old 1.4 ver gamma is 1
//        svm.setGamma(0.1);
//        svm.setCoef0(0.1);
//        svm.setC(0.1);
//        svm.setNu(0.1);
//        svm.setP(0.1);
        
        //svm.setTermCriteria(new TermCriteria(1, 20000, 0.0001));


        long start = System.currentTimeMillis();
        System.out.println("start train...");
        boolean ret = svm.train(trainingData , Ml.ROW_SAMPLE,classes);
        System.out.println(ret);
        
        
        System.out.println("end train...total time ： " +(System.currentTimeMillis()-start) + "ms");

        //svm.xml
        //svm.save("svm.xml");
        //System.out.println("save the train model...");
        System.out.println(svm.getSupportVectors());
        
        
        List<File> c1TestFiles = getFiles(ROOT+TEST_PATH + "00015" );
        List<File> c2TestFiles = getFiles(ROOT+TEST_PATH + "00020" );
        
        int c1TestCount = c1TestFiles.size();
        int c2TestCount = c2TestFiles.size();
        
        for (int i = 0; i < c1TestCount; i++) {
        	Mat img = Imgcodecs.imread(c1TestFiles.get(i).getAbsolutePath(), CvType.CV_32F);
        	System.out.print(c1TestFiles.get(i).getAbsolutePath() + "\t");
            //DisplayUtils.displayImage(img);          
            HogDesc hogDesc = new HogDesc();
            double[] hog = hogDesc.getHogDescriptor(img);     
            Mat hogMat = new Mat(new Size(hog.length,1),CvType.CV_32F);
            //PrintUtils.printImageInfo("hogMat", hogMat);
            hogMat.put(0, 0, hog);    
            System.out.println(svm.predict(hogMat));
            
        }
        
        for (int i = 0; i < c2TestCount; i++) {
        	Mat img = Imgcodecs.imread(c2TestFiles.get(i).getAbsolutePath(), CvType.CV_32F);
        	System.out.print(c2TestFiles.get(i).getAbsolutePath() + "\t");
            //DisplayUtils.displayImage(img);          
            HogDesc hogDesc = new HogDesc();
            double[] hog = hogDesc.getHogDescriptor(img);     
            Mat hogMat = new Mat(new Size(hog.length,1),CvType.CV_32F);
            //PrintUtils.printImageInfo("hogMat", hogMat);
            hogMat.put(0, 0, hog);    
            System.out.println(svm.predict(hogMat));
            
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
