package system;

import corpus.*;

public class Core {

	private static final double CORPUS_PERCENTAGE = 0.6;
	private static final String STANDARD_PARAMETER_STRING = "\"brown\"";

	public static void main(String[] args) {
		String param1 = args[0].length() > 0 ? args[0] : "brown";

		Corpus c = null;
		if (param1.equals("brown")) {
			c = new Brown();
		} else if (param1.equals("negra")) {
			c = new Negra();
		} else {
			System.err.println("Illegal parameter! Using standard parameter "
					+ STANDARD_PARAMETER_STRING + " instead.");
			param1 = "brown";
			c = new Brown();
		}

		int sizeCorpus = c.getSize();
		int sizeSample = (int) (sizeCorpus * CORPUS_PERCENTAGE);

		/*
		 * extracting a sample from the corpus and splitting it up into training set and test set
		 * (currently only the line numbers, lines themselves are written to file)
		 */

		c.extractSample(sizeSample);
		c.splitSample(param1 + "_sample", "tree");
	}

}