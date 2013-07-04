package net.twittchic;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.twittchic.Tweet;
import net.twittchic.constants.Constants;
import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;





public class soundLevDict {



    //levenshtein distance'i uygulayacaksin
    //onceki hecedeki unlu inceyse bunu dondur
    /**
     * @param args
     */
    public static HashMap<String, ArrayList<String>> vocWords =
            new HashMap<String, ArrayList<String>>();

    public static String[] suffixesNoise = {"yom", "yon", "yoz", "cam", "can", "caz", "cem", "cen", "cez", "caım", "ceim",
            "caız", "ceiz"};
    public static String[] suffixesCorr = {"yorum", "yorsun", "yoruz", "cağım", "caksın", "cağız", "ceğim", "ceksin", "ceğiz", "cağım",
            "ceğim", "cağız", "ceğiz"};


    public static Zemberek z = new Zemberek(new TurkiyeTurkcesi());


    private static int minimum(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    public static int computeLevenshteinDistance(CharSequence str1,
                                                 CharSequence str2) {
        int[][] distance = new int[str1.length() + 1][str2.length() + 1];

        for (int i = 0; i <= str1.length(); i++)
            distance[i][0] = i;
        for (int j = 1; j <= str2.length(); j++)
            distance[0][j] = j;

        for (int i = 1; i <= str1.length(); i++)
            for (int j = 1; j <= str2.length(); j++)
                distance[i][j] = minimum(
                        distance[i - 1][j] + 1,
                        distance[i][j - 1] + 1,
                        distance[i - 1][j - 1]
                                + ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0
                                : 1));

        return distance[str1.length()][str2.length()];
    }

