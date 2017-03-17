
import java.awt.Panel;
import java.io.File;
import java.util.StringTokenizer;
import java.util.Vector;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
public class Main extends Application {
	private TextArea _SQLCommands = new TextArea();
	private TextArea _select = new TextArea();
	private TextArea _statuserror = new TextArea();
  public static void main(String[] args) {
    Application.launch(args);
  }
public void Menu (BorderPane root,Stage primaryStage){
	MenuBar menuBar = new MenuBar();
    menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
    root.setTop(menuBar);
    
    Menu filemenu = new Menu("File");
    MenuItem openSQL = new MenuItem("Open SQL script");
    MenuItem runSQL = new MenuItem("Run SQL script");
    MenuItem saveSQL = new MenuItem("Save SQL script");
    
    filemenu.getItems().addAll(openSQL, runSQL, saveSQL);
    
    Menu editMenu = new Menu("Edit");
    MenuItem copy = new MenuItem("Copy");
    MenuItem paste = new MenuItem("Paste");
    MenuItem prefrences = new MenuItem ("Preferences");
	    Dialog<String> options = new Dialog<>();
	    DialogPane pane = options.getDialogPane();
	    options.setTitle("Prefrences");
	    options.setHeight(500.0);
	    options.setWidth(500.0);
	    
	    ToggleButton saveSnap = new ToggleButton("Save snapshot of Open editor on close");
	 //   Label save = new Label("Save snapshot of Open editor on close");
	    Button closep = new Button("Close");
	    closep.setOnAction(new EventHandler<ActionEvent>() {
	        @Override 
	        public void handle(ActionEvent e) {
	            options.close();
	        }
	    });
	    pane.setHeaderText("test");
	    pane.setMinSize(500, 500);
	    pane.getChildren().addAll(saveSnap,closep);
	    prefrences.setOnAction(actionEvent -> options.show() );
	editMenu.getItems().addAll(copy,paste,prefrences);
    
    
    Menu help = new Menu("Help");
    MenuItem about = new MenuItem("About");
    help.getItems().addAll(about);
    menuBar.getMenus().addAll(filemenu,editMenu,help);
  }
public Vector<String> parseSQLStatements(String text){
	Vector<String> messages = new Vector<String>();
	StringTokenizer getCommand = new StringTokenizer(text,";");
	 while(getCommand.hasMoreTokens()) { 
	    String part = getCommand.nextToken();
	    messages.add(part);
	}
	 return messages;
	
}
public void writeStatus (){
	Vector<String> pass = Log.getSuccess();
	for (String message: pass){
		_statuserror.appendText(message);
		_statuserror.appendText("\n");
	}
	Vector<String> fail = Log.getErrors();
	for (String message: fail){
		_statuserror.appendText(message);
		_statuserror.appendText("\n");
	}
}
public void addTree(GridPane gridpane){
	TreeItem<String> rootItem = new TreeItem<>(System.getProperty("user.dir"));
    rootItem.setExpanded(true);
    File dir = new File(System.getProperty("user.dir"));
    String[] entries = dir.list();
    if (entries.length != 0){
    	for (String s : entries) {
			TreeItem<String> item = new TreeItem<>(s);
			rootItem.getChildren().add(item);
    }
    }
    TreeView<String> tree = new TreeView<>(rootItem);
    gridpane.add(tree, 0, 0);
}
public void createRight(GridPane gridpane){
	
	TilePane SQLPane = new TilePane();
	HBox box= new HBox(3);
	Image oneC = new Image(getClass().getResourceAsStream("1 command.png"));
	Image allCommands = new Image(getClass().getResourceAsStream("AllCommands.png"));
	Image sweep = new Image(getClass().getResourceAsStream("sweep.png"));
	Button onecommand = new Button("" ,new ImageView(oneC));
	onecommand.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
        	String text = _SQLCommands.getSelectedText();
        	Log.clearlists();
        	Database.proccessSQL(text);
        	writeStatus();
        }
	});
	Button allC = new Button("",new ImageView(allCommands));
	allC.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
        	String text = _SQLCommands.getText();
        	Vector<String> commands = parseSQLStatements(text);
        	Log.clearlists();
        	for(String command:commands)
        		Database.proccessSQL(command);
        	writeStatus();
        }
	});
	Button sButton = new Button ("", new ImageView(sweep));
	sButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
        	_SQLCommands.deleteText(0, _SQLCommands.getText().length());
        	Log.clearlists();
        	writeStatus();
        }
	});
	Label editorLabel = new Label("Editor:");
	SQLPane.getChildren().add(editorLabel);
	box.getChildren().add(onecommand);
	box.getChildren().add(allC);
	box.getChildren().add(sButton);
	SQLPane.getChildren().add(box);
	gridpane.add(SQLPane, 0,0);
	
	_SQLCommands.setPrefSize(800, 800);
	_SQLCommands.setStyle("-fx-border-color: black; -fx-border-width: 5;");
	gridpane.add(_SQLCommands, 0,1);
	
	Pane selectPane = new Pane();
	Label selectionLabel = new Label("Text Selection From File:");
	selectPane.getChildren().add(selectionLabel);
	gridpane.add(selectionLabel, 0,2);
	
	_select.setPrefSize(800, 800);
	_select.setStyle("-fx-border-color: black; -fx-border-width: 5;");
	gridpane.add(_select, 0,3);
	
	Pane status = new Pane();
	Label successLabel = new Label("Succesess ");
	status.getChildren().add(successLabel);
	gridpane.add(successLabel, 0,4);
	
	_statuserror.setPrefSize(800, 800);
	_statuserror.setStyle("-fx-border-color: black; -fx-border-width: 5;");
	_statuserror.setEditable(false);
	gridpane.add(_statuserror, 0,5);
}
  @Override
  public void start(Stage primaryStage) {
    BorderPane root = new BorderPane();
    Scene scene = new Scene(root, 600, 600, Color.WHITE);
    Menu(root,primaryStage);  
    GridPane gridpane = new GridPane();
    gridpane.setPadding(new Insets(5));
    gridpane.setHgap(5);
    gridpane.setVgap(5);
    ColumnConstraints column1 = new ColumnConstraints(100);
    ColumnConstraints column2 = new ColumnConstraints(50, 150, 300);
    column2.setHgrow(Priority.ALWAYS);
    gridpane.getColumnConstraints().addAll(column1, column2);
    addTree(gridpane);
    gridpane.autosize();
    GridPane rightSideGrid = new GridPane();
    gridpane.add(rightSideGrid, 1, 0);
    createRight(rightSideGrid);
    root.setRight(rightSideGrid);
    root.setLeft(gridpane);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}