package net.formiks.messages;

import java.util.List;

/**
 * User: Fordok
 * Date: 6/18/14
 * Time: 10:38 PM
 */
public class MapData {
    private final List<WordCount> dataList;

    public List<WordCount> getDataList() {
        return dataList;
    }

    public MapData(List<WordCount> dataList) {
        this.dataList = dataList;
    }
}
