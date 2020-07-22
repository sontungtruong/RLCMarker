package Controllers;

import BackEnd.ComplexNumber;
import Models.ACpower;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ACpowerValueController{
	private ACpower u;
	private String name;
	private double value;
	private int frequency;

	@FXML
	private TextField tfName, tfValue, tfFrequency;
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
				frequency = Integer.parseInt(tfFrequency.getText());
				u.setName(name);
				u.setValue(value);
				u.setFrequency(frequency);
				u.setU(new ComplexNumber(u.getValue(),0));
				u.getController().setText(name);
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
	
	public void setFrequency(int frequency)
	{
		this.tfFrequency.setText(""+ frequency);
	}
	


	public String getName() {
		return this.name;
	}
	
	public double getValue() {
		return this.value;
	}
	
	public int getFrequency() {
		return this.frequency;
	}
	
	public void setObject(ACpower u) {
		this.u = u;
	}
}
