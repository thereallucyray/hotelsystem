package csi3471.group5.db;

import java.util.ArrayList;

public abstract class DBStore<E> {
    //singleton type beat
    public abstract String getFilename();
    public abstract DBSerde<E> getSerde();
    public ArrayList<E> getList() {
        return Database.getList(this);
    }
}
