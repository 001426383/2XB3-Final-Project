import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Parser {
	public static void main(String[] args) {
		try {

			FileWriter fw = new FileWriter(new File("AmazonParsed.txt"),true);
			BufferedWriter bw = new BufferedWriter(fw);
			String line = "";
			String line2 = "";
			String line3 = "";
			String line4 = "";
			BufferedReader br = new BufferedReader(new FileReader(new File("Amazon.txt")));
			while ((line = br.readLine()) != null) {
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
