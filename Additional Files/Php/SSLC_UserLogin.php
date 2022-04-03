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
         "SELECT t.teacherName, t.teacherID, t.isTeacher
                FROM SSLC_Teacher t
                WHERE t.teacherID = '$userID' AND t.teacherPassword = '$userPassword'
            UNION
            SELECT s.studentName, s.studentID, s.isTeacher
                FROM SSLC_Student s
                WHERE s.studentID = '$userID' AND s.studentPassword = '$userPassword';"
     );
     mysqli_stmt_execute($statement);
     mysqli_stmt_store_result($statement);
     mysqli_stmt_bind_result($statement, $userName, $userID, $isTeacher);

     $response = array();
     $response["success"] = false;

     while (mysqli_stmt_fetch($statement)) {

        $response["success"] = true;
        $response["userName"] = $userName;
        $response["userID"] = $userID;
        $response["isTeacher"] = $isTeacher;
     }

     echo json_encode($response);
?>