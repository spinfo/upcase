package de.uni_koeln.spinfo.upcase.controller.tree;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.tree.Node;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.tree.Type;

@RestController
public class TreeController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	
	private AtomicInteger idGen = new AtomicInteger(100000);
	
	Node root = new Node("_" + idGen.getAndIncrement(), "my collection", "#", Type.ROOT.getType(), true);
	Node folder_1 = new Node("_" + idGen.getAndIncrement(), "chapter 1", root.getId(), Type.FOLDER.getType(), false);
	Node folder_2 = new Node("_" + idGen.getAndIncrement(), "chapter 2", root.getId(), Type.FOLDER.getType(), true);
	Node file_1 = new Node("_" + idGen.getAndIncrement(), "page 1", root.getId(), Type.FILE.getType());
	Node file_2 = new Node("_" + idGen.getAndIncrement(), "page 2", root.getId(), Type.FILE.getType());
	Node file_3 = new Node("_" + idGen.getAndIncrement(), "page 3", root.getId(), Type.FILE.getType());
	Node file_4 = new Node("_" + idGen.getAndIncrement(), "page 4", folder_2.getId(), Type.FILE.getType());
	Node file_5 = new Node("_" + idGen.getAndIncrement(), "page 5", folder_2.getId(), Type.FILE.getType());
	
	List<Node> nodes;
	
	private String getJsonString(Object obj) throws IOException, JsonGenerationException, JsonMappingException {
		final OutputStream out = new ByteArrayOutputStream();
	    final ObjectMapper mapper = new ObjectMapper();
	    mapper.enable(SerializationFeature.INDENT_OUTPUT);
	    mapper.writeValue(out, obj);
	    final byte[] data = ((ByteArrayOutputStream) out).toByteArray();
		return new String(data);
	}
	
	@RequestMapping(value = { "/tree" }, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public @ResponseBody String init(@RequestParam("id") String id) throws JsonGenerationException, JsonMappingException, IOException {
		
		if(nodes == null) {
			nodes = new ArrayList<>();
			nodes.add(root);
			nodes.add(folder_1);
			nodes.add(folder_2);
			nodes.add(file_1);
			nodes.add(file_2);
			nodes.add(file_3);
			nodes.add(file_4);
			nodes.add(file_5);
		}
		
		if(id.equals("#")) {
			return getJsonString(root);
		} else {
			List<Node> toReturn = new ArrayList<>();
			for (Node node : nodes) {
				if(node.getParent().equals(id)) {
					toReturn.add(node);
				}
			}
			return getJsonString(toReturn);
			
		}
	}
	
	@RequestMapping(value = "/tree/update/", method = RequestMethod.POST)
	public @ResponseBody Node updateNode(@RequestBody Node node) {
		logger.info("update.node: " + node);
		return node;
	}

}
