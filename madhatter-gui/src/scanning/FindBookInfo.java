package scanning;

import java.io.*;  
import java.util.Scanner; 


public class FindBookInfo {
	
	public static File bookCsv() {
		
		return null;
	}
	//basic csv reader
	public static void main(String[] args) throws IOException {
		File csvFile= new File("bookCsv.csv");
		BufferedReader br = new BufferedReader(new FileReader(csvFile));
		
		String line = "";
		try {
			while((line = br.readLine())!=null) {
				String[] count = line.split(",");
				System.out.println(count[0]+","+count[1]);
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
