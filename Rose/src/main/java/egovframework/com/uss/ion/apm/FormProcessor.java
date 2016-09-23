package egovframework.com.uss.ion.apm;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import egovframework.com.uss.ion.apm.service.FormVO;
import egovframework.com.uss.ion.apu.ApprovalException;
import egovframework.com.uss.ion.apu.PathUtil;
import egovframework.com.uss.ion.apv.ApprovalConstants;
import egovframework.com.uss.ion.apv.Doc;
import egovframework.com.uss.ion.apv.service.SignerVO;
import egovframework.com.uss.umt.service.UserManageVO;



public class FormProcessor {
	private static Logger logger = Logger.getLogger(FormProcessor.class);
	
	private final static String SANCLINE_TABLE_ID_DRAFT = "#draft_sign";
	//private final static String SANCLINE_TABLE_ID_DRAFT_AGREE = "#draft_agree_sign";
	private final static String SANCLINE_TABLE_ID_RECV = "#recv_sign";
	
	protected static Properties mappingMap = new Properties();
	
	public Properties getMappingMap() {
		return mappingMap;
	}

	public void setMappingMap(Properties mappingMap) {
		FormProcessor.mappingMap = mappingMap;
	}
	
    private String getDate(Date date, String format){
    	if(date == null) return "";
    	if(format == null) return date.toString();
    	
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
    	return simpleDateFormat.format(date);
    }
    private void mergeElement(Element element, Object data){
    	String value = "";
    	if(data instanceof Date){
    		value = getDate((Date)data, element.attr("format"));
    	}
    	// TODO need to handle other data types.
    	else{
    		value = data == null ? "" : data.toString();
    		try {
				value = URLDecoder.decode(value, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
    	}
    	// handle the element by tagName
    	String tagName = element.tagName();
    	if(tagName == null) return;
    	HtmlTag htmlTag = TagFactory.createTag(tagName);
    	htmlTag.merge(element, value);
    }
    
    private boolean isListElement(String propertyName){
//    	propertyName.matches("₩[₩]");
//    	return propertyName == null ? false : (propertyName.indexOf("["));
    	return true;
    }
    private Object getValue(Object obj, String propertyName){
    	try {
			if(obj instanceof Map){
				return ((Map)obj).get(propertyName);
			}
			if(obj instanceof List && isListElement(propertyName)){
				return BeanUtils.getProperty(obj, propertyName);
			}
			return BeanUtils.getProperty(obj, propertyName);
		} catch (Exception e) {
			logger.warn("Fail to get the value of the object["+obj+"]'s the property["+propertyName+"]", e);
			return "";
		}
    }
    
    private Map<String, Object> createSignerMap(SignerVO signer){
    	Map<String, Object> sancline = new HashMap<String, Object>();
    	sancline.put("id", signer.getSignerID() == null ? "" : signer.getSignerID());
    	sancline.put("seq", signer.getSignSeq());
    	sancline.put("name", signer.getSignerName() == null ? "" : signer.getSignerName());
    	sancline.put("posName", signer.getSignerPositionName() == null ? "" : signer.getSignerPositionName());
    	sancline.put("dutyName", signer.getSignerDutyName() == null ? "" : signer.getSignerDutyName());
    	sancline.put("approvalType", signer.getSignKind() == null ? "" : signer.getSignKind());
    	sancline.put("deptName", signer.getSignerDeptName() == null ? "" : signer.getSignerDeptName());
    	sancline.put("approvalDate", signer.getSignDate()== null ? null : signer.getSignDate());
    	sancline.put("status", signer.getSignState() == null ? "" : signer.getSignState());
    	
    	return sancline;
    }
    
    public String mergeDocForDraftView(FormVO form, UserManageVO draft, List<SignerVO> signerList)throws Exception{
    	try {
    		File formFile = PathUtil.getFormPath(form);
    		logger.debug(form.getFormId()+"=>["+formFile.getAbsoluteFile()+"]");
    		if(!formFile.exists()){
    			throw new ApprovalException("The form file does not exist! formID["+form.getFormId()+"], formFile["+formFile.getAbsolutePath()+"]");
    		}
    		
			Document jsoupDoc = Jsoup.parse(formFile, "utf-8");
			
			Map<String , Object> dataMap = new HashMap<String, Object>();
	    	Map<String, Object> document = new HashMap<String, Object>();
	    	document.put("drafterId", draft != null ? draft.getUniqId() : "");
	    	document.put("drafterName", draft != null ? draft.getEmplyrNm() : "");
	    	document.put("deptName", draft != null ? draft.getOrgnztNm() : "");
	    	document.put("draftDate", new Date());
	    	dataMap.put("draftDocument", document);
	    	
	    	if(signerList != null){
	    		List<Object> sanclines = new ArrayList<Object>();
	    		for(int i=0; i<signerList.size(); i++){
	    		    SignerVO signer = (SignerVO)signerList.get(i);
	    	    	Map<String, Object> sancline = createSignerMap(signer);
	    	    	sanclines.add(sancline);
	    		}
	    		dataMap.put("draftSanclines", sanclines);
	    	}
	    	merge(jsoupDoc, dataMap);
			return jsoupDoc.toString();
		} catch (IOException e) {
			throw e;
		}
    }
    
    public File mergeDoc(Doc doc, File docFile, SignerVO draftSigner, List<SignerVO> signerList, Map parameterMap)throws Exception{
    	if(ApprovalConstants.DOC_TYPE_INCOMING.equals(doc.getDocType())){
    		return mergeDocForReceive(doc, docFile, draftSigner, signerList, parameterMap);
    	}
    	return mergeDocForDraft(doc, docFile, draftSigner, signerList, parameterMap);
    }
    
    public File mergeDoneDoc(Doc doc, File docFile, SignerVO draftSigner, SignerVO lastSigner, List<SignerVO> signerList, Map parameterMap)throws Exception{
    	if(ApprovalConstants.DOC_TYPE_INCOMING.equals(doc.getDocType())){
    		return mergeDocForReceiveDone(doc, docFile, draftSigner, lastSigner, signerList, parameterMap);
    	}
    	return mergeDocForDone(doc, docFile, draftSigner, lastSigner, signerList, parameterMap);
    }
    
    private File mergeDocForDraft(Doc doc, File docFile, SignerVO draftSigner, List<SignerVO> signerList, Map parameterMap)throws Exception{
    	try {
			Document jsoupDoc = Jsoup.parse(docFile, "utf-8");
			Map<String , Object> dataMap = new HashMap<String, Object>();
	    	Map<String, Object> document = new HashMap<String, Object>();
	    	document.put("drafterId", draftSigner != null ? draftSigner.getUserID() : "");
	    	document.put("drafterName", draftSigner != null ? draftSigner.getSignerName() : "");
	    	document.put("deptName", draftSigner != null ? draftSigner.getSignerDeptName() : "");
	    	document.put("draftDate", draftSigner != null ? draftSigner.getSignDate() : "");
	    	document.put("docNum", doc.getDocCd());
	    	dataMap.put("draftDocument", document);
	    	dataMap.put("parameterMap", parameterMap);
	    	
	    	if(signerList != null){
	    		List<Object> sanclines = new ArrayList<Object>();
	    		for(int i=0; i<signerList.size(); i++){
	    			SignerVO signer = (SignerVO)signerList.get(i);
	    	    	Map<String, Object> sancline = createSignerMap(signer);
	    	    	sanclines.add(sancline);
	    		}
	    		dataMap.put("draftSanclines", sanclines);
	    	}
	    	merge(jsoupDoc, dataMap);
	    	PathUtil.saveFile(docFile, jsoupDoc.toString());
	    	return docFile;
		} catch (IOException e) {
			throw e;
		}
    }
    
    private File mergeDocForDone(Doc doc, File docFile, SignerVO draftSigner, SignerVO lastSigner, List<SignerVO> signerList, Map parameterMap)throws Exception{
    	try {
			Document jsoupDoc = Jsoup.parse(docFile, "utf-8");
			Map<String , Object> dataMap = new HashMap<String, Object>();
	    	Map<String, Object> document = new HashMap<String, Object>();
	    	document.put("drafterId", draftSigner.getUserID());
	    	document.put("drafterName", draftSigner.getSignerName());
	    	document.put("deptName", draftSigner.getSignerDeptName());
	    	document.put("draftDate", draftSigner.getSignDate());
	    	document.put("docNum", doc.getDocCd());
	    	document.put("approvalDate", lastSigner.getSignDate());
	    	dataMap.put("draftDocument", document);
	    	
	    	if(signerList != null){
	    		List<Object> sanclines = new ArrayList<Object>();
	    		for(int i=0; i<signerList.size(); i++){
	    			SignerVO signer = (SignerVO)signerList.get(i);
	    	    	Map<String, Object> sancline = createSignerMap(signer);
	    	    	sanclines.add(sancline);
	    		}
	    		dataMap.put("draftSanclines", sanclines);
	    	}
	    	merge(jsoupDoc, dataMap);
	    	PathUtil.saveFile(docFile, jsoupDoc.toString());
	    	return docFile;
		} catch (IOException e) {
			throw e;
		}
    }
    
    private File mergeDocForReceive(Doc doc, File docFile, SignerVO draftSigner, List<SignerVO> signerList, Map parameterMap) throws Exception{
    	try {
			Document jsoupDoc = Jsoup.parse(docFile, "utf-8");
			Map<String , Object> dataMap = new HashMap<String, Object>();
	    	Map<String, Object> document = new HashMap<String, Object>();
	    	document.put("drafterId", draftSigner.getUserID());
	    	document.put("drafterName", draftSigner.getSignerName());
	    	document.put("deptName", draftSigner.getSignerDeptName());
	    	document.put("draftDate", draftSigner.getSignDate());
	    	document.put("docNum", doc.getDocCd());
	    	dataMap.put("recvDocument", document);
	    	
	    	if(signerList != null){
	    		List<Object> sanclines = new ArrayList<Object>();
	    		
	    		for(int i=0; i<signerList.size(); i++){
	    		    SignerVO signer = (SignerVO)signerList.get(i);
	    	    	Map<String, Object> sancline = createSignerMap(signer);
	    	    	sanclines.add(sancline);
	    		}
	    		dataMap.put("recvSanclines", sanclines);
	    	}
	    	merge(jsoupDoc, dataMap);
	    	PathUtil.saveFile(docFile, jsoupDoc.toString());
	    	return docFile;
		} catch (IOException e) {
			throw e;
		}
    }
    
    private File mergeDocForReceiveDone(Doc doc, File docFile, SignerVO draftSigner, SignerVO lastSigner, List<SignerVO> signerList, Map parameterMap) throws Exception{
    	try {
			Document jsoupDoc = Jsoup.parse(docFile, "utf-8");
			Map<String , Object> dataMap = new HashMap<String, Object>();
	    	Map<String, Object> document = new HashMap<String, Object>();
	    	document.put("drafterId", draftSigner.getUserID());
	    	document.put("drafterName", draftSigner.getSignerName());
	    	document.put("deptName", draftSigner.getSignerDeptName());
	    	document.put("draftDate", draftSigner.getSignDate());
	    	document.put("docNum", doc.getDocCd());
	    	document.put("approvalDate", lastSigner.getSignDate());
	    	dataMap.put("recvDocument", document);
	    	
	    	if(signerList != null){
	    		List<Object> sanclines = new ArrayList<Object>();
	    		for(int i=0; i<signerList.size(); i++){
	    			SignerVO signer = (SignerVO)signerList.get(i);
	    	    	Map<String, Object> sancline = createSignerMap(signer);
	    	    	sanclines.add(sancline);
	    		}
	    		dataMap.put("recvSanclines", sanclines);
	    	}
	    	merge(jsoupDoc, dataMap);
	    	PathUtil.saveFile(docFile, jsoupDoc.toString());
	    	return docFile;
		} catch (IOException e) {
			throw e;
		}
    }
    
    public String mergeSignTableForDraft(Form form, List<SignerVO> signerList)throws Exception{
    	File formFile = PathUtil.getFormPath(form);
    	return mergeSignTable(formFile, SANCLINE_TABLE_ID_DRAFT, signerList);
    }
    
    public String mergeSignTableForRecv(Form form, List<SignerVO> signerList)throws Exception{
    	File formFile = PathUtil.getFormPath(form);
    	return mergeSignTable(formFile, SANCLINE_TABLE_ID_RECV, signerList);
    }
    
    public String mergeSignTableForDraft(Doc doc, List<SignerVO> signerList)throws Exception{
    	File docFile = PathUtil.getDocPath(doc);
    	return mergeSignTable(docFile, SANCLINE_TABLE_ID_DRAFT, signerList);
    }
    
    public String mergeSignTableForRecv(Doc doc, List<SignerVO> signerList)throws Exception{
    	File docFile = PathUtil.getDocPath(doc);
    	return mergeSignTable(docFile, SANCLINE_TABLE_ID_RECV, signerList);
    }
    
    public String mergeSignTable(File bodyFile, String signTableId, List<SignerVO> signerList) throws Exception{
		Document jsoupDoc = Jsoup.parse(bodyFile, "utf-8");
		Elements elements = jsoupDoc.select("#"+signTableId);
		Element signtable = null;
		
		if ((elements != null) && (elements.size() > 0)) {
			signtable = elements.get(0);
		}
		if(signtable == null){
			throw new ApprovalException("File["+bodyFile.getAbsolutePath()+"] does not have the sign table["+signTableId+"]");
		}
    	if((signerList != null) 
    	        && (signtable != null) 
    	        && TagFactory.isSignTable(signtable)){
    	    
    		List<Object> sanclines = new ArrayList<Object>();
    		
    		for(int i=0; i<signerList.size(); i++) {
    			SignerVO signer = (SignerVO)signerList.get(i);
    	    	Map<String, Object> sancline = createSignerMap(signer);
    	    	sanclines.add(sancline);
    		}
			
    		SignTableTag signTableTag = TagFactory.createSignTableTag(signTableId);
    		signTableTag.merge(signtable, sanclines);
    	}else{
    		logger.debug("signtable["+signtable+"], signerList["+signerList+"], isSignTable["+ TagFactory.isSignTable(signtable)+"]");
    	}
    	return signtable != null ? signtable.toString() : "";
    }
    
    /**
     * 'doc' : 
     * @param jsoupDoc
     * @param dataMap
     */
	public void merge(Document jsoupDoc, Map<String, Object> dataMap){
 		// merge the input data from request
		if(dataMap.containsKey("parameterMap")){
			applyInputData(jsoupDoc, (Map)dataMap.get("parameterMap"));
		}
		// merge the document, sanclines 
    	for(Iterator keys = mappingMap.keySet().iterator(); keys.hasNext();){
    		String mappingSource = (String)keys.next();
    		String mappingTarget = (String)mappingMap.get(mappingSource);
    		logger.debug("["+ mappingSource +"] is value["+mappingTarget +"]");
    		Object value = null;
    		
    		logger.debug("["+mappingSource+"] is value["+value+"]");
    		logger.debug("["+mappingTarget+"] is value["+value+"]");

    		if(mappingTarget.indexOf(".") < 0){
    			value = dataMap.get(mappingTarget);
    		}else{
    			String objName = mappingTarget.split("\\.")[0];
    			String objPropery = mappingTarget.split("\\.")[1];
    			Object obj = dataMap.get(objName);
    			if(obj instanceof Map){
    				value = ((Map)obj).get(objPropery);
    			}
    			logger.debug("["+objPropery+"]of["+objName+"] is value["+value+"]");
    		}
    		if(value == null){
    			logger.debug("mappingSource["+mappingSource+"], mappingTarget["+mappingTarget+"], value is null.");
    			continue;
    		}
    		//TODO radio, select and so on. only handle the input type=text|hidden
    		Elements elements = jsoupDoc.select(mappingSource);
    		for(int i=0; i<elements.size();i++){
    			Element element = (Element)elements.get(i);
    			if(TagFactory.isSignTable(element) && value instanceof List){
    				String signTableId = element.id();
    				if(signTableId == null) continue;
    				
    	    		SignTableTag signTableTag = TagFactory.createSignTableTag(signTableId);
    	    		signTableTag.merge(element, (List)value);
    			}else{
    				mergeElement(elements.get(i), value);
    			}
    		}
    	}
	}
	
	public String getDocForView(Doc doc)throws Exception{
		File docFile = PathUtil.getDocPath(doc);
		Document jsoupDoc = Jsoup.parse(docFile, "utf-8");
		Elements elements = jsoupDoc.select("input");
		if(elements != null){
			for(int i=0;  i<elements.size(); i++){
				Element element = elements.get(i);
				String type = element.attr("type");
				
				if(type.equalsIgnoreCase("radio")){
					element.attr("disabled", "disabled");
				}else{
					element.attr("readonly", "readonly");
				}
			}
		}
		elements = jsoupDoc.select("textarea");
		if(elements != null){
			for(int i=0;  i<elements.size(); i++){
				Element element = elements.get(i);
				element.attr("readonly", "readonly");
			}
		}
		elements = jsoupDoc.select("select");
		if(elements != null){
			for(int i=0;  i<elements.size(); i++){
				Element element = elements.get(i);
				element.attr("disabled", "disabled");
			}
		}
		return jsoupDoc.toString();
	}
	
	/* 20160404_SUJI.H */
	public String getWriteDoc(Doc doc)throws Exception{
		File docFile = PathUtil.getDocPath(doc);
		Document jsoupDoc = Jsoup.parse(docFile, "utf-8");
		Elements elements = jsoupDoc.select("input");
		if(elements != null){
			for(int i=0;  i<elements.size(); i++){
				Element element = elements.get(i);
				String type = element.attr("type");
				
				if(type.equalsIgnoreCase("radio")){
					element.removeAttr("disabled");
				}else{
					element.removeAttr("readonly");
				}
			}
		}
		elements = jsoupDoc.select("textarea");
		if(elements != null){
			for(int i=0;  i<elements.size(); i++){
				Element element = elements.get(i);
				element.removeAttr("readonly");
			}
		}
		elements = jsoupDoc.select("select");
		if(elements != null){
			for(int i=0;  i<elements.size(); i++){
				Element element = elements.get(i);
				element.removeAttr("disabled");
			}
		}
		return jsoupDoc.toString();
	}
	/* 20160404_SUJI.H */

	private void applyInputData(Document jsoupDoc, Map map) {
		if(map == null || map.size() < 1) return;
		
		for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext();){
			Map.Entry pair = (Map.Entry)iterator.next();
			String key = (String)pair.getKey();
			Object value = pair.getValue();
			
			Elements elements = jsoupDoc.select("[name="+key+"]");
			if(elements == null || elements.size() < 1){
				continue;
			}
			for(int i=0; i<elements.size(); i++){
				Element element = elements.get(i);
				mergeElement(element, value);
			}
		}
	}
}
