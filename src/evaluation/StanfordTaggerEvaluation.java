package evaluation;

/**
 *
 * @author Dennis
 */
public class StanfordTaggerEvaluation extends Evaluation {

	public StanfordTaggerEvaluation(String original, String comparison) {
		super(original, comparison);
	}

	@Override
	protected void processLines() {
		originalLine = originalLine.replace("\t", "_").toUpperCase();
		comparisonLine = comparisonLine.toUpperCase();
	}
}
