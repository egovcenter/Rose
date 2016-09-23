package egovframework.com.uss.ion.apv;

import java.util.Date;

public interface PassboxDoc extends Doc {
	/**
	 * 배부받은 부서들
	 * @return
	 */
	public String getRecpDeptNames() ;
	/**
	 * 배부받은 부서들
	 */
	public void setRecpDeptNames(String recpDeptNames);
}
