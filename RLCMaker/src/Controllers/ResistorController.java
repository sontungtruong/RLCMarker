package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ResistorController {
	@FXML
	private ImageView Ainput, Binput, resistor;
	@FXML
	private Label lbText;

	public ImageView getAInput() {
		return Ainput;
	}

	public ImageView getBInput() {
		return Binput;
	}

	public ImageView getResistor() {
		return resistor;
	}
	

	public String getText() {
		return lbText.getText();
	}

	public void setText(String Text) {
		this.lbText.setText(Text);
	}
	
	
	
}
