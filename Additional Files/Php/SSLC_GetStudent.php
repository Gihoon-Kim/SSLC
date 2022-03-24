<?php
     $db_name = "hoonyhosting";
     $username = "hoonyhosting";
     $password = "wjsghkrl1!";
     $servername = "localhost";
 
     $conn = mysqli_connect($servername, $username, $password, $db_name);

     $result = mysqli_query(
         $conn,
         "SELECT StudentNumber, StudentName, StudentDOB, StudentClass, Country, StudentIntroduce
            FROM SSLC_Student"
     );
     $response = array();
     while ($row = mysqli_fetch_array($result)) {

        array_push(
            $response,
            array(
                'success' => true,
                'StudentNumber' => $row[0],
                'StudentName' => $row[1],
                'StudentDOB' => $row[2],
                'StudentClass' => $row[3],
                'Country' => $row[4],
                'StudentIntroduce' => $row[5]
            )
            );
     }

     echo json_encode(array("Student"=>$response));
     mysqli_close($conn);
?>