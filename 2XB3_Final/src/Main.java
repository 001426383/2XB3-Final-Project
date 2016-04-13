import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
	private static Graph g;
	private static final String[] EMPTY_ARRAY = new String[0];
	
	public static void main(String[] args) {

		g = new Graph();
		String ASIN = "", author = "";
		String line;
		String[] similar = null;
		float salesRank = 0, rating = 0;

		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("Output.txt")));

			while ((line = br.readLine()) != null) {
				
				if (line.startsWith("ASIN:")) {
					
					ASIN = line.replace("ASIN: ", "");
					while ((line = br.readLine()) != null) {
						if (line.startsWith("  salesrank:")) {
							line = line.replace("  salesrank: ", "");
							salesRank = Float.parseFloat(line);
							break;
						}
					}
					
					while ((line = br.readLine()) != null) {
						if (line.startsWith("  similar:")) {
							line = line.trim();
							String[] temp = line.split("  ");
							similar = new String[temp.length - 1];
							for (int i = 0; i < temp.length - 1; i++)
								similar[i] = temp[i + 1].trim();
							break;
						}
					}
					
					while ((line = br.readLine()) != null) {
						if (line.startsWith("  reviews:")) {
							line = line.trim();
							line = line.replace("reviews: total: ", "");
							line = line.replace("downloaded: ", "");
							line = line.replace("avg rating: ", "");
							rating = Float.parseFloat(line.split("  ")[2]);
							break;
						}
					}
					g.addEdge(ASIN, salesRank, rating, similar);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		int counter = 0;
		for (String s : g.graph.keySet()){
			String temp = " ";
			for (String t : g.graph.get(s).similar()){
				temp += t + " ";
			}
			System.out.println(s + "  " + counter + temp);
			counter ++;
		}*/
	}
}
