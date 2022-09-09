package helpers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javafx.collections.ObservableList;
import models.Product;

public class MyCSVparser {
	
	ArrayList<Product> myProductList;
	Product myProduct;
	
	public ArrayList<Product> readCSVfile(String filePath) {
		
		myProductList = new ArrayList<>();
		
		List<List<String>> records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		        String[] values = line.split(",");
		        records.add(Arrays.asList(values));
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<String> labels = records.get(0);
		
		//from 1 so you not read labels again
		for (int i = 1; i < records.size(); i++) {
			myProduct = new Product();
			String itemValue = "";
			List<String> item = records.get(i);
			
			for (int j = 0; j < labels.size(); j++) {
				
				try {
					itemValue = item.get(j);
				}catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
				}

				try {
					switch (labels.get(j)) {
						case "OrderDate" ->{
							
							String strDate = (String) itemValue;
							Date date = null;
							try {
								date = new SimpleDateFormat("MM/dd/yyyy").parse(strDate);
							} catch (java.text.ParseException e) {
								e.printStackTrace();
							}
							myProduct.setOrderDate(date);
						}
						
						case "Region" ->myProduct.setRegion(itemValue);
						case "Rep1" ->myProduct.setRep1(itemValue);
						case "Rep2" ->myProduct.setRep2(itemValue);
						case "Item" ->myProduct.setItem(itemValue);
						case "Units" ->myProduct.setUnits(Long.parseLong(itemValue));
						case "UnitCost" ->myProduct.setUnitCost(Float.parseFloat(itemValue));
						case "Total" ->myProduct.setTotal(Float.parseFloat(itemValue));
						default ->
							throw new IllegalArgumentException("Unexpected value: " + itemValue);
					}
				}
				catch (NumberFormatException ne) {
					ne.printStackTrace();
				}
			}
			myProductList.add(myProduct);
			
		}
		
		for (Product product : myProductList) {
			System.out.println(product.toString());
		}
		
		return myProductList;
	}

	public void writeToCSVfile(ObservableList<Product> products, String filePath) {
		
		String csvString = "";
		
		for (Product product : products) {
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");  
		    String strDate = formatter.format(product.getOrderDate());
		    
		    csvString +=
		    		strDate + "," +
		    		product.getItem().toString() + "," +
		    		product.getRep1().toString() + "," +
		    		product.getRep2().toString() + "," +
		    		product.getRegion() + "," +
		    		product.getUnits() + "," +
		    		product.getUnitCost() + "," +
		    		product.getTotal() + "\r\n";
		    
		}
		//remove last ","
		if (csvString.length() > 0)
			csvString = csvString.substring(0, csvString.length()-1);
		
		try (FileWriter file = new FileWriter(filePath)) {
            file.write(csvString);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
