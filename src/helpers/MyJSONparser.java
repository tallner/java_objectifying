package helpers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.collections.ObservableList;
import models.Product;

public class MyJSONparser  {
	
	ArrayList<Product> myProductList;
	JSONParser parser;
	FileReader reader;
	JSONArray jsonArr;
	JSONObject jsonObject;
	Product myProduct;
	
	
	
	public MyJSONparser() {
	}
	
	public void writeJSONfile(ObservableList<Product> products, String filename) {
		
		JSONArray list = new JSONArray();
        
		for (Product product : products) {
			JSONObject obj = new JSONObject();
			
			
		    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");  
		    String strDate = formatter.format(product.getOrderDate());  
			
			obj.put("Item", product.getItem().toString());
			obj.put("Rep1", product.getRep1().toString());
			obj.put("Rep2", product.getRep2().toString());
			obj.put("Region", product.getRegion());
			obj.put("Units", product.getUnits());
			obj.put("UnitCost", Float.toString(product.getUnitCost()));
			obj.put("OrderDate", strDate);
			obj.put("Total", Float.toString(product.getTotal()));
			
			list.add(obj);
		}
		
        try (FileWriter file = new FileWriter("C:\\ProgrammingCourses\\grit\\avanceradjava\\objectifying\\src\\files\\"+filename+".json")) {
            file.write(list.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public ArrayList<Product> readJSONfile(String filePath) throws ParseException, FileNotFoundException, IOException {
		
		myProductList = new ArrayList<>();
		parser = new JSONParser();
		reader = new FileReader(filePath);
		jsonArr = (JSONArray) parser.parse(reader);

		for (Object object : jsonArr) {
			myProduct = new Product();
			jsonObject = (JSONObject) object;
			
			
			
			String strDate = (String) jsonObject.get("OrderDate");
			Date date = null;
			try {
				date = new SimpleDateFormat("MM/dd/yyyy").parse(strDate);
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
			myProduct.setOrderDate(date);
			
			try {
				myProduct.setRegion((String) jsonObject.get("Region"));
				myProduct.setRep1((String) jsonObject.get("Rep1"));
				myProduct.setRep2((String) jsonObject.get("Rep2"));
				myProduct.setItem((String) jsonObject.get("Item"));
				myProduct.setUnits((long) jsonObject.get("Units"));
				myProduct.setUnitCost(Float.parseFloat((String) jsonObject.get("UnitCost")));
				myProduct.setTotal(Float.parseFloat((String) jsonObject.get("Total")));
			} catch (NumberFormatException ne) {
				ne.printStackTrace();
			}
			
			
			myProductList.add(myProduct);
			
		}
		return myProductList;
		
		
		
		
	}
}


