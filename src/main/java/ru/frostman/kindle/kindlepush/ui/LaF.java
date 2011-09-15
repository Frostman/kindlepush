package ru.frostman.kindle.kindlepush.ui;

import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author slukjanov aka Frostman
 */
public class LaF {
    private static final Set<String> LAF_LIST = new HashSet<String>();

    static {
        LAF_LIST.add("Nimbus");
    }

    public static void applyLaF() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if (LAF_LIST.contains(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (Exception e) {
                    //todo remove
                    e.printStackTrace();
                    continue;
                }
                break;
            }
        }
    }
}
