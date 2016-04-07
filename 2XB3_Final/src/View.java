import java.awt.Font;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class View extends Application{

	public static void main(String[] args){
		launch(args);
	}
	
	public void start(Stage primaryStage) throws Exception{
		primaryStage.setTitle("Amazon Book List Recommendations");
		
		Label titleLbl = new Label("Title");
		ChoiceBox cb = new ChoiceBox();
		Button searchBtn = new Button("Search");
		TextArea results = new TextArea();
		Button findBtn = new Button("Find Similar");
		TextField titleField = new TextField();
		Label sortLbl = new Label("Sorting");
		Button sortRating = new Button("Rating");
		Button sortAlpha = new Button("Title");
		Label title = new Label("Amazon Book List Recommendations");
		
		
		cb.getItems().addAll("Select a Book");
		cb.getSelectionModel().select(0);
		
		searchBtn.setOnAction(e -> {
			if (titleField.getText().equalsIgnoreCase("the hunger games")){
				cb.getItems().addAll("The Hunger Games");
				results.setText("The Hunger Games\t\t4.6/5");
			}
		});
		
		findBtn.setOnAction(e -> {
			if (cb.getValue().equals("The Hunger Games"))
			results.setText("Catching Fire\t\t4.5/5\nMockingjay\t\t4.1/5\nDivergent\t\t4.4/5\nThe Maze Runner\t\t4.2/5");
		});
		
		sortRating.setOnAction(e -> {
			if (cb.getValue().equals("The Hunger Games"))
			results.setText("Catching Fire\t\t4.5/5\nDivergent\t\t4.4/5\nThe Maze Runner\t\t4.2/5\nMockingjay\t\t4.1/5");
		});
		
		sortAlpha.setOnAction(e -> {
			if (cb.getValue().equals("The Hunger Games"))
			results.setText("Catching Fire\t\t4.5/5\nDivergent\t\t4.4/5\nMockingjay\t\t4.1/5\nThe Maze Runner\t\t4.2/5");
		});
		
		results.setEditable(false);
		results.setPrefColumnCount(25);
		results.setPrefRowCount(10);
		
		
		javafx.scene.text.Font f = new javafx.scene.text.Font("Tahoma", 20);
		title.setFont(f);
		
		VBox mainLayout = new VBox(50);
		HBox topLayout = new HBox(20);
		HBox midLayout = new HBox(20);
		HBox botLayout = new HBox(20);
		
		topLayout.getChildren().addAll(titleLbl, titleField, searchBtn);
		
		VBox selectLayout = new VBox(20);
		selectLayout.getChildren().addAll(cb, findBtn);
		midLayout.getChildren().addAll(results, selectLayout);
		
		botLayout.getChildren().addAll(sortLbl, sortRating, sortAlpha);
		
		mainLayout.getChildren().addAll(title, topLayout, midLayout, botLayout);
		
		title.setPadding(new Insets(20,20,20,20));
		topLayout.setPadding(new Insets(-30,20,0,20));
		midLayout.setPadding(new Insets(-30,20,0,20));
		selectLayout.setPadding(new Insets(50,0,0,0));
		botLayout.setPadding(new Insets(-30,0,0,20));
		
		Scene mainScene = new Scene(mainLayout, 470,400);
		
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}
	
}
