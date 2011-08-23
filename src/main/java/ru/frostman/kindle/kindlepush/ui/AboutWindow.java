package ru.frostman.kindle.kindlepush.ui;

import ru.frostman.kindle.kindlepush.ui.handler.HideComponentHandler;

import javax.swing.*;
import java.awt.*;

/**
 * @author slukjanov aka Frostman
 */
public class AboutWindow extends JFrame {
    private JPanel panel;

    public AboutWindow() {
        setTitle("KindlePush :: About");
        setName("AboutWindow");
        setContentPane(panel);

        int width = 500, height = 500;

        Toolkit toolkit = getToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        setResizable(false);
        setBounds(screenSize.width / 2 - width / 2, screenSize.height / 2 - height / 2, width, height);

        HideComponentHandler.apply(panel, this);

    }
}
