package Backend;

import java.util.LinkedList;

public class Dataset{
    private LinkedList<Job> jobs;

    public Dataset(Job job) {
        jobs = new LinkedList<>();
        jobs.addFirst(job);
    }

    public Dataset(LinkedList<Job> jobs) {
        this.jobs = jobs;
    }

    public Dataset(Job[] jobArray) {
        jobs = new LinkedList<>();
        for(int i = 0; i < jobArray.length; i++){
            jobs.addLast(jobArray[i]);
        }
    }

    public Dataset() {
        jobs = new LinkedList<>();
    }

    public void add(Job item){
        jobs.addLast(item);
    }

    public int length(){
        return jobs.size();
    }

    //finds item at location given
    public Job readItem(int i) {
        if (jobs.peekFirst() == null || jobs.get(i) == null) return null;
        else return jobs.get(i);
    }

    //Returns a Job matching a given title.
    public Job fetch(String title) {
        for(int i = 0 ; i < jobs.size(); i++){
            if (jobs.get(i).getTitle().equals(title)){
                return jobs.get(i);
            }
        }
        return null;
    }
    public void remove(Job job){
        if(jobs.remove(this.fetch(job.getTitle())))
            System.out.println(job.getTitle() + " was removed");
        else System.out.println(job.getTitle() + "was not removed");

    }
}
