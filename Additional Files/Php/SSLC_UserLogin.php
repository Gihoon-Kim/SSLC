<?php
     $db_name = "hoonyhosting";
     $username = "hoonyhosting";
     $password = "wjsghkrl1!";
     $servername = "localhost";
 
     $conn = mysqli_connect($servername, $username, $password, $db_name);

     $userID = trim($_POST["userID"]);
     $userPassword = trim($_POST["userPassword"]);

     $statement = mysqli_prepare(
         $conn,
         "SELECT t.isTeacher
            FROM SSLC_Teacher t
            WHERE t.teacherID = '$userID' AND t.teacherPassword = '$userPassword'
            UNION
                SELECT s.isTeacher
                    FROM SSLC_Student s
                    WHERE s.studentID = '$userID' AND s.studentPassword = '$userPassword';"
     );
     mysqli_stmt_execute($statement);
     mysqli_stmt_store_result($statement);
     mysqli_stmt_bind_result($statement, $isTeacher);
     
     $response = array();
     $response["success"] = false;

     while(mysqli_stmt_fetch($statement)) {
         $response["isTeacher"] = $isTeacher;
     }

     if ($isTeacher == 1) {

        $statement = mysqli_prepare(
            $conn,
            "SELECT t.teacherName, t.teacherID, t.teacherDOB, t.teacherClass, t.teacherIntroduce, t.teacherPassword, t.hasProfileImage
                   FROM SSLC_Teacher t
                   WHERE t.teacherID = '$userID' AND t.teacherPassword = '$userPassword';"
                   );

        mysqli_stmt_execute($statement);
        mysqli_stmt_store_result($statement);
        mysqli_stmt_bind_result($statement, $userName, $userID, $userDOB, $userClass, $userIntroduce, $userPassword, $hasProfileImage);

        while (mysqli_stmt_fetch($statement)) {

            $response["success"] = true;
            $response["userName"] = $userName;
            $response["userID"] = $userID;
            $response["userDOB"] = $userDOB;
            $response["userClass"] = $userClass;
            $response["userIntroduce"] = $userIntroduce;
            $response["userPassword"]=  $userPassword;
            $response["hasProfileImage"] = $hasProfileImage;
        }
     } else {

        $statement = mysqli_prepare(
            $conn,
            "SELECT s.studentName, s.studentID, s.studentDOB, s.studentClass, s.studentIntroduce, s.studentPassword, s.hasProfileImage, s.Country
                FROM SSLC_Student s
                WHERE s.studentID = '$userID' AND s.studentPassword = '$userPassword';"
                   );
        mysqli_stmt_execute($statement);
        mysqli_stmt_store_result($statement);
        mysqli_stmt_bind_result($statement, $userName, $userID, $userDOB, $userClass, $userIntroduce, $userPassword, $hasProfileImage, $country);

        while (mysqli_stmt_fetch($statement)) {

            $response["success"] = true;
            $response["userName"] = $userName;
            $response["userID"] = $userID;
            $response["userDOB"] = $userDOB;
            $response["userClass"] = $userClass;
            $response["userIntroduce"] = $userIntroduce;
            $response["userPassword"]=  $userPassword;
            $response["hasProfileImage"] = $hasProfileImage;
            $response["country"] = $country;
        }
     }

     echo json_encode($response);
?>