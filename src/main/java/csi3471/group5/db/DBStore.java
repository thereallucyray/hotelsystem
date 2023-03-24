package csi3471.group5.db;

import csi3471.group5.RoomType;

import java.util.ArrayList;
import java.util.TreeMap;

public abstract class DBStore<E,T extends DBStore<E,T>> {
    private static TreeMap<String,DBStore> stores = null;
    private ArrayList<E> data = null;

    private DBStore<E,T> store() {
        return (DBStore<E, T>) stores.get(getFilename());
    }

    private ArrayList<E> data() {
        if(store().data == null) {
            store().data = Database.getList(this);
        }
        return store().data;
    }

    public DBQuery<E> query() {
        return new DBQuery<E>(data());
    }

    protected DBStore() {
        if(stores == null) {
            stores = new TreeMap<>();
        }
        if(!stores.containsKey(getFilename())) {
            stores.put(getFilename(),this);
        }
    }

    public abstract String getFilename();
    public abstract DBSerde<E> getSerde();
    public void save() {
        Database.save(store());
    }
}
