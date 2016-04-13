import java.util.HashMap;

public class Graph {
	public HashMap<String, Product> graph;

	public Graph() {
		graph = new HashMap<String, Product>();
	}

	public void addEdge(String ASIN, String author, float salesRank, float rating, String[] similarArray) {
		graph.put(ASIN, new Product(author, salesRank, rating, similarArray));
	}

	public void addEdge(String ASIN, float salesRank, float rating, String[] similarArray) {
		graph.put(ASIN, new Product(salesRank, rating, similarArray));
	}

}
