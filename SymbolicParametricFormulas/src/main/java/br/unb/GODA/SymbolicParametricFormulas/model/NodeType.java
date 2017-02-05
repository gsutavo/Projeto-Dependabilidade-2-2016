package br.unb.GODA.SymbolicParametricFormulas.model;

public enum NodeType {

	SEQUENTIAL_AND ("N1 ∗ N2"), 
	SEQUENTIAL_OR ("MAX(N1, N2)"), 
	PARALLEL_AND ("N1 ∗ N2"), 
	PARALLEL_OR ("MAX(N1, N2)"), 
	OPTIONAL ("Xn ∗ N1 − Xn + 1"), 
	ALTERNATIVE ("Xn1 ∗ N1 − Xn1 ∗ Xn2 ∗ N2 + Xn2 ∗ N2"), 
	TRY ("N1 ∗ N2 − N1 ∗ N3 + N3"), 
	CARDINALITY ("(N1)^k"), 
	CARDINALITY_HASH ("(N1)^k"),
	RETRY ("1 − (1 − N1)^k+1"), 
	SKIP ("");

	// Sequential AND (n1;n2) N1 ∗ N2
	// Sequential OR (n1;n2) MAX(N1, N2)
	// Parallel AND (n1#n2) N1 ∗ N2
	// Parallel OR (n1#n2) MAX(N1, N2)
	// Optional (n) Xn ∗ N − Xn + 1
	// Alternative n1|n2 Xn1 ∗ N1 − Xn1 ∗ Xn2 ∗ N2 + Xn2 ∗ N2
	// Try (n)?n1:n2 N ∗ N1 − N ∗ N2 + N2
	// Cardinality (n+k) (N)^k
	// Cardinality (n#k) (N)^k
	// Retry (n@k) 1 − (1 − N)^k+1

	private final String formula;

	private NodeType(String formula) {
		this.formula = formula;
	}

	public String getFormula() {
		return formula;
	}
}
