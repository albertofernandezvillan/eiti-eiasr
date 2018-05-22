package eiasr;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class HogDesc {
	private int blockSizeX = 2;					// normalization block size
	private int blockSizeY = 2;
	private double histInterval = 20; 			// 20 degree interval between histogram bins
	private Size imgSize = new Size(64, 64); 	// size of image to describe (in pixels)
	private Size cellSize = new Size(8, 8); 	// size of image to describe (in pixels)
	
	public HogDesc() {	
		super();
	}
	
	public HogDesc(Size imgSize, Size cellSize, double histInterval, int blockSizeX, int blockSizeY) {
		super();
		this.blockSizeX = blockSizeX;
		this.blockSizeY = blockSizeY;
		this.histInterval = histInterval;
		this.imgSize = imgSize;
		this.cellSize = cellSize;
	}
	
	public double[] getHogDescriptor(Mat input) {
		int cellCountX = (int)(imgSize.width/cellSize.width); 	// count cells in row
		int cellCountY = (int)(imgSize.height/cellSize.height); // count cells in column
		int binCount = (int)(360/histInterval);
		
		int descLength = (cellCountX-blockSizeX + 1)*(cellCountY-blockSizeY + 1)*blockSizeX*blockSizeY*binCount;
		
		double[] hogDesc = new double[descLength];
		
		Mat mat = new Mat();
		Imgproc.resize(input, mat, imgSize);
		
		// calculate Gradient magnitude and angle
		Grad[][] grad = new Grad[cellCountX][cellCountY];
		for( int i=0; i<cellCountX; i++) {
			for( int j=0; j<cellCountX; j++){
				Point cellStartPoint = new Point(i*cellSize.width, j*cellSize.height);
				Rect cellRect = new Rect(cellStartPoint, cellSize);
				grad[i][j] = HogUtils.calculateGradient(mat, cellRect);
			}
		}
		
		// calculate histogram for every cell
		Hist[][] hist = HogUtils.calculateHist(grad);
		
		Hist[][] normBlock = new Hist[blockSizeX][blockSizeY];
		// normalize histograms
		
		//System.out.println(descLength);
		for( int i=0; i<(cellCountX-blockSizeX + 1); i++) {
			for( int j=0; j<(cellCountY-blockSizeY + 1); j++) {			
				normBlock = HogUtils.normHistOverBlock(hist, blockSizeX, blockSizeY, i, j);
				
				double[] block = blockToArray(normBlock, blockSizeX, blockSizeY, binCount);
				
				for(int k =0; k<block.length; k++) {
					hogDesc[i*(cellCountY-blockSizeY + 1)*block.length+j*block.length+k] = block[k];
				}			
			}
		}
		return hogDesc;
		
	}
	
	private double[] blockToArray(Hist block[][], int bSizeX, int bSizeY, int binCount) {
		int arrayLen = bSizeX*bSizeY*binCount;
		
		double[] array = new double[arrayLen]; 
		for(int i = 0; i<bSizeX; i++) {
			for(int j = 0; j<bSizeY; j++) {
				for(int k = 0; k<binCount; k++) array[i*bSizeY*binCount+j*binCount+k] = block[i][j].getBinVal(k);
			}
		}
		return array;
	}
	
	public int getBlockSizeX() {
		return blockSizeX;
	}
	
	public void setBlockSizeX(int blockSizeX) {
		this.blockSizeX = blockSizeX;
	}
	public int getBlockSizeY() {
		return blockSizeY;
	}
	public void setBlockSizeY(int blockSizeY) {
		this.blockSizeY = blockSizeY;
	}
	public double getHistInterval() {
		return histInterval;
	}
	public void setHistInterval(double histInterval) {
		this.histInterval = histInterval;
	}
	public Size getImgSize() {
		return imgSize;
	}
	public void setImgSize(Size imgSize) {
		this.imgSize = imgSize;
	}
	public Size getCellSize() {
		return cellSize;
	}
	public void setCellSize(Size cellSize) {
		this.cellSize = cellSize;
	}
	
	
	
	
	

}
