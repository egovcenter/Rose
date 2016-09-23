package egovframework.com.uss.ion.apv.service;

import java.util.Date;

import egovframework.com.uss.ion.apv.PassboxDoc;
import egovframework.com.uss.ion.apv.DocImpl;

public class PassboxDocVO extends DocImpl implements PassboxDoc {

	/**
	 * 배부받은 부서들
	 */
	protected String recpDeptNames;

	public String getRecpDeptNames() {
		return recpDeptNames;
	}
	public void setRecpDeptNames(String recpDeptNames) {
		this.recpDeptNames = recpDeptNames;
	}

}
