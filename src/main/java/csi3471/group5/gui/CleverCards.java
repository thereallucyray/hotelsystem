package csi3471.group5.gui;

import csi3471.group5.Employee;
import csi3471.group5.SystemHandler;

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
    protected boolean isAdmin() {
        return SystemHandler.handler().isEmployeeFacing() ?
                ((Employee)SystemHandler.handler().getLoggedInUser()).isAdmin()
                : false;
    }
}
