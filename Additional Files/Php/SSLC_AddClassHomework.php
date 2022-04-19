<?php
     $db_name = "hoonyhosting";
     $username = "hoonyhosting";
     $password = "wjsghkrl1!";
     $servername = "localhost";
 
     $conn = mysqli_connect($servername, $username, $password, $db_name);

     $homeworkTitle = $_POST["homeworkTitle"];
     $homeworkScript = $_POST["homeworkScript"];
     $homeworkDeadline = $_POST["homeworkDeadline"];
     $classTitle = $_POST["classTitle"];

     $statement = mysqli_prepare(
        $conn,
        "INSERT INTO SSLC_Homework(homeworkTitle, homeworkScript, homeworkDeadline, homeworkClass) 
            VALUES (?, ?, ?, ?)"
    );
    mysqli_stmt_bind_param(
        $statement, 
        "ssss", 
        $homeworkTitle, $homeworkScript, $homeworkDeadline, $classTitle
    );
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>
