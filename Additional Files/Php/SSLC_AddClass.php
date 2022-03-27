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

    $getNumberSQL = mysqli_query(
        $conn,
        "SELECT classNumber 
           FROM `SSLC_Class` 
           ORDER BY classNumber DESC LIMIT 1"
     );
     
    $response = array();
    $response["rowCount"] = 0;
    while ($row = mysqli_fetch_array($getNumberSQL)) {

        // Return the number of rows in result set
        $response["rowCount"] = (int)$row[0] + 1;
     }

    $response["success"] = true;

    echo json_encode($response);
?>

