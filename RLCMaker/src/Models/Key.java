package Models;

import java.io.IOException;

import BackEnd.CircuitComponent;
import Controllers.KeyController;
import Controllers.MainController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;

public class Key extends CircuitComponent{
	private static int count = 0;
	private KeyController keyController;
	private boolean status; // false is open, true is close
	
	public Key() {
		count++;
		setName("K" +count);
		status = false;
		setValue(Double.MAX_VALUE);
		try {
			FXMLLoader loader = new FXMLLoader();
			
			loader.setLocation(this.getClass().getResource("../Views/Key.fxml"));
			Parent root = loader.load();
			
			this.keyController = loader.<KeyController>getController();
			keyController.setObject(this);

			getKey2().setVisible(false);
			ContextMenu contextMenu = new ContextMenu();
		
		       MenuItem item2 = new MenuItem("Set Value");
		       item2.setOnAction(new EventHandler<ActionEvent>() {
		 
		           @Override
		           public void handle(ActionEvent event) {
		               //label.setText("Select Menu Item 2");
		           }
		       });

		       MenuItem item3 = new MenuItem("Delete");
		       item3.setOnAction(new EventHandler<ActionEvent>() {
		 
		           @Override
		           public void handle(ActionEvent event) {
		               delete();
		           }
		       });
		       
		       
		       contextMenu.getItems().addAll( item3);
		  
		       ImageView key = keyController.getKey();
		       key.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
		 
		           @Override
		           public void handle(ContextMenuEvent event) {
		 
		               contextMenu.show(key, event.getScreenX(), event.getScreenY());
		           }
		       });
			AnchorPane.setTopAnchor(root, 0.0);
			AnchorPane.setLeftAnchor(root, 0.0);
			this.getChildren().add(root);
			
		} catch (IOException e)
		{
			System.out.println("e.getMessage() = " + e.getMessage());
		}
	}
	
	
	public boolean getStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}


	public ImageView getKey() {
		return keyController.getKey();
	}
	
	public ImageView getKey2() {
		return keyController.getKey2();
	}
	
	public ImageView getAInputImage() {
		return keyController.getAInput();
	}
	
	public ImageView getBInputImage() {
		return keyController.getBInput();
	}
	
	public void delete() {
		for (int i=0; i<MainController.getComponent().size(); i++)
		{
			if (MainController.getComponent().get(i) instanceof Key)
			{
				Key key = (Key)MainController.getComponent().get(i);
				if (key.getName().compareTo(getName()) == 0)
				MainController.getComponent().remove(i);
			}
		}
		getKey().setVisible(false);
		getKey2().setVisible(false);
		getAInputImage().setVisible(false);
		getBInputImage().setVisible(false);
		if (getALine()!=null) getALine().setVisible(false);
		if (getBLine()!=null) getBLine().setVisible(false);
	}
}
