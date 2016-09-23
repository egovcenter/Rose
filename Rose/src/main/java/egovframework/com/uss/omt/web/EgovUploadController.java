package egovframework.com.uss.omt.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.select.Evaluator.IsEmpty;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.uss.ion.apu.PathUtil;
import egovframework.com.uss.omt.service.UploadItemVO;

@Controller
public class EgovUploadController {
	
	Logger logger = org.slf4j.LoggerFactory.getLogger(EgovUploadController.class);
	
    @RequestMapping(value="/upload.do", method = RequestMethod.GET)
	public String getUploadForm(Model model) {
		logger.info("{}() start", "getUploadForm");
		model.addAttribute(new UploadItemVO());
		return "egovframework/com/uss/omt/cmm/EgovUploadForm";
	}

	@RequestMapping(value="/upload.do", method = RequestMethod.POST)
	public ModelAndView create(@ModelAttribute("uploadItemVO") UploadItemVO uploadItem, MultipartHttpServletRequest mrequest, BindingResult result) {
		logger.info("{}() start", "create");
		logger.debug("{}", uploadItem.toString());
		if (result.hasErrors()) {
			for (Object error : result.getAllErrors()) {
				System.err.println("Error: " + ((ObjectError) error).getCode() + " - "
						+ ((ObjectError) error).getDefaultMessage());
			}
			return null;
		}

		MultipartFile uploadFile = null;
		Iterator<String> files = mrequest.getFileNames();
		if ((files != null) && (files.hasNext())) {
		    while(files.hasNext()) {
		        String filename = files.next();
		        
		        uploadFile = mrequest.getFile(filename);
		    }
		}
		logger.debug("ContentType        = {}", uploadFile.getContentType());
		logger.debug("Name               = {}", uploadFile.getOriginalFilename());
		logger.debug("Size               = {}", uploadFile.getSize());
		
		uploadItem.setName(uploadFile.getOriginalFilename());
		
		InputStream is = null;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			File tempDir = new File((new PathUtil()).getTempPath());
			FileUtils.forceMkdir(tempDir);
			File tempFile = new File(tempDir,
					StringUtils.join(new String[] {
					"p",
					String.valueOf(new Date().getTime()),
					uploadItem.getExtension()
			}, ""));
			logger.debug("saved filename = {}", tempFile.getName());
			is = uploadFile.getInputStream();
			fos = new FileOutputStream(tempFile);
			int data = 0;
			while ((data = is.read()) != -1) {
				fos.write(data);
			}
			uploadItem.setSavedFilename(tempFile.getName());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) try { fos.close(); } catch (Exception e) {}
			if (fis != null) try { fis.close(); } catch (Exception e) {}
		}

		String viewName = "egovframework/com/uss/omt/cmm/EgovUploadForm";
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("uploadItem", uploadItem);
		return mav;
	}
}