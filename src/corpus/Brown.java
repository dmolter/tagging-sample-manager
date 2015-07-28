package corpus;

import extraction.Splitter;
import extraction.SplitterBrown;

public class Brown extends Corpus {

	private static final int BROWN_SIZE = 57341;

	public Brown() {
		super("brown", BROWN_SIZE);
	}

	public void splitSample(String tagger) {
		Splitter s = new SplitterBrown(sampleLines, tagger);
		s.split();
	}

}
