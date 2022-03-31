<?php
     $db_name = "hoonyhosting";
     $username = "hoonyhosting";
     $password = "wjsghkrl1!";
     $servername = "localhost";
 
     $conn = mysqli_connect($servername, $username, $password, $db_name);

     $newsNumber = $_POST["newsNumber"];
     $newsTitle = $_POST["newsTitle"];
     $newsDescription = $_POST["newsDescription"];
     $newsCreatedAt = $_POST["newsCreatedAt"];

     $statement = mysqli_prepare(
        $conn,
        "UPDATE SSLC_News
            SET newsTitle = '$newsTitle',
                newsDescription = '$newsDescription',
                createdAt = '$newsCreatedAt' 
            WHERE newsNumber = '$newsNumber'"
    );
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>