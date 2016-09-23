package egovframework.com.uss.ion.apm;

public interface Label {

		/**
		 * 분류태그ID
		 */
		public abstract String getLabelId();
		/**
		 * 분류태그ID
		 */
		public abstract void setLabelId(String labelID);
		/**
		 * 부서ID
		 */
		public abstract String getDeptId();
		/**
		 * 부서ID
		 */
		public abstract void setDeptId(String deptId);

		/**
		 * Lft
		 */
		public abstract int getLabelLft();
		/**
		 * Lft
		 */
		public abstract void setLabelLft(int labelLft);
		
		/**
		 * Rgt
		 */
		public abstract int getLabelRgt();
		/**
		 * Rgt
		 */
		public abstract void setLabelRgt(int labelRgt);
		/**
		 * 분류태그명
		 */
		public abstract String getLabelNm();
		/**
		 * 분류태그명
		 */
		public abstract void setLabelNm(String labelNm);
		/**
		 * Top
		 */
		public abstract String getLabelTopF();
		/**
		 * Top
		 */
		public abstract void setLabelTopF(String labelTopF);
		
		/**
		 * 레벨
		 */
		public abstract String getLabelLevel();
		/**
		 * 레벨
		 */
		public abstract void setLabelLevel(String labelLevel);
}