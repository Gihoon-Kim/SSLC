package com.example.sslc.data;

public class Student extends People {

    int studentNumber;
    String studentCountry;

    public Student(
            int studentNumber,
            String studentName,
            String studentDOB,
            String studentClass,
            boolean isTeacher,
            String studentIntroduce
    ) {
        super(studentName, studentDOB, studentClass, isTeacher, studentIntroduce);

        this.studentNumber = studentNumber;
    }

    public Student(
            int studentNumber,
            String studentName,
            String studentDOB,
            String studentClass,
            boolean isTeacher,
            String studentIntroduce,
            String studentCountry
    ) {
        super(studentName, studentDOB, studentClass, isTeacher, studentIntroduce);

        this.studentNumber = studentNumber;
        this.studentCountry = studentCountry;
    }


    public int getStudentNumber() {
        return studentNumber;
    }

    public String getStudentCountry() {
        return studentCountry;
    }
}