package ru.frostman.kindle.kindlepush.ui.handler;

import ru.frostman.kindle.kindlepush.push.Pusher;

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
        component.setBackground(Color.GREEN);

        for (DataFlavor flavor : dataFlavors) {
            if (flavor.equals(DataFlavor.javaFileListFlavor)) {
                return true;
            }
            if (flavor.equals(DataFlavor.stringFlavor)) {
                return true;
            }
        }

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
                    final List<File> files = new LinkedList<File>();
                    for (Object transferData : transferDataList) {
                        if (transferData instanceof File) {
                            File file = (File) transferData;
                            files.add(file);
                        }
                    }

                    Pusher.get().push(files);

                    return true;
                } else if (flavor.equals(DataFlavor.stringFlavor)) {
                    String fileOrURL = (String) transferable.getTransferData(flavor);

                    try {
                        URL url = new URL(fileOrURL);

                        return true;
                    } catch (MalformedURLException ex) {
                        return false;
                    }
                } else {
                    // rejected
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,
                        "IO error while getting data",
                        "KindlePush :: Error",
                        JOptionPane.OK_OPTION);
            } catch (UnsupportedFlavorException e) {
                JOptionPane.showMessageDialog(null,
                        "Unsupported data",
                        "KindlePush :: Error",
                        JOptionPane.OK_OPTION);
            }
        }

        return false;
    }
}
