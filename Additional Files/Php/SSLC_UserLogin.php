<?php
     $db_name = "hoonyhosting";
     $username = "hoonyhosting";
     $password = "wjsghkrl1!";
     $servername = "localhost";
 
     $conn = mysqli_connect($servername, $username, $password, $db_name);

     $userName;
     $isTeacher;
     $userID = trim($_POST["userID"]);
     $userPassword = trim($_POST["userPassword"]);

     $statement = mysqli_prepare(
         $conn,
         "SELECT t.teacherName, t.teacherDOB, t.teacherClass, t.teacherIntroduce, t.teacherImage, t.isTeacher
                FROM SSLC_Teacher t
                WHERE t.teacherID = '$userID' AND t.teacherPassword = '$userPassword'
            UNION
            SELECT s.studentName, s.studentDOB, s.studentClass, s.studentIntroduce, s.studentProfileImage, s.isTeacher
                FROM SSLC_Student s
                WHERE s.studentID = '$userID' AND s.studentPassword = '$userPassword';"
     );
     mysqli_stmt_execute($statement);
     mysqli_stmt_store_result($statement);
     mysqli_stmt_bind_result($statement, $userName, $userDOB, $userClass, $userIntroduce, $userImage, $isTeacher);

     $response = array();
     $response["success"] = false;

     while (mysqli_stmt_fetch($statement)) {

        $response["success"] = true;
        $response["userName"] = $userName;
        $response["userDOB"] = $userDOB;
        $response["userClass"] = $userClass;
        $response["userIntroduce"] = $userIntroduce;
        $response["userImage"] = $userImage;
        $response["isTeacher"] = $isTeacher;
     }

     echo json_encode($response);
?>