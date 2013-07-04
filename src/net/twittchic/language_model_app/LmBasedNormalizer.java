package net.twittchic.language_model_app;

import net.twittchic.Tweet;
import net.twittchic.constants.Constants;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: tyr
 * Date: 7/4/13
 * Time: 12:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class LMBasedNormalizer {


    private HashMap<String, Double> dictionary;



    public LMSystem (String filename) {
        this.dictionary = new HashMap<String, Double>();
    }



    public void prepare(){

    }





    public double scorer(int fileIndex, int wordIndex) {






        return 0.0;
    }



}
