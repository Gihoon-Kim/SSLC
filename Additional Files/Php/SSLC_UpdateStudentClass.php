<?php
     $db_name = "hoonyhosting";
     $username = "hoonyhosting";
     $password = "wjsghkrl1!";
     $servername = "localhost";
 
     $conn = mysqli_connect($servername, $username, $password, $db_name);

     $studentNumber = $_POST["studentNumber"];
     $studentClass = $_POST["studentClass"];

     $statement = mysqli_prepare(
        $conn,
        "UPDATE SSLC_Student
            SET StudentClass = '$studentClass'
            WHERE StudentNumber = '$studentNumber'"
    );
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>