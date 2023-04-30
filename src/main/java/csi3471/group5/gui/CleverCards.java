package csi3471.group5.gui;

import csi3471.group5.Employee;
import csi3471.group5.SystemHandler;

import javax.swing.*;

/**
 * Creates platform that allows for switching between multiple card UI views.
 */
public abstract class CleverCards extends JPanel {
    /**
     * Initializes CleverCards.
     */
    protected abstract void init();

    /**
     * CleverCards constructor.
     */
    public CleverCards() {
        init();
    }

    /**
     * Refresses the pannel inorder to switch to a new card.
     */
    public void refresh() {
        this.removeAll();
        init();
        this.revalidate();
        this.repaint();
    }

    /**
     * Determines if the user logged in is an Admin employee or not.
     * @return boolean
     */
    protected boolean isAdmin() {
        return SystemHandler.handler().isEmployeeFacing() ?
                ((Employee)SystemHandler.handler().getLoggedInUser()).isAdmin()
                : false;
    }
}
