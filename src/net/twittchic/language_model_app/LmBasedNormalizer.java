package net.twittchic.language_model_app;

import net.twittchic.Tweet;
import net.twittchic.constants.Constants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: tyr
 * Date: 7/4/13
 * Time: 12:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class LmBasedNormalizer {



    //key: "line_number,word_index,word", value: probability
    private HashMap<String, Double> dictionary = new HashMap<String, Double>();
    private String filename = "";


    public LmBasedNormalizer () {
        this(Constants.substituteFileName);
    }

    public LmBasedNormalizer (String filename) {
        this.filename = filename;
    }

    public void prepare() throws FileNotFoundException {

        // HashMap construction

        System.out.format("File open: %s\n", filename);
        Scanner scannerInput = new Scanner(new FileInputStream(filename), Constants.fEncoding);
        System.out.println("Processing started");
        String line = "";
        while (scannerInput.hasNextLine()){
            line = scannerInput.nextLine();
            String [] strArr = line.split(" ");
            String key = strArr[0];
            Double val = Double.parseDouble(strArr[1].trim());
            dictionary.put(key, val);
        }
        System.out.println("Processing finished");

    }

    public void printHashMap(){
        for (String key: dictionary.keySet()){
            System.out.format("%20s, %10.5f\n", key, dictionary.get(key));
        }
    }

    public double scorer(int lineNumber, int wordNumber, String word) {

        // Line number, tweet, candidate_word, oov_index

        String key = "" + lineNumber + "," + wordNumber + "," + word;
        return dictionary.get(key);
    }




}
