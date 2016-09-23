package egovframework.com.uss.ion.apu;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.service.Globals;
import egovframework.com.uss.ion.apm.Form;
import egovframework.com.uss.ion.apv.Doc;
import egovframework.com.uss.ion.apv.service.AttachFileVO;
import egovframework.com.uss.ion.apv.service.SignerVO;
import egovframework.com.uss.umt.service.UserManageVO;


/**
 * <baseDir>/doc : 결재문서 파일 폴더, ID 기반으로 생성되는 하위 폴더가 존대. 폴더에는 결재문서(<문서ID>), 싸인파일(<문서ID>_<signer_id>.<확장자>)이 존재 
 * <baseDir>/form : 결재양식 파일 폴더, 하위에 양식ID로 양식 팔일들이 존재
 * <baseDir>/sign : 사용자들의 싸인 파일들이 존재, <사용자ID>.[gif|jpg|bmp]
 * <baseDir>/tmp : 임시폴더로 결재문서의 싸인파일이 임시로 존재하는 위치이며 tmp 폴더는 웹에서 접근가등하도록 WAS에 alias 혹은 context 설정이 필요하다.
 * - <baseDir>/tmp/<문서ID>/<싸인파일> : 결재문서 오픈시 싸인파일이 임시로 생성되는 폴더, 결재문서의 싸인 이미지의 src 가 현재 패스를 웹에서 접근가능한 패스로 들어간다. 
 */
public class PathUtil implements InitializingBean{
    
	private static String baseDir = EgovProperties.getProperty(Globals.APPROVAL_CONF_PATH, "approval.data.basedir");

	public static String getBaseDir() {
		return baseDir;
	}

	public void setBaseDir(String baseDir) {
		PathUtil.baseDir = baseDir;
	}
	
	public void afterPropertiesSet() throws Exception {
		if(baseDir == null || baseDir.length() < 1){
			throw new ApprovalException("Set the base dir[approval.data.basedir] of the approval in the approval.properties");
		}
	}

	public static String getFileExt(String filename){
		if(filename == null) return null;
		int idx = filename.lastIndexOf(".");
		if(idx < 0) return null;
		return filename.substring(idx+1);
	}
	public static File getTmpDir(String base){
		File tmp = new File(base + File.separator + "tmp");
		tmp.mkdirs();
		
		return tmp;
	}
	public static void copy(File src, File tgt) throws Exception{
		InputStream in = null;
		OutputStream out = null;
		try{
			in = new FileInputStream(src);
			out = new FileOutputStream(tgt);
			IOUtils.copy(in, out);
		}catch(Exception e){
			throw e;
		}finally{
			try{if(in != null) in.close();}catch(Exception e){}
			try{if(out != null) out.close();}catch(Exception e){}
		}
	}
	public static File createUserDir(String base, String id){
		return createDirWithID(base + File.separator + "user", id, 0);
	}
	public static File createDocDir(String base, String id){
		return createDirWithID(base + File.separator + "doc", id, 2);
	}
	private static File createDirWithID(String base, String id, int offset){
		String path = base;
		int i=0;
		if(id.length() % 2 != 0){
			path = path + File.separator + id.substring(0, ++i);
		}
		for(; i<id.length()-offset; ){
			path = path + File.separator + id.substring(i, i+2);
			i += 2;
		}
		File dir = new File(path);
		if(!dir.exists()){
			dir.mkdirs();
		}
		return dir;
	}

	public static File getFormPath(Form form){
		return new File(baseDir + File.separator + "form" + File.separator + form.getFormId());
	}
	
	// TODO doc'path will be changed to the relative path with created date.
	public static File getDocPath(Doc doc){
		File docDir = createDocDir(baseDir, doc.getDocID());
		
		return new File(docDir.getAbsoluteFile()+File.separator+doc.getDocID());
	}
	
	public static File getAttachPath(AttachFileVO attach){
		File docDir = createDocDir(baseDir, attach.getDocID());
		
		File attachFile = new File(docDir.getAbsolutePath() + File.separator + attach.getDocID()+"_"+ attach.getAttachID());
		
		return attachFile;
	}
	
