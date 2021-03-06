package fi.maanmittauslaitos.pta.search.metadata;

import fi.maanmittauslaitos.pta.search.documentprocessor.DocumentProcessingConfiguration;
import fi.maanmittauslaitos.pta.search.documentprocessor.DocumentProcessor;
import fi.maanmittauslaitos.pta.search.documentprocessor.DocumentProcessorFactory;
import fi.maanmittauslaitos.pta.search.documentprocessor.FieldExtractorConfiguration;
import fi.maanmittauslaitos.pta.search.documentprocessor.XPathCustomExtractor;
import fi.maanmittauslaitos.pta.search.documentprocessor.XPathFieldExtractorConfiguration;
import fi.maanmittauslaitos.pta.search.documentprocessor.XPathFieldExtractorConfiguration.FieldExtractorType;

import javax.xml.parsers.ParserConfigurationException;
import java.util.List;

import static fi.maanmittauslaitos.pta.search.metadata.utils.XPathHelper.doesntMatch;
import static fi.maanmittauslaitos.pta.search.metadata.utils.XPathHelper.matches;


public class ISOMetadataExtractorConfigurationFactory {
	private DocumentProcessorFactory documentProcessorFactory = new DocumentProcessorFactory();

	public void setDocumentProcessorFactory(DocumentProcessorFactory documentProcessorFactory) {
		this.documentProcessorFactory = documentProcessorFactory;
	}

	public DocumentProcessorFactory getDocumentProcessorFactory() {
		return documentProcessorFactory;
	}


