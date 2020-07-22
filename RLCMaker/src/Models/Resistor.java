package Models;

import java.io.IOException;

import BackEnd.CircuitComponent;
import Controllers.MainController;
import Controllers.ResistorController;
import Controllers.ResistorValueController;
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
import javafx.scene.transform.Rotate;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Resistor extends CircuitComponent{
	private static int count = 0;
	private ResistorController resistorController;
	
	public Resistor() {
		count++;
		setName("R" +count);
		setValue(0);
		try {
			FXMLLoader loader = new FXMLLoader();
			
			loader.setLocation(this.getClass().getResource("../Views/Resistor.fxml"));
			Parent root = loader.load();
			
			this.resistorController = loader.<ResistorController>getController();
			resistorController.setText(getName());
			
	
	       ContextMenu contextMenu = new ContextMenu();
	 
	     
	       
	       
	       MenuItem item2 = new MenuItem("Set Value");
	       item2.setOnAction(new EventHandler<ActionEvent>() {
	    	   private ResistorValueController resistorValueController;

	           @Override
	           public void handle(ActionEvent event) {
					try {
						FXMLLoader loader = new FXMLLoader();
			        	loader.setLocation(this.getClass().getResource("../Views/ResistorValue.fxml"));
			        	Parent second = loader.load();
			        	Scene secondScene = new Scene(second, 300, 142);
			        	this.resistorValueController = loader.<ResistorValueController>getController();
			        	resistorValueController.setName(getName());
			        	resistorValueController.setValue(getValue());
			        	resistorValueController.setObject(getObject());
		                Stage newWindow = new Stage();
		                newWindow.setTitle("Resistor Value");
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
	  
	       ImageView resistor = resistorController.getResistor();
	       resistor.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
	 
	           @Override
	           public void handle(ContextMenuEvent event) {
	 
	               contextMenu.show(resistor, event.getScreenX(), event.getScreenY());
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
	
	public ImageView getResistor() {
		return resistorController.getResistor();
	}
	
	public ImageView getAInputImage() {
		return resistorController.getAInput();
	}
	
	public ImageView getBInputImage() {
		return resistorController.getBInput();
	}
	
	
	public Resistor getObject() {
		return this;
	}
	public ResistorController getController() {
		return this.resistorController;
	}

	public void delete() {
		for (int i=0; i<MainController.getComponent().size(); i++)
		{
			if (MainController.getComponent().get(i) instanceof Resistor)
			{
				Resistor resistor = (Resistor)MainController.getComponent().get(i);
				if (resistor.getName().compareTo(getName()) == 0)
				MainController.getComponent().remove(i);
			}
		}
		getResistor().setVisible(false);
		getAInputImage().setVisible(false);
		getBInputImage().setVisible(false);
		resistorController.setText(null);
		if (getALine()!=null) getALine().setVisible(false);
		if (getBLine()!=null) getBLine().setVisible(false);
	}
}
