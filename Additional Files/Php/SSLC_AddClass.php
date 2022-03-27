<?php
     $db_name = "hoonyhosting";
     $username = "hoonyhosting";
     $password = "wjsghkrl1!";
     $servername = "localhost";
 
     $conn = mysqli_connect($servername, $username, $password, $db_name);

     $classTitle = $_POST["classTitle"];
     $classTeacher = $_POST["classTeacher"];
     $classDescription = $_POST["classDescription"];
     $classStartTime = $_POST["classStartTime"];
     $classEndTime = $_POST["classEndTime"];
     $classRoom = $_POST["classRoom"];

     $statement = mysqli_prepare(
        $conn,
        "INSERT INTO SSLC_Class(
            classTitle,
            classTeacher,
            classDescription,
            classStartTime,
            classEndTime,
            classRoom) 
            VALUES (?, ?, ?, ?, ?, ?)"
    );
    mysqli_stmt_bind_param(
        $statement, 
        "ssssss", 
        $classTitle, $classTeacher, $classDescription, $classStartTime, $classEndTime, $classRoom
    );
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>

