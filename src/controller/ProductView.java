package controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import org.json.simple.parser.ParseException;

import helpers.MyCSVparser;
import helpers.MyJSONparser;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.LongStringConverter;
import models.Product;

public class ProductView extends VBox {
	
	private TableView<Product> table = new TableView<Product>();
	private TableColumn<Product, String> itemColumn;
	private TableColumn<Product, Date> orderDateColumn;
	private TableColumn<Product, String> regionColumn;
	private TableColumn<Product, String> rep1column;
	private TableColumn<Product, String> rep2column;
	private TableColumn<Product, Long> unitsColumn;
	private TableColumn<Product, Float> unitCostColumn;
	private TableColumn<Product, Float> totalCostColumn;
	
	private TextField orderDate,region, rep1, rep2, nrUnits, unitCost, item;
	
	private Button btnAdd;
	private Button btnDel;
	private Button btnSelFile;
	private Button btnSaveToFile;
	
	private FileChooser fileChooser;
	
	private MyJSONparser myJSONparser;
	private MyCSVparser myCSVparser;
	
	public ProductView() {
		BuildUI();
	}

	private void BuildUI() {
		
		table.setEditable(true);
		
		itemColumn = new TableColumn<Product, String>("Item");
		itemColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("item"));
		itemColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		orderDateColumn = new TableColumn<Product, Date>("Order date");
		orderDateColumn.setCellValueFactory(new PropertyValueFactory<Product, Date>("orderDate"));
		orderDateColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
		
