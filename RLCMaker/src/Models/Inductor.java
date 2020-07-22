package Models;

import java.io.IOException;

import BackEnd.CircuitComponent;
import Controllers.InductorController;
import Controllers.InductorValueController;
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
import javafx.scene.transform.Rotate;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Inductor extends CircuitComponent{
	private static int count = 0;
	private InductorController inductorController;
	
	public Inductor() {
		count++;
		setName("L" +count);
		setValue(0);
		try {
			FXMLLoader loader = new FXMLLoader();
			
			loader.setLocation(this.getClass().getResource("../Views/Inductor.fxml"));
			Parent root = loader.load();
			
			this.inductorController = loader.<InductorController>getController();
			inductorController.setText(getName());
			ContextMenu contextMenu = new ContextMenu();
			 
		       MenuItem item2 = new MenuItem("Set Value");
		       item2.setOnAction(new EventHandler<ActionEvent>() {
		    	   private InductorValueController inductorValueController;

		           @Override
		           public void handle(ActionEvent event) {
						try {
							FXMLLoader loader = new FXMLLoader();
				        	loader.setLocation(this.getClass().getResource("../Views/InductorValue.fxml"));
				        	Parent second = loader.load();
				        	Scene secondScene = new Scene(second, 300, 142);
				        	this.inductorValueController = loader.<InductorValueController>getController();
				        	inductorValueController.setName(getName());
				        	inductorValueController.setValue(getValue());
				        	inductorValueController.setObject(getObject());
			                Stage newWindow = new Stage();
			                newWindow.setTitle("Inductor Value");
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
		  
		       ImageView inductor = inductorController.getInductor();
		       inductor.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
		 
		           @Override
		           public void handle(ContextMenuEvent event) {
		 
		               contextMenu.show(inductor, event.getScreenX(), event.getScreenY());
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
	
	public ImageView getInductor() {
		return inductorController.getInductor();
	}
	
	public ImageView getAInputImage() {
		return inductorController.getAInput();
	}
	
	public ImageView getBInputImage() {
		return inductorController.getBInput();
	}
	
	public Inductor getObject() {
		return this;
	}
	public InductorController getController() {
		return this.inductorController;
	}
	
	public void delete() {
		for (int i=0; i<MainController.getComponent().size(); i++)
		{
			if (MainController.getComponent().get(i) instanceof Inductor)
			{
				Inductor inductor = (Inductor)MainController.getComponent().get(i);
				if (inductor.getName().compareTo(getName()) == 0)
				MainController.getComponent().remove(i);
			}
		}
		getInductor().setVisible(false);
		getAInputImage().setVisible(false);
		getBInputImage().setVisible(false);
		inductorController.setText(null);
		if (getALine()!=null) this.getALine().setVisible(false);
		if (getBLine()!=null) this.getBLine().setVisible(false);
	}
}
