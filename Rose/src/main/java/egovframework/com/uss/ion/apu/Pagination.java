package egovframework.com.uss.ion.apu;

public class Pagination {

	/**
	 * 총 레코드 수
	 */
	private int totalRecordCount = 0;
	/**
	 * 한 페이지당 레코드 수
	 */
	private int recordCountPerPage = 10;
	/**
	 * 현재 페이지 번호
	 */
	private int currentPageNo = 1;
	/**
	 * 페이지 리스트에 보여지는 페이지 개수
	 */
	private int pageSize = 10;
	/**
	 * 전체 페이지 수
	 */
	private int totalPageCount;
	/**
	 * 페이지 리스트의 첫 페이지 번호
	 */
	private int firstPageNoOnPageList;
	/**
	 * 페이지 리스트의 마지막 페이지 번호
	 */
	private int lastPageNoOnPageList;
	/**
	 * 현재 페이지의 시작 레코드 번호(for DB SQL)
	 */
	private int firstRecordIndex;
	/**
	 * 현재 페이지의 마지막 레코드 번호(for DB SQL)
	 */
	private int lastRecordIndex;
	/**
	 * 현재 페이지의 시작 레코드 번호(for View)
	 */
	private int firstRecordIndexOnCurrentPage;
	/**
	 * 현재 페이지의 마지막 레코드 번호(for View)
	 */
	private int lastRecordIndexOnCurrentPage;
	/**
	 * 목록 정렬 컬럼
	 */
	private String orderColumn;
	/**
	 * 목록 정렬 방법(asc / desc)
	 */
	private String orderMethod;
	
	/**
	 * 총 레코드 수
	 */
	public int getTotalRecordCount() {
		return totalRecordCount;
	}
	/**
	 * 총 레코드 수
	 */
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
	/**
	 * 한 페이지당 레코드 수
	 */
	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}
	/**
	 * 한 페이지당 레코드 수
	 */
	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}
	/**
	 * 현재 페이지 번호
	 */
	public int getCurrentPageNo() {
		return currentPageNo;
	}
	/**
	 * 현재 페이지 번호
	 */
	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}
	/**
	 * 페이지 리스트에 보여지는 페이지 개수
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * 페이지 리스트에 보여지는 페이지 개수
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * 전체 페이지 수
	 */
	public int getTotalPageCount() {
		totalPageCount = ((getTotalRecordCount()-1)/getRecordCountPerPage()) + 1;
		return totalPageCount;
	}
	/**
	 * 페이지 리스트의 첫 페이지 번호
	 */
	public int getFirstPageNoOnPageList() {
		firstPageNoOnPageList = ((getCurrentPageNo()-1)/getPageSize())*getPageSize() + 1;
		return firstPageNoOnPageList;
	}
	/**
	 * 페이지 리스트의 마지막 페이지 번호
	 */
	public int getLastPageNoOnPageList() {
		lastPageNoOnPageList = getFirstPageNoOnPageList() + getPageSize() - 1;		
		if(lastPageNoOnPageList > getTotalPageCount()){
			lastPageNoOnPageList = getTotalPageCount();
		}
		return lastPageNoOnPageList;
	}
	/**
	 * 현재 페이지의 시작 레코드 번호(for DB SQL)
	 */
	public int getFirstRecordIndex() {
		firstRecordIndex = (getCurrentPageNo() - 1) * getRecordCountPerPage() + 1;
		return firstRecordIndex;
	}
	/**
	 * 현재 페이지의 마지막 레코드 번호(for DB SQL)
	 */
	public int getLastRecordIndex() {
		lastRecordIndex = getCurrentPageNo() * getRecordCountPerPage();
		return lastRecordIndex;
	}
	/**
	 * 현재 페이지의 시작 레코드 번호(for View)
	 */
	public int getFirstRecordIndexOnCurrentPage(){
		if(totalRecordCount==0){
			firstRecordIndexOnCurrentPage = 0;
		}else{
			firstRecordIndexOnCurrentPage = getFirstRecordIndex();
		}
		return firstRecordIndexOnCurrentPage;
	}
	/**
	 * 현재 페이지의 마지막 레코드 번호(for View)
	 */
	public int getLastRecordIndexOnCurrentPage(){
		if(recordCountPerPage >= totalRecordCount){
			lastRecordIndexOnCurrentPage = totalRecordCount;
		}else if(recordCountPerPage < totalRecordCount){
			if(getCurrentPageNo() < getTotalPageCount()){
				lastRecordIndexOnCurrentPage = getLastRecordIndex();
			}else if(getCurrentPageNo() == getTotalPageCount()){
				lastRecordIndexOnCurrentPage = totalRecordCount;
			}
		}
		return lastRecordIndexOnCurrentPage;
	}
	/**
	 * 목록 정렬 컬럼
	 */
	public String getOrderColumn() {
		return orderColumn;
	}
	/**
	 * 목록 정렬 컬럼
	 */
	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}
	/**
	 * 목록 정렬 방법(asc / desc)
	 */
	public String getOrderMethod() {
		return orderMethod;
	}
	/**
	 * 목록 정렬 방법(asc / desc)
	 */
	public void setOrderMethod(String orderMethod) {
		this.orderMethod = orderMethod;
	}
}
