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

    private ArrayList<E> data() {
        if(store().data == null) {
            store().data = Database.getList(this);
        }
        return store().data;
    }

    public DBQuery<E> query() {
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
        int id = getID(e);
        if(id == -1) {
            data().add(e);
            id = data().size()-1;
        } else {
            data().set(id,e);
        }
        return id;
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
    public void save() {
        Database.save(store());
    }
}
