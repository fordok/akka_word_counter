package net.formiks.actors.filecounter;

import akka.actor.UntypedActor;

import java.io.File;

/**
 * User: Fordok
 * Date: 8/23/14
 * Time: 7:04 PM
 */
public class FileExplorer extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Exception {
        String path = (String) message;
        File file = new File(path);

        int count = 0;

        if (file.listFiles() != null) {
            for (File child : file.listFiles()) {
                if (child.isFile()) {
                    count++;
                } else {
                    getSender().tell(child.getPath());
                }
            }
        }

        getContext().sender().tell(count);

    }
}
