package br.unb.GODA.SymbolicParametricFormulas.model;

import java.util.ArrayList;
import java.util.List;

public class Node {
	private NodeType type;
	private Node parent;
	private List<Node> children;

	private String globalID;
	private String id;
	private String description;
	private String runtimeAnnotation;

	public Node(/* String idPrefix, */ String identificator, String desc, String runtimeAnnotation) {
		this.id = identificator;
		this.description = desc;
		this.runtimeAnnotation = runtimeAnnotation;

		parseAnnotation();
		children = new ArrayList<Node>();
	}

	public void setGlobalID(String globalId) {
		this.globalID = globalId;
	}

	private NodeType parseAnnotation() {
		if (hasChar(runtimeAnnotation, '?')) {
			// try
			type = NodeType.TRY;
		} else if (hasChar(runtimeAnnotation, '@')) {
			// retry
			type = NodeType.RETRY;
		} else if (hasChar(runtimeAnnotation, '|')) {
			// alternative
			type = NodeType.ALTERNATIVE;
		} else if (hasChar(runtimeAnnotation, ';')) {
			// and
			type = NodeType.PARALLEL_AND;
		} else if (hasChar(runtimeAnnotation, '+')) {
			// cardinality
			type = NodeType.CARDINALITY;
		} else if (hasChar(runtimeAnnotation, '#')) {
			// or or cardinality
			for (int i = runtimeAnnotation.length() - 1; i > 0; i--) {
				if (runtimeAnnotation.charAt(i) == '#') {
					try {
						Integer.parseInt(runtimeAnnotation.substring(i+1));
						type = NodeType.CARDINALITY_HASH;
						break;
					} catch (NumberFormatException e) {
						type = NodeType.PARALLEL_OR;
						break;
					}
				}
			}
		}

		return type;
	}

	private boolean hasChar(String annotation, char ch) {
		return annotation.indexOf(ch) > 0;
	}

	public void addChild(Node child) {
		children.add(child);
		child.addParent(this);
	}

	private void addParent(Node parent) {
		this.parent = parent;
	}

	public String getId() {
		return this.id;
	}

	public Node getParent() {
		return parent;
	}

	public List<Node> getChildrens() {
		return this.children;
	}

	public void printNode(int level) {
		StringBuilder prefix = new StringBuilder();
		for (int i = 0; i < level; i++) {
			prefix.append('\t');
		}

		System.out.println(prefix.toString() + level + "- id: " + id);
		System.out.println(prefix.toString() + level + "- ds: " + description);
		System.out.println(prefix.toString() + level + "- an: " + runtimeAnnotation);

		for (Node child : children) {
			child.printNode(level + 1);
		}
	}

	public String generateParametricFormula() {

		if (children.size() > 0) {
			String sb = type.getFormula();
			for (int i = 1; i <= children.size(); i++) {
				sb = sb.replaceAll("N" + i, children.get(i - 1).generateParametricFormula());
			}

			if (this.type == NodeType.RETRY) {
				int k = getK(this.runtimeAnnotation);
					k = k+1;
					sb = sb.replaceAll("k\\+1", String.valueOf(k));
			} else if (this.type == NodeType.CARDINALITY || this.type == NodeType.CARDINALITY_HASH) {
				int k = getK(this.runtimeAnnotation);
					sb = sb.replaceAll("k", String.valueOf(k));
			}
			return sb;
		} else {
			return globalID;
		}
	}

	private int getK(String runtimeAnnotation) {
		int k = 0;
		for (int i = runtimeAnnotation.length() - 1; i > 0; i++) {
			try {
				int value = Integer.parseInt(runtimeAnnotation.substring(i));
				k = value;
			} catch (NumberFormatException e) {
				break;
			}
		}
		return k;
	}
}
