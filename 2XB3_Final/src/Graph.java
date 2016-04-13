import java.util.HashMap;

public class Graph {
	public HashMap<String, Product> graph;
	public HashMap<String,String> titles;
	public HashMap<String,String> ASINS;

	public Graph() {
		graph = new HashMap<String, Product>();
		titles = new HashMap<String, String>();
		ASINS = new HashMap<String, String>();
	}

	public void addEdge(String title,String ASIN, String author, float salesRank, float rating, String[] similarArray) {
		graph.put(ASIN, new Product(author, salesRank, rating, similarArray));
		titles.put(title, ASIN);
		ASINS.put(ASIN, title);
	}

	public void addEdge(String title,String ASIN, float salesRank, float rating, String[] similarArray) {
		graph.put(ASIN, new Product(salesRank, rating, similarArray));
		titles.put(title, ASIN);
		ASINS.put(ASIN, title);
	}

}
