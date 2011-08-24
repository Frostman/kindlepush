package ru.frostman.kindle.kindlepush.push;

import ru.frostman.kindle.kindlepush.KindlePush;
import ru.frostman.kindle.kindlepush.config.KindlePushConfig;
import ru.frostman.kindle.kindlepush.util.Mail;

import java.awt.*;
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
                    push(file);
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

    private static void push(File file) {
        //todo show option dialog

        System.out.println("before send");
        Mail.send(KindlePushConfig.get().getKindleMail(), file, true);
        System.out.println("successfully sent: " + file.getAbsolutePath());

        KindlePush.getTraySupport().showMessage("KindlePush", "Sent to Kindle: " + file.getAbsolutePath(), TrayIcon.MessageType.INFO);
    }

}
