package corpus;

import extraction.Splitter;
import extraction.SplitterBrown;

public class Brown extends Corpus {

	private static final int BROWN_SIZE = 57341;

	public Brown() {
		super("brown", BROWN_SIZE);
	}

	@Override
	public void splitSample(String file, String tagger) {
		super.splitSample(file, tagger);
		Splitter s = new SplitterBrown(sampleLines, tagger);
		s.split();
	}

}
