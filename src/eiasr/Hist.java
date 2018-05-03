package eiasr;

import java.util.Arrays;

public class Hist {
	final static double degInterval = 20.0; // interval between following bins in degrees 
	
	private double[] bins = new double[18];  // bins for histogram values 
											// one bin per angle every 20 degree
											// 0, 20, 40, ... , 340
	
	public Hist() {
		super();
	}
	
	public Hist(double[] bins) {
		super();
		this.bins = bins;
	}
	
	public Hist(Grad grad) {
		gradToBins(grad);
	}

	public double[] getBins() {
		return this.bins;
	}

	public void setBins(double[] bins) {
		this.bins = bins;
	}
	
	public double getBinVal(int index) {
		return this.bins[index];
	}
	
	public void addToBin(double addValue, int index) {
		this.bins[index] = this.bins[index] + addValue;
	}
	
	private void gradToBins(Grad grad) {
		
		double[] angleBuffer = new double[1];
		double[] magBuffer = new double[1];
		
		for (int i = 0; i < grad.getMag().rows(); i++) {
			for (int j = 0; j < grad.getMag().cols(); j++) {

				angleBuffer = grad.getAngle().get(i, j); // i - row, j - column
				double angle = angleBuffer[0];

				magBuffer = grad.getMag().get(i, j);	// i - row, j - column
				double mag = magBuffer[0];
				
				int prevBinIndex = (int)(angle/degInterval);
				
				double binsRatio = (angle-prevBinIndex*degInterval)/degInterval;
				//System.out.println(binsRatio);
				
				this.addToBin(mag*(1.0-binsRatio), prevBinIndex);
				
				int nextBinIndex = (prevBinIndex + 1) % 18;
				
				this.addToBin(mag*binsRatio, nextBinIndex);
					

			}
		}
	}

	@Override
	public String toString() {
		return "Hist [bins=" + Arrays.toString(bins) + "]";
	}

}
