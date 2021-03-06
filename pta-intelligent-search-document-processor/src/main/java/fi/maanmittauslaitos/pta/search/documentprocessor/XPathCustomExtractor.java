package fi.maanmittauslaitos.pta.search.documentprocessor;

import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathException;

@FunctionalInterface
public interface XPathCustomExtractor {

	Object process(XPath xPath, Node node) throws XPathException;

}
