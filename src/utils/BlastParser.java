package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BlastParser {
	
	public static ArrayList<Match> parse(String file, int minLength) throws FileNotFoundException {
		
		Scanner sc = new Scanner(new FileInputStream(file));
		ArrayList<Match> matches = new ArrayList<Match>();
		
		String line;
		
		while (sc.hasNextLine()) {
			line = sc.nextLine();
			if (!line.startsWith("#")) {
				Match match = new Match(line.split("\\s"));
				if (match.getLength() >= minLength) matches.add(match);
				break;
			}
		}
		
		while (sc.hasNextLine()) {
			line = sc.nextLine();
			if (!line.isEmpty()) {
				Match match = new Match(line.split("\\s"));
				if (match.getLength() >= minLength) matches.add(match);			}
		}
		
		sc.close();
		System.out.println(matches.size());
		return matches;
	}
}
