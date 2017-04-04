package de.uni_koeln.spinfo.upcase.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Collection;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.Page;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.tree.Node;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.tree.Tree;
import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.tree.Type;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.NodeRepository;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.PageRepository;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.TreeRepository;

@Service
public class TreeService {
	
	ExecutorService executor = Executors.newFixedThreadPool(10);
	
	@Autowired private PageRepository pageRepository;
	
	@Autowired private NodeRepository nodeRepository;
	
	@Autowired private TreeRepository treeRepository;
	
	public Future<Tree> createTree(final Collection collection) {
		
		return executor.submit(new Callable<Tree>() {
			
			private Logger logger = LoggerFactory.getLogger(getClass());

			@Override
			public Tree call() throws Exception {
				logger.info("Creating tree for collection " + collection.getTitle());
				String collectionId = collection.getId();
				String collectionTitle = collection.getTitle();
				List<Page> pages = pageRepository.findByCollectionId(collectionId);
				Tree tree = new Tree(collectionTitle, collectionId);
				Node root = new Node(collectionTitle, "#", Type.ROOT.getType());
				nodeRepository.save(root);
				tree.setRoot(root.getId());
				List<String> nodes = new ArrayList<>();
				int i = 0;
				for (Page page : pages) {
					String pageId = page.getId();
					Node node = new Node("Page " + i, root.getId(), Type.FILE.getType());
					node.setPosition(i);
					node.setPageId(pageId);
					i++;
					nodeRepository.save(node);
					nodes.add(node.getId());
				}
				tree.addAll(nodes);
				treeRepository.save(tree);
				logger.info(tree.toString());
				return tree;
			}
		});
		
	}

}
