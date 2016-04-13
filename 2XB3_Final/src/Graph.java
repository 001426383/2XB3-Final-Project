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
	private SimilarProducts similar;
	
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
					
					// find author
					while ((line = br.readLine()) != null) {
						if (line.startsWith("  categories:")) {
							int numCat = Integer.parseInt(line.substring(14));
							for (int i = 0; i < numCat; i++) {
								line = br.readLine();
								if (line.contains("Authors, A-Z")){
									String[] s = line.split("]");
									for (int j = 0; j < s.length; j++) {
										if (s[j].contains("Authors, A-Z")){
											if (j+2 >= s.length) break;
											int end = s[j+2].indexOf("[");
											author = s[j+2].substring(1, end);
											break;
										}
									}
								}
							}
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
					if (author.equals(""))
						addEdge(title, ASIN, salesRank, rating, similar);
					else addEdge(title, ASIN, author, salesRank, rating, similar);
					author = "";
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

		this.similar = getSimilar(SEARCH_TITLE);

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
		getGraph().put(ASIN, new Product(title, ASIN, author, salesRank, rating, similarArray));
		ASINS.put(title, ASIN);
		titles.put(ASIN, title);
	}

	//ALLOWS YOU TO ADD AN EDGE WITHOUT THE AUTHOR
	private void addEdge(String title, String ASIN, float salesRank, float rating, String[] similarArray) {
		getGraph().put(ASIN, new Product(title, ASIN,salesRank, rating, similarArray));
		ASINS.put(title, ASIN);
		titles.put(ASIN, title);
	}
	// GETS THE SIMILAR TITLES FOR A TITLE
	public void getSimilar(String title) {
		
	}
	
	//CALLS THE OTHER GETSIMILAR METHOD FOR EACH TITLE IN THE ARRAY
	public SimilarProducts getSimilar(String[] stringTitles) {
		String[] stringASINS = new String[stringTitles.length];
		for (int i = 0; i < stringASINS.length; i ++){
			stringASINS[i] = ASINS.get(stringTitles[i]);
		}
		return new SimilarProducts(this, stringASINS);
	}
	
	public SimilarProducts getSimilarList(){
		return this.similar;
	}
	
	public HashMap<String, Product> getGraph() {
		return graph;
	}

	public void setGraph(HashMap<String, Product> graph) {
		this.graph = graph;
	}
	
	public Product getProduct(String s){
		return graph.get(ASINS.get(s));
	}

}
