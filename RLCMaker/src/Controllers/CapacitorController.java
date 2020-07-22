package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class CapacitorController {
	@FXML
	private ImageView Ainput, Binput, capacitor;

	@FXML
	private Label lbText;
	
	public ImageView getAInput() {
		return Ainput;
	}

	public ImageView getBInput() {
		return Binput;
	}

	public ImageView getCapacitor() {
		return capacitor;
	}
	public Label getLabel() {
		return lbText;
	}

	public String getText() {
		return lbText.getText();
	}

	public void setText(String Text) {
		this.lbText.setText(Text);
	}
	
	
	
}
