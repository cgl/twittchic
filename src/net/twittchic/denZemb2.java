
import java.util.ArrayList;
import java.util.Arrays;

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
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		String st = "ile";

		String s[] = z.hecele("merhaba");
		System.out.println(Arrays.toString(s));
		String stri = "gelmiyorm";
		String[] str = z.oner(stri);
		System.out.println(Arrays.toString(z.asciiCozumle("masa")));
		
		System.out.println(z.kelimeDenetle("masa"));
		
		
		System.out.println(Arrays.toString(str));
		
		soundex snd = new soundex();
		String sndStri = snd.sound(stri);
		ArrayList<String> al = new ArrayList<String>();
		for(int i = 0; i < str.length; i++)
		{
			if(sndStri.equals(snd.sound(str[i])))
			{
				al.add(str[i]);
			}
//				System.out.print(str[i] + ": " + snd.sound(str[i]) + " ");
		}
		for(int i = 0; i < al.size(); i++)
		{
			if(al.get(i).contains(" "))
				continue;
			else
			{
				System.out.println(al.get(i));
				break;
			}
				
		}
	}

}
