import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class SimilarProducts {
	private Graph g;
	private ArrayList<Product> similar = new ArrayList<Product>();
	private HashMap<String, Float> visited = new HashMap<String, Float>();

	public SimilarProducts(Graph g, String[] titles) {
		visited = new HashMap<>();
		this.g = g;
		BFS(titles);
	}

	// ********* REPLACE THE COLLECTIONS SORT WITH OUR OWN QUICKSORT ********
	public ArrayList<Product> getSimilar() {
		for (String s : visited.keySet()) {
			similar.add(g.getGraph().get(s));
		}
		Collections.sort(similar);
		if (similar.size() - 11 >= 0)
			similar.subList(0, similar.size() - 10).clear();
		return similar;
	}

	private void BFS(String[] titles) {
		for (String title : titles) {
			BFS(title);
		}
	}

	private void BFS(String root) {
		// Mark all the vertices as not visited(By default
		// set as false)
		

		// Create a queue for BFS
		LinkedList<String> queue = new LinkedList<String>();

		// Mark the current node as visited and enqueue it
		visited.put(root, (float) 0);
		g.getGraph().get(root).emptyWeight();
		queue.add(root);

		int level = 0; // the depth level of the search
		while (queue.size() != 0) {
			// Dequeue a vertex from queue and print it
			root = queue.poll();

			if (root.equals("DONE_LAYER")) {
				level++;
			} else {
				// Get all adjacent vertices of the dequeued vertex s
				// If a adjacent has not been visited, then mark it
				// visited and enqueue it
				String[] i = g.getGraph().get(root).similar();
				for (String s : i) {

					if (!visited.containsKey(s)) {
						// System.out.println(s);
						try {
							visited.put(s, g.getGraph().get(s).getWeight(level));
							queue.add(s);

						} catch (NullPointerException e) {
							g.getGraph().remove(s);
							// similar.put(s, (float) -10);
						}

					}
				}
				queue.add("DONE_LAYER");
				if (visited.size() > 1000) {
					break;
				}
			}

		}
	}
}
