package csi3471.group5.db;

import java.util.ArrayList;

public class DBQuery<E> {
    private ArrayList<E> data;
    DBQuery(ArrayList<E> d) {
        this.data = d;
    }
    public ArrayList<E> get() {
        return data;
    }
}
