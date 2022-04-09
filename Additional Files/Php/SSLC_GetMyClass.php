<?php
     $db_name = "hoonyhosting";
     $username = "hoonyhosting";
     $password = "wjsghkrl1!";
     $servername = "localhost";
 
     $conn = mysqli_connect($servername, $username, $password, $db_name);

     $userID = $_POST["userID"];

     $result = mysqli_query(
         $conn,
         "SELECT *
	        FROM SSLC_Class
            WHERE SSLC_Class.classTeacher IN (
                SELECT teacherName
        	        FROM SSLC_Teacher
        	        WHERE teacherID = '$userID'
            )"
     );
     $response = array();
     while ($row = mysqli_fetch_array($result)) {

        array_push(
            $response,
            array(
                'success' => true,
                'classNumber' => $row[0],
                'classTitle' => $row[1],
                'classTeacher' => $row[2],
                'classDescription' => $row[3],
                'classStartTime' => $row[4],
                'classEndTime' => $row[5],
                'classRoom' => $row[6]
            )
            );
     }

     echo json_encode(array("Class"=>$response));
     mysqli_close($conn);
?>