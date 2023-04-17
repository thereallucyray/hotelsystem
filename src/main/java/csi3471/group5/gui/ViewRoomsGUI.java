package csi3471.group5.gui;

import csi3471.group5.MenuCreator;
import csi3471.group5.Room;
import csi3471.group5.store.RoomStore;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class ViewRoomsGUI extends CleverCards {
    public void init() {
        this.setBackground(new Color(200, 219, 215));
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxLayout);
        this.setBorder(new EmptyBorder(new Insets(150, 100, 150, 100)));
        this.setVisible(true);
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(500, 10000));
        // setup a scroll pane
        JScrollPane scrollPane = new JScrollPane(panel);
        this.add(MenuCreator.createMenuBar());
        this.add(scrollPane);
        ArrayList<Room> rooms = new RoomStore().query().get();
        panel.setPreferredSize(new Dimension(500, 100 * rooms.size()));
        panel.setBackground(new Color(180,207,201));
        for(Room room : rooms) {
            panel.add(roomPanel(room));
            panel.add(Box.createRigidArea(new Dimension(0,5)));
        }
    }

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
