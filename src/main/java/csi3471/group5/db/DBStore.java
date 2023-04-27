package csi3471.group5.db;

import csi3471.group5.RoomType;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * A store of objects in the database.
 * @param <E> The object this store contains.
 * @param <T> The solid type of this store.
 */
public abstract class DBStore<E,T extends DBStore<E,T>> {
    private static TreeMap<String,DBStore> stores = null;
    private ArrayList<E> data = null;

    /**
     * This is a singleton workaround.
     * Gets the store of the specified type.
     * @return The store of the specified type.
     */
    private DBStore<E,T> store() {
        return (DBStore<E, T>) stores.get(getFilename());
    }

    /**
     * Saves the entire database.
     * Note that it only saves stores that have been loaded
     * at least once.
     */
    public static void saveAll() {
        resolveDB();
        for(DBStore store : stores.values()) {
            Database.save(store);
        }
    }

    /**
     * Resolves the entire database. Makes sure that all connections
     * are included in the database.
     */
    private static void resolveDB() {
        for(DBStore store : stores.values()) {
            store.resolveConnections();
        }
    }

    /**
     * Gets an up to date list of all data in a store.
     * @return an array list of all data in a store.
     */
    protected ArrayList<E> data() {
        if(store().data == null) {
            store().data = Database.getList(this);
        }
        return store().data;
    }

    /**
     * Returns a {@link DBQuery} object that can be used to query the database.
     * @return
     */
    public DBQuery<E> query() {
        resolveDB();
        return new DBQuery<E>(data());
    }

    /**
     * Looks to see if an object is in the database.
     * If it is, it returns the id.
     * @param e The object to look for.
     * @return The id or -1 is nonexistent.
     */
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

    /**
     * Gets an object by its id.
     * @param id The id of an object.
     * @return The object or null if nonexistent.
     */
    public E getByID(int id) {
        if(id >= data().size() || id < 0) {
            return null;
        }
        return data().get(id);
    }

    /**
     * Resolves all elements in a list. Makes sure them and all connections are in the database.
     * @param list A list of elements to resolve.
     */
    public void resolve(List<E> list) {
        for(E e : list) {
            resolve(e);
        }
    }

    /**
     * takes in an element. If it is not in the store, it adds it and returns the id.
     * If it is in the store, it updates the element and returns the id.
     * @param e The element to resolve.
     * @return The id of the element.
     */
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

    /**
     * Resolves all connections in the store.
     */
    protected void resolveConnections() {
        for(E e : data()) {
            getSerde().resolveConnections(e);
        }
    }

    /**
     * The actual constructor. Makes sure the original map of stores is initialized.
     * Also makes sure that this store is in the map.
     */
    protected DBStore() {
        if(stores == null) {
            stores = new TreeMap<>();
        }
        if(!stores.containsKey(getFilename())) {
            stores.put(getFilename(),this);
        }
    }

    /**
     * Just queries the database so that it will resolve.
     */
    public void init() {
        data();
    }

    /**
     * Gets the name of the file this store is saved in.
     * Each store is different.
     * @return The name of the file this store is saved in.
     */
    public abstract String getFilename();

    /**
     * @return The {@link DBSerde} for this store.
     */
    public abstract DBSerde<E> getSerde();

    /**
     * Saves this store to the database.
     */
    private void save() {
        Database.save(store());
    }
}
