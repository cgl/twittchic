//Y ve H ne olacak


//ETCEM [eteğim, etceğiz, emceğim, enceğim]
package net.twittchic;

public class soundex {

	/**
	 * @param args
	 */
	public static String vow = "AEIOUHWY";
	public static String str1 = "BFPV";
	public static String str2 = "CGJKQSXZ";
	public static String str3 = "DT";
	public static String str4 = "L";
	public static String str5 = "MN";
	public static String str6 = "R";
	
	public static String vowEl = "aeiou";
//	public static String vowElim(String word)
//	{
//
//		String lowWord = word.toLowerCase();
//
//		String temp1 = Character.toString(lowWord.charAt(0));
//		for(int i = 1; i < lowWord.length(); i++)
//		{
//			char ch = lowWord.charAt(i);
//			if(lowWord.charAt(i) == 'ö')
//				ch = 'o';
//			else if(lowWord.charAt(i) == 'ı')
//				ch = 'i';
//			else if(lowWord.charAt(i) == 'ü')
//				ch = 'u';
//			if(!vowEl.contains(Character.toString(ch)))
//			{
//				temp1 += Character.toString(ch);
//			}
//			else if(vowEl.contains(Character.toString(ch)))
//				temp1 += "0";
//			
//		}
//		
//		String temp2 = "";
//		
//		temp2 += Character.toString(temp1.charAt(0));
//		for(int i = 1; i < temp1.length(); i++)
//		{
//			
//			if(temp1.charAt(i) != temp2.charAt(temp2.length() - 1))
//				temp2 += Character.toString(temp1.charAt(i));
//			
//		}
//		temp2 = temp2.replaceAll("0", "");
//
//		return temp2;
//	}
	
	public static String str1_ = "bp";
	public static String str2_ = "sşz";
	public static String str3_ = "cç";
	public static String str4_ = "kg";
//	public static String str5_ = "mn";
//	public static String str6_ = "r";

	
	public static String vowElim(String word)
	{

		String lowWord = word.toLowerCase();

		String temp1 = Character.toString(lowWord.charAt(0));
		for(int i = 1; i < lowWord.length(); i++)
		{
			char ch = lowWord.charAt(i);
			if(lowWord.charAt(i) == 'ö')
				ch = 'o';
			else if(lowWord.charAt(i) == 'ı')
				ch = 'i';
			else if(lowWord.charAt(i) == 'ü')
				ch = 'u';
			else if(lowWord.charAt(i) == 'h')
				ch = 'u';
			
//			if(!vowEl.contains(Character.toString(ch)))
//			{
//				temp1 += Character.toString(ch);
//			}
//			else if(vowEl.contains(Character.toString(ch)))
//				temp1 += "0";
			
			if(vowEl.contains(Character.toString(ch)))
				temp1 += "0";
			else if(str1_.contains(Character.toString(ch)))
				temp1 += "1";
			else if(str2_.contains(Character.toString(ch)))
				temp1 += "2";
			else if(str3_.contains(Character.toString(ch)))
				temp1 += "3";
			else if(str4_.contains(Character.toString(ch)))
				temp1 += "4";
			else
				temp1 += lowWord.charAt(i);
			
		}
		
		String temp2 = "";
		
		temp2 += Character.toString(temp1.charAt(0));
		for(int i = 1; i < temp1.length(); i++)
		{
			
			if(temp1.charAt(i) != temp2.charAt(temp2.length() - 1))
				temp2 += Character.toString(temp1.charAt(i));
			
		}
		temp2 = temp2.replaceAll("0", "");

		return temp2;
	}
	
	
//	public static String vowAndMultElim(String word)
//	{
//		String lowWord = word.toLowerCase();
//
//		String temp1 = Character.toString(lowWord.charAt(0));
//		for(int i = 1; i < lowWord.length(); i++)
//		{
//			char ch = lowWord.charAt(i);
//			if(lowWord.charAt(i) == 'ö')
//				ch = 'o';
//			else if(lowWord.charAt(i) == 'ı')
//				ch = 'i';
//			else if(lowWord.charAt(i) == 'ü')
//				ch = 'u';
//			if(!vowEl.contains(Character.toString(ch)))
//				temp1 += Character.toString(ch);
//			
//		}
//		String temp2 = "";
//		
//		temp2 += Character.toString(temp1.charAt(0));
//		for(int i = 1; i < temp1.length(); i++)
//		{
//			
//			if(temp1.charAt(i) != temp2.charAt(temp2.length() - 1))
//				temp2 += Character.toString(temp1.charAt(i));
//			
//		}
//
//		return temp2;
//
//	}
	
	public static String sound(String word)
	{
	
		String uppWord = word.toUpperCase();
		String temp1 = Character.toString(uppWord.charAt(0));
		for(int i = 1; i < uppWord.length(); i++)
		{
			char ch = uppWord.charAt(i);
			if(uppWord.charAt(i) == 'Ö')
				ch = 'O';
			else if(uppWord.charAt(i) == 'Ç')
				ch = 'C';
			else if(uppWord.charAt(i) == 'Ş')
				ch = 'S';
			else if(uppWord.charAt(i) == 'İ')
				ch = 'I';
			else if(uppWord.charAt(i) == 'Ğ')
				ch = 'A';
			else if(uppWord.charAt(i) == 'Ü')
				ch = 'U';
			if(vow.contains(Character.toString(ch)))
				temp1 += "0";
			else if(str1.contains(Character.toString(ch)))
				temp1 += "1";
			else if(str2.contains(Character.toString(ch)))
				temp1 += "2";
			else if(str3.contains(Character.toString(ch)))
				temp1 += "3";
			else if(str4.contains(Character.toString(ch)))
				temp1 += "4";
			else if(str5.contains(Character.toString(ch)))
				temp1 += "5";
			else if(str6.contains(Character.toString(ch)))
				temp1 += "6";
			
		}
		String temp2 = "";
//		System.out.println("1: " + temp1);
		temp2 += Character.toString(temp1.charAt(0));
		for(int i = 1; i < temp1.length(); i++)
		{
			
			if(temp1.charAt(i) != temp2.charAt(temp2.length() - 1))
				temp2 += Character.toString(temp1.charAt(i));
			
		}
//		System.out.println("2: " + temp2);
		String temp3 = "";
		
		temp3 = temp2.replaceAll("0", "");
		int N = 20;
		if(temp3.length() < N)
		{
			for(int i = temp3.length(); i < N; i++)
				temp3 += "0";
		}
//		System.out.println("3: " + temp3.substring(0, 20));
		
		return temp3;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		sound("ÇEKOSLOVAKYALILAŞTIRAMADIKLARIMIZDANMISINIZ");
		
		
		System.out.println(vowElim("cücüşdekb"));
		
//		ETCEM [eteğim, etceğiz, emceğim, enceğim]
//		System.out.println(vowElim("ETCEM"));
//		System.out.println(vowElim("etceğiz"));
//		sound("mrb");
//		System.out.println(sound("herkezzzz"));
//		System.out.println(sound("harçsız"));
//		System.out.println("harçsız".toUpperCase());
	}

}
