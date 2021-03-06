package fi.maanmittauslaitos.pta.search.metadata;

import java.util.Arrays;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpression;

import org.w3c.dom.Node;

import fi.maanmittauslaitos.pta.search.documentprocessor.XPathCustomExtractor;

public class GeographicBoundingBoxXPathCustomExtractor implements XPathCustomExtractor {

	private Double getDoubleValue(String elementName, XPath xPath, Node node) 
			throws XPathException, MissingCoordException
	{
		XPathExpression nameExpr =
				xPath.compile("./gmd:"+elementName+"/gco:Decimal/text()");
		Double ret = (Double)nameExpr.evaluate(node, XPathConstants.NUMBER);
		if (ret == null || ret.isNaN()) {
			throw new MissingCoordException();
		}
		return ret;
	}
	
	@Override
	public Object process(XPath xPath, Node node) throws XPathException {
		Double [] ret = new Double[4];
		
		try {
			ret[0] = getDoubleValue("westBoundLongitude", xPath, node);
			ret[1] = getDoubleValue("southBoundLatitude", xPath, node);
			ret[2] = getDoubleValue("eastBoundLongitude", xPath, node);
			ret[3] = getDoubleValue("northBoundLatitude", xPath, node);
		} catch(MissingCoordException e) {
			return null;
		}
		return Arrays.asList(ret);
	}

	private class MissingCoordException extends Exception {
		private static final long serialVersionUID = 1L;
		
	}
}
