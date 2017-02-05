package porto.SymbolicParametricFormulas;

import br.unb.GODA.SymbolicParametricFormulas.util.SymbolicParametricFormulaGenerator;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class SymbolicParametricFormulaGeneratorTest extends TestCase {
	public SymbolicParametricFormulaGeneratorTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(SymbolicParametricFormulaGeneratorTest.class);
	}
	
	public void testGenerate() {
		System.out.println(SymbolicParametricFormulaGenerator.generateSequencialAND("n1", "n2"));
		System.out.println(SymbolicParametricFormulaGenerator.generateSequencialOR("n1", "n2"));
		System.out.println(SymbolicParametricFormulaGenerator.generateParallelAND("n1", "n2"));
		System.out.println(SymbolicParametricFormulaGenerator.generateParallelOR("n1", "n2"));
		System.out.println(SymbolicParametricFormulaGenerator.generateOptional("n"));
		System.out.println(SymbolicParametricFormulaGenerator.generateAlternative("n1", "n2"));
		System.out.println(SymbolicParametricFormulaGenerator.generateTry("n", "n1", "n2"));
		System.out.println(SymbolicParametricFormulaGenerator.generateCardinality("n", 3));
		System.out.println(SymbolicParametricFormulaGenerator.generateRetry("n", 3));
		assert true;
	}
	
	
	
}
