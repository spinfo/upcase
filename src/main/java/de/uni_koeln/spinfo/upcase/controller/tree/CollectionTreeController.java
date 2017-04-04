package de.uni_koeln.spinfo.upcase.controller.tree;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.tree.Node;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.tree.Tree;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.NodeRepository;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.TreeRepository;

@Controller
public class CollectionTreeController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired private NodeRepository nodeRepository;
	@Autowired private TreeRepository treeRepository;
	
	@RequestMapping(value = { "tree/collection/{collectionId}" }, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public String displayTree(@PathVariable("collectionId") String collectionId, Model model) throws JsonGenerationException, JsonMappingException, IOException {
		Tree tree = treeRepository.findByCollectionId(collectionId);
		// FIXME:	tree for collection with 'collectionId' is not generated properly ...
		List<Node> nodes = new ArrayList<>();
		nodes.add(nodeRepository.findById(tree.getRoot()));
		nodes.addAll(nodeRepository.findByNodes(tree.getNodes()));
		model.addAttribute("tree", nodes);
		model.addAttribute("collectionId", collectionId);
		return "treeCollection";
	}
	
	@RequestMapping(value = "/tree/collection/{collectionId}/create/", method = RequestMethod.POST)
	public @ResponseBody Node createNode(@RequestBody Node node, @PathVariable("collectionId") String collectionId, Model model) {
		logger.info("node.create=" + node);
		nodeRepository.save(node);
		model.addAttribute("collectionId", collectionId);
		return node;
	}
	
	@RequestMapping(value = "/tree/collection/{collectionId}/update/", method = RequestMethod.POST)
	public @ResponseBody Node updateNode(@RequestBody Node node, @PathVariable("collectionId") String collectionId, Model model) {
		logger.info("node.update=" + node);
		Node update = nodeRepository.update(node);
		model.addAttribute("collectionId", collectionId);
		return update;
	}
	
	@RequestMapping(value = "/tree/collection/{collectionId}/delete/", method = RequestMethod.POST)
	public @ResponseBody Node deleteNode(@RequestBody Node node, @PathVariable("collectionId") String collectionId, Model model) {
		logger.info("node.delete=" + node);
		nodeRepository.delete(node);
		model.addAttribute("collectionId", collectionId);
		return node;
	}
	
	public String getJsonString(Object obj) throws IOException, JsonGenerationException, JsonMappingException {
		final OutputStream out = new ByteArrayOutputStream();
	    final ObjectMapper mapper = new ObjectMapper();
	    mapper.enable(SerializationFeature.INDENT_OUTPUT);
	    mapper.writeValue(out, obj);
	    final byte[] data = ((ByteArrayOutputStream) out).toByteArray();
		return new String(data);
	}

}
