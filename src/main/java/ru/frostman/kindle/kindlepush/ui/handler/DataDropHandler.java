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
import java.util.LinkedList;
import java.util.List;

/**
 * @author slukjanov aka Frostman
 */
public class DataDropHandler extends TransferHandler {

    @Override
    public boolean canImport(final JComponent component, DataFlavor[] dataFlavors) {
        //todo replace with img
        //todo implement color alpha changing for good effects
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

    @Override
    public boolean importData(JComponent component, Transferable transferable) {
        DataFlavor[] flavors = transferable.getTransferDataFlavors();

        for (DataFlavor flavor : flavors) {
            try {
                if (flavor.equals(DataFlavor.javaFileListFlavor)) {
                    List transferDataList = (List) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                    List<File> files = new LinkedList<File>();
                    for (Object transferData : transferDataList) {
                        if (transferData instanceof File) {
                            File file = (File) transferData;
                            files.add(file);
                        }
                    }

                    //todo compute files

                    return true;
                } else if (flavor.equals(DataFlavor.stringFlavor)) {
                    String fileOrURL = (String) transferable.getTransferData(flavor);

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
    }
}
