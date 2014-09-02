package net.formiks.actors.wordcounter;

import akka.actor.UntypedActor;
import net.formiks.messages.MapData;
import net.formiks.messages.WordCount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * User: Fordok
 * Date: 6/18/14
 * Time: 10:53 PM
 */
public class MapActor extends UntypedActor {

    String[] EXCLUDE_WORDS = { "a", "am", "an", "and", "are", "as", "at",
            "be","do", "go", "if", "in", "is", "it", "of", "on", "to", "the"};
    List<String> EXCLUDE_WORDS_LIST = Arrays.asList(EXCLUDE_WORDS);

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            String work = (String) message;
            getSender().tell(evaluateExpression(work));
        } else {
            unhandled(message);
        }
    }

    private MapData evaluateExpression(String line) {
        List<WordCount> dataList = new ArrayList<WordCount>();
        for (String word : line.split("[^a-zA-Z]+")) {
            if (!EXCLUDE_WORDS_LIST.contains(word.toLowerCase())) {
                dataList.add(new WordCount(word.toLowerCase(), Integer.valueOf(1)));
            }
        }
        return new MapData(dataList);
    }
}
