package net.formiks.messages;

/**
 * User: Fordok
 * Date: 6/18/14
 * Time: 10:42 PM
 */
public class WordCount {
    private final String word;
    private final Integer count;

    public WordCount(String word, Integer count) {
        this.word = word;
        this.count = count;
    }

    public String getWord() {
        return word;
    }

    public Integer getCount() {
        return count;
    }
}
