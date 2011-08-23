package ru.frostman.kindle.kindlepush.ui.handler;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * @author slukjanov aka Frostman
 */
public class DataDropHandler extends TransferHandler {

    public boolean canImport(JComponent component, DataFlavor[] dataFlavors) {
        //todo replace with img
        component.setBackground(Color.GREEN);

        for (DataFlavor flavor : dataFlavors) {
            if (flavor.equals(DataFlavor.javaFileListFlavor)) {
                return true;
            }
            if (flavor.equals(DataFlavor.stringFlavor)) {
                return true;
            }

            //todo remove
            System.err.println("canImport: Rejected Flavor: " + flavor);
        }

        //todo replace with img
        component.setBackground(Color.RED);

        return false;
    }

    public boolean importData(JComponent component, Transferable t) {
        try {
            DataFlavor[] flavors = t.getTransferDataFlavors();

            for (DataFlavor flavor : flavors) {
                try {
                    if (flavor.equals(DataFlavor.javaFileListFlavor)) {
                        List transferDataList = (List) t.getTransferData(DataFlavor.javaFileListFlavor);
                        for (Object transferData : transferDataList) {
                            if (transferData instanceof File) {
                                File file = (File) transferData;

                                //todo remove
                                System.out.println("GOT FILE: " + file.getCanonicalPath());
                            }
                        }

                        return true;
                    } else if (flavor.equals(DataFlavor.stringFlavor)) {
                        String fileOrURL = (String) t.getTransferData(flavor);

                        //todo remove
                        System.out.println("GOT STRING: " + fileOrURL);
                        try {
                            URL url = new URL(fileOrURL);

                            //todo remove
                            System.out.println("Valid URL: " + url.toString());
                            return true;
                        } catch (MalformedURLException ex) {
                            //todo remove
                            System.err.println("Not a valid URL");
                            return false;
                        }
                    } else {
                        //todo remove
                        System.out.println("importData rejected: " + flavor);
                    }
                } catch (IOException ex) {
                    //todo remove
                    System.err.println("IOError getting data: " + ex);
                } catch (UnsupportedFlavorException e) {
                    //todo remove
                    System.err.println("Unsupported Flavor: " + e);
                }
            }

            return false;
        } finally {
            //todo replace with img
            component.setBackground(Color.BLACK);
        }
    }
}
