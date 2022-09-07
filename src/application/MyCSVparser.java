package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyCSVparser {
	
	public void readCSVfile(String filePath) {
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
		
		for (int i = 0; i < records.size(); i++) {
			
			List<String> item = records.get(i);
			
			for (int j = 0; j < item.size(); j++) {
				String content = item.get(j);
				if (content.equals("?"))
					item.set(j, item.get(j-1));
			}
			
			System.out.println(item);
			
		}
	}

}