	public DocumentProcessingConfiguration createMetadataDocumentProcessingConfiguration() throws ParserConfigurationException {
		DocumentProcessingConfiguration configuration = new DocumentProcessingConfiguration();
		configuration.getNamespaces().put("gmd", "http://www.isotc211.org/2005/gmd");
		configuration.getNamespaces().put("gco", "http://www.isotc211.org/2005/gco");
		configuration.getNamespaces().put("srv", "http://www.isotc211.org/2005/srv");
		configuration.getNamespaces().put("gmx", "http://www.isotc211.org/2005/gmx");

		configuration.getNamespaces().put("xlink", "http://www.w3.org/1999/xlink");


		List<FieldExtractorConfiguration> extractors = configuration.getFieldExtractors();

		// Id extractor
		extractors.add(createXPathExtractor(
				ISOMetadataFields.ID,
				FieldExtractorType.FIRST_MATCHING_VALUE,
				"//gmd:fileIdentifier/*/text()"));

		// Title extractors
		extractors.add(createXPathExtractor(
				ISOMetadataFields.TITLE,
				FieldExtractorType.FIRST_MATCHING_VALUE,
				"(" +
						"//gmd:identificationInfo/*/gmd:citation/*/gmd:title//gmd:LocalisedCharacterString[" +
						matches("@locale", "'#FI'") + "]" +
						"|" +
						"//gmd:identificationInfo/*/gmd:citation/*/gmd:title/*[self::gco:CharacterString]" +
						")/text()"));

		extractors.add(createXPathExtractor(
				ISOMetadataFields.TITLE_SV,
				FieldExtractorType.FIRST_MATCHING_VALUE,
				"(" +
						"//gmd:identificationInfo/*/gmd:citation/*/gmd:title//gmd:LocalisedCharacterString[" +
						matches("@locale", "'#SV'") + "]" +
						")/text()"));

		extractors.add(createXPathExtractor(
				ISOMetadataFields.TITLE_EN,
				FieldExtractorType.FIRST_MATCHING_VALUE,
				"(" +
						"//gmd:identificationInfo/*/gmd:citation/*/gmd:title//gmd:LocalisedCharacterString[" +
						matches("@locale", "'#EN'") + "])/text()"));

		// Abstract extractors
		extractors.add(createXPathExtractor(
				ISOMetadataFields.ABSTRACT,
				FieldExtractorType.FIRST_MATCHING_VALUE,
				"(" +
						"//gmd:identificationInfo/*/gmd:abstract//gmd:LocalisedCharacterString[" +
						matches("@locale", "'#FI'") + "]" +
						"|" +
						"//gmd:identificationInfo/*/gmd:abstract/*[self::gco:CharacterString]" +
						")/text()"));

		extractors.add(createXPathExtractor(
				ISOMetadataFields.ABSTRACT_SV,
				FieldExtractorType.FIRST_MATCHING_VALUE,
				"(" +
						"//gmd:identificationInfo/*/gmd:abstract//gmd:LocalisedCharacterString[" +
						matches("@locale", "'#SV'") + "])/text()"));

		extractors.add(createXPathExtractor(
				ISOMetadataFields.ABSTRACT_EN,
				FieldExtractorType.FIRST_MATCHING_VALUE,
				"(" +
						"//gmd:identificationInfo/*/gmd:abstract//gmd:LocalisedCharacterString[" +
						matches("@locale", "'#EN'") + "])/text()"));

		// isService 
		extractors.add(createXPathExtractor(
				ISOMetadataFields.IS_SERVICE,
				FieldExtractorType.TRUE_IF_MATCHES_OTHERWISE_FALSE,
				"//gmd:hierarchyLevel/gmd:MD_ScopeCode["
						+ matches("@codeList", "'http://standards.iso.org/ittf/PubliclyAvailableStandards/ISO_19139_Schemas/resources/codelist/ML_gmxCodelists.xml#MD_ScopeCode'")
						+ " and "
						+ matches("@codeListValue", "'service'")
						+ "]"));

		// isDataset
		extractors.add(createXPathExtractor(
				ISOMetadataFields.IS_DATASET,
				FieldExtractorType.TRUE_IF_MATCHES_OTHERWISE_FALSE,
				"//gmd:hierarchyLevel/gmd:MD_ScopeCode["
						+ matches("@codeList", "'http://standards.iso.org/ittf/PubliclyAvailableStandards/ISO_19139_Schemas/resources/codelist/ML_gmxCodelists.xml#MD_ScopeCode'")
						+ " and "
						+ "(" + matches("@codeListValue", "'dataset'") + " or " + matches("@codeListValue", "'series'") + ")"
						+ "]"));


		// isAvoindata
		extractors.add(createXPathExtractor(
				ISOMetadataFields.IS_AVOINDATA,
				FieldExtractorType.TRUE_IF_MATCHES_OTHERWISE_FALSE,
				"//gmd:identificationInfo/*/gmd:descriptiveKeywords/*/gmd:keyword/*[" +
						matches("text()", "'avoindata.fi'") +
						"]"));

		// Topic categories
		extractors.add(createXPathExtractor(
				ISOMetadataFields.TOPIC_CATEGORIES,
				FieldExtractorType.ALL_MATCHING_VALUES,
				"//gmd:identificationInfo/*/gmd:topicCategory/gmd:MD_TopicCategoryCode/text()"));

		// Keywords
		extractors.add(createXPathExtractor(
				ISOMetadataFields.KEYWORDS_ALL,
				FieldExtractorType.ALL_MATCHING_VALUES,
				"//gmd:identificationInfo/*/gmd:descriptiveKeywords/*/gmd:keyword/gco:CharacterString[" +
						doesntMatch("text()", "'avoindata.fi'") + "]/text()"));

		// Inspire themes
		extractors.add(createXPathExtractor(
				ISOMetadataFields.KEYWORDS_INSPIRE,
				FieldExtractorType.ALL_MATCHING_VALUES,
				"//gmd:identificationInfo/*/gmd:descriptiveKeywords/*[" +
						matches("gmd:thesaurusName/gmd:CI_Citation/gmd:title/*/text()", "'GEMET - INSPIRE themes, version 1.0'") +
						"]/gmd:keyword/gco:CharacterString/text()"));

		// Distribution Formats
		extractors.add(createXPathExtractor(
				ISOMetadataFields.DISTRIBUTION_FORMATS,
				FieldExtractorType.ALL_MATCHING_VALUES,
				"//gmd:distributionInfo/*/gmd:distributionFormat/*/gmd:name/gco:*/text()"));


		// Datestamp
		extractors.add(createXPathExtractor(
				ISOMetadataFields.DATESTAMP,
				FieldExtractorType.FIRST_MATCHING_VALUE,
				"//gmd:MD_Metadata/gmd:dateStamp/*/text()"));


		// Organisation names + roles
		extractors.add(createXPathExtractor(
				ISOMetadataFields.ORGANISATIONS,
				new ResponsiblePartyXPathCustomExtractor(),
				"//gmd:MD_Metadata/gmd:identificationInfo/*/gmd:pointOfContact/gmd:CI_ResponsibleParty"));

		// Geographic bounding box
		extractors.add(createXPathExtractor(
				ISOMetadataFields.GEOGRAPHIC_BOUNDING_BOX,
				new GeographicBoundingBoxXPathCustomExtractor(),
				"//gmd:MD_Metadata/gmd:identificationInfo/*/*[self::gmd:extent or self::srv:extent]/*/gmd:geographicElement/gmd:EX_GeographicBoundingBox"));

		return configuration;
	}

	public DocumentProcessor createMetadataDocumentProcessor() throws ParserConfigurationException {
		DocumentProcessingConfiguration configuration = createMetadataDocumentProcessingConfiguration();
		return getDocumentProcessorFactory().createProcessor(configuration);
	}

	private FieldExtractorConfiguration createXPathExtractor(String field, FieldExtractorType type, String xpath) {
		XPathFieldExtractorConfiguration ret = new XPathFieldExtractorConfiguration();
		ret.setField(field);
		ret.setType(type);
		ret.setXpath(xpath);

		return ret;
	}

	private FieldExtractorConfiguration createXPathExtractor(String field, XPathCustomExtractor extractor, String xpath) {
		XPathFieldExtractorConfiguration ret = new XPathFieldExtractorConfiguration();
		ret.setField(field);
		ret.setType(FieldExtractorType.CUSTOM_CLASS);
		ret.setXpath(xpath);
		ret.setCustomExtractor(extractor);

		return ret;
	}
}
