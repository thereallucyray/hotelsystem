package csi3471.group5.db;

import java.util.ArrayList;

public abstract class DBSerde<E> {
    /**
     * Serialize an object to a string array.
     * @param obj The object to serialize.
     * @return The string array.
     */
    public abstract ArrayList<String> serialize(E obj);

    /**
     * Deserialize a string array to an object.
     * @param s The string array to deserialize.
     * @return The object.
     */
    public abstract E deserialize(String[] s);

    /**
     * Resolve connections between objects
     * (e.g. if a user has a list of messages, resolve the messages to the actual objects)
     * @param obj The object to resolve connections for.
     */
    public void resolveConnections(E obj) {};
}
