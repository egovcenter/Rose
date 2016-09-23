package egovframework.com.uss.ion.apm;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SelectTag extends DefaultTag {

	public void merge(Element element, String value) {
    	Elements options = element.select("option");
    	for(int i=0; i<options.size(); i++){
    		Element option = options.get(i);
    		if(value.equals(option.val())){
    			option.attr("selected", "selected"); 
    		}else{
    			option.removeAttr("selected");
    		}
    	}
	}

}
