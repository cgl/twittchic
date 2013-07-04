package net.twittchic;

import net.twittchic.constants.Constants;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: ogrenci1
 * Date: 7/4/13
 * Time: 10:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class Assembler {
    public void process(List<Tweet> tweets)
    {
        List<String> resultList = new ArrayList<String>();
        List<String> annotatedList = readAnnotatedFile();
        String line = "";
        int i = 0;
        int buffer = 0;
        try{
            for(Tweet t : tweets)
            {
                line = annotatedList.get(i++);
                String[] words = line.split(" ");
                for(int ind : t.getNumberResults().keySet())
                {
                    buffer = ind;
                    words[ind-1] = t.getNumberResults().get(ind);
                }
                resultList.add(concatWords(words));
                System.out.println(i+" "+concatWords(words) +" - "+line);
            }
        }catch (Exception e)
        {
            System.out.println("Assembler.process error : "+e.getMessage()+" - "+buffer);
        }
        writeResultFile(resultList);

    }
    private String concatWords(String[] words)
    {
         String result = "";
         for(String s : words)
         {
             result+=s+" ";
         }
         return  result;
    }
    private List<String> readAnnotatedFile()
    {
        List<String> annotatedTweets = new ArrayList<String>();
        Scanner scannerInput = null;
        try
        {
            scannerInput = new Scanner(new FileInputStream(Constants.fFileName), Constants.fEncoding);
            String line = "";
            while(scannerInput.hasNextLine())
            {
                line = scannerInput.nextLine();
                annotatedTweets.add(line);
            }

        }
        catch (IOException ex)
        {
            System.out.println("Control.readFİle hata : "+ex.getMessage());
        }
        finally {
            scannerInput.close();
        }
        return annotatedTweets;
    }
    private void writeResultFile(List<String> lines)
    {
        try {
            FileWriter fileWriter = new FileWriter(Constants.resultNumFileName);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for(String l : lines)
            {
                 writer.append(l);
                 writer.newLine();
            }
            writer.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
