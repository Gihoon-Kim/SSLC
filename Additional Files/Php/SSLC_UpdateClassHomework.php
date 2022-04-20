<?php
     $db_name = "hoonyhosting";
     $username = "hoonyhosting";
     $password = "wjsghkrl1!";
     $servername = "localhost";
 
     $conn = mysqli_connect($servername, $username, $password, $db_name);

     $homeworkOldTitle = $_POST["homeworkOldTitle"];
     $homeworkNewTitle = $_POST["homeworkNewTitle"];
     $className = $_POST["className"];
     $homeworkDeadline = $_POST["homeworkDeadline"];
     $homeworkScript = $_POST["homeworkScript"];

     $statement = mysqli_prepare(
        $conn,
        "UPDATE SSLC_Homework
            SET homeworkTitle = '$homeworkNewTitle',
                homeworkScript = '$homeworkScript',
                homeworkDeadline = '$homeworkDeadline'
            WHERE homeworkTitle = '$homeworkOldTitle' and homeworkClass = '$className'"
    );
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>