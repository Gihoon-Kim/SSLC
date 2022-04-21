<?php
     $db_name = "hoonyhosting";
     $username = "hoonyhosting";
     $password = "wjsghkrl1!";
     $servername = "localhost";
 
     $conn = mysqli_connect($servername, $username, $password, $db_name);

     $result = mysqli_query(
         $conn,
         "SELECT teacherNumber, teacherName, teacherDOB, teacherClass, teacherIntroduce, hasProfileImage
            FROM SSLC_Teacher"
     );
     $response = array();
     while ($row = mysqli_fetch_array($result)) {

        array_push(
            $response,
            array(
                'success' => true,
                'teacherNumber' => $row[0],
                'teacherName' => $row[1],
                'teacherDOB' => $row[2],
                'teacherClass' => $row[3],
                'teacherIntroduce' => $row[4],
                'hasProfileImage' => $row[5]
            )
            );
     }

     echo json_encode(array("Teacher"=>$response));
     mysqli_close($conn);
?>