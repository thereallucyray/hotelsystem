package csi3471.group5.db;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Database {
    public static <E> ArrayList<E> getList(DBStore<E> store) {
        ArrayList<E> list = new ArrayList<E>();
        //open and read file
        File file = new File(Database.class.getResource("/db/"+store.getFilename()+".csv").getFile());

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
    public static <E> void save(DBStore<E> store) {
        //open and write file
        File file = new File(Database.class.getResource("/db/"+store.getFilename()+".csv").getFile());
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
        for(E e : store.getList()) {
            ArrayList<String> fields = store.getSerde().serialize(e);
            writer.println(fields.stream().collect(Collectors.joining(",")));
        }
        writer.close();
    }
}
