<?php
     $db_name = "hoonyhosting";
     $username = "hoonyhosting";
     $password = "wjsghkrl1!";
     $servername = "localhost";
 
     $conn = mysqli_connect($servername, $username, $password, $db_name);

     $classNumber = $_POST["classNumber"];
     $classTitle = $_POST["classTitle"];
     $classTeacher = $_POST["classTeacher"];
     $classDescription = $_POST["classDescription"];
     $classStartTime = $_POST["classStartTime"];
     $classEndTime = $_POST["classEndTime"];
     $classRoom = $_POST["classRoom"];

     $statement = mysqli_prepare(
        $conn,
        "UPDATE SSLC_Class
            SET classTitle = '$classTitle',
                classTeacher = '$classTeacher',
                classDescription = '$classDescription',
                classStartTime = '$classStartTime',
                classEndTime = '$classEndTime',
                classRoom = '$classRoom'
            WHERE classNumber = '$classNumber'"
    );
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>