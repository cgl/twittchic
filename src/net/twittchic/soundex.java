package net.twittchic;
//Y ve H ne olacak

public class soundex {

	/**
	 * @param args
	 */
	//asagisi once AEIOUHWY idi
	public static String vow = "AEIOUHW";
	public static String str1 = "BFPV";
	public static String str2 = "CGJKQSXZ";
	public static String str3 = "DT";
	public static String str4 = "L";
	public static String str5 = "MN";
	public static String str6 = "R";
	public static String str7 = "Y";
	
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
			else if(str7.contains(Character.toString(ch)))
				temp1 += "7";
			
		}
		String temp2 = "";
		temp2 += Character.toString(temp1.charAt(0));
		for(int i = 1; i < temp1.length(); i++)
		{

			if(temp1.charAt(i) != temp2.charAt(temp2.length() - 1))
				temp2 += Character.toString(temp1.charAt(i));

		}
		String temp3 = "";

		temp3 = temp2.replaceAll("0", "");
		int N = 20;
		if(temp3.length() < N)
		{
			for(int i = temp3.length(); i < N; i++)
				temp3 += "0";
		}


		return temp3;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        System.out.print(sound("merhaba"));
	}

}
