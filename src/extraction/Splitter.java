package extraction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/** splits up the sample into a training and a test sample **/

public abstract class Splitter {

	private final String PATH = new File("").getAbsolutePath().concat("\\tagging\\");
	private LineNumberReader input = null;
	private BufferedWriter outputTraining = null, outputTest = null;
	protected BufferedWriter outputTestOriginal = null;
	private int sizeSample;
	private ArrayList<Integer> lineSample = new ArrayList<Integer>(sizeSample);
	private List<Integer> training = null;
	private List<Integer> test = null;
	protected String tagger;

	protected int sizeCorpus;

	// TODO: make file names variable, too

	/**
	 * initializing, setting source files and target files
	 * TODO: tagger class, move init to that class, replace tagger string by instance call
	 */
	protected Splitter(ArrayList<Integer> sample, String file, String prefix, String tagger) {
		sizeSample = sample.size();
		lineSample = sample;
		this.tagger = tagger;
		if (tagger.equals("tree")) {
			initTreeIO(file, prefix);
		}
		else if (tagger.equals("stanford")) {
			initStanfordIO(file, prefix);
		}
	}

	/**
	 * sets up I/O
	 * subclasses do not need knowledge of the Reader and Writer
	 * TODO: use only one init method
	 **/
	private void initTreeIO(String file, String prefix) {
		try {
			input = new LineNumberReader(new FileReader(PATH + file));
			outputTraining = new BufferedWriter(new FileWriter(PATH + prefix + "_tree_training.utf8"));
			outputTest = new BufferedWriter(new FileWriter(PATH + prefix + "_tree_test.utf8"));
			outputTestOriginal = new BufferedWriter(new FileWriter(PATH + prefix + "_tree_test_original.utf8"));
		} catch (Exception e) {
			System.err.println("File error!");
			System.out.println(e.getMessage());
		}
	}

	private void initStanfordIO(String file, String prefix) {
		try {
			input = new LineNumberReader(new FileReader(PATH + file));
			outputTraining = new BufferedWriter(new FileWriter(PATH + prefix + "_stanford_training.utf8"));
			outputTest = new BufferedWriter(new FileWriter(PATH + prefix + "_stanford_test.utf8"));
			outputTestOriginal = new BufferedWriter(new FileWriter(PATH + prefix + "_stanford_test_original.utf8"));
		} catch (IOException ex) {
			System.err.println("File error!");
		}

	}

	/** split sample into samples for test set and training set each **/
	private void prepareSample() {
		final int sizeTraining = (int) (sizeSample * 0.9);
		// create test sample by duplicating line_sample and clearing the training sample from it
		test = (List<Integer>) (lineSample.clone()); // unsafe conversion ??
		test.subList(0, sizeTraining).clear();
		// create training sample by clearing test sample from it
		lineSample.subList(sizeTraining, sizeSample).clear();
		training = lineSample; // assign a new name for clarity
	}

	/**
	 * write training set and test set to respective output
	 * currently offers option to print picked lines with TRAI and TEST identifier tags to console
	 **/
	private void createOutput(String setLabel, String element, int elementNumber) throws IOException {
		BufferedWriter output = null;
		List<Integer> set = null;

		if (setLabel.equals("TRAI")) {
			set = training;
			output = outputTraining;
		}
		else if (setLabel.equals("TEST")) {
			set = test;
			output = outputTest;

		}
		else {
			System.err.println("This set designation does not exist!");
		}

		if (set.contains(elementNumber)) {
			element = readElement(input, elementNumber);
			element = processElement(setLabel, element);
			// System.out.println(set_label + ": " + element_number + element);
			// output.write(element_number + ": " + "\n"); // optional
			output.write(element);
			// output.newLine();
			output.flush();
		}

	}

	/**
	 * redirection method
	 * TODO: Training and Test classes? Tagger classes?
	 **/
	private String processElement(String setType, String element) {
		if (setType.equals("TRAI"))
			return processTrainingElement(element);
		else if (setType.equals("TEST"))
			return processTestElement(element);
		else
			return null;
	}

	/**
	 * reads an element (either line or sentence)
	 * TODO better OOP to hand over LineNumberReader??
	 **/
	protected abstract String readElement(LineNumberReader reader, int elementNumber) throws IOException;

	/** process an element which is member of the training set **/
	protected abstract String processTrainingElement(String element);

	/** process an element which is member of the test set **/
	protected abstract String processTestElement(String element);

	/**
	 * 
	 * NOTE: an element is either a line or a sentence, depending on the corpus
	 * 
	 * TODO: the training sample will be the first part of the sample, in order
	 * since the sample is not in numerical order (atm), this is not too tragic,
	 * but if the sample is at some point sorted (which might be advised, because
	 * currently the lines are searched in the sample, which is costly), this must
	 * be changed
	 * TODO: iterate across the sample, not the entire number of elements!
	 **/
	public void split() {
		String element = null;
		try
		{
			prepareSample();

			for (int elementNum = 1; elementNum <= sizeCorpus; elementNum++) {
				// System.out.println("# " + element_num); // tracking progress in the console

				createOutput("TRAI", element, elementNum);
				createOutput("TEST", element, elementNum);

			}
		} catch (IOException e) {
			System.err.println("File error!");
		} finally {
			try {
				input.close();
				outputTraining.close();
				outputTest.close();
				outputTestOriginal.close();
			} catch (Exception e) {
			}
		}

	}

}
