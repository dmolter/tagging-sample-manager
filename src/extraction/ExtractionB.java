package extraction;

import java.util.ArrayList;
import java.util.Random;

/**
 * creates a list of the exact dimension of the sample size
 * uses a separate array to keep track of duplicates
 * 
 * @author Dennis
 *
 */
public class ExtractionB extends Extraction {

	protected boolean duplicateChecker[];

	ExtractionB(int sizeCorpus, int sizeSample) {
		super(sizeCorpus, sizeSample);
	}

	/**
	 * @override public ArrayList<Integer> Extraction.createSample()
	 *           redirects to the specific methods
	 */
	public ArrayList<Integer> createSample() {
		initDuplicateChecker();
		pickLines();
		return workingCopy;
	}

	/** initializes the boolean field line **/
	private void initDuplicateChecker() {
		this.duplicateChecker = new boolean[ORIGINAL_SIZE];
		for (int i = 0; i < ORIGINAL_SIZE; i++) {
			duplicateChecker[i] = false;
		}
	}

	/** picks random lines, checking for duplicates in boolean field **/
	private void pickLines() {
		Random generator = new Random();
		int r;

		for (int i = 1; i <= SAMPLE_SIZE; i++) {
			do {
				r = generator.nextInt(ORIGINAL_SIZE);
			} while (duplicateChecker[r]);
			workingCopy.add(r + 1);
			duplicateChecker[r] = true;
		}

		for (int i = 0; i < ORIGINAL_SIZE; i++) {
			if (duplicateChecker[i])
				System.out.print("X ");
			else
				System.out.print("O ");
			if ((i + 1) % 10 == 0)
				System.out.println();
		}
	}

}
