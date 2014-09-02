package net.formiks.actors.filecounter;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Fordok
 * Date: 8/23/14
 * Time: 6:35 PM
 */
public class FileCollector extends UntypedActor {

    private int count;
    private int pending;
    private List<ActorRef> fileExplorers = new ArrayList<ActorRef>();
    private long start = System.nanoTime();

    @Override
    public void preStart() {
        for (int i = 0; i < 100; i++) {
            ActorRef slave = getContext().system().actorOf(new Props(FileExplorer.class));
            fileExplorers.add(slave);
        }
    }

    @Override
    public void onReceive(final Object message) {
        if (message instanceof String) {
            pending++;
            ActorRef aSlave = fileExplorers.remove(0);
            aSlave.tell(message, getSelf());
            fileExplorers.add(aSlave);
        }

        if (message instanceof Integer) {
            count+= (Integer) message;
            pending--;

            if (pending == 0) {
                System.out.println("Files count : " + count);
                System.out.println("Time taken : " + (System.nanoTime() - start) / 1.0e9);
                getContext().system().shutdown();
            }
        }
    }
}
