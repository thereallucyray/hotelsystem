package csi3471.group5.gui;

import csi3471.group5.MenuCreator;
import csi3471.group5.Room;
import csi3471.group5.store.RoomStore;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * Implements the front-end interface for viewing the rooms of Hotel
 */
public class ViewRoomsGUI extends CleverCards {
    /**
     * Initializes the swing components for viewing rooms.
     * Gets the list of rooms and adds it to the panel
     */
    public void init() {
        this.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(500, 10000));
        // setup a scroll pane
        JScrollPane scrollPane = new JScrollPane(panel);
        this.add(MenuCreator.createMenuBar(), BorderLayout.NORTH);
        ArrayList<Room> rooms = new RoomStore().query().get();
        panel.setPreferredSize(new Dimension(500, 100 * rooms.size()));
        panel.setBackground(new Color(180,207,201));
        for(Room room : rooms) {
            panel.add(roomPanel(room));
            panel.add(Box.createRigidArea(new Dimension(0,5)));
        }
        this.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * @param room The room which will be displayed on the screen
     * @return A Panel containing the room information to be displayed
     */
    JPanel roomPanel(Room room) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(230,239,237));
        panel.setPreferredSize(new Dimension(500, 55));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel lab = new JLabel(room.toString());
        lab.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lab);
        return panel;
    }
}
