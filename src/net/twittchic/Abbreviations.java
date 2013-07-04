package net.twittchic;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import net.twittchic.constants.Constants;

public class Abbreviations {

	/**
	 * @param args
	 */
	
	//oncesinde kelimeleri lowercase yapmaliyiz, ayriyeten duplicate subsequent character'leri elemeliyiz
	public static String[] abbr = {"unf", "fav", "rt", ":)", ";)", "(:", "(;",
		"olm", "bkz", "bi", "bjk", "chp", "akp", "di", 
		"FB", "kib"};
	public static String[] abbrCnter = {"unfollow", "favori", "retweet", "(gülücük)", "(göz kırpma)", "(gülücük)", "(göz kırpma)",
		"oğlum", "bakınız", "bir", "beşiktaş jimnastik kulübü", "Cumhuriyet Halk Partisi", "Adalet ve Kalkınma Partisi", "değil",
		"Fenerbahçe", "kendine iyi bak"};
	
	
	public static void main(String[] args) {
		List<String> tokenizedList = readTokenizedFile();
		String[] words;
		int i = 0;
		for (String s : tokenizedList) {
			words = s.split(" ");
			i++;
			for (String w : words) {
				if(w.trim().length()<=3)
				{
					System.out.println(w);
				}
			}
		}
		

	}

	public static List<String> readTokenizedFile() {
		List<String> list = new ArrayList<String>();
		try {
			Scanner scanner = new Scanner(new FileInputStream(
					Constants.fFileName), Constants.fEncoding);
			String line = "";
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				list.add(line);
			}
			scanner.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return list;
	}

}
