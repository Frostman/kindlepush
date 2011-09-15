package ru.frostman.kindle.kindlepush.config;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author slukjanov aka Frostman
 */
public class KindlePushConfig {
    private static final File appDir = new File(System.getProperty("user.home") + "/.kindlebox/");
    private static final File configFile = new File(appDir, "settings.yaml");
    private static final String CONFIG_TAG = "!config";
    private static Yaml yaml;

    private static KindlePushConfig currentConfig = new KindlePushConfig();

    static {
        Constructor constructor = new Constructor();
        constructor.addTypeDescription(new TypeDescription(KindlePushConfig.class, CONFIG_TAG));
        Representer representer = new Representer();
        representer.addClassTag(KindlePushConfig.class, new Tag(CONFIG_TAG));
        yaml = new Yaml(constructor, representer);

        if (!appDir.isDirectory()) {
            if (!appDir.mkdirs()) {
                JOptionPane.showMessageDialog(null,
                        "Error while creating dirs: " + appDir.getAbsolutePath(),
                        "KindlePush :: Error",
                        JOptionPane.OK_OPTION);
            }
        }

        boolean newConfig = false;
        if (!configFile.exists()) {
            try {
                if (!configFile.createNewFile()) {
                    JOptionPane.showMessageDialog(null,
                            "Can't create config file: " + configFile.getAbsolutePath(),
                            "KindlePush :: Error",
                            JOptionPane.OK_OPTION);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,
                        "Can't create config file: " + configFile.getAbsolutePath(),
                        "KindlePush :: Error",
                        JOptionPane.OK_OPTION);
            }

            if (!save()) {
                JOptionPane.showMessageDialog(null,
                        "Can't save config file: " + configFile.getAbsolutePath(),
                        "KindlePush :: Error",
                        JOptionPane.OK_OPTION);
            }
            newConfig = true;
        }

        if (!newConfig) {
            if (!load()) {
                JOptionPane.showMessageDialog(null,
                        "Can't load config file: " + configFile.getAbsolutePath(),
                        "KindlePush :: Error",
                        JOptionPane.OK_OPTION);
            }
        }
    }

    public synchronized static boolean load() {
        try {
            KindlePushConfig config = (KindlePushConfig) yaml.load(new FileInputStream(configFile));

            boolean changed = false;
            if (!config.equals(currentConfig)) {
                changed = true;

                currentConfig = config;
            }

            return changed;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Can't load config file: " + configFile.getAbsolutePath() + ";" + e.getMessage(),
                    "KindlePush :: Error",
                    JOptionPane.OK_OPTION);
        }

        return false;
    }

    public synchronized static boolean save() {
        try {
            yaml.dump(currentConfig, new PrintWriter(configFile));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Can't save config file: " + configFile.getAbsolutePath(),
                    "KindlePush :: Error",
                    JOptionPane.OK_OPTION);
            return false;
        }

        return true;
    }

    public static KindlePushConfig get() {
        return currentConfig;
    }

    private String fromMailLogin = "";
    private String fromMailPassword = "";
    private String kindleMail = "";

    public KindlePushConfig() {
    }

    public String getFromMailLogin() {
        return fromMailLogin;
    }

    public void setFromMailLogin(String fromMailLogin) {
        this.fromMailLogin = fromMailLogin;
    }

    public String getFromMailPassword() {
        return fromMailPassword;
    }

    public void setFromMailPassword(String fromMailPassword) {
        this.fromMailPassword = fromMailPassword;
    }

    public String getKindleMail() {
        return kindleMail;
    }

    public void setKindleMail(String kindleMail) {
        this.kindleMail = kindleMail;
    }
}
