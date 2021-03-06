package fi.maanmittauslaitos.pta.search.documentprocessor;

import javax.xml.xpath.XPath;

import org.w3c.dom.Document;

public interface FieldExtractorConfiguration {

	/**
	 * Name of the field where the extracted value should be stored
	 * @return
	 */
	public String getField();

	public void setField(String field);
	
	/**
	 * Process the document and returns the raw value. If this configuration has a text processor configured,
	 * that is not applied within this function.
	 * 
	 * @param doc
	 * @return
	 */
	public Object process(Document doc, XPath xpath) throws DocumentProcessingException;

	
	public String getTextProcessorName();
	public void setTextProcessorName(String textProcessorName);

	public FieldExtractorConfiguration copy();
}
