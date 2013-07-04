package net.twittchic;


import  java.io.IOException;
import  java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.twittchic.*;
import net.twittchic.constants.Constants;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ogrenci1
 * Date: 7/3/13
 * Time: 2:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class Test {
      public static  void main(String[] args)
      {
          System.out.print("Tamam");
          List<String> list = readTokenizedFile();
          Pattern pattern = Pattern.compile("[0-9]+");
          Matcher m;
          for(String tweet : list)
          {
                m = pattern.matcher(tweet);
                while (m.find())
                {
                    System.out.print(m.group()+" ");
                }
                System.out.println("");
          }
      }
      public static List<String> readTokenizedFile(){

             List<String> list = new ArrayList<String>();
             Scanner scanner = null;
             try {
                 scanner = new Scanner(new FileInputStream(Constants.fFileName),Constants.fEncoding);
                 String line = "";
                 while(scanner.hasNextLine())
                 {
                        line = scanner.nextLine();
                        list.add(line);
                 }
             }
             catch (IOException ex)
             {
                  scanner.close();
                  System.out.println("Test.readTokenizedFile error : "+ex.getMessage());
             }
             return list;
      }
}
