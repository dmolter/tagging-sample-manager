package extraction;

import java.util.ArrayList;

/**
 * TODO: maybe as interface?
 * TODO: generalize, in order to receive an original rather than generating one within the class
 **/
public abstract class Extraction {

	protected final int ORIGINAL_SIZE;
	protected final int SAMPLE_SIZE;
	protected ArrayList<Integer> workingCopy = new ArrayList<Integer>();

	protected Extraction(int sizeOriginal, int sizeSample) {
		SAMPLE_SIZE = sizeSample;
		ORIGINAL_SIZE = sizeOriginal;
	}

	public abstract ArrayList<Integer> createSample();

}