	public static File getUserSign(final UserManageVO user){
		File signDir = new File(baseDir+ File.separator + "sign");
		
		File[] matchingFiles = signDir.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.startsWith(user.getOrgnztId()) && (name.toLowerCase().endsWith("gif") || name.toLowerCase().endsWith("jpg") || name.toLowerCase().endsWith("bmp"));
		    }
		});
		return matchingFiles == null || matchingFiles.length < 1 ? null : matchingFiles[0];
	}
	
	public static File createSign(Doc doc, SignerVO signer, UserManageVO user)throws Exception{
		File userSign = getUserSign(user);
		if(userSign == null || !userSign.exists()) return null;
		
		File docPath = getDocPath(doc);
		File targetSignFile = new File(docPath.getParent() + File.separator + doc.getDocID() + "_" + signer.getSignerID() + "."+getFileExt(userSign.getName()) );
		
		copy(userSign, targetSignFile);

		return targetSignFile;
	}
	
	public static File createTmpSign(final Doc doc, final SignerVO signer, UserManageVO user)throws Exception{
		File docPath = getDocPath(doc);
		File[] matchingFiles = docPath.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.startsWith(doc.getDocID() + "_" + signer.getSignerID()) && (name.toLowerCase().endsWith("gif") || name.toLowerCase().endsWith("jpg") || name.toLowerCase().endsWith("bmp"));
		    }
		});
		
		if(matchingFiles == null || matchingFiles.length < 1) return null;
		
		File signFile = matchingFiles[0];
		File tmpSignFile = new File(getTmpDir(baseDir) + File.separator + "sign" + File.separator + signFile.getName());
		
		copy(signFile, tmpSignFile);

		return tmpSignFile;
	}
	
	public static File createDocFile(Doc doc, String html) throws Exception{
		if(html == null) return null;
		
		OutputStream out = null;
		try{
			File file = getDocPath(doc);
			
			out = new BufferedOutputStream(new FileOutputStream(file));
			IOUtils.write(html, out, "utf-8");
			
			return file;
		}catch(Exception e){
			throw e;
		}finally{
			try{if(out != null) out.close();}catch(Exception e){}
		}
	}
	
	public static File saveFile(File file, String html) throws Exception{
		OutputStream out = null;
		try{
			out = new BufferedOutputStream(new FileOutputStream(file));
			IOUtils.write(html, out, "utf-8");
			
			return file;
		}catch(Exception e){
			throw e;
		}finally{
			try{if(out != null) out.close();}catch(Exception e){}
		}
	}
	
	public static File moveFile(AttachFileVO attach, File srcFile) throws Exception{
		File docDir = createDocDir(baseDir, attach.getDocID());
		
		File attachFile = new File(docDir.getAbsolutePath() + File.separator + attach.getDocID()+"_"+ attach.getAttachID());
		FileUtils.moveFile(srcFile, attachFile);
		
		return attachFile;
	}
	
   public static File saveAttachFile(AttachFileVO attach, MultipartFile srcFile) throws Exception{
       File attachFile = null;
       
        try {
            File docDir = createDocDir(baseDir, attach.getDocID());

            attachFile = new File(docDir.getAbsolutePath() + File.separator + attach.getDocID() + "_" + attach.getAttachID());
            srcFile.transferTo(attachFile);
        } catch (Exception e) {
            ;
        }
        return attachFile;
    }
   
    public static String getDirectoryForAttachFile(AttachFileVO attach) {
        File docDir = createDocDir(baseDir, attach.getDocID());

        return docDir.getAbsolutePath();
    }

	public static File copyFile(AttachFileVO srcAttach, AttachFileVO tgtAttach) throws Exception{
		File srcDocDir = createDocDir(baseDir, srcAttach.getDocID());
		File tgtDocDir = createDocDir(baseDir, tgtAttach.getDocID());
		
		File srcAttachFile = new File(srcDocDir.getAbsolutePath() + File.separator + srcAttach.getDocID()+"_"+ srcAttach.getAttachID());
		File tgtAttachFile = new File(tgtDocDir.getAbsolutePath() + File.separator + tgtAttach.getDocID()+"_"+ tgtAttach.getAttachID());
		
		copy(srcAttachFile, tgtAttachFile);
		
		return tgtAttachFile;
	}

    public String getDocBasePath() {
        return getBaseDir();
    }
    
    public String getTempPath() {
        String baseDir = this.getClass().getResource("/").getPath();
        return StringUtils.join(new String[] {baseDir, "../../temp"}, File.separator);
    }
    
    public String getUserDataPath() {
        return StringUtils.join(new String[] {getBaseDir(), "user"}, File.separator);
    }
}