    public static String baselineC1(String str)
    {

        String sRet = "";
        Pattern p = Pattern.compile("(.)\\1+");
        Matcher m = p.matcher(str);
        ArrayList<String> rep = new ArrayList<String>();
        if(!m.find())
        {
            return sRet;
        }
        m = p.matcher(str);
        while(m.find())
        {
            rep.add(m.group());

        }


        int dec = rep.size();
        ArrayList<String> cand = new ArrayList<String>();
        int no = (int) Math.pow(2, dec);
        for(int i = 0; i < no; i++)
        {

            String res = str;
            String tmp = String.format("%" + dec + "s", Integer.toBinaryString(i)).replace(' ', '0');

            int ind = 0;
            String subs = "";
            for(int k = 0; k < tmp.length(); k++)
            {
                ind = res.indexOf(rep.get(k));
                if(tmp.charAt(k) == '0')
                {

                    String s = res.replaceFirst(rep.get(k), rep.get(k).substring(0, 1));
                    if(k == tmp.length() - 1)
                        subs += s.substring(0, s.length());
                    else
                        subs += s.substring(0, ind + 1);
                }
                else
                {
                    String s = res.replaceFirst(rep.get(k), rep.get(k).substring(0, 2));

                    if(k == tmp.length() - 1)
                        subs += s.substring(0, s.length());
                    else
                        subs += s.substring(0, ind + 2);
                }
                res = res.substring(ind + rep.get(k).length(), res.length());

            }

//				System.out.println(subs);
            if(z.kelimeDenetle(subs))
            {
//					System.out.println(subs);
                sRet = subs;
                cand.add(subs);

//					break;
            }
            //asagisi eklendi

        }
        int min = 100;
        int index = 0;
        for(int i = 0; i < cand.size(); i++)
        {
            int dist = computeLevenshteinDistance(cand.get(i), str);
            if(dist < min)
            {
                index = i;
                min = dist;
            }
        }
        if(cand.size() > 0)
        {
            //geri dondur
//				System.out.println(sRet);
            sRet = cand.get(index);
        }
        return sRet;
    }
    public static String baselineC11(String str)
    {

        String sRet = "";
        Pattern p = Pattern.compile("(.)\\1+");
        Matcher m = p.matcher(str);
        ArrayList<String> rep = new ArrayList<String>();
        if(!m.find())
        {
            String suff = "";



            for(int w = 0; w < suffixesNoise.length; w++)
            {
                if(str.contains(suffixesNoise[w]))
                {


                    String yourString = str;
                    StringBuilder b = new StringBuilder(yourString);
                    b.replace(yourString.lastIndexOf(suffixesNoise[w]), yourString.lastIndexOf(suffixesNoise[w]) + suffixesNoise[w].length(), suffixesCorr[w] );
                    suff = b.toString();


//						suff = str.replace(suffixesNoise[w], suffixesCorr[w]);
                    String[] prop = z.oner(suff);
                    if(z.kelimeDenetle(suff))
                    {
                        //geri dondur
//							System.out.println(suff);
                        return suff;
                    }
                    else
                    {

                        for(int j = 0; j < prop.length; j++)
                        {
                            if(computeLevenshteinDistance(prop[j], suff) == 1)
                            {
                                //geri dondur
//									System.out.println(prop[j]);
                                return prop[j];
                            }
                        }
                    }
                }
            }


            return sRet;
        }
        m = p.matcher(str);
        while(m.find())
        {
            rep.add(m.group());

        }


        int dec = rep.size();
        ArrayList<String> cand = new ArrayList<String>();
        int no = (int) Math.pow(2, dec);
        for(int i = 0; i < no; i++)
        {

            String res = str;
            String tmp = String.format("%" + dec + "s", Integer.toBinaryString(i)).replace(' ', '0');

            int ind = 0;
            String subs = "";
            for(int k = 0; k < tmp.length(); k++)
            {
                ind = res.indexOf(rep.get(k));
                if(tmp.charAt(k) == '0')
                {

                    String s = res.replaceFirst(rep.get(k), rep.get(k).substring(0, 1));
                    if(k == tmp.length() - 1)
                        subs += s.substring(0, s.length());
                    else
                        subs += s.substring(0, ind + 1);
                }
                else
                {
                    String s = res.replaceFirst(rep.get(k), rep.get(k).substring(0, 2));

                    if(k == tmp.length() - 1)
                        subs += s.substring(0, s.length());
                    else
                        subs += s.substring(0, ind + 2);
                }
                res = res.substring(ind + rep.get(k).length(), res.length());

            }

//				System.out.println(subs);
            if(z.kelimeDenetle(subs))
            {
//					System.out.println(subs);
                sRet = subs;
                cand.add(subs);

//					break;
            }
            //asagisi eklendi
            else
            {
                String suff = "";


                for(int w = 0; w < suffixesNoise.length; w++)
                {
                    if(subs.contains(suffixesNoise[w]))
                    {

                        String yourString = subs;
                        StringBuilder b = new StringBuilder(yourString);
                        b.replace(yourString.lastIndexOf(suffixesNoise[w]), yourString.lastIndexOf(suffixesNoise[w]) + suffixesNoise[w].length(), suffixesCorr[w] );
                        suff = b.toString();
//							System.out.println("kelimmeeeeeeeeee: 1- " + str + ", 2- " + suff);

//							suff.replace(suffixesNoise[w], suffixesCorr[w]);

                        if(z.kelimeDenetle(suff))
                        {
                            //geri dondur
//								System.out.println(suff);
                            return suff;
                        }
                        else
                        {
                            String[] prop = z.oner(suff);
                            for(int j = 0; j < prop.length; j++)
                            {
                                if(computeLevenshteinDistance(prop[j], suff) == 1)
                                {
                                    //geri dondur
//										System.out.println(prop[j]);
                                    return prop[j];
                                }
                            }
                        }


                    }
                }



            }
        }
        int min = 100;
        int index = 0;
        for(int i = 0; i < cand.size(); i++)
        {
            int dist = computeLevenshteinDistance(cand.get(i), str);
            if(dist < min)
            {
                index = i;
                min = dist;
            }
        }
        if(cand.size() > 0)
        {
            //geri dondur
//				System.out.println(sRet);
            sRet = cand.get(index);
        }
        return sRet;

    }

