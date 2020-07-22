package Models;

import java.io.IOException;

import BackEnd.CircuitComponent;
import Controllers.CapacitorController;
import Controllers.CapacitorValueController;
import Controllers.MainController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Capacitor extends CircuitComponent	{
	private static int count = 0;
	private CapacitorController capacitorController;
	public Capacitor() {
		try {
			count++;
			setName("C" +count);
			setValue(0);
			FXMLLoader loader = new FXMLLoader();
			
			loader.setLocation(this.getClass().getResource("../Views/Capacitor.fxml"));
			Parent root = loader.load();
			
			this.capacitorController = loader.<CapacitorController>getController();
			capacitorController.setText(getName());
			
			
			ContextMenu contextMenu = new ContextMenu();
			 
		       
		       MenuItem item2 = new MenuItem("Set Value");
		       item2.setOnAction(new EventHandler<ActionEvent>() {
		    	   private CapacitorValueController capacitorValueController;
		           @Override
		           public void handle(ActionEvent event) {
		        	   try {
							FXMLLoader loader = new FXMLLoader();
				        	loader.setLocation(this.getClass().getResource("../Views/CapacitorValue.fxml"));
				        	Parent second = loader.load();
				        	Scene secondScene = new Scene(second, 300, 142);
				        	this.capacitorValueController = loader.<CapacitorValueController>getController();
				        	capacitorValueController.setName(getName());
				        	capacitorValueController.setValue(getValue());
				        	capacitorValueController.setObject(getObject());
			                Stage newWindow = new Stage();
			                newWindow.setTitle("Capacitor Value");
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
		  
		       ImageView capacitor =capacitorController.getCapacitor();
		       capacitor.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
		 
		           @Override
		           public void handle(ContextMenuEvent event) {
		 
		               contextMenu.show(capacitor, event.getScreenX(), event.getScreenY());
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
	
	public ImageView getCapacitor() {
		return capacitorController.getCapacitor();
	}
	
	public ImageView getAInputImage() {
		return capacitorController.getAInput();
	}
	
	public ImageView getBInputImage() {
		return capacitorController.getBInput();
	}
	
	public Label getLabel() {
		return capacitorController.getLabel();
	}
	public Capacitor getObject() {
		return this;
	}
	public CapacitorController getController() {
		return this.capacitorController;
	}
	
	public void delete() {
		for (int i=0; i<MainController.getComponent().size(); i++)
		{
			if (MainController.getComponent().get(i) instanceof Capacitor)
			{
				Capacitor capacitor = (Capacitor)MainController.getComponent().get(i);
				if (capacitor.getName().compareTo(getName()) == 0)
				MainController.getComponent().remove(i);
			}
		}
		getCapacitor().setVisible(false);
		getAInputImage().setVisible(false);
		getBInputImage().setVisible(false);
		getLabel().setVisible(false);
		if (getALine() != null) getALine().setVisible(false);
		if (getBLine()!=null) getBLine().setVisible(false);
	}
}
