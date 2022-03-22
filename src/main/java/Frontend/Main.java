package Frontend;

import Backend.Dataset;
import Backend.Job;

import java.util.concurrent.ThreadLocalRandom;

import static Backend.DataMunging.pullJSON;
import static Backend.DataMunging.pushJSON;

public class Main {
    private String[] dataSetNames;
    private MainScreen mainScreen;
    private Dataset data;

    public Main(MainScreen inputMain){
        pushJSON(refreshRandomData());
        mainScreenStartup(inputMain);
    }

    private void mainScreenStartup(MainScreen inputMain){
        data = pullJSON("test.json");
        mainScreen = inputMain;
        mainScreen.setUp();
        dropdownData(data);
        mainScreen.setVisible(true);
    }

    // helper-ish function that creates a list of titles to feed to the JComboBox.
    public void dropdownData(){
        if(dataSetNames == null) {
            dataSetNames = new String[data.length()];
        }else if(dataSetNames.length != data.length()){
            String[] temp = new String[data.length()];
            dataSetNames = temp;
        }
        for(int i = 0; i < data.length(); i++){
            dataSetNames[i] = data.readItem(i).getTitle();
        }
        mainScreen.dataComboBoxRefresh(dataSetNames);
    }

    public void dropdownData(Dataset data){
        if(dataSetNames == null) {
            dataSetNames = new String[data.length()];
        }else if(dataSetNames.length != data.length()){
            String[] temp = new String[data.length()];
            dataSetNames = temp;
        }
        for(int i = 0; i < data.length(); i++){
            dataSetNames[i] = data.readItem(i).getTitle();
        }
        mainScreen.dataComboBoxRefresh(dataSetNames);
    }


    public void addData(Job newJob){
        data.add(newJob);
        pushJSON(data);
        dropdownData();
    }
    public void deleteData(Job deleteJob){
        data.remove(deleteJob);
        pushJSON(data);
        dropdownData();
    }

    //Test code that creates random datasets
    public Dataset refreshRandomData(){
        Dataset data = new Dataset();
        for(int i = 0; i < 10; i++) {
            data.add((new Job("Title" + String.valueOf(ThreadLocalRandom.current().nextInt(1, 10)),
                    "Client" + String.valueOf(i),
                    (double) ThreadLocalRandom.current().nextInt(1, 10),
                    (double) ThreadLocalRandom.current().nextInt(1, 10),
                    (double) ThreadLocalRandom.current().nextInt(1, 10))));
        }
        return data;
    }






}
