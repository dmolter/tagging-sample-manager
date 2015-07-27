package extraction;

import java.util.ArrayList;
import java.util.Collections;

/**
 * shuffles the line index and trims it down to the sample size
 **/
public class ExtractionA extends Extraction {

	ExtractionA(int sizeOriginal, int sizeSample) {
		super(sizeOriginal, sizeSample);
	}

	/**
	 * @override public ArrayList<Integer> Extraction.createSample()
	 *           redirects to the specific methods
	 */
	public ArrayList<Integer> createSample() {
		shuffle();
		trim();
		return workingCopy;
	}

	/**
	 * creates an ArrayList of size CORPUS_SIZE and returns it after shuffling
	 * 
	 * 
	 */
	private void shuffle() {
		for (int i = 1; i <= ORIGINAL_SIZE; i++) {
			workingCopy.add(i);
		}
		Collections.shuffle(workingCopy);
	}

	private void trim() {
		workingCopy.subList(SAMPLE_SIZE, workingCopy.size()).clear();
	}

}
