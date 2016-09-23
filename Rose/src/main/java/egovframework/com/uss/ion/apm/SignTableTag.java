package egovframework.com.uss.ion.apm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;

import egovframework.com.uss.ion.apv.ApprovalConstants;


public class SignTableTag implements HtmlTag {
    protected Element makeSignTableForRow(Element signTable, List signlines){
    	if(signlines == null || signlines.size() == 0){
    		return signTable;
    	}
    	// TODO handle the width and height.
		Element posTh = signTable.select("thead tr th").first(); // search the pos row for the original style in the signtable
		if(posTh == null){
			posTh = signTable.select("tr th").first();
		}
		
		Attributes posThAttrs = null;
		if(posTh != null){
			posTh.removeAttr("id");
			posThAttrs = posTh.attributes();
		}
		Element signTh = signTable.select("tbody tr td").first(); // search the pos row for the original style in the signtable
		if(posTh == null){
			signTh = signTable.select("tr td").first();
		}
		
		Attributes signThAttrs = null;
		if(signTh != null){
			signTh.removeAttr("id");
			signThAttrs = signTh.attributes();
		}
		
		String colgroup = "<colgroup>";
		String row4Pos = "<thead><tr>";
		String row4Sign = "<tbody><tr>";
		String width = (100/signlines.size()) + "%";
		SimpleDateFormat format4signcell = new SimpleDateFormat("(yyyy-MM-dd)");
		for(int i=0; i<signlines.size(); i++){
			Map signline = (Map)signlines.get(i);
			colgroup += "<col style='width:"+(width)+"'/>";
			
			// TODO for poscell
			//String attrs = "id='"+(String)signline.get("posCellName")+"'";
			String attrs = "";
			if(posThAttrs != null){
				attrs += posThAttrs.html();  
			}
			row4Pos += "<th "+attrs+">"+ ((String)signline.get("posName")) + "</th>";
			
			// TODO for signcell
			//attrs = "id='"+(String)signline.get("signCellName")+"'";
			if(signThAttrs != null){
				attrs += signThAttrs.html();  
			}
			row4Sign += "<td "+attrs+">";
			String status = (String)signline.get("status");
			if(ApprovalConstants.SIGNER_STATE_FINISHED.equals(status)){
				if(signline.get("signPath") != null){
					row4Sign += "<img src='"+(String)signline.get("signPath")+"' style='width:60px; height:60px'><br/>";
				}
				Date approvalDate = (Date)signline.get("approvalDate");
				row4Sign += "<span style='font-family: Times New Roman; font-style: italic; font-weight:bold; font-size: 14pt'>";
				row4Sign += (String)signline.get("name");
				row4Sign += "</span>";
				row4Sign += "<br>"; 
				row4Sign += "<span style='font-size: 8pt'>";
				row4Sign += (approvalDate == null ? "none" : format4signcell.format(approvalDate));
				row4Sign += "</span>";
			}else{
				row4Sign += "<font color='blue'><i>"+((String)signline.get("name"))+"</i></font>";
			}
			row4Sign += "</td>";
		}
		colgroup += "</colgroup>";
		row4Pos += "</tr></thead>";
		row4Sign += "</tr></tbody>";

		signTable.html("<table "+(signTable.attributes().html())+">"
				+ colgroup
				+ row4Pos
				+ row4Sign
				+ "</table>");
		
		return signTable;
    }
	public void merge(Element signTable, List sanclines) {
    	String approvalType = signTable.attr("approvaltype");
    	int count = 0;
    	List applySancLines = null;
    	if(approvalType == null){
    		applySancLines = sanclines;
    	}else{
    		approvalType = ";"+approvalType+";";
    		applySancLines = new ArrayList();
        	for(int i=0; i<sanclines.size(); i++){
        		Map sancline = (Map)sanclines.get(i);
        		String thisType = (String)sancline.get("approvalType");
        		if(approvalType.indexOf(";"+thisType+";") > -1){
        			applySancLines.add(sancline);
        		}
        	}
    	}
    	
    	String dir = signTable.attr("dir");
    	
    	// TODO for col
    	if("col".equalsIgnoreCase(dir)){
    		
    	}else{
    		makeSignTableForRow(signTable, sanclines);
    	}
	}
	public void merge(Element element, Object value) {
		if(value instanceof List){
			merge(element, (List)value);
		}
		throw new IllegalArgumentException("The value is not a List["+value+"]");

	}

}
