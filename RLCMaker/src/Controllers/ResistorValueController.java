package Controllers;

import Models.Resistor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ResistorValueController{
	private Resistor r;
	private String name;
	private double value;

	@FXML
	private TextField tfName, tfValue;
	@FXML
	private Button btnOK;
	
	@FXML
    public void initialize() {
		btnOK.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				name = tfName.getText();
				System.out.println(name);
				value = Double.parseDouble(tfValue.getText());
				r.setName(name);
				r.setValue(value);
				r.getController().setText(name);
				Stage stage = (Stage)btnOK.getScene().getWindow();
				stage.close();
			}
			
		});
    }
	
	
	public void setName(String name) {
		this.tfName.setText(name);
	}


	public void setValue(double value) {
		this.tfValue.setText(""+value);
	}


	public String getName() {
		return this.name;
	}
	
	public double getValue() {
		return this.value;
	}
	
	public void setObject(Resistor r) {
		this.r = r;
	}
}
