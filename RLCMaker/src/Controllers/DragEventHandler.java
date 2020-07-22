package Controllers;


import BackEnd.CircuitComponent;
import Constants.Constants;
import Constants.DragConstants;
import Models.ACpower;
import Models.Capacitor;
import Models.Inductor;
import Models.Key;
import Models.Resistor;
import Models.Wire;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class DragEventHandler implements Constants, DragConstants {

    private MainController mainController;
    private Node currentComponent;
    private AnchorPane sourceComponent;
    private boolean newComponent;
    private Pane canvasPanel;
    public DragEventHandler(final MainController mainController){
        this.mainController  = mainController;
        this.currentComponent = null;
        this.newComponent = false;
        this.canvasPanel = mainController.getCanvasPanel();
        addListeners();
    }
    private void addListeners() {
        mainController.getBtnResistor().setOnDragDetected(event -> paletteDragDetected(event, TYPE_RESISTOR));
        mainController.getBtnCapacitor().setOnDragDetected(event -> paletteDragDetected(event, TYPE_CAPACITOR));
        mainController.getBtnInductor().setOnDragDetected(event -> paletteDragDetected(event, TYPE_INDUCTOR));
        mainController.getBtnKey().setOnDragDetected(event -> paletteDragDetected(event, TYPE_KEY));
        mainController.getBtnACpower().setOnDragDetected(event -> paletteDragDetected(event, TYPE_ACPOWER));
        mainController.getCanvasPanel().setOnMouseDragEntered(this::canvasDragEntered);
        mainController.getCanvasPanel().setOnMouseDragExited(this::canvasDragExited);
        mainController.getCanvasPanel().setOnMouseDragOver(this::canvasDragOver);
    }

    public void paletteDragDetected(MouseEvent mouseEvent, String componentType) {
        ((Node)mouseEvent.getSource()).startFullDrag();
        this.currentComponent = generateRequiredComponent(componentType);
        this.newComponent = false;
    }

    public void canvasDragEntered(MouseDragEvent dragEvent) {
        if(!this.canvasPanel.getChildren().contains(currentComponent))
            this.canvasPanel.getChildren().add(currentComponent);
    }
    public void canvasDragOver(MouseEvent mouseEvent) {
        this.setPosition(mouseEvent,currentComponent);
    }

   

    public void canvasDragExited(MouseDragEvent dragEvent) {
        if(!newComponent)
        addListenersToComponent((AnchorPane) currentComponent);
        System.out.println(MainController.getComponent().size());
        this.newComponent = false;
        this.currentComponent = null;
    }

    public void componentDragDetected(MouseEvent mouseEvent) {
        Object object = mouseEvent.getSource();
        if(object instanceof Node) {
        	Node component = ((Node)object);
            getNodeParent(component).startFullDrag();
            System.out.println("componentDragDetected checking the Parent node : " + getNodeParent(component));
            this.currentComponent = component.getParent().getParent();
            this.newComponent = true;
        }
    }
    
    
    private Node generateRequiredComponent(String componentType) {
    	CircuitComponent component;
        switch (componentType) {
            case TYPE_RESISTOR :
            	component = new Resistor();
            	MainController.getComponent().add(component);
                return component;

            case TYPE_CAPACITOR:
            	component = new Capacitor();
            	MainController.getComponent().add(component);
                return component;

            case TYPE_INDUCTOR:
            	component = new Inductor();
            	MainController.getComponent().add(component);
                return component;

            case TYPE_KEY:
            	component = new Key();
            	MainController.getComponent().add(component);
                return component;
                
            case TYPE_ACPOWER:
            	component = new ACpower();
            	MainController.getComponent().add(component);
                return component;

            default:
                return null;
        }
    }

    
    
    
    
    //     Line
    public void outputDragDetected(MouseEvent mouseEvent) {
        Node component = ((Node)mouseEvent.getSource());
        Dragboard dragboard = component.getParent().getParent().startDragAndDrop(TransferMode.MOVE);
        ClipboardContent clipboardContent = new ClipboardContent();

        double x = component.getLayoutX() + component.getParent().getParent().getLayoutX() + component.getLayoutBounds().getWidth()/2;
        double y = component.getLayoutY() + component.getParent().getParent().getLayoutY() + component.getLayoutBounds().getHeight()/2;
      
        this.currentComponent = createWire( x,y,x,y);
     
        this.sourceComponent = (AnchorPane)getNodeParent(mouseEvent.getSource());

       mapLineTo(getNodeParent(mouseEvent.getSource()),(Wire)(this.currentComponent), ((Node) mouseEvent.getSource()).getId());


        clipboardContent.putString("String");
        dragboard.setContent(clipboardContent);
        setDragView(dragboard);
    }

    public void inputDragEntered(DragEvent dragEvent) {
        if(this.currentComponent instanceof Line) {
            setLinePosition((Node)dragEvent.getSource(), (Wire)this.currentComponent);

            if(!this.canvasPanel.getChildren().contains(this.currentComponent))
                this.canvasPanel.getChildren().add(this.currentComponent);
        }
    }

    public void inputDragOver(DragEvent dragEvent) {
        if(this.currentComponent instanceof Line) {
            mapLineTo(getNodeParent(dragEvent.getSource()), (Wire)this.currentComponent, ((Node)dragEvent.getSource()).getId());
            System.out.println("InputDragOver checking FXID : " + ((Node) dragEvent.getSource()).getId());
            mapNodeTo(sourceComponent,getNodeParent(dragEvent.getSource()),((Node) dragEvent.getSource()).getId());

            ((Line)this.currentComponent).setStroke(Color.BLACK);

            setLinePosition((Node)dragEvent.getSource(),(Wire)this.currentComponent);
            if(!this.canvasPanel.getChildren().contains(this.currentComponent))
                this.canvasPanel.getChildren().add(this.currentComponent);
            dragEvent.acceptTransferModes(TransferMode.MOVE);
        }
    }

    public void inputDragExited(DragEvent dragEvent) {
    }


    private void setPosition(final MouseEvent mouseEvent,
                             final Node node) {
        if(node instanceof AnchorPane) {
            AnchorPane anchorPane = (AnchorPane)node;
            double layoutX = mouseEvent.getX() - anchorPane.getWidth() / 2;
            double layoutY = mouseEvent.getY() - anchorPane.getHeight() / 2;
            anchorPane.setLayoutX(layoutX);
            anchorPane.setLayoutY(layoutY);
            if(node instanceof Resistor) {
                Resistor resistor = (Resistor)node;
                adjustLine(resistor.getBLine(),layoutX,layoutY,resistor.getBInputImage(),false);
                adjustLine(resistor.getALine(),layoutX,layoutY,resistor.getAInputImage(),true);
            } else if(node instanceof Capacitor) {
                Capacitor capacitor = (Capacitor)node;
                adjustLine(capacitor.getBLine(),layoutX,layoutY,capacitor.getBInputImage(),false);
                adjustLine(capacitor.getALine(),layoutX, layoutY,capacitor.getAInputImage(),true);
            } else if(node instanceof Inductor){
            	Inductor inductor = (Inductor) node;
                adjustLine(inductor.getBLine(),layoutX,layoutY,inductor.getBInputImage(),false);
                adjustLine(inductor.getALine(),layoutX,layoutY,inductor.getAInputImage(),true);
            
            } else if(node instanceof Key){
            	Key key = (Key) node;
                adjustLine(key.getBLine(),layoutX,layoutY,key.getBInputImage(),false);
                adjustLine(key.getALine(),layoutX,layoutY,key.getAInputImage(),true);
            } else if(node instanceof ACpower) {
            	ACpower acpower = (ACpower) node;
            	adjustLine(acpower.getBLine(),layoutX,layoutY,acpower.getBInputImage(),false);
                adjustLine(acpower.getALine(),layoutX,layoutY,acpower.getAInputImage(),true);
            }
        }
    }

    private void adjustLine(final Line lineToAdjust,
                            final double x,
                            final double y,
                            final ImageView targetToAdjustWith,
                            final boolean isEnd) {
        if(lineToAdjust == null)
            return;
        if(isEnd) {
            lineToAdjust.setEndX(x + targetToAdjustWith.getLayoutX() + targetToAdjustWith.getLayoutBounds().getWidth()/2);
            lineToAdjust.setEndY(y + targetToAdjustWith.getLayoutY() + targetToAdjustWith.getLayoutBounds().getHeight()/2);
        } else {
            lineToAdjust.setStartX(x + targetToAdjustWith.getLayoutX() + targetToAdjustWith.getLayoutBounds().getWidth()/2);
            lineToAdjust.setStartY(y + targetToAdjustWith.getLayoutY() + targetToAdjustWith.getLayoutBounds().getHeight()/2);
        }
    }

    private void setDragView(Dragboard dragboard) {
        dragboard.setDragView(new Image("resources/icons/drag-image.png"));
    }

    private void addListenersToComponent(AnchorPane currentComponent) {
        if(currentComponent == null) {
            return;
        }
        System.out.println("Listeners Added");
        if(currentComponent instanceof Resistor) {
        	Resistor resistor = (Resistor)currentComponent;
        	resistor.getResistor().setOnDragDetected(this::componentDragDetected);
        	
            resistor.getAInputImage().setOnDragDetected(this::outputDragDetected);
            resistor.getAInputImage().setOnDragOver(this::inputDragOver);
            resistor.getAInputImage().setOnDragEntered(this::inputDragEntered);
            resistor.getAInputImage().setOnDragExited(this::inputDragExited);
            
            resistor.getBInputImage().setOnDragDetected(this::outputDragDetected);
            resistor.getBInputImage().setOnDragOver(this::inputDragOver);
            resistor.getBInputImage().setOnDragEntered(this::inputDragEntered);
            resistor.getBInputImage().setOnDragExited(this::inputDragExited);
        } else if(currentComponent instanceof Capacitor) {
        	Capacitor capacitor = (Capacitor)currentComponent;
        	capacitor.getCapacitor().setOnDragDetected(this::componentDragDetected);
        	
        	capacitor.getAInputImage().setOnDragDetected(this::outputDragDetected);
        	capacitor.getAInputImage().setOnDragOver(this::inputDragOver);
        	capacitor.getAInputImage().setOnDragEntered(this::inputDragEntered);
        	capacitor.getAInputImage().setOnDragExited(this::inputDragExited);
            
        	capacitor.getBInputImage().setOnDragDetected(this::outputDragDetected);
        	capacitor.getBInputImage().setOnDragOver(this::inputDragOver);
        	capacitor.getBInputImage().setOnDragEntered(this::inputDragEntered);
        	capacitor.getBInputImage().setOnDragExited(this::inputDragExited);
        } else if(currentComponent instanceof Inductor) {
        	Inductor inductor = (Inductor)currentComponent;
        	inductor.getInductor().setOnDragDetected(this::componentDragDetected);
        	
        	inductor.getAInputImage().setOnDragDetected(this::outputDragDetected);
        	inductor.getAInputImage().setOnDragOver(this::inputDragOver);
        	inductor.getAInputImage().setOnDragEntered(this::inputDragEntered);
        	inductor.getAInputImage().setOnDragExited(this::inputDragExited);
            
        	inductor.getBInputImage().setOnDragDetected(this::outputDragDetected);
        	inductor.getBInputImage().setOnDragOver(this::inputDragOver);
        	inductor.getBInputImage().setOnDragEntered(this::inputDragEntered);
        	inductor.getBInputImage().setOnDragExited(this::inputDragExited);
        
        }else if(currentComponent instanceof Key) {
        	Key key = (Key)currentComponent;
        	key.getKey().setOnDragDetected(this::componentDragDetected);
        	key.getKey2().setOnDragDetected(this::componentDragDetected);
        	
        	key.getAInputImage().setOnDragDetected(this::outputDragDetected);
        	key.getAInputImage().setOnDragOver(this::inputDragOver);
        	key.getAInputImage().setOnDragEntered(this::inputDragEntered);
        	key.getAInputImage().setOnDragExited(this::inputDragExited);
            
        	key.getBInputImage().setOnDragDetected(this::outputDragDetected);
        	key.getBInputImage().setOnDragOver(this::inputDragOver);
        	key.getBInputImage().setOnDragEntered(this::inputDragEntered);
        	key.getBInputImage().setOnDragExited(this::inputDragExited);
        }else if(currentComponent instanceof ACpower) {
        	ACpower acpower = (ACpower)currentComponent;
        	acpower.getACpower().setOnDragDetected(this::componentDragDetected);
        	
        	acpower.getAInputImage().setOnDragDetected(this::outputDragDetected);
        	acpower.getAInputImage().setOnDragOver(this::inputDragOver);
        	acpower.getAInputImage().setOnDragEntered(this::inputDragEntered);
        	acpower.getAInputImage().setOnDragExited(this::inputDragExited);
            
        	acpower.getBInputImage().setOnDragDetected(this::outputDragDetected);
        	acpower.getBInputImage().setOnDragOver(this::inputDragOver);
        	acpower.getBInputImage().setOnDragEntered(this::inputDragEntered);
        	acpower.getBInputImage().setOnDragExited(this::inputDragExited);
        }
    }

    private  Line createWire(final double startX, final double startY, final double endX, final double endY) {
        Wire wire = new Wire(startX,startY,endX,endY);
        return wire;
    }

    private void setLinePosition(final Node target,
                                 final Wire wire) {

        double x = target.getLayoutX() + target.getParent().getParent().getLayoutX() + target.getLayoutBounds().getWidth()/2;
        double y = target.getLayoutY() + target.getParent().getParent().getLayoutY() + target.getLayoutBounds().getHeight()/2;

        wire.setEndX(x);
        wire.setEndY(y);
    }

    private void mapLineTo(final Object target,
                           final Wire wire,
                           final String fxID) {
         if(target instanceof CircuitComponent) {
        	 CircuitComponent component = (CircuitComponent) target;
	            switch (fxID) {
	                case "Ainput" :
	                	component.setALine(wire);
	                	wire.setEndNode(component);
	                    break;
	
	                case "Binput":
	                	component.setBLine(wire);
	                	wire.setEndNode(component);
	                    break;
	            }
        	 }
    }
//
    public void mapNodeTo(final Parent outputNode,
                          final Parent inputNode,
                          final String fxId) {
        ((CircuitComponent) outputNode).setBNode((CircuitComponent) inputNode);
        ((CircuitComponent) inputNode).setANode((CircuitComponent) outputNode);
    }

    private Parent getNodeParent(Object object) {
        return ((Node)object).getParent().getParent();
    }

    
}
