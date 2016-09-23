package egovframework.com.uss.ion.apm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.uss.ion.apm.service.EgovLabelService;
import egovframework.com.uss.ion.apm.service.LabelVO;
import egovframework.com.uss.ion.apv.ApprovalConstants;
import egovframework.com.uss.ion.apv.service.EgovApprovalDocService;
import egovframework.com.uss.omt.service.EgovDeptService;
import egovframework.com.uss.umt.service.UserManageVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

/**
 * @Class Name : EgovAttachFileService.java
 * @Description : Service for managing attached file information
 * @Modification Information
 *
 *    Date         Author	   Content
 *   -----------  -------     -------------------
 *    2016.02.04   paul        initial
 *
 * @since 2016.02.04.
 * @version
 * @see
 */
@Service("EgovLabelService")
public class EgovLabelServiceImpl extends EgovAbstractServiceImpl implements EgovLabelService {
	
	@Resource(name = "LabelDAO")
	private LabelDAO		labelDAO;
	
	@Resource(name = "EgovApprovalDocService")
	private EgovApprovalDocService docService;
	
	@Resource(name = "egovLabelIdGnrService")
	private EgovIdGnrService idgenService;
	
	@Resource(name = "EgovDeptService")
	private EgovDeptService deptService;
	
	private final String ROOT_LABEL_NM = "TOP LABEL";

    private final String ROOT_LABEL_FLAG = "1";
	
	@Override
	public List<LabelVO> getLabelTreeOfDeptID(String deptId) throws Exception {
		return labelDAO.selectLabelTreeOfDeptID(deptId);
	}

	@Override
	public LabelVO getRootLabel(String deptId) throws Exception {
	    return labelDAO.selectRootLabel(deptId);
	}
	
	@Override
	public LabelVO getLabel(String labelID) throws Exception {
		return labelDAO.selectLabel(labelID);
	}

	@Override
	public LabelVO insertRootLabel(String orgId) throws Exception {
	    if(orgId == null) {
	        return null;
	    }
	    
        LabelVO label = new LabelVO();
        label.setDeptId(orgId);
        
        String labelID = idgenService.getNextStringId();
        label.setLabelId(labelID);
        
        label.setLabelSeq(1);
        label.setLabelNm(ROOT_LABEL_NM);
        label.setLabelTopF(ROOT_LABEL_FLAG);
        labelDAO.insertLabel(label);
        
        return labelDAO.selectLabel(label.getLabelId());
	}
	
    @Override
    public LabelVO insertLabel(LabelVO label, String targetLabelID, String targetPosition,  UserManageVO user) throws Exception{
        if(label.getLabelId() == null){
            String labelID = idgenService.getNextStringId();
            label.setLabelId(labelID);
        }
        
        if(user != null){
            label.setDeptId(user.getOrgnztId());
        }
        
        LabelVO targetLabel = labelDAO.selectLabel(targetLabelID);
        if(targetLabel == null){
            throw new Exception("can not find the label["+targetLabelID+"]");
        }
        if(ApprovalConstants.POSITION_LOWER.equals(targetPosition)){
            label.setLabelParentId(targetLabel.getLabelId());
            int maxSeq = labelDAO.selectMaxSeq(targetLabel.getLabelId());
            label.setLabelSeq(maxSeq);
            labelDAO.insertLabel(label);
        }else if(ApprovalConstants.POSITION_UPPER.equals(targetPosition)){
            String targetParLabelId = targetLabel.getLabelParentId();
            LabelVO targetParLabel = labelDAO.selectLabel(targetParLabelId);
            label.setLabelParentId(targetParLabel.getLabelParentId());
            int maxSeq = labelDAO.selectMaxSeq(targetParLabel.getLabelParentId());
            label.setLabelSeq(maxSeq);
            /*label.setLabelSeq(targetLabel.getLabelSeq());
            DAOFactory.getLabelDAO().shiftSeq(targetLabel.getLabelParentID(), targetLabel.getLabelSeq()-1);*/
            labelDAO.insertLabel(label);
        }else{
            label.setLabelParentId(targetLabel.getLabelParentId());
            label.setLabelSeq(targetLabel.getLabelSeq()+1);
            labelDAO.shiftSeq(targetLabel.getLabelParentId(), targetLabel.getLabelSeq());
            labelDAO.insertLabel(label);
        }
        return labelDAO.selectLabel(label.getLabelId());
    }

	@Override
    public void updateLabel(LabelVO label, String targetLabelID, String targetPosition, UserManageVO user) throws Exception{
        if(StringUtils.isNotEmpty(targetLabelID) && StringUtils.isNotEmpty(targetPosition)){
            LabelVO targetLabel = labelDAO.selectLabel(targetLabelID);
            if(targetLabel == null){
                throw new Exception("can not find the label["+targetLabelID+"]");
            }
            
            if(ApprovalConstants.POSITION_LOWER.equals(targetPosition)){
                label.setLabelParentId(targetLabel.getLabelId());
                int maxSeq = labelDAO.selectMaxSeq(targetLabel.getLabelId());
                label.setLabelSeq(maxSeq);
            }else if(ApprovalConstants.POSITION_UPPER.equals(targetPosition)){
                String targetParLabelId = targetLabel.getLabelParentId();
                
                LabelVO targetParLabel = labelDAO.selectLabel(targetParLabelId);
                label.setLabelParentId(targetParLabel.getLabelParentId());
                int maxSeq = labelDAO.selectMaxSeq(targetParLabel.getLabelParentId());
                label.setLabelSeq(maxSeq);
                //label.setLabelParentID(targetLabel.getLabelParentID());
                //label.setLabelSeq(targetLabel.getLabelSeq());
                //DAOFactory.getLabelDAO().shiftSeq(targetLabel.getLabelParentID(), targetLabel.getLabelSeq()-1);
            }else{
                label.setLabelParentId(targetLabel.getLabelParentId());
                label.setLabelSeq(targetLabel.getLabelSeq()+1);
                labelDAO.shiftSeq(targetLabel.getLabelParentId(), targetLabel.getLabelSeq());
            }
        }
        labelDAO.updateLabel(label);
    }

	@Override
    public void deleteLabel(String labelId) throws Exception{
        int count = docService.getDocCntByLabelId(labelId);
        if(count > 0){
            throw new Exception("This label is currently in use.");
        }
        //Label label = DAOFactory.getLabelDAO().getLabel(labelID);
        labelDAO.deleteLabel(labelId);      
    }

	@Override
	public void shiftSeq(String parentID, int baseSeq) throws Exception {
		labelDAO.shiftSeq(parentID, baseSeq);
	}

	@Override
	public int selectMaxSeq(String parentID) throws Exception {
		return (Integer)labelDAO.selectMaxSeq(parentID);
	}

	@Override
	public ModelAndView getLabelTree(String userId, String deptId) throws Exception {
		ModelAndView model = new ModelAndView();
		
	    List<LabelVO> labelList = getLabelTreeOfDeptID(deptId);
		
		//UserApprovalAuthVO userAuth = approvalAuthService.getUserApprovalAuth(userId);
		//DeptApprovalAuthVO deptAuth = approvalAuthService.getDeptApprovalAuth(depEgovDeptServicetId);
		
		model.addObject("labelList",labelList);
		//model.addObject("userAuth",userAuth);
		//model.addObject("deptAuth",deptAuth);
		return model;
	}

}
