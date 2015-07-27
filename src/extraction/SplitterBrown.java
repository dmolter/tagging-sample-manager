package extraction;

import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SplitterBrown extends Splitter {

	// TODO: retrieve from Brown
	private final int BROWN_SIZE = 57341;

	// TODO: retrieve from Brown
	public SplitterBrown(ArrayList<Integer> sample, String tagger) {
		super(sample, "brown_c.utf8", "brown", tagger);
		sizeCorpus = BROWN_SIZE;
	}


	/** reads the next relevant line and returns it [for Brown] **/
	private String readLine(LineNumberReader reader, int lineNumber) throws IOException {
		String s = null;
		while (reader.getLineNumber() < lineNumber) {
			s = reader.readLine();
		}
		return s;
	}

	protected String readElement(LineNumberReader reader, int elementNumber) throws IOException {
		return readLine(reader, elementNumber);
	}

	private String splitElement(String element) {
		// pattern:
		// "/" followed by group 1
		// group 1 = at least one character which is non-whitespace and non-digit

		Pattern p = Pattern.compile("/([^/&&\\S&&\\D]+)(\\s|$)");
		Matcher m = p.matcher(element);

		List<MatchResult> results = new ArrayList<MatchResult>();
		String[] splitElement = null;

		while (m.find()) {

			// collect matcher results and split element with the matcher regex
			// (calling m.group(1) does NOT work!)
			results.add(m.toMatchResult());
			splitElement = element.split("/([^/&&\\S&&\\D]+)(\\s|$)");

		}

		// if there are no matches / if there is nothing to split, return the unchanged element
		// (both events should be coinciding, but to be on the safe side...)
		if (results.isEmpty() || splitElement == null) {
			return element;
		}
		ListIterator<MatchResult> iter = results.listIterator();
		element = "";
		// append the tags to the elements of the split with a tab inbetween and finishing with
		// a newline
		for (int i = 0; i < splitElement.length && iter.hasNext(); i++) {
			element += splitElement[i].trim() + "\t" + iter.next().group(1).trim() + "\n";
		}

		return element;
	}

	protected String processTrainingElement(String element) {
		if (tagger.equals("tree")) {
			return splitElement(element);
		}
		else if (tagger.equals("stanford")) {
			return element + "\n";
		}
		else
			return null;
	}

	protected String processTestElement(String element) {
		try {
			if (tagger.equals("tree")) {
				outputTestOriginal.write(splitElement(element));
				outputTestOriginal.flush();
				/*
				 * remove the tags via a regular expression
				 * meaning: replaceAll
				 * (a string starting with "/",
				 * followed by at least one non-whitespace, non-digit character
				 * terminating with a whitespace character or the end of the line ,
				 * by a newline)
				 */
				element = element.replaceAll("/([^/&&\\S&&\\D]+(\\s|$))", "\n");
				return element;
			}
			else if (tagger.equals("stanford")) {
				outputTestOriginal.write(element);
				outputTestOriginal.newLine();
				outputTestOriginal.flush();
				/*
				 * remove the tags via a regular expression
				 * meaning: replaceAll
				 * (a string starting with "/",
				 * followed by at least one non-whitespace, non-digit character
				 * terminating with a whitespace character or the end of the line ,
				 * by a newline)
				 */
				element = element.replaceAll("/([^/&&\\S&&\\D]+(\\s|$))", " ");
				return element + "\n";
			}
		} catch (IOException ex) {
			System.out.println("ERROR!");
		}
		return null;

	}
}
// "/([^/&&\\S&&\\D]+)"
// "/[^/&&\\S&&\\D]+(\\s|$)"
// "/([^/&&\\S&&\\D][\\S&&\\D]*)"
// "/([^/&&\\S&&\\D]+(\\s|$))"

