import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Graph {
	private HashMap<String, Product> graph;
	private HashMap<String, String> ASINS;
	private HashMap<String, String> titles;
	
	public Graph(String FILE_NAME, String[] SEARCH_TITLE){
		setGraph(new HashMap<String, Product>());
		ASINS = new HashMap<String, String>();
		titles = new HashMap<String, String>();

		String ASIN = "", author = "", title = "";
		String line;
		String[] similar = null;
		float salesRank = 0, rating = 0;

		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(FILE_NAME)));

			while ((line = br.readLine()) != null) {

				if (line.startsWith("ASIN:")) {

					ASIN = line.replace("ASIN: ", "");
					while ((line = br.readLine()) != null) {
						if (line.startsWith("  title:")) {
							line = line.replace("  title: ", "");
							line = line.trim();
							title = line;
							break;
						}
					}
					while ((line = br.readLine()) != null) {
						if (line.startsWith("  salesrank:")) {
							String temp = line.replace("  salesrank: ", "");
							salesRank = Float.parseFloat(temp);
							// if (salesRank > 548552) {
							// System.out.println(line);
							// }
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
					// System.out.println("Title: " + title + " " + ASIN);
					addEdge(title, ASIN, salesRank, rating, similar);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println(g.titles.get("Ostara: Customs, Spells & Rituals
		// for the Rites of Spring"));
		// System.out.println(g.graph.get("0738700827").weight);

		getSimilar(SEARCH_TITLE);

		/*
		 * int counter = 0; for (String s : g.graph.keySet()) {
		 * System.out.println(s + "  " + counter + "  " +
		 * g.graph.get(s).getWeight(1)); counter++; }
		 */
	}
	
	public Graph(String FILE_NAME, String SEARCH_TITLE) {
		this(FILE_NAME , new String[]{SEARCH_TITLE});
	}

	//ALLOWS YOU TO ADD AN EDGE WITH THE AUTHOR
	private void addEdge(String title, String ASIN, String author, float salesRank, float rating,
			String[] similarArray) {
		getGraph().put(ASIN, new Product(ASIN, author, salesRank, rating, similarArray));
		ASINS.put(title, ASIN);
		titles.put(ASIN, title);
	}

	//ALLOWS YOU TO ADD AN EDGE WITHOUT THE AUTHOR
	private void addEdge(String title, String ASIN, float salesRank, float rating, String[] similarArray) {
		getGraph().put(ASIN, new Product(ASIN,salesRank, rating, similarArray));
		ASINS.put(title, ASIN);
		titles.put(ASIN, title);
	}
	// GETS THE SIMILAR TITLES FOR A TITLE
	public void getSimilar(String title) {
		
	}
	
	//CALLS THE OTHER GETSIMILAR METHOD FOR EACH TITLE IN THE ARRAY
	public void getSimilar(String[] stringTitles) {
		String[] stringASINS = new String[stringTitles.length];
		for (int i = 0; i < stringASINS.length; i ++){
			stringASINS[i] = ASINS.get(stringTitles[i]);
		}
		SimilarProducts sp = new SimilarProducts(this, stringASINS);
		for (Product p : sp.getSimilar()) {
			System.out.println("ASIN: " + p.getASIN() + " Rating: " + p.getWeight() +" Title: " + titles.get(p.getASIN()));
		}
	}
	public HashMap<String, Product> getGraph() {
		return graph;
	}

	public void setGraph(HashMap<String, Product> graph) {
		this.graph = graph;
	}

}
