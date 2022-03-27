<?php
     $db_name = "hoonyhosting";
     $username = "hoonyhosting";
     $password = "wjsghkrl1!";
     $servername = "localhost";
 
     $conn = mysqli_connect($servername, $username, $password, $db_name);
     
     $studentName = $_POST["studentName"];
     $studentClass = $_POST["studentClass"];
     $studentDOB = $_POST["studentDOB"];
     $studentCountry = $_POST["studentCountry"];
     $studentID = $_POST["studentID"];
     $studentPassword = $_POST["studentPassword"];

     $response = array();
     // validation
     $sql = 
         "SELECT StudentID
            FROM SSLC_Student
            WHERE StudentID = '$studentID'";
     $result = mysqli_query($conn, $sql);
     if (!is_null(mysqli_fetch_array($result))) {

        $response["validation"] = false;
        $response["success"] = false;
        echo json_encode($response);
     } else {

         $response["validation"] = true;
         $statement = mysqli_prepare(
            $conn,
            "INSERT INTO SSLC_Student(
                            StudentName,
                            StudentDOB,
                            StudentClass,
                            Country,
                            StudentID,
                            StudentPassword) 
                VALUES (?, ?, ?, ?, ?, ?)"
         );
         mysqli_stmt_bind_param(
            $statement, 
            "ssssss", 
            $studentName, $studentDOB, $studentClass, $studentCountry, $studentID, $studentPassword
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