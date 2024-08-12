package org.regexjava;

public class Regex {
    private int matchStar(char character, String text) {
        int matchLenght = 0;
        for (char textCharacter : text.toCharArray()) {
            if (textCharacter != character) {
                break;
            }
            matchLenght++;
        }
        return matchLenght;
    }

    public int match(String regexp, String text) {
        if (regexp == null || text == null) {
            return -1;
        }

        for (int i = 0, j = 0; i < text.length(); j++, i++) {
            if (j >= regexp.length()) {
                return -1;
            }

            if (j + 1 < regexp.length() && regexp.charAt(j + 1) == '*') {
                int numCharsMatched = this.matchStar(regexp.charAt(j), text.substring(i, text.length()));
                i += numCharsMatched == 0 ? 0 : numCharsMatched - 1;
                j++;
            } else if (regexp.charAt(j) != text.charAt(i)) {
                return -1;
            }
        }

        return 0;
    }
}
