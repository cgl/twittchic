package net.twittchic;

import net.twittchic.constants.Constants;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: ogrenci1
 * Date: 7/1/13
 * Time: 3:19 PM
 * To change this template use File | Settings | File Templates.
 */

public class Control {

    public  void processFromFile()
    {
        try {

            Scanner scannerOut = new Scanner(new FileInputStream(Constants.outFileName), Constants.fEncoding);
            Scanner scannerInput = new Scanner(new FileInputStream(Constants.trainingFileName), Constants.fEncoding);
            int total = 0;
            int pozitive = 0;
            int negative = 0;
            int count = 0;
            while(scannerOut.hasNextLine())
            {
                count++;
                String outline = scannerOut.nextLine().trim();
                List<ParsedWord> parseWord =  parse(outline);
                if(parseWord == null){
                    scannerInput.hasNextLine();
                    scannerInput.nextLine();
                }
                else{
                    if(scannerInput.hasNextLine())
                    {
                        String inputline = scannerInput.nextLine();
                        String line[] = inputline.split(" ");
                        for(ParsedWord p : parseWord)
                        {
                            if(line[p.getNum()-1].equals(p.getTxt()))
                                pozitive++;
                            else{
                                System.out.println("Negatif : "+p.getTxt()+" - "+line[p.getNum()-1]+" - "+count);
                                negative++;
                            }
                        }
                    }
                }
            }
            System.out.println("Toplam  : "+(pozitive+negative));
            System.out.println("Pozitif : "+pozitive);
            System.out.println("Negatif : "+negative);
            System.out.println("Yuzde   : "+calculataPercentage(pozitive,negative)+" %");
            scannerInput.close();
            scannerOut.close();
        }
        catch (IOException ex)
        {
               System.out.println("Control.process hata : "+ex.getMessage());
        }
    }
    public void process(List<Tweet> tweets)
    {
        try {
            Scanner scannerInput = new Scanner(new FileInputStream(Constants.trainingFileName), Constants.fEncoding);
            int total = 0;
            int pozitive = 0;
            int negative = 0;
            int count = 0;
            for (Tweet tweet : tweets)
            {
                count++;
                if(scannerInput.hasNextLine()){
                    String inputline = scannerInput.nextLine();
                    TreeMap <Integer, String> oovs = tweet.getOovs();
                    if(oovs.size()<1){
                        continue;
                    }
                    else{
                        String line[] = inputline.split(" ");
                        for (Integer ind : oovs.keySet()) {
                            String corrected = line[ind-1].replace("_"," ");
                            if(!corrected.startsWith("#")){
                                if(corrected.equalsIgnoreCase(oovs.get(ind)))
                                    pozitive++;
                                else{
                                    System.out.println("Negatif : "+oovs.get(ind)+" - "+corrected+" - "+count);
                                    negative++;
                                }
                            }
                        }
                    }
                }

            }
            System.out.println("Toplam  : "+(pozitive+negative));
            System.out.println("Pozitif : "+pozitive);
            System.out.println("Negatif : "+negative);
            System.out.println("Yuzde   : "+calculataPercentage(pozitive,negative)+" %");
            scannerInput.close();
        }
        catch (IOException ex)
        {
            System.out.println("Control.process hata : "+ex.getMessage());
        }
    }
    private double calculataPercentage(int pozitive,int negative)
    {
         return  (((double)pozitive)/(double)(pozitive+negative)) *100;
    }
    public List<ParsedWord> parse(String line){
         List<ParsedWord> result = null;
         String[] sep=line.split(";");
         if(sep.length==2)
         {
            if(sep[0].trim().substring(1,sep[0].trim().length()-1).length() == 0)
                return null;
            String[] sepNum = sep[0].trim().substring(1,sep[0].trim().length()-1).split(",");
            String[] sepWord = sep[1].trim().substring(1,sep[1].trim().length()-1).split(",");
            if(sepNum.length == sepWord.length)
            {
                    int len = sepNum.length;
                    //System.out.println("SepNum : "+len);
                    if(len == 1)
                    {
                        //System.out.println("Kelime : "+sepWord[0]);
                    }
                    if(len == 0)
                        return  null;
                    result = new ArrayList<ParsedWord>();
                    for(int i =0;i<len;i++)
                    {
                        ParsedWord parseWord = new ParsedWord();
                        parseWord.setNum(Integer.parseInt(sepNum[i].trim()));
                        parseWord.setTxt(sepWord[i].trim());
                        result.add(parseWord);
                    }
            }
            else
            {
                System.out.println("Control.parse : Parserda bir problem var !!!");
            }
         }
         return result;
    }

    public class ParsedWord{
        private int Num;
        private String txt;

        public int getNum() {
            return Num;
        }

        public void setNum(int num) {
            Num = num;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }
    }

}
