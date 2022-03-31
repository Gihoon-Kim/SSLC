<?php
     $db_name = "hoonyhosting";
     $username = "hoonyhosting";
     $password = "wjsghkrl1!";
     $servername = "localhost";
 
     $conn = mysqli_connect($servername, $username, $password, $db_name);

     $result = mysqli_query(
         $conn,
         "SELECT studentNumber, studentName, studentDOB, studentClass, Country, StudentIntroduce
            FROM SSLC_Student"
     );
     $response = array();
     while ($row = mysqli_fetch_array($result)) {

        array_push(
            $response,
            array(
                'success' => true,
                'studentNumber' => $row[0],
                'studentName' => $row[1],
                'studentDOB' => $row[2],
                'studentClass' => $row[3],
                'studentCountry' => $row[4],
                'studentIntroduce' => $row[5]
            )
            );
     }

     echo json_encode(array("Student"=>$response));
     mysqli_close($conn);
?>