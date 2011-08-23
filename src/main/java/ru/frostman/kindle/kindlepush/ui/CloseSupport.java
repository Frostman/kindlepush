package ru.frostman.kindle.kindlepush.ui;

import java.io.Closeable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author slukjanov aka Frostman
 */
public class CloseSupport {
    private static final List<Closeable> closeableList = new LinkedList<Closeable>();

    public static synchronized void add(Closeable closeable) {
        closeableList.add(closeable);
    }

    public static synchronized void close() {
        for (Closeable closeable : closeableList) {
            try {
                if (closeable != null) {
                    closeable.close();
                }
            } catch (Throwable th) {
                // no operations
            }
        }
    }
}
