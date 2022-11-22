package Backend;

public class Job {
    private String title;
    private String client;
    private Double hoursWorked;
    private Double billedPerHour;
    private Double time;
    private Double totalCharge;

    public Job(String title, String client, Double time, Double billedPerHour){
        this.title = title;
        this.client = client;
        this.time = time;
        this.billedPerHour = billedPerHour;
        calculateTime();
    }

    public Job(String title, String client, Double time, Double billedPerHour, Double hoursWorked){
        this.title = title;
        this.client = client;
        this.time = time;
        this.billedPerHour = billedPerHour;
        this.hoursWorked = hoursWorked;
        calculateTime();
    }
    public Job(){
        title = null;
        client = null;
        hoursWorked = 0.0;
        billedPerHour = 0.0;
    }

    public Double getBilledPerHour() {
        return billedPerHour;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public double getTime() {
        return time;
    }

    public double getTotalCharge() {
        return totalCharge;
    }

    public String getClient() {
        return client;
    }

    public String getTitle() {
        return title;
    }

    public void setBilledPerHour(double billedPerHour) {
        this.billedPerHour = billedPerHour;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public void setTime(double time) {
        this.time = time;
        calculateTime();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Job{" +
                "title='" + title + '\'' +
                ", client='" + client + '\'' +
                ", hoursWorked=" + hoursWorked +
                ", billedPerHour=" + billedPerHour +
                ", time=" + time +
                ", totalCharge=" + totalCharge +
                '}';
    }

    public String toReadableString(){
        return  "Title: '" + title + '\n' +
                "Client: '" + client + '\n' +
                "HoursWorked: " + hoursWorked + '\n'+
                "BilledPerHour: " + billedPerHour + '\n'+
                "Time: " + time +'\n'+
                "TotalCharge: " + totalCharge;
    }


    public String[] toStringArray(){
        String val[] = {title, client, String.valueOf(hoursWorked), String.valueOf(billedPerHour), String.valueOf(totalCharge)};
        return val;
    }



    private void calculateTime(){
        hoursWorked = time / 60;
        totalCharge = hoursWorked * billedPerHour;
    }

    public void setAll(String title, String client, Double hoursWorked, Double billedPerHour){
        this.title = title;
        this.client = client;
        this.time = hoursWorked * 60;
        this.billedPerHour = billedPerHour;
        calculateTime();
    }
}
