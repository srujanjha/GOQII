package com.example.logactivity;

public class LogTask {
    private String taskName;
    private int id;
    private long startTime,  endTime;
    private String Intensity,  Calories;
    public LogTask()
    {
        this.taskName=null;
        this.startTime = 0;
        this.endTime = 0;
        this.Intensity = null;
        this.Calories = null;
    }
    public LogTask(String taskName, long startTime, long endTime, String Intensity, String Calories){
        this.taskName = taskName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.Intensity = Intensity;
        this.Calories = Calories;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
}