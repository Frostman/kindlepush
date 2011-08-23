package ru.frostman.kindle.kindlepush.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

/**
 * @author slukjanov aka Frostman
 */
public class TraySupport {
    private final PushWindow pushWindow = new PushWindow();

    public boolean buildTrayIcon() {
        //Check the SystemTray is supported
        if (!SystemTray.isSupported()) {
            //todo replace with normal window app
            System.out.println("SystemTray is not supported");
            return false;
        }
        final PopupMenu popUp = new PopupMenu();
        final TrayIcon trayIcon = new TrayIcon(createImage("/bulb.gif", "tray icon"));
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("Kindle Push");
        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                pushWindow.setVisible(!pushWindow.isVisible());
            }
        });
        final SystemTray tray = SystemTray.getSystemTray();

        // Create a popUp menu components
        MenuItem aboutItem = new MenuItem("About");
        CheckboxMenuItem cb1 = new CheckboxMenuItem("Set auto size");
        CheckboxMenuItem cb2 = new CheckboxMenuItem("Set tooltip");
        Menu displayMenu = new Menu("Display");
        MenuItem errorItem = new MenuItem("Error");
        MenuItem warningItem = new MenuItem("Warning");
        MenuItem infoItem = new MenuItem("Info");
        MenuItem noneItem = new MenuItem("None");
        MenuItem exitItem = new MenuItem("Exit");

        //Add components to popUp menu
        popUp.add(aboutItem);
        popUp.addSeparator();
        popUp.add(cb1);
        popUp.add(cb2);
        popUp.addSeparator();
        popUp.add(displayMenu);
        displayMenu.add(errorItem);
        displayMenu.add(warningItem);
        displayMenu.add(infoItem);
        displayMenu.add(noneItem);
        popUp.add(exitItem);

        trayIcon.setPopupMenu(popUp);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            //todo remove
            System.out.println("TrayIcon could not be added.");

            return false;
        }

        return true;
    }

    protected static Image createImage(String path, String description) {
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
