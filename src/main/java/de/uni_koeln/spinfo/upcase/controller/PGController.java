package de.uni_koeln.spinfo.upcase.controller;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.uni_koeln.spinfo.upcase.lucene.Searcher;
import de.uni_koeln.spinfo.upcase.lucene.json.PGQuery;
import de.uni_koeln.spinfo.upcase.lucene.json.PGResult;

@RestController
public class PGController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private Searcher searcher;

	@RequestMapping(value = "/drc/pg/quotation", method = RequestMethod.POST)
	public @ResponseBody List<PGResult> find(@RequestBody PGQuery pgQuery)
			throws IOException, ParseException, InvalidTokenOffsetsException {
		logger.debug("Request on [/drc/pg/quotation], query: " + pgQuery);
		// return searcher.findQuotation(pgQuery);
		return null;
	}

}
