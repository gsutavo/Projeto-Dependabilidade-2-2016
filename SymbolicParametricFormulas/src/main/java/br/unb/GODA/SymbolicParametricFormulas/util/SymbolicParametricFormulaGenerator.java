package br.unb.GODA.SymbolicParametricFormulas.util;

public class SymbolicParametricFormulaGenerator {
	
	//TODO remover?

	// Sequential AND (n1;n2) N1 ∗ N2
	public static String generateSequencialAND(String n1, String n2) {
		return n1 + "*" + n2;
	}

	// Sequential OR (n1;n2) MAX(N1, N2)
	public static String generateSequencialOR(String n1, String n2) {
		return "MAX(" + n1 + "," + n2 + ")";
	}

	// Parallel AND (n1#n2) N1 ∗ N2
	public static String generateParallelAND(String n1, String n2) {
		return generateSequencialAND(n1, n2);
	}

	// Parallel OR (n1#n2) MAX(N1, N2)
	public static String generateParallelOR(String n1, String n2) {
		return generateSequencialOR(n1, n2);
	}

	// Optional (n) Xn ∗ N − Xn + 1
	public static String generateOptional(String n) {
		return "Xn ∗ " + n + " − Xn + 1";
	}

	// Alternative n1|n2 Xn1 ∗ N1 − Xn1 ∗ Xn2 ∗ N2 + Xn2 ∗ N2
	public static String generateAlternative(String n1, String n2) {
		return "Xn1 ∗ " + n1 + " − Xn1 ∗ Xn2 ∗ " + n2 + " + Xn2 ∗ " + n2;
	}

	// Try (n)?n1:n2 N ∗ N1 − N ∗ N2 + N2
	public static String generateTry(String n, String n1, String n2) {
		return n + "∗ " + n1 + " − " + n + " ∗ " + n2 + " + " + n2;
	}

	// Cardinality (n+k) (N)^k
	// Cardinality (n#k) (N)^k
	public static String generateCardinality(String n, int k) {
		return "(" + n + ")^" + k;
	}

	// Retry (n@k) 1 − (1 − N)^k+1
	public static String generateRetry(String n, int k) {
		return "1-(1-" + n + ")^" + (k + 1);
	}

}
