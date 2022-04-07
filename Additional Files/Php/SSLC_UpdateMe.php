<?php
    $db_name = "hoonyhosting";
    $hostname = "hoonyhosting";
    $password = "wjsghkrl1!";
    $servername = "localhost";

    $conn = mysqli_connect($servername, $hostname, $password, $db_name);

    $userID = $_POST["userID"];
    $userIntroduce = $_POST["userIntroduce"];
    $userDOB = $_POST["userDOB"];
    $userImage = $_POST["userImage"];
    $isTeacher = $_POST["isTeacher"];
    
    if ($isTeacher == "1") {

        $statement = mysqli_prepare(
            $conn,
            "UPDATE SSLC_Teacher
                SET teacherDOB = '$userDOB',
                    teacherIntroduce = '$userIntroduce',
                    teacherImage = '$userImage'
                WHERE teacherID = '$userID'"
        );
    } else {

        $statement = mysqli_prepare(
            $conn,
            "UPDATE SSLC_Student
                SET studentDOB = '$userDOB',
                    studentIntroduce = '$userIntroduce',
                    studentImage = '$userImage'
                WHERE studentID = '$userID'"
        );
    }
    
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>