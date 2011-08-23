package ru.frostman.kindle.kindlepush;

import ru.frostman.kindle.kindlepush.ui.TraySupport;

/**
 * @author slukjanov aka Frostman
 */
public class KindlePush implements Runnable {
    public void run() {
        final TraySupport traySupport = new TraySupport();
        traySupport.buildTrayIcon();
    }
}
