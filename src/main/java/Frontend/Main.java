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
        mainScreenStartup(inputMain);
    }

    private void mainScreenStartup(MainScreen inputMain){
        data = pullJSON("test.json");
        if(data == null){
            data = new Dataset();
            pushJSON(data);
            System.out.println("No Data found, creating internal database");
        }
        mainScreen = inputMain;
        mainScreen.setUp();
        dropdownData(data);
        mainScreen.setVisible(true);
    }

    // helper-ish function that creates a list of titles to feed to the JComboBox.
    public void dropdownData(){
        if(dataSetNames == null) {
            dataSetNames = new String[data.length()];
        }else if((dataSetNames.length != data.length())){
            String[] temp = new String[data.length()];
            dataSetNames = temp;
        }
        for(int i = 0; i < data.length(); i++){
            dataSetNames[i] = data.readItem(i).getTitle();
        }
        mainScreen.dataComboBoxRefresh(dataSetNames);
    }

    Job fetchJob(String name){
        //fetches job from the primary dataset
        return data.fetch(name);
    }

    public void dropdownData(Dataset inputData){
        // refreshes dataset that appears in the dropdown menu

        if(dataSetNames == null) {
            dataSetNames = new String[inputData.length()];
        }else if(dataSetNames.length != inputData.length()) {
            String[] temp = new String[inputData.length()];
            dataSetNames = temp;
        }
        for (int i = 0; i < inputData.length(); i++) {
            dataSetNames[i] = inputData.readItem(i).getTitle();

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

    public void deleteData(String deleteJobTitle){
        deleteData(data.fetch(deleteJobTitle));
    }

    public void addTime(Job job, int time){
        (data.fetch(job.getTitle())).setTime(job.getTime() + time);
        pushJSON(data);
    }

    public void addTime(String jobTitle, int time){
        Job job = data.fetch(jobTitle);
        job.setTime(job.getTime() + time);
        pushJSON(data);
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
