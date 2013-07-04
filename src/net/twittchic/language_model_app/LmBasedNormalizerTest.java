package net.twittchic.language_model_app;

import java.io.FileNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: tyr
 * Date: 7/4/13
 * Time: 1:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class LmBasedNormalizerTest {

    public static void main(String[] args) throws FileNotFoundException {
        LmBasedNormalizer lmNormalizer = new LmBasedNormalizer();
        lmNormalizer.prepare();
        lmNormalizer.printHashMap();

    }
}