		regionColumn = new TableColumn<Product, String>("Region");
		regionColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("region"));
		regionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		rep1column = new TableColumn<Product, String>("Rep1");
		rep1column.setCellValueFactory(new PropertyValueFactory<Product, String>("rep1"));
		rep1column.setCellFactory(TextFieldTableCell.forTableColumn());
		rep1column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Product,String>>() {
			
			@Override
			public void handle(CellEditEvent<Product, String> event) {
				// TODO Auto-generated method stub
				Product product = event.getRowValue();
				product.setRep1(event.getNewValue());
				
				System.out.println(table.getItems().get(0).getItem());
			}
		});

		rep2column = new TableColumn<Product, String>("Rep2");
		rep2column.setCellValueFactory(new PropertyValueFactory<Product, String>("rep2"));
		
		unitsColumn = new TableColumn<Product, Long>("Nr units");
		unitsColumn.setCellValueFactory(new PropertyValueFactory<Product, Long>("units"));
		unitsColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));

		unitCostColumn = new TableColumn<Product, Float>("Unit cost");
		unitCostColumn.setCellValueFactory(new PropertyValueFactory<Product, Float>("unitCost"));
		
		totalCostColumn = new TableColumn<Product, Float>("Total cost");
		totalCostColumn.setCellValueFactory(new PropertyValueFactory<Product, Float>("total"));



		table.getColumns().add(orderDateColumn);
		table.getColumns().add(regionColumn);
		table.getColumns().add(rep1column);
		table.getColumns().add(rep2column);
		table.getColumns().add(unitsColumn);
		table.getColumns().add(unitCostColumn);
		table.getColumns().add(itemColumn);
		table.getColumns().add(totalCostColumn);
		
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setPrefHeight(1000);
		
		orderDate = new TextField();
		orderDate.setPromptText("Date MM/dd/yyyy");
		Pattern DATE_PATTERN = Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$");
		
		region = new TextField();
		region.setPromptText("Region");
		
		rep1 = new TextField();
		rep1.setPromptText("Rep1");
		
		rep2 = new TextField();
		rep2.setPromptText("Rep2");
		
		nrUnits = new TextField();
		nrUnits.setPromptText("Nr of units");
		
		unitCost = new TextField();
		unitCost.setPromptText("Cost per units");
				
		item = new TextField();
		item.setPromptText("Item name");
		item.setTooltip(new Tooltip("Enter item name"));
		
		
		
		HBox hbox1 = new HBox();
		hbox1.setSpacing(10);
		hbox1.setPadding(new Insets(10,10,10,10));
		hbox1.getChildren().addAll(orderDate,region, rep1, rep2, nrUnits, unitCost, item);
		hbox1.setAlignment(Pos.TOP_LEFT);
		
		
		btnAdd = new Button("Add");
		btnAdd.setPrefWidth(100);
		btnAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//set date if pattern matches
				Date date = null;
				if (DATE_PATTERN.matcher(orderDate.getText()).matches())
					try {
						date = new SimpleDateFormat("dd/MM/yyyy").parse(orderDate.getText());
					} catch (java.text.ParseException e) {
						e.printStackTrace();
					}
				else //if pattern does not match, set current date
					date = new Date();
					
				
				Product product = new Product(
							date, 
							region.getText(), 
							rep1.getText(),
							rep2.getText(),
							item.getText(), 
							Integer.parseInt(nrUnits.getText()), 
							Float.parseFloat(unitCost.getText()),
							Integer.parseInt(nrUnits.getText())*Float.parseFloat(unitCost.getText())
						);
				add(product);
				clearTextFields();
				
			}
		});
		btnDel = new Button("Delete");
		btnDel.setPrefWidth(100);
		btnDel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				int row = table.getSelectionModel().getSelectedIndex();
				delete(row);
			}
		});
		
		HBox hbox2 = new HBox();
		hbox2.setSpacing(10);
		hbox2.setPadding(new Insets(0,10,10,10));
		hbox2.getChildren().addAll(btnAdd,btnDel);
		hbox2.setAlignment(Pos.TOP_RIGHT);
		
		
		fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
        		new FileChooser.ExtensionFilter("All Files", "*.*"),
        		new FileChooser.ExtensionFilter("CSV", "*.csv"),
        		new FileChooser.ExtensionFilter("JSON", "*.json")	            		
        		);
	      
		btnSelFile = new Button("Open");
		btnSelFile.setPrefWidth(100);		
		btnSelFile.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				File selectedFile = fileChooser.showOpenDialog(null);
	            readItemsFromFile(selectedFile.getAbsolutePath());  
			}
		});
		
		btnSaveToFile = new Button("Save");
		btnSaveToFile.setPrefWidth(100);
		btnSaveToFile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
	            //Show save file dialog
	            File file = fileChooser.showSaveDialog(null);
	 
	            if (file != null) {
	                writeItemsToFile(table.getItems(),file);
	            }
	            
	            
								
			}
		});
		
		HBox hbox3 = new HBox();
		hbox3.setSpacing(10);
		hbox3.setPadding(new Insets(0,10,10,10));
		hbox3.getChildren().addAll(btnSelFile, btnSaveToFile);
		hbox3.setAlignment(Pos.BOTTOM_RIGHT);
		
		getChildren().addAll(table,hbox1,hbox2,hbox3);
	}
	
	public void add(Product product) {
		table.getItems().add(product);
	}
	
	public void delete(int row) {
		if (row >= 0) {
			table.getItems().remove(row);
			table.getSelectionModel().clearSelection();
		}
	}
	
	private void clearTextFields() {
		item.clear();
		rep1.clear();
		rep2.clear();
		nrUnits.clear();
		orderDate.clear();
		region.clear();
		unitCost.clear();
	}
	
	private void writeItemsToFile(ObservableList<Product> products, File file) {
		myJSONparser = new MyJSONparser();
		myCSVparser = new MyCSVparser();
		
		//check extension
		String fileName = file.getName();
		int indexOfDot = fileName.indexOf('.');
		String extension = file.getName().substring(indexOfDot+1);
		String filePath = file.getPath();
		
		switch (extension) {
			case "csv" -> myCSVparser.writeToCSVfile(products, filePath);
			case "json" -> myJSONparser.writeJSONfile(products,filePath);

			default ->
				throw new IllegalArgumentException("Unexpected extension: " + extension);
		}
		
		
		
	}
	
	private void readItemsFromFile(String filePath) {
		String fileType = filePath.substring(filePath.indexOf('.')+1);
		
		if (!fileType.equals("xml") && !fileType.equals("csv") && !fileType.equals("json"))
			return;
		
		ArrayList<Product> myProductList = new ArrayList<>();
		myJSONparser = new MyJSONparser();
		myCSVparser = new MyCSVparser();
		
		switch (fileType) {
			case "json" -> {
				try {
					myProductList = myJSONparser.readJSONfile(filePath);
				} catch (ParseException | IOException e) {
					e.printStackTrace();
				}
			}
			case "xml" -> {
				//not yet implemented
			}
			
			case "csv" -> {
				//not fully implemented
				myProductList = myCSVparser.readCSVfile(filePath);
				
			}
			
			default ->
				throw new IllegalArgumentException("Unexpected value: " + fileType);
		}
			
		for (Product product : myProductList) {
			table.getItems().add(product);
		}
		
	}

}
