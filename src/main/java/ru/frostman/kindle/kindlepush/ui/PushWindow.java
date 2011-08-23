package ru.frostman.kindle.kindlepush.ui;

import com.sun.awt.AWTUtilities;
import ru.frostman.kindle.kindlepush.ui.handler.ContainerMoveHandler;
import ru.frostman.kindle.kindlepush.ui.handler.DataDropHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.TooManyListenersException;

/**
 * @author slukjanov aka Frostman
 */
public class PushWindow extends JWindow {

    public PushWindow() throws HeadlessException {
        int shiftX = 0, shiftY = 40, width = 100, height = 100;

        setName("PushWindow");
        setAlwaysOnTop(true);

        Toolkit toolkit = getToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        setBounds(screenSize.width - shiftX - width, screenSize.height - shiftY - height, width, height);

        final JPanel panel = new JPanel();
        panel.setBounds(0, 0, 100, 100);
        panel.setBackground(Color.BLACK);
        setContentPane(panel);

        ContainerMoveHandler containerMoveHandler = new ContainerMoveHandler(panel, this);
        panel.addMouseListener(containerMoveHandler);
        panel.addMouseMotionListener(containerMoveHandler);

        DataDropHandler dataDropHandler = new DataDropHandler();
        panel.setTransferHandler(dataDropHandler);

        try {
            panel.getDropTarget().addDropTargetListener(new DropTargetAdapter() {
                public void drop(DropTargetDropEvent event) {
                    panel.setBackground(Color.BLACK);
                }

                @Override
                public void dragExit(DropTargetEvent dte) {
                    panel.setBackground(Color.BLACK);
                }
            });
        } catch (TooManyListenersException e) {
            //todo remove
            e.printStackTrace();
            throw new RuntimeException();
        }

        //todo notify that only for 6u10+
        try {
            AWTUtilities.setWindowOpacity(this, 0.75f);
            AWTUtilities.setWindowShape(this, new RoundRectangle2D.Float(0, 0, width, height, 25, 25));
        } catch (Exception e) {
            //todo remove
            e.printStackTrace();
        }
    }


}
