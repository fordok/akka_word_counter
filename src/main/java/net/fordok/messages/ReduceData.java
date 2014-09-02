package net.fordok.messages;

import java.util.HashMap;

/**
 * User: Fordok
 * Date: 6/18/14
 * Time: 10:47 PM
 */
public class ReduceData {
    private final HashMap<String, Integer> reduceDataList;

    public ReduceData(HashMap<String, Integer> reduceDataList) {
        this.reduceDataList = reduceDataList;
    }

    public HashMap<String, Integer> getReduceDataList() {
        return reduceDataList;
    }
}
