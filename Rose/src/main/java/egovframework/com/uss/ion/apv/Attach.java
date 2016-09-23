package egovframework.com.uss.ion.apv;

public interface Attach {

	public abstract String getAttachID();

	public abstract void setAttachID(String attachID);

	public abstract String getDocID();

	public abstract void setDocID(String docID);

	public abstract String getAttachNm();

	public abstract void setAttachNm(String attachNm);

	public abstract int getAttachSeq();

	public abstract void setAttachSeq(int attachSeq);

	public abstract int getAttachSize();

	public abstract void setAttachSize(int attachSize);

	public abstract String getAttachType();

	public abstract void setAttachType(String attachType);

	public abstract String toString();

}