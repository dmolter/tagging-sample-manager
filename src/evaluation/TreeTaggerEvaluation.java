package evaluation;

/**
 *
 * @author Dennis
 */
public class TreeTaggerEvaluation extends Evaluation {

	public TreeTaggerEvaluation(String original, String comparison) {
		super(original, comparison);
	}

	@Override
	protected void processLines() {
		originalLine = originalLine.replace("\t", "").toUpperCase();
		comparisonLine = comparisonLine.replace("\t", "").toUpperCase();
	}
}
