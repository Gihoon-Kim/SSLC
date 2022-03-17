<?php
     $db_name = "hoonyhosting";
     $username = "hoonyhosting";
     $password = "wjsghkrl1!";
     $servername = "localhost";
 
     $conn = mysqli_connect($servername, $username, $password, $db_name);

     $newsTitle = $_POST["newsTitle"];
     $newsDescription = $_POST["newsDescription"];
     $newsCreatedAt = $_POST["newsCreatedAt"];

     $statement = mysqli_prepare(
        $conn,
        "INSERT INTO SSLC_News(newsTitle, newsDescription, createdAt) 
            VALUES (?, ?, ?)"
    );
    mysqli_stmt_bind_param(
        $statement, 
        "sss", 
        $newsTitle, $newsDescription, $newsCreatedAt
    );
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>
