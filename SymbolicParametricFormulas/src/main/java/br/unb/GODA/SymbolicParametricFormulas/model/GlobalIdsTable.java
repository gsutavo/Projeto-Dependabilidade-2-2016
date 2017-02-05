package br.unb.GODA.SymbolicParametricFormulas.model;

import java.util.HashMap;
import java.util.Map;

public class GlobalIdsTable {

	private String ids = "ABCDEFGHIJKLMNOPQRSTUVXWYZ";
	
	private Map<String,String> globalIds = new HashMap<String,String>();

	public String addId(String id) {
		String globalId = getNextId();
		globalIds.put(globalId, id);
		return globalId;
	}

	private String getNextId() {
		return new Character(ids.charAt(globalIds.size())).toString();
	}

	public void print() {
		for(String key:globalIds.keySet()){
			System.out.println(key+" - "+globalIds.get(key));
		}
		
	} 
}
