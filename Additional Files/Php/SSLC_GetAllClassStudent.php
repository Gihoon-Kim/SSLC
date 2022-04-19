<?php
     $db_name = "hoonyhosting";
     $username = "hoonyhosting";
     $password = "wjsghkrl1!";
     $servername = "localhost";
 
     $conn = mysqli_connect($servername, $username, $password, $db_name);

     $classTitle = $_POST["className"];

     $result = mysqli_query(
         $conn,
         "SELECT studentName, studentDOB, studentIntroduce, Country, studentProfileImage
            FROM SSLC_Student
            WHERE studentClass = '$classTitle';"
     );
     $response = array();
     while ($row = mysqli_fetch_array($result)) {

        array_push(
            $response,
            array(
                'success' => true,
                'studentName' => $row[0],
                'studentDOB' => $row[1],
                'studentIntroduce' => $row[2],
                'studentCountry' => $row[3],
                'studentProfileImage' => $row[4]
            )
            );
     }

     echo json_encode(array("ClassStudent"=>$response));
     mysqli_close($conn);
?>