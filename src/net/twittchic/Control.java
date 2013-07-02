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

    public void process(List<Tweet> tweets, boolean False)
    {
        try {
            Scanner scannerInput = new Scanner(new FileInputStream(Constants.trainingFileName), Constants.fEncoding);
            int total = 0;
            int pozitive = 0;
            int negative = 0;
            int count = 0;
            boolean found;
            for (Tweet tweet : tweets)
            {
                count++;
                if(scannerInput.hasNextLine()){
                    String inputline = scannerInput.nextLine();
                    TreeMap <Integer, String> oovs = tweet.getOovs();
                    TreeMap<Integer,ArrayList<String>> confusionSet = tweet.getConfusionSet();
                    if(oovs.size()<1){
                        continue;
                    }
                    else{
                        String line[] = inputline.split(" ");
                        for (Integer ind : confusionSet.keySet()) {
                            String corrected = line[ind-1].replace("_"," ");
                            if(!corrected.startsWith("#")){
                                ArrayList<String> confusions = confusionSet.get(ind);
                                found = false;
                                for (String word : confusions) {
                                    if(corrected.equalsIgnoreCase(word))   {
                                        pozitive++;
                                        found = true;
                                        break;
                                    }
                                }
                                if(!found){
                                    negative++;
                                    System.out.println("Negatif : "+oovs.get(ind)+" - "+corrected+" - "+count);

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

    public void processB1B2B3(List<Tweet> tweets)
    {
        try {
            Scanner scannerInput = new Scanner(new FileInputStream(Constants.trainingFileName), Constants.fEncoding);
            int total = 0;
            int pozitiveB1 = 0;
            int negativeB1 = 0;
            int pozitiveB2 = 0;
            int negativeB2 = 0;
            int pozitiveB3 = 0;
            int negativeB3 = 0;
            int count = 0;
            boolean found;
            for (Tweet tweet : tweets)
            {
                count++;
                if(scannerInput.hasNextLine()){
                    String inputline = scannerInput.nextLine();
                    TreeMap <Integer, String> oovs = tweet.getOovs();
                    TreeMap<Integer,ArrayList<String>> confusionSet = tweet.getConfusionSet();
                    if(oovs.size()<1){
                        continue;
                    }
                    else{
                        String line[] = inputline.split(" ");
                        for (Integer ind : confusionSet.keySet()) {
                            String corrected = line[ind-1].replace("_"," ");
                            String neg = "";
                            if(!corrected.startsWith("#")){
                                ArrayList<String> confusions = confusionSet.get(ind);
                                neg += oovs.get(ind) +" : ";
                                if(!confusions.get(0).equals("") & corrected.equalsIgnoreCase(confusions.get(0)))   {
                                    pozitiveB1++;
                                    }
                                else{
                                    negativeB1++;
                                    neg += " [B1] "+confusions.get(0)+" || ";
                                }
                                if(!confusions.get(1).equals("") & corrected.equalsIgnoreCase(confusions.get(1)))   {
                                    pozitiveB2++;
                                }
                                else{
                                    negativeB2++;
                                    neg += " [B2] "+confusions.get(1)+" || ";
                                }
                                if(!confusions.get(2).equals("") & corrected.equalsIgnoreCase(confusions.get(2)))   {
                                    pozitiveB3++;
                                }
                                else{
                                    negativeB3++;
                                    neg += " [B3] "+confusions.get(2)+" || ";
                                }
                            }
                            System.out.println("Negatif : "+neg+" - "+count);
                        }



                    }
                }

            }
            System.out.println("Toplam  : "+(pozitiveB1+negativeB1+pozitiveB2+negativeB2+pozitiveB3+negativeB3));
            System.out.println("Pozitif 1 : "+pozitiveB1);
            System.out.println("Negatif 1 : "+negativeB1);
            System.out.println("Pozitif 2 : "+pozitiveB2);
            System.out.println("Negatif 2 : "+negativeB2);
            System.out.println("Pozitif 3 : "+pozitiveB3);
            System.out.println("Negatif 3 : "+negativeB3);
            System.out.println("Yuzde  1  : "+calculataPercentage(pozitiveB1, negativeB1)+" %");
            System.out.println("Yuzde  2  : "+calculataPercentage(pozitiveB2, negativeB2)+" %");
            System.out.println("Yuzde  2  : "+calculataPercentage(pozitiveB3, negativeB3)+" %");
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
