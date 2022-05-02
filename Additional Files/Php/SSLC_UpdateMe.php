<?php
    $db_name = "hoonyhosting";
    $hostname = "hoonyhosting";
    $password = "wjsghkrl1!";
    $servername = "localhost";

    $conn = mysqli_connect($servername, $hostname, $password, $db_name);

    $userID = $_POST["userID"];
    $userIntroduce = $_POST["userIntroduce"];
    $userDOB = $_POST["userDOB"];
    $isTeacher = $_POST["isTeacher"];
    $hasProfileImage = $_POST["hasProfileImage"];
    
    if ($isTeacher == "1") {

        $statement = mysqli_prepare(
            $conn,
            "UPDATE SSLC_Teacher
                SET teacherDOB = '$userDOB',
                    teacherIntroduce = '$userIntroduce',
                    hasProfileImage = '$hasProfileImage'
                WHERE teacherID = '$userID'"
        );
    } else {

        $statement = mysqli_prepare(
            $conn,
            "UPDATE SSLC_Student
                SET studentDOB = '$userDOB',
                    studentIntroduce = '$userIntroduce',
                    hasProfileImage = '$hasProfileImage'
                WHERE studentID = '$userID'"
        );
    }
    
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>