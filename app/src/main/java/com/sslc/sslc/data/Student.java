package com.sslc.sslc.data;

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

    public Student(
            String studentName,
            String studentDOB,
            String studentIntroduce,
            String studentCountry,
            boolean hasProfileImage
    ) {
        super(studentName, studentDOB, studentIntroduce, hasProfileImage);

        this.studentCountry = studentCountry;
    }


    public int getStudentNumber() {
        return studentNumber;
    }

    public String getStudentCountry() {
        return studentCountry;
    }
}