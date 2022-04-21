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
     $hasProfileImage = $_POST["hasProfileImage"];

     $response = array();
     // validation
     $sql = 
        "SELECT teacherID
            FROM SSLC_Teacher
            WHERE teacherID = '$teacherID'";
    $result = mysqli_query($conn, $sql);

    if (!is_null(mysqli_fetch_array($result))) {

        $response["validation"] = false;
        $response["success"] = false;
        echo json_encode($response);
    } else {

        $response["validation"] = true;
        $statement = mysqli_prepare(
            $conn,
            "INSERT INTO SSLC_Teacher(
                            teacherName,
                            teacherDOB,
                            teacherClass,
                            teacherID,
                            teacherPassword,
                            teacherIntroduce,
                            hasProfileImage) 
                VALUES (?, ?, ?, ?, ?, ?, ?)"
        );
        mysqli_stmt_bind_param(
            $statement, 
            "sssssss", 
            $teacherName, $teacherDOB, $teacherClass, $teacherID, $teacherPassword, $teacherIntroduce, $hasProfileImage
        );
        mysqli_stmt_execute($statement);

        $getNumberSQL = mysqli_query(
            $conn,
            "SELECT StudentNumber 
               FROM `SSLC_Student` 
               ORDER BY StudentID DESC LIMIT 1"
         );

         $response["rowCount"] = 0;
         while ($row = mysqli_fetch_array($getNumberSQL)) {

            // Return the number of rows in result set
            $response["rowCount"] = (int)$row[0] + 1;
         }
      
         $response["success"] = true;
    
         echo json_encode($response);
    }
?>