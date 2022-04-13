<?php
     $db_name = "hoonyhosting";
     $username = "hoonyhosting";
     $password = "wjsghkrl1!";
     $servername = "localhost";
 
     $conn = mysqli_connect($servername, $username, $password, $db_name);

     $newsTitle = $_POST["newsTitle"];
     $newsDescription = $_POST["newsDescription"];
     $classTitle = $_POST["classTitle"];
     $newsCreatedAt = $_POST["newsCreatedAt"];

     $statement = mysqli_prepare(
        $conn,
        "INSERT INTO SSLC_ClassNews(newsTitle, newsDescription, className, createdAt) 
            VALUES (?, ?, ?, ?)"
    );
    mysqli_stmt_bind_param(
        $statement, 
        "ssss", 
        $newsTitle, $newsDescription, $classTitle, $newsCreatedAt
    );
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>
