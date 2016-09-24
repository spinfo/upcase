package de.uni_koeln.spinfo.upcase.controller.tree;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.jtree.Node;

@Controller
public class TreeController {

	@RequestMapping(value = { "/tree" })
	public String tree() {
		return "tree";
	}
	
	@RequestMapping(value = { "/tree/json" }, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public @ResponseBody String data() throws JsonGenerationException, JsonMappingException, IOException {
		
		Node root = new Node("collectionID", "collectionName", "#"); // ROOT
		Node n1 = new Node("n1_ID", "node 1", "collectionID");
		Node n2 = new Node("n2_ID", "node 2", "collectionID");
		Node n3 = new Node("n3_ID", "node 3", "collectionID");
		Node n4 = new Node("n4_ID", "node 4", "collectionID");

		List<Node> tree = new ArrayList<>();
		tree.add(root);
		tree.add(n1);
		tree.add(n2);
		tree.add(n3);
		tree.add(n4);

		final OutputStream out = new ByteArrayOutputStream();
	    final ObjectMapper mapper = new ObjectMapper();
	    mapper.enable(SerializationFeature.INDENT_OUTPUT);
	    mapper.writeValue(out, tree);
	    final byte[] data = ((ByteArrayOutputStream) out).toByteArray();
	    
		return new String(data);
	}
}
