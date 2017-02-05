package br.unb.GODA.SymbolicParametricFormulas.model;

import java.util.List;

public class Tree {

	private static final char TAB = '\t';
	private static final char ID_SEPARATOR = ':';
	private static final char ANOTATION_SEPARATOR = '[';

	private int actualLevel;
	private int previusLevel;
	private Node root;
	private Node actuaNode;
	private GlobalIdsTable globalIdsTable = new GlobalIdsTable();
	private boolean hasGenerateIds = false;

	public Tree() {
		actualLevel = 0;
		previusLevel = 0;
	}

	public void addNode(String line) {
		// IDENTIFIER: DESCRIPTION [runtime annotation]
		int position = 0;
		int level = 0;
		while (line.charAt(position) == TAB) {
			position++;
		}
		level = position;
		actualLevel = level;

		position = line.indexOf(":");

		String identifier = getIdentificator(line);
		String description = getDescription(line);
		String anotation = getAnotation(line);

		Node node = new Node(identifier, description, anotation);

		if (null == actuaNode) {
			root = node;
		} else {

			if (actualLevel == previusLevel) {
				actuaNode.getParent().addChild(node);
			} else if (actualLevel > previusLevel) {
				actuaNode.addChild(node);
			} else if (actualLevel < previusLevel) {
				actuaNode.getParent().getParent().addChild(node);
			}
		}
		previusLevel = actualLevel;
		actuaNode = node;
		System.out.println(line);
	}

	public void printTree() {
		root.printNode(0);
	}

	public void genetateIds() {
		if (!hasGenerateIds) {
			generateId(root, "");
			hasGenerateIds = true;
		}
	}

	private void generateId(Node node, String prefix) {
		List<Node> childrens = node.getChildrens();
		if (childrens.size() == 0) {
			// this is a leaf node. Generate globalID
			node.setGlobalID(globalIdsTable.addId(prefix + "_" + node.getId()));
		} else {
			for (Node child : childrens) {
				generateId(child, prefix + "_" + node.getId());
			}
		}
	}

	public String getFormula() {
		genetateIds();
		return root.generateParametricFormula();
	}

	private String getAnotation(String line) {
		String anotation = line.substring(line.indexOf(ANOTATION_SEPARATOR) + 1).trim();
		return anotation.substring(0, anotation.length() - 1);
	}

	private String getDescription(String line) {
		return line.substring(line.indexOf(ID_SEPARATOR) + 1, line.indexOf(ANOTATION_SEPARATOR)).trim();
	}

	private String getIdentificator(String line) {
		return line.substring(0, line.indexOf(ID_SEPARATOR)).trim();
	}

	public void printGlobalIds() {
		genetateIds();
		this.globalIdsTable.print();
	}
}
