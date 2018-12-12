package com.mzaart.aquery.utils;

/**
 * This class contains utility methods for input validation.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Validator {

    /**
     * Checks if the Sting is null
     * @param str The String to check
     * @return true if the String is not null, false otherwise
     */
    public boolean notNull(String str) {
        return str != null;
    }

    /**
     * Checks if the String is empty
     * @param str The Sting to check
     * @return True if the String is not empty, false otherwise
     */
    public boolean notEmpty(String str) {
        return !str.isEmpty();
    }

    /**
     * Checks if the String is composed of white spce entirely
     * @param str The Sting to check
     * @return True if the String contains non-white space characters, false otherwise
     */
    public boolean notWhiteSpace(String str) {
        return str.trim().length() > 0;
    }

    /**
     * Checks if the String is not null and not empty or composed of white space entirely
     * @param str The Sting to check
     * @return True if the String contains non-white space characters, false otherwise
     */
    public boolean present(String str) {
        return notNull(str) && notWhiteSpace(str);
    }

    /**
     * Checks if a String is a number in base 10
     * @param str The String to check
     * @return True if the String is a number in base 10, false otherwise
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public boolean number(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if the String is composed entirely of alphabetic characters (spaces aren't allowed)
     * @param str The String to check
     * @return True if the String is composed entirely of alphabetic characters, false otherwise
     */
    public boolean alpha(String str) {
        return str.matches("[a-zA-Z]+");
    }

    /**
     * Checks if the String is composed of alphabetic characters (space are allowed)
     * @param str The String to check
     * @return True if the String is composed of alphabetic characters, false otherwise
     */
    public boolean alphaSpace(String str) {
        return str.matches("[a-zA-Z ]+");
    }
}
