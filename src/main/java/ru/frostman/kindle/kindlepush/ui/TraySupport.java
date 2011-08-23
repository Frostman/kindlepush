package ru.frostman.kindle.kindlepush.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

/**
 * @author slukjanov aka Frostman
 */
public class TraySupport {
    private static final String AC_ABOUT = "about";
    private static final String AC_SETTINGS = "settings";
    private static final String AC_EXIT = "exit";

    private final PushWindow pushWindow = new PushWindow();
    private final SettingsWindow settingsWindow = new SettingsWindow();

    public boolean buildTrayIcon() {
        //Check the SystemTray is supported
        if (!SystemTray.isSupported()) {
            //todo replace with notification to contact me@frostman.ru
            System.out.println("SystemTray is not supported");
            return false;
        }

        final TrayIcon trayIcon = new TrayIcon(createImage("/bulb.gif", "tray icon"));
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("KindlePush");
        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                if (event.getButton() == MouseEvent.BUTTON1) {
                    pushWindow.setVisible(!pushWindow.isVisible());
                }
            }
        });

        buildPopupMenu(trayIcon);

        try {
            SystemTray.getSystemTray().add(trayIcon);
        } catch (AWTException e) {
            //todo remove
            System.out.println("TrayIcon could not be added.");

            return false;
        }

        return true;
    }

    private void buildPopupMenu(TrayIcon trayIcon) {
        final MenuItem aboutItem = new MenuItem("About");
        aboutItem.setActionCommand(AC_ABOUT);
        aboutItem.setShortcut(new MenuShortcut(KeyEvent.VK_A));

        final MenuItem settingsItem = new MenuItem("Settings");
        settingsItem.setActionCommand(AC_SETTINGS);
        settingsItem.setShortcut(new MenuShortcut(KeyEvent.VK_S));

        final MenuItem exitItem = new MenuItem("Exit");
        exitItem.setActionCommand(AC_EXIT);
        exitItem.setShortcut(new MenuShortcut(KeyEvent.VK_X));

        final PopupMenu popupMenu = new PopupMenu();
        popupMenu.add(aboutItem);
        popupMenu.addSeparator();
        popupMenu.add(settingsItem);
        popupMenu.addSeparator();
        popupMenu.add(exitItem);

        popupMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String actionCommand = event.getActionCommand();

                if (AC_ABOUT.equals(actionCommand)) {

                } else if (AC_SETTINGS.equals(actionCommand)) {
                    settingsWindow.setVisible(true);
                } else if (AC_EXIT.equals(actionCommand)) {
                    //todo release all resources
                    pushWindow.dispose();
                    settingsWindow.dispose();
                    System.exit(0);
                }
            }
        });

        trayIcon.setPopupMenu(popupMenu);
    }

    private static Image createImage(String path, String description) {
        URL imageURL = TraySupport.class.getResource(path);

        if (imageURL == null) {

            //todo remove
            System.err.println("Resource not found: " + path);

            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }

}
