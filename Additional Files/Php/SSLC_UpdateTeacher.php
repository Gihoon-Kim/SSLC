<?php
    $db_name = "hoonyhosting";
    $hostname = "hoonyhosting";
    $password = "wjsghkrl1!";
    $servername = "localhost";

    $conn = mysqli_connect($servername, $hostname, $password, $db_name);

    $teacherID = $_POST["teacherID"];
    $teacherName = $_POST["teacherName"];
    $teacherClass = $_POST["teacherClass"];
    $teacherIntroduce = $_POST["teacherIntroduce"];
    $teacherDOB = $_POST["teacherDOB"];
    $teacherImage = $_POST["teacherImage"];
    
    $statement = mysqli_prepare(
        $conn,
        "UPDATE SSLC_Teacher
            SET TeacherName = '$teacherName',
                TeacherDOB = '$teacherDOB',
                TeacherClass = '$teacherClass',
                TeacherIntroduce = '$teacherIntroduce',
                TeacherImage = '$teacherImage'
            WHERE TeacherID = '$teacherID'"
    );
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>