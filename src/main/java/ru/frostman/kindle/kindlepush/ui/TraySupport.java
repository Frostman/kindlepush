package ru.frostman.kindle.kindlepush.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Closeable;
import java.net.URL;

/**
 * @author slukjanov aka Frostman
 */
public class TraySupport implements Closeable {
    private static final String AC_ABOUT = "about";
    private static final String AC_SETTINGS = "settings";
    private static final String AC_EXIT = "exit";

    private final PushWindow pushWindow = new PushWindow();
    private final SettingsWindow settingsWindow = new SettingsWindow();
    private final AboutWindow aboutWindow = new AboutWindow();

    private TrayIcon trayIcon;

    public TraySupport() {
        CloseSupport.add(this);
    }

    public boolean buildTrayIcon() {
        //Check the SystemTray is supported
        if (!SystemTray.isSupported()) {
            JOptionPane.showMessageDialog(null,
                    "SystemTray isn't supported\nPlease, contact me@frostman.ru",
                    "KindlePush :: Error",
                    JOptionPane.OK_OPTION);
            return false;
        }

        trayIcon = new TrayIcon(createImage("/trayIcon.gif", "tray icon"));
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("KindlePush");
        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent event) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        if (event.getButton() == MouseEvent.BUTTON1) {
                            pushWindow.setVisible(!pushWindow.isVisible());
                        }
                    }
                });
            }
        });

        buildPopupMenu(trayIcon);

        try {
            SystemTray.getSystemTray().add(trayIcon);
        } catch (AWTException e) {
            JOptionPane.showMessageDialog(null,
                    "TrayIcon couldn't be added",
                    "KindlePush :: Error",
                    JOptionPane.OK_OPTION);

            return false;
        }

        trayIcon.displayMessage("KindlePush", "Loaded and ready to work!", TrayIcon.MessageType.INFO);

        return true;
    }

    public void showMessage(String caption, String text, TrayIcon.MessageType messageType) {
        if (trayIcon != null) {
            trayIcon.displayMessage(caption, text, messageType);
        }
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
            public void actionPerformed(final ActionEvent event) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        String actionCommand = event.getActionCommand();

                        if (AC_ABOUT.equals(actionCommand)) {
                            aboutWindow.setVisible(!aboutWindow.isVisible());
                        } else if (AC_SETTINGS.equals(actionCommand)) {
                            if (settingsWindow.isVisible()) {
                                settingsWindow.doHide();
                            } else {
                                settingsWindow.doShow();
                            }
                        } else if (AC_EXIT.equals(actionCommand)) {
                            CloseSupport.close();
                        }
                    }
                });
            }
        });

        trayIcon.setPopupMenu(popupMenu);
    }

    private Image createImage(String path, String description) {
        URL imageURL = TraySupport.class.getResource(path);

        if (imageURL == null) {
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }

    public void close() {
        if (trayIcon != null) {
            SystemTray.getSystemTray().remove(trayIcon);
        }

        if (pushWindow != null) {
            pushWindow.dispose();
        }

        if (settingsWindow != null) {
            settingsWindow.dispose();
        }

        if (aboutWindow != null) {
            aboutWindow.dispose();
        }
    }

}
