package net.formiks.mapreduce;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.Await;
import akka.dispatch.Future;
import akka.pattern.Patterns;
import akka.util.Duration;
import akka.util.Timeout;
import net.formiks.actors.wordcounter.MasterActor;
import net.formiks.messages.Result;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * User: Fordok
 * Date: 6/18/14
 * Time: 11:55 PM
 */
public class WordCounter {

    final static String FILE_NAME = "D:\\test.txt";

    public static void main (String[] args) throws Exception {
        Timeout timeout = new Timeout(Duration.parse("5 seconds"));
        ActorSystem system = ActorSystem.create("WordCounter");
        ActorRef master = system.actorOf(new Props(MasterActor.class), "master");

        BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
        String line = null;
        while ((line = reader.readLine()) != null) {
            master.tell(line);
        }
        reader.close();

        Thread.sleep(2000);
        Future<Object> future = Patterns.ask(master, new Result(), timeout);
        String result = (String) Await.result(future, timeout.duration());
        System.out.println(result);
        system.shutdown();
    }
}
