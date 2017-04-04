package de.uni_koeln.spinfo.upcase.controller.tree;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.CollectionRepository;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.NodeRepository;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.PageRepository;

@RestController
public class TreeController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired private PageRepository pageRepository;
	@Autowired private CollectionRepository collectionRepository;
	@Autowired private NodeRepository nodeRepository;
	
	private AtomicInteger idGen = new AtomicInteger();
	
	Node root = new Node("_" + idGen.getAndIncrement(), "my collection", "#", Type.ROOT.getType(), 0);
	Node folder_1 = new Node("_" + idGen.getAndIncrement(), "chapter 1", root.getId(), Type.FOLDER.getType(), 0);
	Node folder_2 = new Node("_" + idGen.getAndIncrement(), "chapter 2", root.getId(), Type.FOLDER.getType(), 1);
	Node file_1 = new Node("_" + idGen.getAndIncrement(), "page 1", root.getId(), Type.FILE.getType(), 2);
	Node file_2 = new Node("_" + idGen.getAndIncrement(), "page 2", root.getId(), Type.FILE.getType(), 3);
	Node file_3 = new Node("_" + idGen.getAndIncrement(), "page 3", root.getId(), Type.FILE.getType(), 4);
	Node file_4 = new Node("_" + idGen.getAndIncrement(), "page 4", folder_2.getId(), Type.FILE.getType(), 0);
	Node file_5 = new Node("_" + idGen.getAndIncrement(), "page 5", folder_2.getId(), Type.FILE.getType(), 1);
	
	List<Node> nodes;
	
	private String getJsonString(Object obj) throws IOException, JsonGenerationException, JsonMappingException {
		final OutputStream out = new ByteArrayOutputStream();
	    final ObjectMapper mapper = new ObjectMapper();
	    mapper.enable(SerializationFeature.INDENT_OUTPUT);
	    mapper.writeValue(out, obj);
	    final byte[] data = ((ByteArrayOutputStream) out).toByteArray();
		return new String(data);
	}
	
	/**
	 * USED ONLY FOR TESTING. IN PRODUCTION MODE NODES MUST BE LOADED FROM REPOSITORY.
	 * @param id
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
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
		logger.info("update on: " + node);
		for (Node n : nodes) {
			if(n.getId().equals(node.getId())) {
				n.setText(node.getText());
				//n.setChildren(node.isChildren());
				if(n.getPosition() != node.getPosition() || !n.getParent().equals(node.getParent())) {
					n.setPosition(node.getPosition());
					n.setParent(node.getParent());
					for (Node p : nodes) {
						if (p.getId().equals(node.getParent())) {
							//p.setChildren(true);
						}
					}
				}
			}
		}
		return node;
	}
	
//	@RequestMapping(value = "/tree/create/", method = RequestMethod.POST)
//	public @ResponseBody Node createNode(@RequestBody Node node) {
//		node.setId("_" + idGen.getAndIncrement());
//		nodes.add(node);
//		return node;
//	}

}
