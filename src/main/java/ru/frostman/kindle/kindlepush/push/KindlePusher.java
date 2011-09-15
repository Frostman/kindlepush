package ru.frostman.kindle.kindlepush.push;

import ru.frostman.kindle.kindlepush.KindlePush;
import ru.frostman.kindle.kindlepush.util.Mail;

import javax.swing.*;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author slukjanov aka Frostman
 */
public class KindlePusher {
    private static final Set<String> SUPPORTED_EXT = new HashSet<String>();

    static {
        // to convert
        SUPPORTED_EXT.add(".doc");
        SUPPORTED_EXT.add(".docx");
        SUPPORTED_EXT.add(".htm");
        SUPPORTED_EXT.add(".html");
        SUPPORTED_EXT.add(".rtf");
        SUPPORTED_EXT.add(".jpg");
        SUPPORTED_EXT.add(".jpeg");
        SUPPORTED_EXT.add(".gif");
        SUPPORTED_EXT.add(".png");
        SUPPORTED_EXT.add(".bmp");

        // need convert subject 'convert' to convert
        SUPPORTED_EXT.add(".pdf");

        // supported by kindle
        SUPPORTED_EXT.add(".azw");
        SUPPORTED_EXT.add(".azw1");
        SUPPORTED_EXT.add(".txt");
        SUPPORTED_EXT.add(".mobi");
        SUPPORTED_EXT.add(".prc");
        SUPPORTED_EXT.add(".aa");
        SUPPORTED_EXT.add(".aax");
        SUPPORTED_EXT.add(".mp3");
    }

    private static final Set<String> CAN_CONVERT_EXT = new HashSet<String>();

    static {
        CAN_CONVERT_EXT.add(".pdf");
    }

    public static void push(List<File> files) {
        for (File file : files) {
            String name = file.getName();
            int extDotIdx = name.lastIndexOf('.');
            if (extDotIdx > -1) {
                String ext = name.substring(extDotIdx).toLowerCase();
                if (SUPPORTED_EXT.contains(ext)) {
                    push(file, CAN_CONVERT_EXT.contains(ext));
                } else {
                    // todo process unsupported ext
                    System.out.println("unsupported ext: " + file.getAbsolutePath());
                }
            } else {
                // todo process unknown ext
                System.out.println("unknown ext: " + file.getAbsolutePath());
            }
        }
    }

    private static void push(final File file, boolean canConvert) {
        boolean convert = canConvert;
        if (convert) {
            int confirmed = JOptionPane.showConfirmDialog(null,
                    "Do you want to use Amazon's converter?\nFile: " + file.getName(), "KindlePush :: Send to Kindle",
                    JOptionPane.YES_NO_CANCEL_OPTION);

            if (JOptionPane.YES_OPTION == confirmed) {
                convert = true;
            } else if (JOptionPane.NO_OPTION == confirmed) {
                convert = false;
            } else {
                return;
            }
        } else {
            int confirmed = JOptionPane.showConfirmDialog(null,
                    "Send file to your Kindle?\nFile: " + file.getName(), "KindlePush :: Send to Kindle",
                    JOptionPane.YES_NO_CANCEL_OPTION);

            if (JOptionPane.NO_OPTION == confirmed) {
                return;
            }
        }

        final boolean finalConvert = convert;
        KindlePush.execute(new Runnable() {
            @Override
            public void run() {
                Mail.send(file, finalConvert);
            }
        });
    }

}
