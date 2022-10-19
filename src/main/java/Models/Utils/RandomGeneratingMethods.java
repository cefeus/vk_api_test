package Models.Utils;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomGeneratingMethods {

    public static String getRandomString(int length,boolean useLetters, boolean useNumbers )
    {
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

}
