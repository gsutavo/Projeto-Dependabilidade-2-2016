package br.unb.GODA.SymbolicParametricFormulas.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import br.unb.GODA.SymbolicParametricFormulas.model.Tree;

public class ModelReader {

	// IDENTIFIER: DESCRIPTION [runtime annotation]
	public Tree readFile(String arquivo) {
		BufferedReader br = null;
		FileReader fr = null;

		Tree arvore = new Tree();

		try {
			fr = new FileReader(this.getClass().getResource(arquivo).getPath());
			br = new BufferedReader(fr);

			String linha;
			while ((linha = br.readLine()) != null) {
				arvore.addNode(linha);
			}
		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return arvore;
	}

}
