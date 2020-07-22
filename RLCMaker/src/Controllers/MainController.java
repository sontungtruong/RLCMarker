package Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import BackEnd.CircuitComponent;
import BackEnd.ComplexNumber;
import BackEnd.Equations;
import Models.ACpower;
import Models.Capacitor;
import Models.Inductor;
import Models.Key;
import Models.Resistor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController  {

    private DragEventHandler dragEventHandler ;

    private static List component = new ArrayList<CircuitComponent>();
    private ACpower power;
    @FXML
    private AnchorPane btnResistor,btnCapacitor,btnInductor, btnKey, btnACpower;
    @FXML
    private Pane pnlCanvas;
    
    @FXML
    private MenuItem btnRun;
    
    
    
    @FXML
    public void initialize() {
        this.dragEventHandler = new DragEventHandler(this);
        
        btnRun.setOnAction(new EventHandler<ActionEvent>() {
			private ChartController chartController;

			@Override
			public void handle(ActionEvent arg0) {
				
				//de nguon o cuoi mang
				for (int i=0; i<component.size(); i++)
				{
					if (component.get(i) instanceof ACpower) {
						System.out.println(i);
						power = (ACpower)component.get(i);
						component.remove(i);
						component.add(power);		
					}
				}
				if (power != null) {
				
					Equations equation = new Equations();				
					ComplexNumber M[][] = new ComplexNumber[100][100];
					for (int i = 0 ; i<100; i++)
					{
						for (int j=0; j<100; j++) {
							M[i][j] = new ComplexNumber(0);
						}
					}
					
					int k=1;
					for (int i=0 ; i<component.size(); i++)
					{
						try{
							if (component.get(i) instanceof Resistor)
							{
								Resistor resistor = (Resistor)component.get(i);
								M[0][i].setValue(new ComplexNumber(resistor.getValue()));
								M[k][i].setValue(1);
								M[k][search(resistor.getBNode().getName())].setValue(-1);
								k++;
							}
							if (component.get(i) instanceof Capacitor)
							{
								Capacitor capacitor = (Capacitor)component.get(i);
								M[0][i].setValue(new ComplexNumber(0,-(capacitor.getValue())));
								M[k][i].setValue(1);
								M[k][search(capacitor.getBNode().getName())].setValue(-1);
								k++;
							}
							if (component.get(i) instanceof Inductor)
							{
								Inductor inductor = (Inductor)component.get(i);
								M[0][i].setValue(new ComplexNumber(0, inductor.getValue()));
								M[k][i].setValue(1);
								M[k][search(inductor.getBNode().getName())].setValue(-1);
								k++;
							}
							if (component.get(i) instanceof Key)
							{
								Key key = (Key)component.get(i);
								M[0][i].setValue(new ComplexNumber(0, key.getValue()));
								M[k][i].setValue(1);
								M[k][search(key.getBNode().getName())].setValue(-1);
								k++;
							}
							if (component.get(i) instanceof ACpower)
							{
								System.out.println(i);
								ACpower acpower = (ACpower)component.get(i);
								M[0][i].setValue(new ComplexNumber(acpower.getValue()));
							}
						} catch (Exception e){
							 showAlertWithoutHeaderText("Please check the wire!"); 
							 return;
						}
					}
					
					ComplexNumber resultI = new ComplexNumber(0);
					equation.Handler(M, component.size()-1, component.size());
					equation.Result(M, component.size()-1, component.size(), resultI);
				
					getObject().power.setI(resultI);
					for (int i=0; i<component.size(); i++)
					{
						if (component.get(i) instanceof Resistor){
							CircuitComponent c = (CircuitComponent)component.get(i);
							c.setU(resultI.mul(c.getValue()));
						} else if (component.get(i) instanceof Capacitor) {
							CircuitComponent c = (CircuitComponent)component.get(i);
							c.setU(resultI.mul(new ComplexNumber(0, (-1)*(c.getValue()))));
						}else if (component.get(i) instanceof Inductor) {
							CircuitComponent c = (CircuitComponent)component.get(i);
							c.setU(resultI.mul(new ComplexNumber(0, c.getValue())));
						}
					}
					
					try {
						FXMLLoader loader = new FXMLLoader();
			        	loader.setLocation(this.getClass().getResource("../Views/Result.fxml"));
			        	Parent second = loader.load();
			        	Scene secondScene = new Scene(second, 1139, 656);
			        	this.chartController = loader.<ChartController>getController();
			        	chartController.setMain(getObject());
		                Stage newWindow = new Stage();
		                newWindow.setTitle("Result");
		                newWindow.setScene(secondScene);
		                newWindow.initModality(Modality.WINDOW_MODAL);
		                newWindow.initOwner(getCanvasPanel().getScene().getWindow());
		                newWindow.show();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					 showAlertWithoutHeaderText("Plesae add power!"); 
				}
				
				
				
			}
        });
    }

    @FXML
    public void newButtonAction() {
    	while(component.size()>0)
    	{
			if (component.get(0) instanceof Resistor)
			{
				((Resistor)component.get(0)).delete();
			}
			else if (component.get(0) instanceof Capacitor)
			{
				((Capacitor)component.get(0)).delete();
			}
			else if (component.get(0) instanceof Inductor)
			{
				((Inductor)component.get(0)).delete();
			}
			else if (component.get(0) instanceof Key)
			{
				((Key)component.get(0)).delete();
			}
			else if (component.get(0) instanceof ACpower)
			{
				((ACpower)component.get(0)).delete();
			}
			
		}
    }
    
    public DragEventHandler getDragEventHandler() {
		return this.dragEventHandler;
	}


	public AnchorPane getBtnResistor() {
		return this.btnResistor;
	}


	public AnchorPane getBtnCapacitor() {
		return this.btnCapacitor;
	}


	public AnchorPane getBtnInductor() {
		return this.btnInductor;
	}

	public AnchorPane getBtnKey() {
		return this.btnKey;
	}
	
	public AnchorPane getBtnACpower() {
		return this.btnACpower;
	}


	public Pane getCanvasPanel() {
        return this.pnlCanvas;
    }



	public static List getComponent() {
		return component;
	}

	public MainController getObject() {
		return this;
	}

	public ACpower getAcpower() {
		return power;
	}



	public int search(String name)
	{
		for (int i = 0; i<component.size(); i++)
		{
			CircuitComponent tmp = (CircuitComponent)component.get(i);
			if (tmp.getName().compareTo(name)==0)
				return i;
		}
		return -1;
	}
	 private void showAlertWithoutHeaderText(String text) {
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.setTitle("Warning");
	 
	        alert.setHeaderText(null);
	        alert.setContentText(text);
	 
	        alert.showAndWait();
	    }
	
}
