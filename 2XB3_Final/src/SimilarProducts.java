import java.util.HashMap;
import java.util.LinkedList;

public class SimilarProducts {
	private Graph g;
	HashMap<String, Float> similar = new HashMap<String, Float>();

	public SimilarProducts(Graph g, String root) {
		this.g = g;
		BFS(root);
	}

	public HashMap<String, Float> getSimilar() {
		return similar;
	}

	private void BFS(String root) {
		// Mark all the vertices as not visited(By default
		// set as false)
		similar = new HashMap<>();

		// Create a queue for BFS
		LinkedList<String> queue = new LinkedList<String>();

		// Mark the current node as visited and enqueue it
		similar.put(root, (float) 0);
		queue.add(root);

		int level = 0;
		while (queue.size() != 0) {
			// Dequeue a vertex from queue and print it
			root = queue.poll();

			// Get all adjacent vertices of the dequeued vertex s
			// If a adjacent has not been visited, then mark it
			// visited and enqueue it
			String[] i = g.graph.get(root).similar();
			for (String s : i) {

				if (!similar.containsKey(s)) {
					// System.out.println(s);
					try {
						similar.put(s, g.graph.get(s).weight);
						queue.add(s);
					} catch (NullPointerException e) {
						g.graph.remove(s);
						//similar.put(s, (float) -10);
					}

				}
			}
			if (similar.size() > 1000) {
				break;
			}

		}
	}
}