    public static String baselineC2(String word)
    {

        String retWord = "";
        String el = baselineC11(word.toLowerCase());
        try
        {

            if(el.length() == 0)
            {


                BufferedReader br = new BufferedReader(new FileReader(Constants.vocabulary));
                ArrayList<String> cand = new ArrayList<String>();
                //asagidakini kullanabilirsin
                ArrayList<String> candSound = new ArrayList<String>();
                String firstChar = Character.toString(word.charAt(0));
                if(!vocWords.containsKey(firstChar))
                    return retWord;
                ArrayList<String> vocAl = vocWords.get(firstChar);
                for(int w = 0; w < vocAl.size(); w++)
                {
                    String line = vocAl.get(w);
                    //asagidakileri de
                    String soundDict = soundex.sound(line);
                    String soundWord = soundex.sound(word);
                    String tmp = soundex.vowElim(line);
                    String mainWord = soundex.vowElim(word);
                    if(mainWord.equals(tmp) && !mainWord.equals(line))
                    {
                        //sildim
                        retWord = line;ArrayList<String> dist1 = new ArrayList<String>();
                        for(int i = 0; i < cand.size(); i++)
                        {
//								System.out.print(i + ": " + cand.get(i) + " ");
                            int dist = computeLevenshteinDistance(cand.get(i), word);
                            if(dist == 1)
                                dist1.add(line);
                        }
//							System.out.println();
                        int max = -1;
//							System.out.println(line);
                        //ekledim
//							if(z.kelimeDenetle(line))
                        cand.add(line);
//							break;
                    }
                }



                br.close();


                //asagidakini uncomment yapacaksin

                int min = 100;
                int index = 0;
                for(int i = 0; i < cand.size(); i++)
                {
                    int dist = computeLevenshteinDistance(cand.get(i), word);
                    if(dist < min)
                    {
                        index = i;
                        min = dist;
                    }
                }
                if(cand.size() > 0)
                {
//						System.out.println(sRet);
                    retWord = cand.get(index);
                }
                else
                {
                    //asagidakini sildim
//						if(candSound.size() > 0)
//							retWord = candSound.get(0);
                }
            }
            else
                return el;

        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
//			System.out.println(retWord);
        return retWord;

    }
    public static String elimDupl(String str)
    {

        String sRet = "";
        Pattern p = Pattern.compile("(.)\\1+");
        Matcher m = p.matcher(str);
        ArrayList<String> rep = new ArrayList<String>();
        if(!m.find())
        {
            String suff = "";



            for(int w = 0; w < suffixesNoise.length; w++)
            {
                if(str.contains(suffixesNoise[w]))
                {


                    String yourString = str;
                    StringBuilder b = new StringBuilder(yourString);
                    b.replace(yourString.lastIndexOf(suffixesNoise[w]), yourString.lastIndexOf(suffixesNoise[w]) + suffixesNoise[w].length(), suffixesCorr[w] );
                    suff = b.toString();


//					suff = str.replace(suffixesNoise[w], suffixesCorr[w]);
                    String[] prop = z.oner(suff);
                    if(z.kelimeDenetle(suff))
                    {
                        System.out.println(suff);
                        return suff;
                    }
                    else
                    {

                        for(int j = 0; j < prop.length; j++)
                        {
                            if(computeLevenshteinDistance(prop[j], suff) == 1)
                            {

                                System.out.println(prop[j]);
                                return prop[j];
                            }
                        }
                    }
                    //üzmiycem denemesi - vocab'da üzmeyeceğim olmadığı için doğru çalışamaz, yani vocab'ın hatası
//					try
//					{
//						System.out.println("sakjhdkjsahdahdkashksashkjadhs");
//						BufferedReader br = new BufferedReader(new FileReader("vocab"));
//						String line = "";
//						//asagidakini kullanabilirsin
//						ArrayList<String> candSound = new ArrayList<String>();
//						String vowEl = soundex.vowElimY(suff);
//						while((line = br.readLine()) != null)
//						{
//							
//							if(vowEl.equals(soundex.vowElimY(line)))
//							{
//								System.out.println(line);
//								return line;
//							}
//						}
//						br.close();
//					}
//					catch(IOException ioe)
//					{
//						ioe.printStackTrace();
//					}

//					for(int j = 0; j < prop.length; j++)
//					{
//						if(soundex.vowElimY(prop[j]).equals(suff))
//						{
//							
//							System.out.println(prop[j]);
//							return prop[j];
//						}
//					}

                    //buraya koyacaksin
                }
            }


            return sRet;
        }
        m = p.matcher(str);
        while(m.find())
        {
            rep.add(m.group());

        }


        int dec = rep.size();
        ArrayList<String> cand = new ArrayList<String>();
        int no = (int) Math.pow(2, dec);
        for(int i = 0; i < no; i++)
        {

            String res = str;
            String tmp = String.format("%" + dec + "s", Integer.toBinaryString(i)).replace(' ', '0');

            int ind = 0;
            String subs = "";
            for(int k = 0; k < tmp.length(); k++)
            {
                ind = res.indexOf(rep.get(k));
                if(tmp.charAt(k) == '0')
                {

                    String s = res.replaceFirst(rep.get(k), rep.get(k).substring(0, 1));
                    if(k == tmp.length() - 1)
                        subs += s.substring(0, s.length());
                    else
                        subs += s.substring(0, ind + 1);
                }
                else
                {
                    String s = res.replaceFirst(rep.get(k), rep.get(k).substring(0, 2));

                    if(k == tmp.length() - 1)
                        subs += s.substring(0, s.length());
                    else
                        subs += s.substring(0, ind + 2);
                }
                res = res.substring(ind + rep.get(k).length(), res.length());

            }

//			System.out.println(subs);
            if(z.kelimeDenetle(subs))
            {
//				System.out.println(subs);
                sRet = subs;
                cand.add(subs);

//				break;
            }
            //asagisi eklendi
            else
            {
                String suff = "";


                for(int w = 0; w < suffixesNoise.length; w++)
                {
                    if(subs.contains(suffixesNoise[w]))
                    {

                        String yourString = subs;
                        StringBuilder b = new StringBuilder(yourString);
                        b.replace(yourString.lastIndexOf(suffixesNoise[w]), yourString.lastIndexOf(suffixesNoise[w]) + suffixesNoise[w].length(), suffixesCorr[w] );
                        suff = b.toString();
//						System.out.println("kelimmeeeeeeeeee: 1- " + str + ", 2- " + suff);

//						suff.replace(suffixesNoise[w], suffixesCorr[w]);

                        if(z.kelimeDenetle(suff))
                        {
                            System.out.println(suff);
                            return suff;
                        }
                        else
                        {
                            String[] prop = z.oner(suff);
                            for(int j = 0; j < prop.length; j++)
                            {
                                if(computeLevenshteinDistance(prop[j], suff) == 1)
                                {
                                    System.out.println(prop[j]);
                                    return prop[j];
                                }
                            }
                        }

//						try
//						{
//							System.out.println("sakdgadjskgdasjkshskajagdsasdkg: " + suff);
//							BufferedReader br = new BufferedReader(new FileReader("vocab"));
//							String line = "";
//							//asagidakini kullanabilirsin
//							ArrayList<String> candSound = new ArrayList<String>();
//							String vowEl = soundex.vowElimY(suff);
//							while((line = br.readLine()) != null)
//							{
//								
//								if(vowEl.equals(soundex.vowElimY(line)))
//								{
//									
//									System.out.println(line);
//									return line;
//								}
//							}
//							br.close();
//						}
//						catch(IOException ioe)
//						{
//							ioe.printStackTrace();
//						}

                    }
                }



            }
        }
        int min = 100;
        int index = 0;
        for(int i = 0; i < cand.size(); i++)
        {
            int dist = computeLevenshteinDistance(cand.get(i), str);
            if(dist < min)
            {
                index = i;
                min = dist;
            }
        }
        if(cand.size() > 0)
        {
            System.out.println(sRet);
            sRet = cand.get(index);
        }
//		System.out.println(cand);
        return sRet;
    }

    public static String elSound(String word)
    {

//		HashMap<String, Integer> hm = new HashMap<String, Integer>();
//		try
//		{
//			
//			BufferedReader brVoc = new BufferedReader(new FileReader(Constants.vocabularyFrequency));
//			String line_ = "";
//			while((line_ = brVoc.readLine()) != null)
//			{
//				String rowN = line_.split("\t")[0];
//				Integer cnt = Integer.parseInt(line_.split("\t")[1]);
////				int count = hm.containsKey(rowN) ? hm.get(rowN) : 0;
//				if(!hm.containsKey(rowN))
//					hm.put(rowN, cnt);
//			}
//			brVoc.close();
//			
//		}
//		catch(IOException ioe)
//		{
//			ioe.printStackTrace();
//		}
//		
        String retWord = "";
        String el = elimDupl(word.toLowerCase());
        try
        {

            if(el.length() == 0)
            {


                BufferedReader br = new BufferedReader(new FileReader(Constants.vocabulary));
                String line = "";
                ArrayList<String> cand = new ArrayList<String>();
                //asagidakini kullanabilirsin
                ArrayList<String> candSound = new ArrayList<String>();
                while((line = br.readLine()) != null)
                {
                    //asagidakileri de
                    String soundDict = soundex.sound(line);
                    String soundWord = soundex.sound(word);
                    String tmp = soundex.vowElim(line);
                    String mainWord = soundex.vowElim(word);
                    if(mainWord.equals(tmp) && !mainWord.equals(line))
                    {
                        //sildim
                        retWord = line;ArrayList<String> dist1 = new ArrayList<String>();
                        for(int i = 0; i < cand.size(); i++)
                        {
//							System.out.print(i + ": " + cand.get(i) + " ");
                            int dist = computeLevenshteinDistance(cand.get(i), word);
                            if(dist == 1)
                                dist1.add(line);
                        }
//						System.out.println();
                        int max = -1;
//						System.out.println(line);
                        //ekledim
//						if(z.kelimeDenetle(line))
                        cand.add(line);
//						break;
                    }
                    //sildim
//					if(soundWord.equals(soundDict) && !soundWord.equals(line))
//					{
//						//sildim
////						retWord = line;
////						System.out.println(line);
//						//ekledim
//						if(z.kelimeDenetle(line))
//							candSound.add(line);
////						break;
//					}
                }
                br.close();

                //asagidakini ekledim
//				ArrayList<String> dist1 = new ArrayList<String>();
//				for(int i = 0; i < cand.size(); i++)
//				{
//					System.out.print(i + ": " + cand.get(i) + " ");
//					int dist = computeLevenshteinDistance(cand.get(i), word);
//					if(dist == 1)
//						dist1.add(line);
//				}
//				System.out.println();
//				int max = -1;
//				for(int i = 0; i < dist1.size(); i++)
//				{
//					if(hm.containsKey(dist1.get(i)))
//						if(hm.get(dist1.get(i)) > max)
//							retWord = dist1.get(i);
//				}

                //asagidakini uncomment yapacaksin

                int min = 100;
                int index = 0;
                for(int i = 0; i < cand.size(); i++)
                {
                    int dist = computeLevenshteinDistance(cand.get(i), word);
                    if(dist < min)
                    {
                        index = i;
                        min = dist;
                    }
                }
                if(cand.size() > 0)
                {
//					System.out.println(sRet);
                    retWord = cand.get(index);
                }
                else
                {
                    //asagidakini sildim
//					if(candSound.size() > 0)
//						retWord = candSound.get(0);
                }
            }
            else
                return el;

        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
        System.out.println(retWord);
        return retWord;
    }


    public  static List<Tweet> process(List<Tweet> tweets){


        //basladi
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(Constants.vocabulary));
            String line = "";
            while((line = br.readLine()) != null)
            {
                String firstCh = Character.toString(line.charAt(0));
                ArrayList<String> al = vocWords.containsKey(firstCh) ? vocWords.get(firstCh):
                        new ArrayList<String>();
                al.add(line);
                vocWords.put(firstCh, al);
                //asagidakileri de

            }
            br.close();

        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }

        //bitti






        for (Tweet tweet : tweets) {
            TreeMap<Integer, String> oovs = tweet.getOovs();
            if(oovs.size() > 0){
                String iv;
                for (Integer ind : oovs.keySet()) {
                    iv = oovs.get(ind);
                    if(iv.length() > 0)   {
                        //System.out.println(22222);
                        //System.out.println(44444);
                        String str = baselineC2(iv);
                        if(!str.equals(""))
                            oovs.put(ind,baselineC2(iv));
                    }
                }
            }
        }
        return tweets;
    }




