
public class Product implements Comparable<Product>{
	private String ASIN;
	private String[] similarArray;
	private String author;
	private float weight;
	
	//depth at which the offset starts to become negative
	private final static float maxDepth = 50;
	
	
	// Some value between 0-5 which indicated how reliant on depth you want the
	// rating to be (5 being less reliant on similarity/depth, and 0 being more)
	private final static float SIMILARITY_COEFFICIENT = (float) 4.5;

	public Product(String ASIN,String author, float salesRank, float rating, String[] similarArray) {
		this.ASIN = ASIN;
		this.author = author;
		this.similarArray = similarArray;
		weight = 0;
		setWeight(salesRank, rating);
	}

	public Product(String ASIN, float salesRank, float rating, String[] similarArray) {
		this.ASIN = ASIN;
		this.author = "Author Unknown";
		this.similarArray = similarArray;
		weight = 0;
		setWeight(salesRank, rating);
	}
	public float getWeight(){
		return weight;
	}
	public float getWeight(float depth) {
		float offset = ((float) (5 - SIMILARITY_COEFFICIENT) - (depth / maxDepth));
		if (Math.abs(offset) > (5 - SIMILARITY_COEFFICIENT)) {
			if (offset < 0) {
				offset = (float) -(5 - SIMILARITY_COEFFICIENT);
			} else {
				offset = (float) (5 - SIMILARITY_COEFFICIENT);
			}
		}
		this.weight = weight+ offset;
		return (float) (weight);
	}
	public void emptyWeight(){
		this.weight = -10;
	}
	private void setWeight(float salesRank, float rating) {
		// out of 5 : 1/5 for sales rank, 4/5 for rating (default is 1/3),
		float weight = 0;
		weight += (1 - (((float) salesRank) / 548552.0));

		if (rating < 0.1) {
			weight += 1;
		} else {
			weight += (rating / 5.0) * 4;
		}
		if (weight < 0)
			weight = 0;
		weight = (float) ((weight / 5) * SIMILARITY_COEFFICIENT);
		this.weight = weight;
	}

	public String[] similar() {
		return similarArray;
	}
	public String getASIN(){
		return ASIN;
		
	}
	

	@Override
	public int compareTo(Product other) {
		if (weight < other.getWeight()){
			return -1;
		}else if (weight > other.getWeight()){
			return 1;
		}
		return 0;
	}

}
