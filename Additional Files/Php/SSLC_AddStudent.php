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
    
         $sql = "SELECT * from SSLC_Student";

      if ($result = mysqli_query($conn, $sql)) {

            // Return the number of rows in result set
            $rowcount = mysqli_num_rows( $result );
            $response["rowCount"] = $rowcount;
      }
      $response["success"] = true;
    
      echo json_encode($response);
     }

     
?>