package csi3471.group5.db;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Database {
    public static <E> ArrayList<E> getList(DBStore<E> store) {
        ArrayList<E> list = new ArrayList<E>();
        //open and read file
        InputStream is = Database.class.getResourceAsStream("/db/"+store.getFilename()+".csv");

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        return reader.lines().map(line -> {
            String[] fields = line.split(",");
            E e = store.getSerde().deserialize(fields);
            return e;
        }).collect(Collectors.toCollection(ArrayList::new));
    }
}
