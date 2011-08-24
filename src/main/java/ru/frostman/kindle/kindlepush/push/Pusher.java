package ru.frostman.kindle.kindlepush.push;

import ru.frostman.kindle.kindlepush.ui.CloseSupport;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author slukjanov aka Frostman
 */
public class Pusher implements Closeable {
    private static final Pusher INSTANCE = new Pusher();
    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    private Pusher() {
        CloseSupport.add(this);
    }

    public static Pusher get() {
        return INSTANCE;
    }

    public void push(List<File> files) {
        if (files == null || files.size() == 0) {
            return;
        }

        executor.submit(new FilesPushTask(files));
    }

    private class FilesPushTask implements Runnable {
        private final List<File> files;

        public FilesPushTask(List<File> files) {
            this.files = files;
        }

        public void run() {
            KindlePusher.push(files);
        }
    }

    public void close() throws IOException {
        executor.shutdown();
    }
}
