package csi3471.group5.gui;

import csi3471.group5.RoomType;
import csi3471.group5.store.RoomTypeStore;

import javax.swing.*;

public class RoomTypeSelector extends JComboBox<RoomType> {
    public RoomTypeSelector() {
        super(new RoomTypeStore().query().get().toArray(new RoomType[0]));
    }
    public RoomType getSelectedRoomType() {
        return (RoomType) this.getSelectedItem();
    }
}
