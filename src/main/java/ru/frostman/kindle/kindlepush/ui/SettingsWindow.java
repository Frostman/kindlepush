package ru.frostman.kindle.kindlepush.ui;

import ru.frostman.kindle.kindlepush.config.KindlePushConfig;
import ru.frostman.kindle.kindlepush.ui.handler.HideComponentHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author slukjanov aka Frostman
 */
public class SettingsWindow extends JFrame {
    private JPanel panel;
    private JTabbedPane tabbedPane;
    private JTextField kindleMail;
    private JTextField testProperty;

    public SettingsWindow() throws HeadlessException {
        setTitle("KindlePush :: Settings");
        setName("SettingsWindow");
        setContentPane(panel);
        setResizable(false);

        int width = 500, height = 500;

        Toolkit toolkit = getToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        setResizable(false);
        setBounds(screenSize.width / 2 - width / 2, screenSize.height / 2 - height / 2, width, height);

        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        HideComponentHandler.apply(tabbedPane, new Runnable() {
            public void run() {
                doHide();
            }
        });

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent event) {
                doShow();
            }

            @Override
            public void windowClosing(WindowEvent event) {
                doHide();
            }
        });

        tabbedPane.setSelectedIndex(1);

        pack();
    }

    public void doShow() {
        setData(KindlePushConfig.get());
        setVisible(true);
    }

    public void doHide() {
        if (!isModified(KindlePushConfig.get())) {
            setVisible(false);
            return;
        }

        int confirmed = JOptionPane.showConfirmDialog(this,
                "Save changes?", "KindlePush :: Settings",
                JOptionPane.YES_NO_CANCEL_OPTION);

        if (JOptionPane.YES_OPTION == confirmed) {
            KindlePushConfig config = KindlePushConfig.get();
            getData(config);
            KindlePushConfig.save();

            setVisible(false);
        } else if (JOptionPane.NO_OPTION == confirmed) {
            setVisible(false);
        } else {
            // no operations
        }
    }

    public void setData(KindlePushConfig data) {
        kindleMail.setText(data.getKindleMail());
    }

    public void getData(KindlePushConfig data) {
        data.setKindleMail(kindleMail.getText());
    }

    public boolean isModified(KindlePushConfig data) {
        if (kindleMail.getText() != null ? !kindleMail.getText().equals(data.getKindleMail()) : data.getKindleMail() != null)
            return true;
        return false;
    }
}
