package evaluation;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

/**
 * a general class for comparing two lines in an unmodified state (subclasses introduce modifications)
 *
 * @author Dennis
 */
public class Evaluation {

	private final String PATH = new File("").getAbsolutePath().concat("\\tagging\\");
	private String original;
	private String comparison;
	private LineNumberReader originalReader = null;
	private LineNumberReader comparisonReader = null;
	protected String originalLine = null;
	protected String comparisonLine = null;

	public Evaluation(String original, String comparison) {
		this.original = original;
		this.comparison = comparison;
		initIO();
	}

	/**
	 * sets up I/O
	 *
	 **/
	private void initIO() {
		try {
			originalReader = new LineNumberReader(new FileReader(PATH + original));
			comparisonReader = new LineNumberReader(new FileReader(PATH + comparison));
		} catch (Exception e) {
			System.err.println("File error!");
		}
	}

	protected void processLines() {
	}

	// TODO: write a verbose output to file
	public void compare() {

		int matches = 0;
		try {
			// compare each line with the corresponding one and count the matches
			// !! all tabs are removed first, because else Negra won't work !!
			// TODO: compare only tags instead, avoiding the trouble with different
			// formats, but causing more work in preparation of the files ??
			while (((originalLine = originalReader.readLine()) != null)
					&& ((comparisonLine = comparisonReader.readLine()) != null)) {
				processLines();
				System.out.println(originalLine + " --- " + comparisonLine);
				if (originalLine.equals(comparisonLine)) {
					matches++;
				}
			}
		} catch (IOException ex) {
			System.out.println("Files are not in the proper format!");
		} finally {
			try {
				originalReader.close();
				comparisonReader.close();
				/* outputWriter.close(); */
			} catch (Exception e) {
			}
		}

		int finalLineNumber = originalReader.getLineNumber();
		// double foundPercentage = Double.parseDouble(Integer.toString(matches))
		// / Double.parseDouble(Integer.toString(lineNumber));
		double foundPercentage = (int) ((Double.parseDouble(Integer.toString(matches))
				/ Double.parseDouble(Integer.toString(finalLineNumber))) * 10000) / 100.0;
		System.out.println("Matches found: " + matches);
		System.out.println("Misses found: " + (finalLineNumber - matches));
		// System.out.println("Percentage of matches: " + (int)(foundPercentage * 10000) / 100.0);
		// System.out.println("Percentage of misses: " + ((int)(1 - foundPercentage) * 10000) / 100.0);
		System.out.println("Percentage of matches: " + foundPercentage);
		System.out.println("Percentage of misses: " + (100 - foundPercentage));

	}
}
