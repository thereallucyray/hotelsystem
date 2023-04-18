package csi3471.group5.db;

import csi3471.group5.RoomType;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public abstract class DBStore<E,T extends DBStore<E,T>> {
    private static TreeMap<String,DBStore> stores = null;
    private ArrayList<E> data = null;

    private DBStore<E,T> store() {
        return (DBStore<E, T>) stores.get(getFilename());
    }
    public static void saveAll() {
        resolveDB();
        for(DBStore store : stores.values()) {
            Database.save(store);
        }
    }

    private static void resolveDB() {
        for(DBStore store : stores.values()) {
            store.resolveConnections();
        }
    }

    protected ArrayList<E> data() {
        if(store().data == null) {
            store().data = Database.getList(this);
        }
        return store().data;
    }

    public DBQuery<E> query() {
        resolveDB();
        return new DBQuery<E>(data());
    }

    public int getID(E e) {
        int id = -1;
        for(E e2 : data()) {
            id++;
            if(e == e2) {
                return id;
            }
        }
        return -1;
    }
    public E getByID(int id) {
//        ArrayList<E> data = data();
        if(id >= data().size() || id < 0) {
            return null;
        }
        return data().get(id);
    }

    public void resolve(List<E> list) {
        for(E e : list) {
            resolve(e);
        }
    }

    // takes in an element. If it is not in the store, it adds it and returns the id.
    // If it is in the store, it updates the element and returns the id.
    public int resolve(E e) {
        if(e == null) {
            return -1;
        }
        int id = getID(e);
        if(id == -1) {
            data().add(e);
            id = data().size()-1;
            resolveConnections();
        } else {
            data().set(id,e);
        }
        return id;
    }

    protected void resolveConnections() {
        for(E e : data()) {
            getSerde().resolveConnections(e);
        }
    }

    protected DBStore() {
        if(stores == null) {
            stores = new TreeMap<>();
        }
        if(!stores.containsKey(getFilename())) {
            stores.put(getFilename(),this);
        }
    }

    public void init() {
        data();
    }

    public abstract String getFilename();
    public abstract DBSerde<E> getSerde();
    private void save() {
        Database.save(store());
    }
}
