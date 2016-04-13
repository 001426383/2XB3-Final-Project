
public class Product {
	// private String ASIN;
	private String[] similarArray;
	private String author;
	public float weight;
	private final static float maxDepth = 50;

	public Product(String author, float salesRank, float rating, String[] similarArray) {
		this.author = author;
		this.similarArray = similarArray;
		weight = 0;
		setWeight(salesRank, rating);
	}

	public Product(float salesRank, float rating, String[] similarArray) {
		this.author = "Author Unknown";
		this.similarArray = similarArray;
		weight = 0;
		setWeight(salesRank, rating);
	}

	public float getWeight(float depth) {
		return (float) (weight + (1.0 - (depth / maxDepth)));
	}

	private void setWeight(float salesRank, float rating) {
		// out of 5 : 1.5/5 for sales rank, 2.5/5 for rating (default is 1/2.5),
		// 1 point for depth similarity.
		float weight = 0;
		weight += (1.0 - (((float) salesRank) / 548552.0));
		if (rating < 0.1) {
			weight += 1;
		} else {
			weight += (rating / 5.0) * 2.5;
		}
		if (weight < 0)
			weight = 0;
		this.weight = weight;
	}

	public String[] similar() {
		return similarArray;
	}

	public static void main(String args[]) {
		int x = 1;
		String s = "  reviews: total: 4  downloaded: 4  avg rating: 5";
		s = s.trim();
		System.out.println(s.split("  ").length);
		s = s.replace("reviews: total: ", "");
		s = s.replace("downloaded: ", "");
		s = s.replace("avg rating: ", "");

		for (String p : s.split("  ")) {
			p.trim();
			System.out.print(p + "    ,    ");
		}

	}

}
