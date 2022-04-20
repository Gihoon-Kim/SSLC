<?php
     $db_name = "hoonyhosting";
     $username = "hoonyhosting";
     $password = "wjsghkrl1!";
     $servername = "localhost";
 
     $conn = mysqli_connect($servername, $username, $password, $db_name);
     
     $homeworkTitle = $_POST["homeworkTitle"];
     $className = $_POST["className"];

     $statement = mysqli_prepare(
        $conn,
        "DELETE 
            FROM `SSLC_Homework` 
            WHERE homeworkTitle = '$homeworkTitle' and homeworkClass = '$className'"
    );
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
    mysqli_close($conn);
?>