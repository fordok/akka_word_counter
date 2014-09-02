package net.fordok.actors.wordcounter;

import akka.actor.UntypedActor;
import net.fordok.messages.ReduceData;
import net.fordok.messages.Result;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * User: Fordok
 * Date: 6/18/14
 * Time: 11:22 PM
 */
public class AggregateActor extends UntypedActor {

    private Map<String, Integer> finalReduceMap = new TreeMap<String, Integer>();

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof ReduceData) {
            ReduceData reduceData = (ReduceData) message;
            aggregateInMemoryReduce(reduceData.getReduceDataList());
        } else if (message instanceof Result) {
            getSender().tell(finalReduceMap.toString() + "total unique word count : " + finalReduceMap.size());
        } else {
            unhandled(message);
        }
    }

    private void aggregateInMemoryReduce(HashMap<String, Integer> reducedList) {
        Integer count = null;
        for (String key : reducedList.keySet()) {
            if (finalReduceMap.containsKey(key)) {
                count = reducedList.get(key) + finalReduceMap.get(key);
                finalReduceMap.put(key, count);
            } else {
                finalReduceMap.put(key, reducedList.get(key));
            }
        }
    }
}
