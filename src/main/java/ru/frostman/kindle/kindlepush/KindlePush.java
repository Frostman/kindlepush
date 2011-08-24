package ru.frostman.kindle.kindlepush;

import ru.frostman.kindle.kindlepush.ui.TraySupport;

/**
 * @author slukjanov aka Frostman
 */
public class KindlePush implements Runnable {
    private static final TraySupport traySupport = new TraySupport();

    public void run() {
        traySupport.buildTrayIcon();
    }

    public static TraySupport getTraySupport() {
        return traySupport;
    }
}
