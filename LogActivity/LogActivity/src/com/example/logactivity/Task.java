package com.example.logactivity;

public class Task {
    private String taskName;
    private int id;
    private long startTime,  endTime;
    private String Intensity,  Calories;
    private int Days;
    private int Frequency;
    private int Notify;
    public Task()
    {
        this.taskName=null;
        this.startTime = 0;
        this.endTime = 0;
        this.Intensity = null;
        this.Calories = null;
        this.Days=0;
        this.Frequency=0;
        this.Notify=0;
    }
    public Task(String taskName, long startTime, long endTime, String Intensity, String Calories,int Days, int Frequency,int Notify){
        this.taskName = taskName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.Intensity = Intensity;
        this.Calories = Calories;
        this.Days=Days;
        this.Frequency=Frequency;
        this.Notify=Notify;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getDays() {
        return Days;
    }
    public void setDays(int Days) {
        this.Days = Days;
    }
    public String getIntensity() {
        return Intensity;
    }
    public void setIntensity(String Intensity) {
        this.Intensity = Intensity;
    }
    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public String getCalories() {
        return Calories;
    }
    public void setCalories(String Calories) {
        this.Calories = Calories;
    }
    public long getStartTime() {
        return startTime;
    }
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    public long getEndTime() {
        return endTime;
    }
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
	public int getFrequency() {
		// TODO Auto-generated method stub
		return Frequency;
	}
	public void setFrequency(int Frequency) {
        this.Frequency = Frequency;
    }
	public int getNotify() {
		// TODO Auto-generated method stub
		return Notify;
	}
	public void setNotify(int Notify) {
        this.Notify = Notify;
    }
	
}