/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Adiacenza;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtPorzioni"
    private TextField txtPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnGrassi"
    private Button btnGrassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="boxFood"
    private ComboBox<Food> boxFood; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Creazione grafo...");
    	int porzioni;
    	try {
    		porzioni = Integer.parseInt(txtPorzioni.getText());
    	} catch (NumberFormatException e) {
    		txtResult.setText("Inserire una porzione valida\n");
    		e.printStackTrace();
    		return;
    	}
    	model.creaGrafo(porzioni);
    	List<Food> vertici = model.getVertici();
    	Collections.sort(vertici);
    	boxFood.getItems().setAll(vertici);
    }

    @FXML
    void doGrassi(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Analisi grassi...");
    	if (!model.grafoCreato()) {
    		txtResult.setText("Creare un grafo\n");
    		return;
    	}
    	Food cibo = boxFood.getValue();
    	if (cibo==null) {
    		txtResult.setText("Selezionare un cibo\n");
    		return;
    	}
    	for (Adiacenza a : model.doGrassi(cibo,5)) {
    		txtResult.appendText(a.getF2()+" "+a.getPeso()+"\n");
    	}
    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Simulazione...");
    	if (!model.grafoCreato()) {
    		txtResult.setText("Creare un grafo\n");
    		return;
    	}
    	Food cibo = boxFood.getValue();
    	if (cibo==null) {
    		txtResult.setText("Selezionare un cibo\n");
    		return;
    	}
    	int k;
    	try {
    		k = Integer.parseInt(txtK.getText());
    	} catch (NumberFormatException e) {
    		txtResult.setText("Inserire un valore k numerico\n");
    		e.printStackTrace();
    		return;
    	}
    	if (k<1 || k>10) {
    		txtResult.setText("Inserire un valore k compreso tra 1 e 10\n");
    		return;
    	}
    	model.simula(cibo,k);
    	txtResult.setText("Numero di cibi preparati: "+model.getSim().getCibiPreparati().size()+"\n");
    	txtResult.appendText("Tempo totale di preparazione: "+model.getSim().getDurata()+"\n");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtPorzioni != null : "fx:id=\"txtPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnGrassi != null : "fx:id=\"btnGrassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxFood != null : "fx:id=\"boxFood\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
