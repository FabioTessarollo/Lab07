package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import it.polito.tdp.poweroutages.model.*;

public class PowerOutagesController {
	
	private Model model;
	private List<Nerc> nercList = new ArrayList<Nerc>();


    public void setModel(Model model) {
		this.model = model; 
		nercList = model.getNercList();
		choiseBox.getItems().addAll(nercList);
	}

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<Nerc> choiseBox;

    @FXML
    private TextField fieldYears;

    @FXML
    private TextField fieldHours;

    @FXML
    private Button btnAnalysis;

    @FXML
    private TextArea textResult;

    @FXML
    void doAnalysis(ActionEvent event) {
    	
    	textResult.appendText(model.analysis(Integer.valueOf(fieldYears.getText()), Integer.valueOf(fieldHours.getText()), choiseBox.getValue().getId()).toString());

    }

    @FXML
    void initialize() {
        assert choiseBox != null : "fx:id=\"choiseBox\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert fieldYears != null : "fx:id=\"fieldYears\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert fieldHours != null : "fx:id=\"fieldHours\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnAnalysis != null : "fx:id=\"btnAnalysis\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert textResult != null : "fx:id=\"textResult\" was not injected: check your FXML file 'PowerOutages.fxml'.";

    }
}
