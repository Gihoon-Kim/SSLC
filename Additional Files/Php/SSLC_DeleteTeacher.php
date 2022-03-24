<?php
     $db_name = "hoonyhosting";
     $username = "hoonyhosting";
     $password = "wjsghkrl1!";
     $servername = "localhost";
 
     $conn = mysqli_connect($servername, $username, $password, $db_name);
     
     $teacherNumber = $_POST["teacherNumber"];

     $statement = mysqli_prepare(
        $conn,
        "DELETE 
            FROM `SSLC_Teacher` 
            WHERE teacherNumber = '$teacherNumber'"
    );
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
    mysqli_close($conn);
?>