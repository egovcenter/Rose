package egovframework.com.uss.ion.apm;

import org.jsoup.nodes.Element;

public class DefaultTag implements HtmlTag {

	public void merge(Element element, String value) {
		element.html(value);
	}
	public void merge(Element element, Object value) {
		if(value instanceof String){
			merge(element, (String)value);
			return;
		}
		throw new IllegalArgumentException("The value is not a String["+value+"]");
	}
}
