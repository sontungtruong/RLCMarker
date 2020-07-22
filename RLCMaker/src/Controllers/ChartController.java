package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;

import BackEnd.CircuitComponent;
import BackEnd.ComplexNumber;
import BackEnd.Graph;
import Models.ACpower;
import Models.Capacitor;
import Models.Inductor;
import Models.Resistor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ChartController implements Initializable {
	private MainController mainController;
	
	@FXML
	private LineChart<Double, Double> lineGraphU;
	@FXML
	private LineChart<Double, Double> lineGraphI;

	@FXML
	private NumberAxis YAxisU;
	@FXML
	private NumberAxis YAxisI;

	@FXML
	private Button PowerButton;
	@FXML
	private Button PowerButton2;
	
	@FXML
	private Button clearButton;
	@FXML
	private Button clearButton1;
	
	@FXML
	VBox VoltPane;
	@FXML
	VBox AmPane;
	
	private Graph UGraph;
	private Graph IGraph;

	@Override
	public void initialize(final URL url, final ResourceBundle rb) {
		UGraph = new Graph(lineGraphU, 50);
		IGraph = new Graph(lineGraphI, 50);
		for (int i=0; i<MainController.getComponent().size(); i++)
		{
			if (MainController.getComponent().get(i) instanceof Resistor ||MainController.getComponent().get(i) instanceof Capacitor|| MainController.getComponent().get(i) instanceof Inductor) {
				CircuitComponent component = (CircuitComponent) MainController.getComponent().get(i);
				addButtonVoltPane(component);
			}
		}
		
	}

	private void plotLine(Function<Double, Double> function, Graph x) {
			x.plotLine(function);
	}

	@FXML
	private void handleUButtonAction(final ActionEvent event) {
		ACpower acpower = mainController.getAcpower();
		ComplexNumber u = acpower.getU();
		double bound = u.module()*3;
		YAxisU.setUpperBound(bound);
		YAxisU.setLowerBound(-bound);
		plotLine(x -> (u.module())*Math.cos(2*acpower.getFrequency()*Math.PI*x/1000), UGraph);
	}
	
	@FXML
	private void handleIButtonAction(final ActionEvent event) {
		ACpower acpower = mainController.getAcpower();
		ComplexNumber i = acpower.getI();
		double bound = i.module()*3;
		YAxisI.setUpperBound(bound);
		YAxisI.setLowerBound(-bound);
		plotLine(x -> (i.module())*Math.cos(2*acpower.getFrequency()*Math.PI*x/1000 + i.trigonometricAngle()), IGraph);
	}
	

	@FXML
	private void handleClearButtonAction(final ActionEvent event) {
		clear();
	}
	

	private void clear() {
		if (lineGraphU.isVisible()) {
			UGraph.clear();
		} 
		if (lineGraphI.isVisible()) {
			IGraph.clear();
		} 
	}
	
	public void addButtonVoltPane(CircuitComponent component)
	{
		Button button = new Button(component.getName());
		button.setMinHeight(66);
		button.setMaxWidth(207);
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ACpower acpower = mainController.getAcpower();
				ComplexNumber u = component.getU();
				double bound = acpower.getU().module()*3/2;
				YAxisI.setUpperBound(bound);
				YAxisI.setLowerBound(-bound);
				plotLine(x -> (u.module())*Math.cos(2*acpower.getFrequency()*Math.PI*x/1000 + u.trigonometricAngle()), UGraph);
				
			}
			
		});
		VoltPane.getChildren().add(button);
	}
	
	
	public void setMain(MainController main)
	{
		this.mainController = main;
	}
}