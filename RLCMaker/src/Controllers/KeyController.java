package Controllers;

import Models.Key;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class KeyController {
	private Key K;
	@FXML
	private ImageView Ainput, Binput, key, key2;

	public ImageView getAInput() {
		return Ainput;
	}

	public ImageView getBInput() {
		return Binput;
	}

	public ImageView getKey() {
		return key;
	}
	
	public ImageView getKey2() {
		return key2;
	}
	
	@FXML
	public void initialize() {
		
		key.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				key.setVisible(false);
				key2.setVisible(true);
				K.setStatus(true);
				K.setValue(0);
			}
		});
		
		
		key2.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				key.setVisible(true);
				key2.setVisible(false);	
				K.setStatus(false);
				K.setValue(Double.MAX_VALUE);
			}
		});
	}
	
	public void setObject(Key K) {
		this.K = K;
	}
}
