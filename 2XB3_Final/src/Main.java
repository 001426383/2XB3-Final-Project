import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
	private static Graph g = new Graph();
	private static final String[] EMPTY_ARRAY = new String[0];

	public static void main(String[] args) {

		
		String ASIN = "", author = "",title = "";
		String line;
		String[] similar = null;
		float salesRank = 0, rating = 0;

		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("Output.txt")));

			while ((line = br.readLine()) != null) {

				if (line.startsWith("ASIN:")) {

					ASIN = line.replace("ASIN: ", "");
					while ((line = br.readLine()) != null) {
						if (line.startsWith("  title:")) {
							line = line.replace("  title: ","");
							line = line.trim();
							title = line;
							break;
						}
					}
					while ((line = br.readLine()) != null) {
						if (line.startsWith("  salesrank:")) {
							String temp = line.replace("  salesrank: ", "");
							salesRank = Float.parseFloat(temp);
						//	if (salesRank > 548552) {
								// System.out.println(line);
						//	}
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
					//System.out.println("Title:  " + title + "   " + ASIN);
					g.addEdge(title , ASIN, salesRank, rating, similar);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println(g.titles.get("Ostara: Customs, Spells & Rituals for the Rites of Spring"));
		//System.out.println(g.graph.get("0738700827").weight);
	
		getSimilar("A Game of Thrones (A Song of Ice and Fire, Book 1)");
		
		/*
		 * int counter = 0; for (String s : g.graph.keySet()) {
		 * System.out.println(s + "  " + counter + "  " +
		 * g.graph.get(s).getWeight(1)); counter++; }
		 */
	}
	
	
	
	//CALLED IN MAIN METHOD OF THIS CLASS
	public static void getSimilar(String title){
		
		SimilarProducts sp = new SimilarProducts(g,g.titles.get(title));
		for (String s : sp.getSimilar().keySet()){
			System.out.println(s +" "+ sp.getSimilar().get(s) + "  " + g.ASINS.get(s));
		}
	}
}
