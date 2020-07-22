package Models;

import BackEnd.CircuitComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Wire extends Line {
	private static int count =0;
	//private double startX, startY, endX, endY;
	private CircuitComponent StartNode, EndNode;
	private int id;
	
	
	public Wire(double startX, double startY, double endX, double endY) {
		super(startX, startY, endX, endY);
		setStrokeWidth(3);
		setStroke(Color.BLACK);
        id = count++;
	}



	public CircuitComponent getStartNode() {
		return StartNode;
	}


	public void setStartNode(CircuitComponent startNode) {
		StartNode = startNode;
	}


	public CircuitComponent getEndNode() {
		return EndNode;
	}


	public void setEndNode(CircuitComponent endNode) {
		EndNode = endNode;
	}


	public int getID() {
		return id;
	}


	public void setID(int id) {
		this.id = id;
	}
	
	public boolean isEndNode(CircuitComponent node) {
		if (node.equals(EndNode)) return true;
		else return false;
		}
	
	
}
