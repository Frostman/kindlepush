package ru.frostman.kindle.kindlepush;

import ru.frostman.kindle.kindlepush.ui.TraySupport;

/**
 * @author slukjanov aka Frostman
 */
public class Main {
    public static void main(String[] args) {
        TraySupport traySupport = new TraySupport();
        if (!traySupport.buildTrayIcon()) {
            //todo create normal app
        }
    }
}
