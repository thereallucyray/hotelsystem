package csi3471.group5.gui;

import javax.swing.*;

public abstract class CleverCards extends JPanel {
    protected abstract void init();
    public CleverCards() {
        init();
    }
    public void refresh() {
        this.removeAll();
        init();
        this.revalidate();
        this.repaint();
    }
}
