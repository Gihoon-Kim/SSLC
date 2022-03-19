<?php
     $db_name = "hoonyhosting";
     $username = "hoonyhosting";
     $password = "wjsghkrl1!";
     $servername = "localhost";
 
     $conn = mysqli_connect($servername, $username, $password, $db_name);
     
     $teacherName = $_POST["teacherName"];
     $teacherClass = $_POST["teacherClass"];
     $teacherDOB = $_POST["teacherDOB"];
     $teacherIntroduce = $_POST["teacherIntroduce"];
     $teacherImage = $_POST["teacherImage"];

     $statement = mysqli_prepare(
        $conn,
        "INSERT INTO SSLC_Teacher(
                        teacherName,
                        teacherDOB,
                        teacherClass,
                        teacherIntroduce,
                        teacherImage) 
            VALUES (?, ?, ?, ?, ?)"
    );
    mysqli_stmt_bind_param(
        $statement, 
        "sssss", 
        $teacherName, $teacherDOB, $teacherClass, $teacherIntroduce, $teacherImage
    );
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>