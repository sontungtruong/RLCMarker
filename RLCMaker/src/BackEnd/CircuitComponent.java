package BackEnd;

import Constants.ComponentConstants;
import Models.Wire;
import javafx.scene.layout.AnchorPane;

public class CircuitComponent extends AnchorPane implements ComponentConstants{

    

    private CircuitComponent ANode;
    private CircuitComponent BNode;
    private ComplexNumber U = new ComplexNumber(0);
    private ComplexNumber I = new ComplexNumber(0);
    private Wire ALine;
    private Wire BLine;
    private String Type;
    private double value;
	private String name;


    
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public boolean hasANode() {
        if(ANode == null)
            return false;
        return true;
    }
    public boolean hasBNode() {
        if(BNode == null)
            return false;
        return true;
    }


    public void setANode(CircuitComponent inputANode) {
        this.ANode = inputANode;
    }
    public void setBNode(CircuitComponent inputBNode) {
        this.BNode = inputBNode;
    }
    public void setType(String Type) {
        this.Type = Type;
    }


    public void setALine(Wire aLine) {
		ALine = aLine;
	}

	public void setBLine(Wire bLine) {
		BLine = bLine;
	}

    public CircuitComponent getANode() {
        return ANode;
    }
    public CircuitComponent getBNode() {
        return BNode;
    }
     public String getType() {
        return Type;
    }
     
    public Wire getALine() {
    	return ALine;
    }
    
    public Wire getBLine() {
    	return BLine;
    }
    
	public ComplexNumber getU() {
		return U;
	}

	public void setU(ComplexNumber u) {
		this.U.setValue(u);
	}

	public ComplexNumber getI() {
		return I;
	}

	public void setI(ComplexNumber i) {
		this.I.setValue(i);
	}
    
}

