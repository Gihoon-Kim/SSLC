package com.sslc.sslc.data;

public class Programs {

    int programNumber;
    String programTitle;
    String programStartTime;
    String programEndTime;
    String programTeacher;
    String programDescription;
    String programClassRoom;

    public Programs(
            int classNumber,
            String classTitle,
            String classTeacher,
            String classDescription,
            String classStartTime,
            String classEndTime,
            String classRoom
    ) {

        this.programNumber = classNumber;
        this.programTitle = classTitle;
        this.programTeacher = classTeacher;
        this.programDescription = classDescription;
        this.programStartTime = classStartTime;
        this.programEndTime = classEndTime;
        this.programClassRoom = classRoom;
    }

    public Programs(String classTitle, String classDescription, String classStartTime, String classEndTime, String classRoom) {

        this.programTitle = classTitle;
        this.programDescription = classDescription;
        this.programStartTime = classStartTime;
        this.programEndTime = classEndTime;
        this.programClassRoom = classRoom;
    }

    public String getProgramClassRoom() {
        return programClassRoom;
    }

    public void setProgramClassRoom(String programClassRoom) {
        this.programClassRoom = programClassRoom;
    }

    public int getProgramNumber() {
        return programNumber;
    }

    public String getProgramTitle() {
        return programTitle;
    }

    public void setProgramTitle(String programTitle) {
        this.programTitle = programTitle;
    }

    public String getProgramStartTime() {
        return programStartTime;
    }

    public void setProgramStartTime(String programStartTime) {
        this.programStartTime = programStartTime;
    }

    public String getProgramEndTime() {
        return programEndTime;
    }

    public void setProgramEndTime(String programEndTime) {
        this.programEndTime = programEndTime;
    }

    public String getProgramTeacher() {
        return programTeacher;
    }

    public void setProgramTeacher(String programTeacher) {
        this.programTeacher = programTeacher;
    }

    public String getProgramDescription() {
        return programDescription;
    }

    public void setProgramDescription(String programDescription) {
        this.programDescription = programDescription;
    }
}
