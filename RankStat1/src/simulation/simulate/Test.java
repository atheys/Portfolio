package simulation.simulate;

import java.io.IOException;

public class Test {
	public static void main(String[] args) throws IOException, InterruptedException {
		String path = "/Applications/TeX/TeXShop";
		String doc = "/Users/andreastheys/Documents/Test.tex";
		ProcessBuilder pb = new ProcessBuilder(path, doc);
		Process p = pb.start();
	}
}
