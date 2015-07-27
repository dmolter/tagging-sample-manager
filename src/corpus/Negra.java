package corpus;

import extraction.Splitter;
import extraction.SplitterNegra;

public class Negra extends Corpus {

	private static final int NEGRA_SIZE = 20602;

	public Negra() {
		super("negra", NEGRA_SIZE);
	}

	@Override
	public void splitSample(String file, String tagger) {
		super.splitSample(file, tagger);
		Splitter s = new SplitterNegra(sampleLines, tagger);
		s.split();
	}
}
