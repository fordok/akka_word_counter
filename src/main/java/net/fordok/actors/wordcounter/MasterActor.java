package net.fordok.actors.wordcounter;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinRouter;
import net.fordok.messages.MapData;
import net.fordok.messages.ReduceData;
import net.fordok.messages.Result;

/**
 * User: Fordok
 * Date: 6/18/14
 * Time: 11:40 PM
 */
public class MasterActor extends UntypedActor {

    private static long totalWordCount;

    ActorRef mapActor = getContext().actorOf(
            new Props(MapActor.class).withRouter(
                    new RoundRobinRouter(5)), "map");

    ActorRef reduceActor = getContext().actorOf(
            new Props(ReduceActor.class).withRouter(
                    new RoundRobinRouter(5)), "reduce");

    ActorRef aggregateActor = getContext().actorOf(
            new Props(AggregateActor.class), "aggregate");

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            mapActor.tell(message, getSelf());
        } else if (message instanceof MapData) {
            totalWordCount += ((MapData) message).getDataList().size();
            reduceActor.tell(message, getSelf());
        } else if (message instanceof ReduceData) {
            aggregateActor.tell(message);
        } else if (message instanceof Result) {
            System.out.println("Total word count : " + totalWordCount);
            aggregateActor.forward(message, getContext());
        } else {
            unhandled(message);
        }
    }
}
