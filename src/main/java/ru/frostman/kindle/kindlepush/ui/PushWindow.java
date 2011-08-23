package ru.frostman.kindle.kindlepush.ui;

import com.sun.awt.AWTUtilities;
import ru.frostman.kindle.kindlepush.ui.handler.DataDropHandler;
import ru.frostman.kindle.kindlepush.ui.handler.ContainerMoveHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * @author slukjanov aka Frostman
 */
public class PushWindow extends JWindow {

    public PushWindow() throws HeadlessException {
        int shiftX = 0, shiftY = 40, width = 100, height = 100;

        setName("KindlePush:PushWindow");
        setAlwaysOnTop(true);
//        setLocationByPlatform(true);
        Toolkit toolkit = getToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        setBounds(screenSize.width - shiftX - width, screenSize.height - shiftY - height, width, height);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 100, 100);
        panel.setBackground(Color.BLACK);

        ContainerMoveHandler containerMoveHandler = new ContainerMoveHandler(panel, this);
        panel.addMouseListener(containerMoveHandler);
        panel.addMouseMotionListener(containerMoveHandler);

        DataDropHandler dataDropHandler = new DataDropHandler();
        panel.setTransferHandler(dataDropHandler);

        getContentPane().add(panel);

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
