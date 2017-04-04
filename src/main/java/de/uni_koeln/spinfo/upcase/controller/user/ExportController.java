package de.uni_koeln.spinfo.upcase.controller.user;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import de.uni_koeln.spinfo.upcase.mongodb.repository.future.PageRepository;

@Controller
public class ExportController {

	@Autowired private PageRepository pageRepository;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/export/page/{id}")
	public void getXmlFromPage(@PathVariable("id") String id, HttpServletResponse response) throws FileNotFoundException, IOException {
		String page = pageRepository.findOne(id);
		logger.info(page);
		JSONObject json = new JSONObject(page);
		String xml = XML.toString(json, "pageRoot");
		xml = xml.replaceAll("\\$", "");
		logger.info(xml);
		File xmlFile = new File(id + ".xml");
		BufferedWriter writer = new BufferedWriter(new FileWriter(xmlFile));
		writer.write(xml);
		writer.close();
		response.setContentType("application/xml");
		response.setHeader("Content-Disposition", "attachment; filename=" + xmlFile); 
		InputStream is = new FileInputStream(xmlFile);
		IOUtils.copy(is, response.getOutputStream());
		response.flushBuffer();
		xmlFile.delete();
	}

}
