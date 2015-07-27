package extraction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public abstract class SampleExtractor {

	protected final ArrayList<Integer> sample;
	private final String PATH = new File("").getAbsolutePath().concat("\\tagging\\");
	private BufferedWriter output = null;

	/** creates sample of line numbers via extractor subclass (polymorphism) **/
	public SampleExtractor(Extraction that, String corpus) {
		sample = that.createSample();
		initOutput(corpus);
		writeSample();
	}

	private void initOutput(String corpus) {
		try {
			output = new BufferedWriter(new FileWriter(PATH + corpus + "_sample"));
		} catch (IOException ex) {
			System.out.println("File error in initOutput!");
		}
	}

	public ArrayList<Integer> getSampleLines() {
		return sample;
	}

	private void writeSample() {
		boolean writeNewLine = false;
		try {
			for (Integer i : sample) {
				if (!writeNewLine) {
					writeNewLine = true;
				} else {
					output.newLine();
				}
				output.write(i.toString());
				output.flush();
			}
		} catch (IOException ex) {
			System.out.println("File error!");
		}
	}
}
