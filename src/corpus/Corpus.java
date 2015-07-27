package corpus;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

import extraction.SampleExtractor;
import extraction.SampleExtractorA;
//import extraction.SampleExtractorB;

public class Corpus {

	private final String PATH = new File("").getAbsolutePath().concat("\\tagging\\");
	protected String name;
	protected int size;
	protected ArrayList<Integer> sampleLines;

	private LineNumberReader input = null;

	public Corpus(String name, int size) {
		this.name = name;
		this.size = size;
		sampleLines = new ArrayList<Integer>(size);
	}

	public void extractSample(int sampleSize) {
		SampleExtractor se = new SampleExtractorA(this.size, sampleSize, name);
		this.sampleLines = se.getSampleLines();
	}

	/**
	 * subclasses call this first to read the sample from file, then instantiate the splitter
	 * TODO: tagger currently unused in the supermethod
	 * 
	 *
	 **/
	public void splitSample(String file, String tagger) {
		String number = null;
		try {
			input = new LineNumberReader(new FileReader(PATH + file));
			while ((number = input.readLine()) != null) {
				sampleLines.add(Integer.valueOf(number));
			}
		} catch (IOException ex) {
			System.err.println("File error!");
		}
	}

	@Override
	public String toString() {
		if (sampleLines == null) {
			System.err.println("There is no sample!");
			return null;
		} else {
			String s = "";
			for (Integer i : sampleLines) {
				s += i + " ";
			}
			return s;
		}
	}

	public int getSize() {
		return size;
	}

}
