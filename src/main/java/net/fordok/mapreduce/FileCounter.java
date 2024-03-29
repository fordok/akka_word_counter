package net.fordok.mapreduce;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import net.fordok.actors.filecounter.FileCollector;

/**
 * User: Fordok
 * Date: 8/23/14
 * Time: 5:59 PM
 */
public class FileCounter {

    final static String FILE_PATH = "D:\\workspace";

    public static void main (String[] args) {
        ActorSystem actorSystem = ActorSystem.create();
        ActorRef fileCollector = actorSystem.actorOf(new Props(FileCollector.class));
        fileCollector.tell(FILE_PATH);
    }
}
