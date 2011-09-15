package ru.frostman.kindle.kindlepush;

import ru.frostman.kindle.kindlepush.ui.TraySupport;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author slukjanov aka Frostman
 */
public class KindlePush implements Runnable {
    private static final TraySupport traySupport = new TraySupport();
    private static final Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

    public void run() {
        traySupport.buildTrayIcon();
    }

    public static TraySupport getTraySupport() {
        return traySupport;
    }

    public static void execute(Runnable runnable) {
        executor.execute(runnable);
    }
}
