package ru.frostman.kindle.kindlepush.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @author slukjanov aka Frostman
 */
public class SettingsWindow extends JFrame {

    public SettingsWindow() throws HeadlessException {
        setTitle("KindlePush :: Settings");
        setName("SettingsWindow");

        int width = 500, height = 500;

        Toolkit toolkit = getToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        setResizable(false);
        setBounds(screenSize.width / 2 - width / 2, screenSize.height / 2 - height / 2, width, height);

        JTabbedPane tabbedPane = new JTabbedPane();

        JComponent panel1 = makeTextPanel("Application settings");
        tabbedPane.addTab("App", null, panel1, "KindlePush settings");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_A);

        JComponent panel2 = makeTextPanel("Kindle settings");
        tabbedPane.addTab("Kindle", null, panel2, "Kindle delivering settings");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_K);

        JComponent panel3 = makeTextPanel("Mail settings");
        tabbedPane.addTab("Mail", null, panel3, "Mail settings");
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_M);

        JComponent panel4 = makeTextPanel("Dropbox settings");
        tabbedPane.addTab("Dropbox", null, panel4, "Dropbox settings");
        tabbedPane.setMnemonicAt(3, KeyEvent.VK_D);

        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        getContentPane().add(tabbedPane);
    }

    protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);

        return panel;
    }
}
