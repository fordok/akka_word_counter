package net.fordok.actors.wordcounter;

import akka.actor.UntypedActor;
import net.fordok.messages.MapData;
import net.fordok.messages.ReduceData;
import net.fordok.messages.WordCount;

import java.util.HashMap;
import java.util.List;

/**
 * User: Fordok
 * Date: 6/18/14
 * Time: 11:14 PM
 */
public class ReduceActor extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof MapData) {
            MapData mapData = (MapData) message;
            getSender().tell(reduce(mapData.getDataList()));
        } else {
            unhandled(message);
        }
    }

    private ReduceData reduce(List<WordCount> dataList) {
        HashMap<String, Integer> reducedMap = new HashMap<String, Integer>();
        for (WordCount wordCount : dataList) {
            if (reducedMap.containsKey(wordCount.getWord())) {
                Integer value = (Integer) reducedMap.get(wordCount.getWord());
                value++;
                reducedMap.put(wordCount.getWord(), value);
            } else {
                reducedMap.put(wordCount.getWord(),Integer.valueOf(1));
            }
        }
        return new ReduceData(reducedMap);
    }
}
