package egovframework.com.uss.ion.apm;

import org.jsoup.nodes.Element;

public class TagFactory {
	public static String TABLE_ID_DRAFT = "draft_sign";
	public static String TABLE_ID_DRAFT_AGREE = "draft_agree_sign";
	public static String TABLE_ID_RECV = "recv_sign";
			
	public static HtmlTag createTag(String tagName){
    	if(tagName.toLowerCase().equals("input")){
    		return new InputTag();
    	}else if(tagName.toLowerCase().equals("textarea")){
    		return new DefaultTag();
    	}else if(tagName.toLowerCase().equals("select")){
    		return new SelectTag();
    	}else if(tagName.toLowerCase().equals("div")
    			|| tagName.toLowerCase().equals("span")
    			|| tagName.toLowerCase().equals("p")){
    		return new DefaultTag();
    	}
    	throw new IllegalArgumentException("This tag ["+tagName+"] does not  be supported");
	}
	public static boolean isSignTable(Element element){
		if(element == null) return false;
    	boolean isTable = "table".equalsIgnoreCase(element.tagName());
    	boolean isSignTableID = false;
    	String tableId = element.id();
    	if(TABLE_ID_DRAFT.equals(tableId) || TABLE_ID_RECV.equals(tableId) || TABLE_ID_DRAFT_AGREE.equals(tableId)){
    		isSignTableID = true;
    	}
    	
    	return isTable && isSignTableID;
    }
	public static SignTableTag createSignTableTag(String tableId){
		if(TABLE_ID_DRAFT.equals(tableId)){
			return new SignTableTag();
		}else if(TABLE_ID_DRAFT_AGREE.equals(tableId)){
			return new AgreeSignTableTag();
		}else if(TABLE_ID_RECV.equals(tableId)){
			return new SignTableTag();
		}
		throw new IllegalArgumentException("This sign table ["+tableId+"] does not  be supported");
	}
}
