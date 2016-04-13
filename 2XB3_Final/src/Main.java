import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
	public static void main(String[] args){
		String[] temp = {"The Quality Improvement Handbook:  Team Guide to Tools and Techniques", "One Flew over the Cuckoo's Nest (Penguin Classics)"};
		Graph g = new Graph("Output.txt", temp);
	}

}
