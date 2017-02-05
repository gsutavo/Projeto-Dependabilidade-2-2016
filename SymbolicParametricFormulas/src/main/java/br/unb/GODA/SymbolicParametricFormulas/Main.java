package br.unb.GODA.SymbolicParametricFormulas;

import br.unb.GODA.SymbolicParametricFormulas.model.Tree;
import br.unb.GODA.SymbolicParametricFormulas.util.ModelReader;

public class Main {

	public static void main(String[] args) {

		readFileTest("/modelo_basico_TRY.txt");
		readFileTest("/modelo_basico_RETRY.txt");
		readFileTest("/modelo_basico_ALTERNATIVE.txt");
		readFileTest("/modelo_basico_AND.txt");
		readFileTest("/modelo_basico_CARDINALITY+.txt");
		readFileTest("/modelo_basico_CARDINALITY_hash.txt");
		readFileTest("/modelo_basico_OR.txt");
		
	}

	private static void readFileTest(String file) {
		Tree t = new ModelReader().readFile(file);
//		t.printTree();
//		System.out.println("\n-------\n");
		t.printGlobalIds();
		System.out.println("\n-------\n");
		System.out.println( t.getFormula());
		System.out.println("\n=================================================\n");
	}

}
