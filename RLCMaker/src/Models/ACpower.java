package Models;

import java.io.IOException;

import BackEnd.CircuitComponent;
import Controllers.ACpowerController;
import Controllers.ACpowerValueController;
import Controllers.MainController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ACpower extends CircuitComponent{
	private static int count = 0;
	private ACpowerController acpowerController;
	
	private int frequency;
	
	public ACpower() {
		count++;
		setName("U" +count);
		setValue(0);
		setFrequency(50);
		try {
			FXMLLoader loader = new FXMLLoader();
			
			loader.setLocation(this.getClass().getResource("../Views/ACpower.fxml"));
			Parent root = loader.load();
			
			this.acpowerController = loader.<ACpowerController>getController();
			acpowerController.setText(getName());
			
			ContextMenu contextMenu = new ContextMenu();
			 
		     
		       MenuItem item2 = new MenuItem("Set Value");
		       item2.setOnAction(new EventHandler<ActionEvent>() {
		    	   private ACpowerValueController acpowerValueController;

		           @Override
		           public void handle(ActionEvent event) {
						try {
							FXMLLoader loader = new FXMLLoader();
				        	loader.setLocation(this.getClass().getResource("../Views/ACpowerValue.fxml"));
				        	Parent second = loader.load();
				        	Scene secondScene = new Scene(second, 300, 142);
				        	this.acpowerValueController = loader.<ACpowerValueController>getController();
				        	acpowerValueController.setName(getName());
				        	acpowerValueController.setValue(getValue());
				        	acpowerValueController.setFrequency(frequency);
				        	acpowerValueController.setObject(getObject());
			                Stage newWindow = new Stage();
			                newWindow.setTitle("AC Value");
			                newWindow.setScene(secondScene);
			                newWindow.initModality(Modality.WINDOW_MODAL);
			                newWindow.initOwner(root.getScene().getWindow());
			                newWindow.show();
						} catch (IOException e) {
							e.printStackTrace();
						}
		            }
		       });

		       MenuItem item3 = new MenuItem("Delete");
		       item3.setOnAction(new EventHandler<ActionEvent>() {
		 
		           @Override
		           public void handle(ActionEvent event) {
		               delete();
		           }
		       });
		       
		       
		       contextMenu.getItems().addAll( item2, item3);
		  
		       ImageView acpower = acpowerController.getACpower();
		       acpower.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
		 
		           @Override
		           public void handle(ContextMenuEvent event) {
		 
		               contextMenu.show(acpower, event.getScreenX(), event.getScreenY());
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
	
	public ImageView getACpower() {
		return acpowerController.getACpower();
	}
	
	public ImageView getAInputImage() {
		return acpowerController.getAInput();
	}
	
	public ImageView getBInputImage() {
		return acpowerController.getBInput();
	}
	
	
	
	public int getFrequency() {
		return frequency;
	}
	
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	
	


	public ACpower getObject() {
		return this;
	}
	public ACpowerController getController() {
		return this.acpowerController;
	}
	
	public void delete() {
		for (int i=0; i<MainController.getComponent().size(); i++)
		{
			if (MainController.getComponent().get(i) instanceof ACpower)
			{
				ACpower acpower = (ACpower)MainController.getComponent().get(i);
				if (acpower.getName().compareTo(getName()) == 0)
				MainController.getComponent().remove(i);
			}
		}
		getACpower().setVisible(false);
		getAInputImage().setVisible(false);
		getBInputImage().setVisible(false);
		acpowerController.setText(null);
		if (getALine()!=null) getALine().setVisible(false);
		if (getBLine()!=null) getBLine().setVisible(false);
	}
}
