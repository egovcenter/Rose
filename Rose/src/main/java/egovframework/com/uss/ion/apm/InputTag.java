package egovframework.com.uss.ion.apm;

import org.jsoup.nodes.Element;

public class InputTag extends DefaultTag {

	public void merge(Element element, String value) {
    	String type = element.attr("type");
    	// handle the element by type of the input tag
    	if("radio".equalsIgnoreCase(type))// TODO handle the radio from the checkbox differently, because only one radio among the radios with the same name is checked. 
    	{
    		boolean checked = value != null && element.val() != null && value.equals(element.val());
    		if(checked){
    			element.attr("checked", "checked");
    		}else{
    			element.removeAttr("checked");
    		}
    	}
    	/*
    	else if("checkbox".equalsIgnoreCase(type)){
    		boolean checked = value != null && element.val() != null && value.equals(element.val());
    		if(checked){
    			element.attr("checked", "checked");
    		}else{
    			element.removeAttr("checked");
    		}
    	}*/
    	else{//othercases are 'type' or 'hidden'
    		element.attr("value", value);
    	}
	}
}
