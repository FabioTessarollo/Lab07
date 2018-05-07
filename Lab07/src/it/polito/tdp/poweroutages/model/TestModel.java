package it.polito.tdp.poweroutages.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		System.out.println(model.getNercList());
		System.out.println(model.analysis(4, 200, 3).toString());
	}
}
