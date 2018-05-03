package eiasr;

import java.util.Arrays;

public class Hist {
	private double[] bins = new double[17];
	
	public Hist() {
		super();
	}
	
	public Hist(double[] bins) {
		super();
		this.bins = bins;
	}

	public double[] getBins() {
		return bins;
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

	@Override
	public String toString() {
		return "Hist [bins=" + Arrays.toString(bins) + "]";
	}

}
