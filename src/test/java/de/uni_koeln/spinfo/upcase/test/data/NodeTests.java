package de.uni_koeln.spinfo.upcase.test.data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.tree.Node;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.tree.Type;

public class NodeTests {

	@Test
	public void createSimpleTree() throws JsonGenerationException, JsonMappingException, IOException {
		Node root = new Node("collectionID", "collectionName", "#", Type.ROOT.getType()); // ROOT
		Node n1 = new Node("n1_ID", "node 1", "collectionID", Type.ROOT.getType());
		Node n2 = new Node("n2_ID", "node 2", "collectionID", Type.FILE.getType());
		Node n3 = new Node("n3_ID", "node 3", "collectionID", Type.FILE.getType());
		Node n4 = new Node("n4_ID", "node 4", "collectionID", Type.FILE.getType());

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
	    System.out.println(new String(data));
	    
	    
	}

}
