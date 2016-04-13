import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Parser {
	private static ArrayList<String> removeList;
	private static int numEdgesRemoved;

	public static void main(String[] args) {
		removeList = new ArrayList<String>();
		String[] lines;
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("Output.txt"), false));
			BufferedReader br = new BufferedReader(new FileReader(new File("Amazon.txt")));

			String a = "", b = "", c = "", d = "", e = "", f = "", line = "", g = "", h = "";

			while ((line = br.readLine()) != null) {
				if (line.equalsIgnoreCase("  discontinued product")) {
					a = a.replace("ASIN: ", "");
					removeList.add(a);
					b = "";

				} else if (line.startsWith("    20") || line.startsWith("    19")) {

				} else if (line.startsWith("  similar: 4") || line.startsWith("  similar: 0")
						|| line.startsWith("  similar: 1") || line.startsWith("  similar: 2")
						|| line.startsWith("  similar: 3")) {
					e = "";
					d = d.replace("ASIN: ", "");
					removeList.add(d);
					d = "";
					c = "";
					b = "";
					a = "";
					boolean flag = true;
					while ((line = br.readLine()) != null && flag) {
						if (line.length() == 0) {
							flag = false;
						}
					}
					a = line;

				} else if (line.startsWith("  group: DV") || line.startsWith("  group: M") || line.startsWith("  group: Vi")) {
					a = "";
					c = "";
					b = b.replace("ASIN: ", "");
					if (b.length() != 10)
						System.out.println(b);
					removeList.add(b);
					b = "";
					boolean flag = true;
					while ((line = br.readLine()) != null && flag) {
						if (line.length() == 0) {
							flag = false;
						}
					}
					a = line;

				} else {
					h = g;
					g = f;
					f = e;
					e = d;
					d = c;
					c = b;
					b = a;
					a = line;
					if (!f.equalsIgnoreCase("") || !h.equalsIgnoreCase("")) {
						bw.write(h);
						bw.newLine();
					}
				}
			}

			int counter = 0;

			for (String s : removeList) {
				if (s.length() != 10) {
					counter++;
				}
			}

			System.out.println(counter + " Fake Elements.");

			counter = 0;
			Collections.sort(removeList);

			br = new BufferedReader(new FileReader(new File("Output.txt")));
			while ((line = br.readLine()) != null) {
				if (line.startsWith("  similar")) {
					lines = line.split("  ");
					for (int i = 0; i < lines.length; i++) {
						if (binarySearch(lines[i])) {
							System.out.print(lines[i] + " removed " + line + "   AFTER  ");
							line = line.replace("  " + lines[i] , "");
							System.out.println(line);
							counter++;
						}
					}
				}
			}
			System.out.println(counter + " " + " External Edges Removed.");
			System.out.println(removeList.size() + " Elements Removed.");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean binarySearch(String key) {

		int low = 0;
		int high = removeList.size() - 1;

		while (high >= low) {
			int middle = (low + high) / 2;
			if (removeList.get(middle).equalsIgnoreCase(key)) {

				// System.out.println(removeList.get(middle) + ", " + key);
				return true;
			}
			if (removeList.get(middle).compareTo(key) < 0) {
				low = middle + 1;
			}
			if (removeList.get(middle).compareTo(key) > 0) {
				high = middle - 1;
			}
		}
		return false;
	}

	public void BFS() {

	}
}
