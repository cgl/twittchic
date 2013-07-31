package net.twittchic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 */
import net.zemberek.erisim.Zemberek;
//import net.zemberek.islemler.AsciiDonusturucu;
//import net.zemberek.islemler.KelimeUretici;
//import net.zemberek.istatistik.Hece;
//import net.zemberek.yapi.Kelime;
//import net.zemberek.yapi.KelimeTipi;

import net.zemberek.tr.yapi.TurkiyeTurkcesi;

public class denZemb2 {

	/**
	 * @param args
	 */
	public static Zemberek z = new Zemberek(new TurkiyeTurkcesi());
	public static String turkCh = "ÇçÖöŞşİıĞğÜüÂâÛûÎî";
	public static String turkChLower = "çöşığüâûî";
	public static String turkChUpper = "ÇÖŞİĞÜÂÛÎ";
	
	public static ArrayList<String> soundDict(String str)
	{
		ArrayList<String> words = new ArrayList<String>();
		
		ArrayList<String> cand = new ArrayList<String>();
		
		try
		
		{
			BufferedReader br = new BufferedReader(new FileReader("resources/input/vocab"));

			String word = "";
			while((word = br.readLine()) != null)
			{
				words.add(word);
			}
			br.close();
			for(int i = 0; i < words.size(); i++)
			{
				String dictWord = words.get(i);
			
				if(dictWord.length() > 0 && str.length() > 0)
					if(soundex.sound(dictWord).equals(soundex.sound(str)))
						cand.add(dictWord);
			}

		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		return cand;
	}
	public static ArrayList<String> analyzerRepeat(String str)
	{
		ArrayList<String> al = new ArrayList<String>();
		
		
		Pattern p = Pattern.compile("(.)\\1");
		Matcher m = p.matcher("meerhabaa buguuuun nasilsssinizz");
		ArrayList<String> rep = new ArrayList<String>();
		while(m.find())
		{
			rep.add(m.group());
			System.out.println(m.group());
		}
	
		
		System.out.println(Integer.toBinaryString(5));
		System.out.println(Integer.toBinaryString(4));
		System.out.println(Math.pow(2, 3));
		int dec = rep.size();

		int no = (int) Math.pow(2, 3);
		System.out.println("*************");
		System.out.println(String.format("%" + dec + "s", "foo").replace(' ', '0'));  
		for(int i = 0; i < no; i++)
			System.out.println(String.format("%" + dec + "s", Integer.toBinaryString(i)).replace(' ', '0'));
		
		
		return al;
	}
	
	public static ArrayList<String> compAnalyzer(String str)
	{
		

		ArrayList<String> al = new ArrayList<String>();
//		String regex = "^([A-Z0-9]+[A-Za-z0-9,./\\-]*)+$";
		String regex;
		//asagidaki regex ile CabukCabukTak.. tarzindaki bilesik kelimeleri
		//ayirabiliriz
		regex = "([A-Z" + turkChUpper + "]+)[a-z" + turkChLower + "]*";
		
		//AByeGirebilecegiz
		Pattern pat = Pattern.compile(regex);
		Matcher mat = pat.matcher(str);
		while((mat.find()))
		{
			al.add(zembSound(mat.group()));
			
//			al.add(mat.group());
		}
		for(int i = 0; i < al.size(); i++)
			if(i != al.size() - 1)
				System.out.print(al.get(i) + " ");
			else
				System.out.println(al.get(i));
		
		
		return al;
		
	}
	
	
	
	
	
	public static String zembSound(String stri)
	{
		String[] str = z.oner(stri);
		
//		System.out.println(Arrays.toString(str));
		
		soundex snd = new soundex();
		String sndStri = snd.vowElim(stri);
		ArrayList<String> al = new ArrayList<String>();
		
		for(int i = 0; i < str.length; i++)
		{
			
//			System.out.print(str[i] + " ");
			if(sndStri.equals(snd.vowElim(str[i])))
			{
				
				al.add(str[i]);
			}
//				System.out.print(str[i] + ": " + snd.sound(str[i]) + " ");
		}
		for(int i = 0; i < str.length; i++)
		{
			if(z.asciidenTurkceye(stri).length > 0)
				if(str[i].equals(z.asciidenTurkceye(stri)[0].toLowerCase()))
				{
					
					al = new ArrayList<String>();
					al.add(z.asciidenTurkceye(stri)[0].toLowerCase());
					break;
				}
		}
		String zembSound = "";
		
		for(int i = 0; i < al.size(); i++)
		{
			if(al.get(i).contains(" "))
				continue;
			else
			{	
				zembSound = al.get(i);
//				System.out.println(al.get(i));
				break;
			}
				
		}
		if(zembSound.equals(""))
			return stri;
		return zembSound;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		zembSound("Çapuk");
		compAnalyzer("PekSevincliyiz");
		compAnalyzer("ÇapukÇapukTakiplesiyoruz");
		compAnalyzer("CokCalistik");
		compAnalyzer("PekGuzelDegilGibi");
		compAnalyzer("AptlAptlKonuşma");
		compAnalyzer("BurasıÇapulcuuDolmuşş");
		compAnalyzer("HaytGüselBeKardesim");
		compAnalyzer("MuhsinYazıcıoğlunun");
		compAnalyzer("KatilleriBulunsun");
		compAnalyzer("HayatımFilmOlsaİsmi");
		compAnalyzer("TurkishHuntersWantSPN");
		compAnalyzer("UyuşturucuyaHayırDiyor");
		compAnalyzer("TürkGençliği");
		
		
		System.out.println(soundDict("ağğğlama"));
		System.out.println(soundDict("mrb"));
		System.out.println(soundDict("slm"));
		System.out.println(soundDict("arkdş"));
		
//		z.kelimeAyristir("degilabdestle");
//		System.out.println(Arrays.toString(z.oner("salk")));
//		System.out.println(Arrays.toString(z.oner("Calistik")));
		

	}
	

}
