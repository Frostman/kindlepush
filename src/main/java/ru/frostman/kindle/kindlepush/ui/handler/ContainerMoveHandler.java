package ru.frostman.kindle.kindlepush.ui.handler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * @author slukjanov aka Frostman
 */
public class ContainerMoveHandler implements MouseListener, MouseMotionListener {
    private final JComponent target;
    private final Container moveContainer;
    private Point startDrag;
    private Point startLocation;

    public ContainerMoveHandler(JComponent target, Container moveContainer) {
        this.target = target;
        this.moveContainer = moveContainer;
    }

    public void mousePressed(MouseEvent event) {
        startDrag = getScreenLocation(event);
        startLocation = moveContainer.getLocation();
    }

    public void mouseDragged(MouseEvent event) {
        Point currentLocation = getScreenLocation(event);
        Point offset = new Point((int) currentLocation.getX() - (int) startDrag.getX()
                , (int) currentLocation.getY() - (int) startDrag.getY());

        Point newLocation = new Point((int) (startLocation.getX() + offset.getX())
                , (int) (startLocation.getY() + offset.getY()));

        moveContainer.setLocation(newLocation);
    }

    private Point getScreenLocation(MouseEvent event) {
        Point cursor = event.getPoint();
        Point targetLocation = target.getLocationOnScreen();

        return new Point((int) (targetLocation.getX() + cursor.getX())
                , (int) (targetLocation.getY() + cursor.getY()));
    }

    public void mouseClicked(MouseEvent event) {
        // no operations
    }

    public void mouseEntered(MouseEvent event) {
        // no operations
    }

    public void mouseExited(MouseEvent event) {
        // no operations
    }

    public void mouseReleased(MouseEvent event) {
        // no operations
    }

    public void mouseMoved(MouseEvent e) {
        // no operations
    }
}
