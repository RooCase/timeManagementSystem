package Backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;

public class DataMunging {
    //Pulls JSON file and returns a Dataset object full of Job objects.
    //If JSON file does not exist, creates the file and makes it empty.
    public static Dataset pullJSON(String filename) {
        File file = new File(filename);
        Gson gson = new Gson();
        JsonReader reader;
        Dataset data = null;
        try {
            reader = new JsonReader(new FileReader(filename));
            data = gson.fromJson(reader, Dataset.class);
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            try {
                file.createNewFile();
                pullJSON(filename);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
            try {
                file.createNewFile();
                pullJSON(filename);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return data;
    }

    //Pushes a dataset into JSON format.
    public static boolean pushJSON(Dataset data) {
        Gson gson = new GsonBuilder().create();
        File file = new File("test.json");
        Writer fileWriter;
        try{
            fileWriter = new FileWriter(file);
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        //fails if it can't write data
        // (which should never happen, unless the entire
        //  system becomes read-only, which means user has
        // bigger fish to fry. Much bigger fish.)
        try {
            gson.toJson(data, fileWriter);
            fileWriter.flush();
            fileWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
//Deprecated testing code, keeping here for posterity
/*    public static void main(String[] args) throws IOException {
        File file = new File("./test.json");
        FileWriter jsonOut = new FileWriter(file);
        String outputToWrite = "";
        Dataset data = new Dataset();
        for(int i = 0; i < 10; i++) {
            data.add((new Job("Title" + String.valueOf(i),
                    "Client" + String.valueOf(i),
                    ThreadLocalRandom.current().nextInt(1, 10),
                    ThreadLocalRandom.current().nextInt(1, 10),
                    ThreadLocalRandom.current().nextInt(1, 10))));
        }

        pushJSON(data);
        Dataset dataIn = pullJSON("test.json");

    }*/