    public static void main(String[] args) {
        // TODO Auto-generated method stub


        //asagidakiler eger zemberek oneri propose edemiyorsa calistirilmali
        //asagidakiler istemedigimiz kelimeler return ediyorsa context analysis yapmaliyiz

        //System.out.println(baselineC2("yapıyooom"));
        elSound("spr");// - süper
        elSound("ayn");// - aynen
        elSound("dvm");// - devam

        elSound("Aaaaaaraaaaaaaaabaaaamddddaaa");
        elSound("ediyomuşum");
        elSound("kooooonnnnuşşşyoooooooooooooozzzz");

        elSound("yapacammmmmmmm"); //sozlukte var, o yuzden yapacagim'i donduremez
        elSound("yapacan");
        elSound("yapacazz");
        elSound("ettiimm"); //etim olarak da dondurebiliyor, levensh. bunu duzeltiyor
        elSound("edicem");
        elSound("yapyrsnnnn"); //context analysis gerekiyor -> yapıyorsun || yapıyorsan ?
        elSound("nslsn");
        elSound("mrbbbbbbbbbbbbbbb");
        elSound("buldmmmm"); //buldum vs bildim, levensh. bunu duzeltiyor
        elSound("sevdmmmm"); //sevdam vs sevdim. Vowel prob. burada kullanilabilir (Con. Anal.)
        elSound("anlymadıııııımmmmmmmmm");
        elSound("hşçaaaaakl");
        elSound("a");
        elSound("sçmalamaaaaa");
        elSound("yapçam"); //soundex ile yapcan mi olur, yapcam mi?
        elSound("edeceim"); // edecem, edecegim - her ikisinin de dist.'i 1
        elSound("yapıyom"); //yapiyorum'u donduremez, cunku yapiyom da zaten sozlukte
        elSound("anlaymyorumm");
        elSound("herkezzzzz"); //herkez sozlukte var zaten, o yuzden yanlis
        elSound("isstmiiiiiiiiiiiiyoooooruuuummmmm");
        elSound("nbrrrrrrrrrrrrrrr");
        System.out.println(Arrays.toString(z.oner("herkez")));
        //aslinda sozlugun bu kadar comprehensive olmasi yanlis
//		System.out.println(Arrays.toString(z.oner("hoşçakal"))); //zemb bunu donduremiyor
//		//yapacan - yapacaksın, bunu bulamayız
//		System.out.println(Arrays.toString(z.oner("yapacan")));
//		System.out.println(Arrays.toString(z.oner("herkez")));

        //yapacam yapacağım   cam
        //blduum -> bulduğum || buldum ?
        // [herken, herkes, merkez, herke, herkiz, Çerkez, her kez, herk ez] -> herkes
        //Yukarisi icin kelimelerin frekansini goz onunde tutmak zorundayiz
        //biliyomusun vs. biyometrik && myom
        //alacaz vs. cazibe
        //yapacan vs. afacan
    }
}