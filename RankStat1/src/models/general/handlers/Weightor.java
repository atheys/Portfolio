package models.general.handlers;

import java.util.ArrayList;
import java.util.List;

import mathematics.interpolation.Matrix;

/**
 * Weightor class with helper functions.
 * 
 * @author Andreas Theys.
 * @version 2.0
 */
public class Weightor {

	/**
	 * 
	 * @param n
	 * @return
	 */
	public static Matrix uniformWeights(int n){
		double[][] uni = new double[n][1];
		for(int i=0; i<n; i++){
			uni[i][0] = (double) 1/n;
		}
		return new Matrix(uni);
	}
	
	/**
	 * Normalizes weights value array.
	 * 
	 * @param weights
	 *            weights value array.
	 * @return normalized weights array (1st degree).
	 */
	public static double[] normalize(double[] weights) {
		double sum = 0.;
		for (double d : weights) {
			sum += d;
		}
		for (int i = 0; i < weights.length; i++) {
			weights[i] /= sum;
		}
		return weights;
	}

	/**
	 * Normalizes weights value array.
	 * 
	 * @param weights
	 *            weights value array.
	 * @return normalized weights array (1st degree).
	 */
	public static double[][] normalize(double[][] weights) {
		assert weights.length > 0 && weights[0].length == 1;
		double sum = 0.;
		for (double[] d : weights) {
			sum += d[0];
		}
		for (int i = 0; i < weights.length; i++) {
			weights[i][0] /= sum;
		}
		return weights;
	}

	/**
	 * Top helper method for partition development.
	 * 
	 * @param n
	 *            precision figure.
	 * @param k
	 *            partition element size.
	 * @param partitions
	 *            temporary story list.
	 */
	private static void partition(int n, int k, List<double[]> partitions) {
		partition(n, n, "", k, partitions);
	}

	/**
	 * Top helper method for partition development.
	 * 
	 * @param n
	 *            precision figure.
	 * @param max
	 *            recursive input variable.
	 * @param prefix
	 *            recursive input string variable.
	 * @param k
	 *            partition element size.
	 * @param partitions
	 *            temporary story list.
	 */
	private static void partition(int n, int max, String prefix, int k, List<double[]> partitions) {
		if (n == 0) {
			String[] strings = prefix.split(" ");
			String[] strings_mod = new String[strings.length - 1];
			for (int i = 1; i < strings.length; i++) {
				strings_mod[i - 1] = strings[i];
			}
			if (strings_mod.length == k) {
				double[] numbers = new double[k];
				for (int i = 0; i < strings_mod.length; i++) {
					numbers[i] = Double.parseDouble(strings_mod[i]);
				}
				partitions.add(numbers);
			}
		}
		for (int i = Math.min(max, n); i >= 1; i--) {
			partition(n - i, i, prefix + " " + i, k, partitions);
		}
	}

	/**
	 * Provides partition elements with precision 1/n.
	 * 
	 * @param n
	 *            precision figure.
	 * @param k
	 *            partition element size.
	 * @return partition elements.
	 */
	private static double[][] partitions(int n, int k) {
		List<double[]> temp = new ArrayList<double[]>();
		partition(n, k, temp);
		double[][] result = new double[temp.size()][k];
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < k; j++) {
				result[i][j] = temp.get(i)[j] / n;
			}
		}
		return result;
	}

	/**
	 * Provides overall partition elements.
	 * 
	 * @param k
	 *            partition element size.
	 * @return partition elements.
	 */
	public static double[][] partitions(int k) {
		return partitions(2*k, k);
	}
	
	public static void main(String[] args){
		for(int i=1; i<=10; i++){
			double[][] par = partitions(i);
			/*for(double[] p: par){
				for(double d: p){
					System.out.print(d+" ");
				}
				System.out.println();
			}*/
			System.out.println(par.length);
		}
		
	}
	
}