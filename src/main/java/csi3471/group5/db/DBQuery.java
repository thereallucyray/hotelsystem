package csi3471.group5.db;

import java.util.ArrayList;

public class DBQuery<E> {
    private ArrayList<E> data;
    DBQuery(ArrayList<E> d) {
        this.data = new ArrayList<E>(d);
    }
    public E getOne() {
        if(data.size() == 0) {
            return null;
        }
        return data.get(0);
    }
    public ArrayList<E> get() {
        return data;
    }
    public E getIndex(int index) { return data.get(index); }
}
