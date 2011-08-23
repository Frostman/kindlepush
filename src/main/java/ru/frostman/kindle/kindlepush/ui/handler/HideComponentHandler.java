package ru.frostman.kindle.kindlepush.ui.handler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * @author slukjanov aka Frostman
 */
public class HideComponentHandler implements ActionListener {
    private final Component target;
    private final Runnable runnable;

    public HideComponentHandler(Component target) {
        this.target = target;
        this.runnable = null;
    }

    public HideComponentHandler(Runnable runnable) {
        this.target = null;
        this.runnable = runnable;
    }

    public static void apply(JComponent component, Component target) {
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        component.registerKeyboardAction(new HideComponentHandler(target), stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    public static void apply(JComponent component, Runnable runnable) {
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        component.registerKeyboardAction(new HideComponentHandler(runnable), stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    public void actionPerformed(ActionEvent event) {
        if (target != null) {
            target.setVisible(false);
        } else if (runnable != null) {
            runnable.run();
        }
    }
}
