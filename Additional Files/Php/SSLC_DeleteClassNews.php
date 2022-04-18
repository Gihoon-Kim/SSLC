<?php
     $db_name = "hoonyhosting";
     $username = "hoonyhosting";
     $password = "wjsghkrl1!";
     $servername = "localhost";
 
     $conn = mysqli_connect($servername, $username, $password, $db_name);
     
     $newsTitle = $_POST["newsTitle"];
     $className = $_POST["className"];

     $statement = mysqli_prepare(
        $conn,
        "DELETE 
            FROM `SSLC_ClassNews` 
            WHERE newsTitle = '$newsTitle' and className = '$className'"
    );
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
    mysqli_close($conn);
?>