<?php
     $db_name = "hoonyhosting";
     $username = "hoonyhosting";
     $password = "wjsghkrl1!";
     $servername = "localhost";
 
     $conn = mysqli_connect($servername, $username, $password, $db_name);
     
     $teacherName = $_POST["teacherName"];
     $teacherDOB = $_POST["teacherDOB"];
     $teacherClass = $_POST["teacherClass"];
     $teacherID = $_POST["teacherID"];
     $teacherPassword = $_POST["teacherPassword"];
     $teacherIntroduce = $_POST["teacherIntroduce"];
     $teacherImage = $_POST["teacherImage"];

     $statement = mysqli_prepare(
        $conn,
        "INSERT INTO SSLC_Teacher(
                        teacherName,
                        teacherDOB,
                        teacherClass,
                        teacherID,
                        teacherPassword,
                        teacherIntroduce,
                        teacherImage) 
            VALUES (?, ?, ?, ?, ?, ?, ?)"
    );
    mysqli_stmt_bind_param(
        $statement, 
        "sssssss", 
        $teacherName, $teacherDOB, $teacherClass, $teacherID, $teacherPassword, $teacherIntroduce, $teacherImage
    );
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>