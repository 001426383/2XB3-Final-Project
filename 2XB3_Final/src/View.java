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
	
	private Graph g = null;

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
			if (titleField.getText().equals("")) return;
			cb.getItems().clear();
			cb.getItems().add("Select a Book");
			String[] temp = titleField.getText().split(",");
			g = new Graph("Output.txt", temp);
			Product p = g.getProduct(temp[0]);
			if (p != null){
				results.setText(p.toString());
				cb.getItems().add(p.getTitle());
				cb.getSelectionModel().select(1);
			}
			else results.setText("BOOK NOT FOUND");
		});
		
		findBtn.setOnAction(e -> {
			if (!cb.getValue().equals("Select a Book")||!cb.getValue().equals("")){
				String[] temp = new String[1];
				SimilarProducts sp = g.getSimilarList();
				
				String sum = "";
				for (Product p : sp.getSimilar()){
					sum = sum.concat(p.toString() + '\n');
				}
				results.setText(sum);
				}
		});
		
		sortRating.setOnAction(e -> {
			Comparable[] t1;
			String[] t2 = results.getText().split("\n");
			t1 = new Comparable[t2.length];
			for (int i = 0; i < t2.length; i++) {
				String[] t3 = t2[i].split("\t");
				t1[i] = Float.parseFloat(t3[1]);
			}
			insertionSort(t1,t2);
			
			String sum = "";
			for (int i = 0; i < t2.length; i++) {
				sum = sum.concat(t2[i] + '\n');
			}
			results.setText(sum);
		});
		
		sortAlpha.setOnAction(e -> {
			String[] t1;
			String[] t2 = results.getText().split("\n");
			t1 = new String[t2.length];
			for (int i = 0; i < t2.length; i++) {
				String[] t3 = t2[i].split("by");
				t1[i] = t3[0].trim();
			}
			insertionSort(t1,t2);
			
			String sum = "";
			for (int i = 0; i < t2.length; i++) {
				sum = sum.concat(t2[i] + '\n');
			}
			results.setText(sum);
		});
		
		results.setEditable(false);
		results.setPrefColumnCount(50);
		results.setPrefRowCount(20);
		cb.setMaxWidth(150);
		
		
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
		
		Scene mainScene = new Scene(mainLayout, 800,550);
		
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}
	
	private void insertionSort(Comparable[]value, Comparable[]line){
		for (int i = 0; i < line.length; i++){
			for (int j = i; j > 0; j--) {
				if (less(value[j], value[j-1])){
					exch(value, j, j-1);
					exch(line, j, j-1);
				}
			}
		}
	}

	private void exch(Comparable[] a, int j, int i) {
		Comparable t = a[i]; a[i] = a[j]; a[j] = t;
	}

	private boolean less(Comparable a, Comparable b) {
		return a.compareTo(b) < 0;
	}
	
}
