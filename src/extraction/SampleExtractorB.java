package extraction;

public class SampleExtractorB extends SampleExtractor {

	/** hand over the instance of the extraction class to the super constructor **/
	public SampleExtractorB(int sizeOriginal, int sizeSample, String corpus) {
		super(new ExtractionB(sizeOriginal, sizeSample), corpus);
	}

}
