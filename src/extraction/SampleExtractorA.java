package extraction;

public class SampleExtractorA extends SampleExtractor {

	/** hand over the instance of the extraction class to the super constructor **/
	public SampleExtractorA(int sizeOriginal, int sizeSample, String corpus) {
		super(new ExtractionA(sizeOriginal, sizeSample), corpus);
	}

}
