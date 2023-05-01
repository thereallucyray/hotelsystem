package csi3471.group5.gui;

import csi3471.group5.RoomType;
import csi3471.group5.store.RoomTypeStore;

import javax.swing.*;

/**
 * This class initializes a room type array and provides functionality to get the selected
 * room type for use in reserve room/modify reservation
 */
public class RoomTypeSelector extends JComboBox<RoomType> {
    public RoomTypeSelector() {
        super(new RoomTypeStore().query().get().toArray(new RoomType[0]));
    }
    public RoomType getSelectedRoomType() {
        return (RoomType) this.getSelectedItem();
    }
}
