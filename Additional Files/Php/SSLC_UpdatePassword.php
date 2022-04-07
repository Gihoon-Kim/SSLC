<?php
     $db_name = "hoonyhosting";
     $username = "hoonyhosting";
     $password = "wjsghkrl1!";
     $servername = "localhost";
 
     $conn = mysqli_connect($servername, $username, $password, $db_name);

     $userID = $_POST["userID"];
     $userPassword = $_POST["userPassword"];
     $isTeacher = $_POST["isTeacher"];

     if ($isTeacher == "1") {

        $statement = mysqli_prepare(
            $conn,
            "UPDATE SSLC_Teacher
                SET teacherPassword = '$userPassword'
                WHERE teacherID = '$userID'"
        );
    } else {

        $statement = mysqli_prepare(
            $conn,
            "UPDATE SSLC_Student
                SET studentPassword = '$userPassword'
                WHERE studentID = '$userID'"
        );
    }
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>