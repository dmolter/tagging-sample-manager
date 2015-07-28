package corpus;

import extraction.Splitter;
import extraction.SplitterNegra;

public class Negra extends Corpus {

	private static final int NEGRA_SIZE = 20602;

	public Negra() {
		super("negra", NEGRA_SIZE);
	}

	public void splitSample(String tagger) {
		Splitter s = new SplitterNegra(sampleLines, tagger);
		s.split();
	}
}
