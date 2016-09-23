package egovframework.com.uss.ion.apm;

import java.util.List;

import org.jsoup.nodes.Element;

public class AgreeSignTableTag extends SignTableTag {
	public void merge(Element signTable, List sanclines) {
    	// TODO handle for agree sign table
		super.merge(signTable, sanclines);
	}
}
