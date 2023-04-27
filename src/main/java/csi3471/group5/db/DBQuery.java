package csi3471.group5.db;

import java.util.ArrayList;

/**
 * A query result from the database.
 * Was going to do chaining and builder pattern but I don't think it's necessary.
 * @param <E> The type of object to query.
 */
public class DBQuery<E> {
    private ArrayList<E> data;
    DBQuery(ArrayList<E> d) {
        this.data = new ArrayList<E>(d);
    }

    /**
     * Get the first item in the query.
     * @return that item or null if there are no items.
     */
    public E getOne() {
        if(data.size() == 0) {
            return null;
        }
        return data.get(0);
    }

    /**
     * @return an ArrayList of the query.
     */
    public ArrayList<E> get() {
        return data;
    }

    /**
     * Get the item at the specified index.
     * (Not very useful as queries can change. Use {@link DBStore} to get a specific item.)
     * @param index - index in the query
     * @return the item at that index.
     */
    public E getIndex(int index) { return data.get(index); }
}
