<?php
     $db_name = "hoonyhosting";
     $username = "hoonyhosting";
     $password = "wjsghkrl1!";
     $servername = "localhost";
 
     $conn = mysqli_connect($servername, $username, $password, $db_name);

     $newsOldTitle = $_POST["newsOldTitle"];
     $newsNewTitle = $_POST["newsNewTitle"];
     $newsDescription = $_POST["newsDescription"];

     $statement = mysqli_prepare(
        $conn,
        "UPDATE SSLC_ClassNews
            SET newsTitle = '$newsNewTitle',
                newsDescription = '$newsDescription'
            WHERE newsTitle = '$newsOldTitle'"
    );
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>