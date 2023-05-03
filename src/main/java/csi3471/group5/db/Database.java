package csi3471.group5.db;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * The main database interface. It is what reads and writes to csv files.
 */
public class Database {
    /**
     * Get a list of objects from a csv file.
     * @param store The solid store to get that explains how to serialize and deserialize the objects.
     * @param <E> The type of object to get.
     * @param <T> The type of store to get.
     * @return Returns an ArrayList of objects of type <E>.
     */
    public static <E,T extends DBStore<E,T>> ArrayList<E> getList(DBStore<E,T> store) {
        ArrayList<E> list = new ArrayList<E>();
        //open and read file
        System.out.println(Database.class.getResource("/db/"+store.getFilename()+".csv").getFile());
//        URL res = Database.class.getResource("/db/"+store.getFilename()+".csv");
        File file;
        try {
            file = getFileResource(store.getFilename());
        } catch (URISyntaxException e) {
            System.out.println("Can't get resource " + store.getFilename() + ".csv");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.out.println("Can't open file " + store.getFilename() + ".csv");
            throw new RuntimeException(e);
        }
        reader.lines().forEach(line -> {
            String[] fields = line.split(",");
            list.add(store.getSerde().deserialize(fields));
        });
        return list;
    }

    /**
     * Saves all items in a store to a csv file.
     * @param store The store to save.
     * @param <E> The type of object to save.
     * @param <T> The solid type of store to save.
     */
    public static <E,T extends DBStore<E,T>> void save(DBStore<E,T> store) {
        //open and write file
//        URL res = Database.class.getResource("/db/"+store.getFilename()+".csv");
        File file;
        try {
            file = getFileResource(store.getFilename());
        } catch (URISyntaxException e) {
            System.out.println("Can't get resource " + store.getFilename() + ".csv");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            System.out.println("Can't open file " + store.getFilename() + ".csv");
            throw new RuntimeException(e);
        }
        for(E e : store.query().get()) {
            ArrayList<String> fields = store.getSerde().serialize(e);
            writer.println(String.join(",", fields));
        }
        writer.close();
    }

    /**
     * Gets a resources at /db/<filename>.csv and makes sure it exists at ./.db/<filename>.csv
     * Once it is there, it returns the file.
     * @param filename
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    private static File getFileResource(String filename) throws IOException, URISyntaxException {
        URI uri = Database.class.getResource("/db/"+filename+".csv").toURI();
        File actual = new File("./.db/"+filename+".csv");
        if(!actual.exists()) {
            actual.getParentFile().mkdirs();
            actual.createNewFile();
            InputStream is = Database.class.getResourceAsStream("/db/"+filename+".csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            PrintWriter writer = new PrintWriter(actual);
            writer.println(reader.lines().collect(Collectors.joining("\n")));
            writer.close();
        }
        return actual;
    }
}
