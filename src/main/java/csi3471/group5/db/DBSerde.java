package csi3471.group5.db;

import java.util.ArrayList;

public abstract class DBSerde<E> {
    public abstract ArrayList<String> serialize(E obj);
    public abstract E deserialize(String[] s);
}
