package csi3471.group5.db;

import csi3471.group5.RoomType;

import java.util.ArrayList;

public abstract class DBStore<E> {
    protected ArrayList<E> list = null;
    public abstract String getFilename();
    public abstract DBSerde<E> getSerde();
    public ArrayList<E> getList() {
        if(list == null) {
            list = Database.getList(this);
        }
        return list;
    }
    public void save() {
        Database.save(this);
    }
}
