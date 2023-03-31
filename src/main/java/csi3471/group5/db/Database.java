package csi3471.group5.db;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Database {
    public static <E,T extends DBStore<E,T>> ArrayList<E> getList(DBStore<E,T> store) {
        ArrayList<E> list = new ArrayList<E>();
        //open and read file
        System.out.println(Database.class.getResource("/db/"+store.getFilename()+".csv").getFile());
        URL res = Database.class.getResource("/db/"+store.getFilename()+".csv");
        File file = null;
        try {
            file = new File(res.toURI());
        } catch (URISyntaxException e) {
            System.out.println("Can't get resource " + store.getFilename() + ".csv");
            throw new RuntimeException(e);
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            //create file
            try {
                file.createNewFile();
                reader = new BufferedReader(new FileReader(file));
            } catch (IOException e1) {
                //die
                System.out.println("Could not create file");
                return list;
            }
        }
        reader.lines().forEach(line -> {
            String[] fields = line.split(",");
            list.add(store.getSerde().deserialize(fields));
        });
        return list;
    }
    public static <E,T extends DBStore<E,T>> void save(DBStore<E,T> store) {
        //open and write file
        URL res = Database.class.getResource("/db/"+store.getFilename()+".csv");
        File file = null;
        try {
            file = new File(res.toURI());
        } catch (URISyntaxException e) {
            System.out.println("Can't get resource " + store.getFilename() + ".csv");
            throw new RuntimeException(e);
        }
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            //create file
            try {
                file.createNewFile();
                writer = new PrintWriter(file);
            } catch (IOException e1) {
                //die
                System.out.println("Could not create file");
                return;
            }
        }
        for(E e : store.query().get()) {
            ArrayList<String> fields = store.getSerde().serialize(e);
            writer.println(fields.stream().collect(Collectors.joining(",")));
        }
        writer.close();
    }
}
