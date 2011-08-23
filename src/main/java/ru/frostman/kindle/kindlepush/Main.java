package ru.frostman.kindle.kindlepush;

import ru.frostman.kindle.kindlepush.ui.LaF;

import javax.swing.*;

/**
 * @author slukjanov aka Frostman
 */
public class Main {
    public static void main(String[] args) {
        LaF.applyLaF();

        SwingUtilities.invokeLater(new KindlePush());
    }
}
