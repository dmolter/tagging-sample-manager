package extraction;

import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SplitterNegra extends Splitter {

	// TODO: retrieve from Negra
	private final int NEGRA_SIZE = 20602;

	// TODO: retrieve from Negra
	public SplitterNegra(ArrayList<Integer> sample, String tagger) {
		super(sample, "negra-corpus.tt.utf8", "negra", tagger);
		sizeCorpus = NEGRA_SIZE;
	}

	/**
	 * matches the first relevant line, then for each iteration (outside of this method)
	 * reads all lines up to the blank line into the String "sentence" [for Negra]
	 **/
	private String readSentence(LineNumberReader reader, int sentenceNumber) throws IOException {
		String sentence = "";
		String line = null;
		Pattern p = Pattern.compile("%% Sent (\\d+)");
		Matcher m = null;

		for (;;) {
			line = reader.readLine();

			m = p.matcher(line);

			if (m.find()) {
				int currentSentenceNumber = Integer.valueOf(m.group(1));
				if (currentSentenceNumber == sentenceNumber)
					break;
			}
		}

		line = reader.readLine();
		while (Pattern.compile("\\S").matcher(line).find()) {

			sentence += line + "\n";
			line = reader.readLine();
		}

		return sentence;
	}

	protected String readElement(LineNumberReader reader, int elementNumber) throws IOException {
		return readSentence(reader, elementNumber);
	}

	protected String processTrainingElement(String element) {
		if (tagger.equals("tree")) {
			return element;
		}
		else if (tagger.equals("stanford")) {
			return element.replaceAll("\\t+", "/").replaceAll("[^$\\.]\\n", " ");
		}
		else
			return null;

	}

	protected String processTestElement(String element) {
		try {
			if (tagger.equals("tree")) {
				outputTestOriginal.write(element);
				outputTestOriginal.flush();
				/*
				 * remove the tags via a regular expression
				 * meaning: replaceAll
				 * (at least one tab, followed by at least one non-whitespace,
				 * followed by one whitespace ,
				 * by a newline)
				 */
				return element.replaceAll("\\t+\\S+\\s", "\n");
			} else if (tagger.equals("stanford")) {
				String temp = element.replaceAll("\\n", " ");
				outputTestOriginal.write(temp.replaceAll("\\t+", "/"));
				outputTestOriginal.newLine();
				outputTestOriginal.flush();
				return temp.replaceAll("\\t+\\S+\\s", " ") + "\n";
			}
		} catch (IOException ex) {
			System.out.println("ERROR!");
		}
		return null;
	}

}
