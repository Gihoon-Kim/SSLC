<?php
     $db_name = "hoonyhosting";
     $username = "hoonyhosting";
     $password = "wjsghkrl1!";
     $servername = "localhost";
 
     $conn = mysqli_connect($servername, $username, $password, $db_name);

     $result = mysqli_query(
         $conn,
         "SELECT teacherName
            FROM SSLC_Teacher"
     );
     $response = array();
     while ($row = mysqli_fetch_array($result)) {

        array_push(
            $response,
            array(
                'success' => true,
                'teacherName' => $row[0]
            )
            );
     }

     echo json_encode(array("allTeacher"=>$response));
     mysqli_close($conn);
?>