<?php
     $db_name = "hoonyhosting";
     $username = "hoonyhosting";
     $password = "wjsghkrl1!";
     $servername = "localhost";
 
     $conn = mysqli_connect($servername, $username, $password, $db_name);

     $classTitle = $_POST["classTitle"];

     $result = mysqli_query(
         $conn,
         "SELECT homeworkTitle, homeworkScript, homeworkDeadline
            FROM SSLC_Homework
            WHERE homeworkClass = '$classTitle'"
     );
     $response = array();
     while ($row = mysqli_fetch_array($result)) {

        array_push(
            $response,
            array(
                'success' => true,
                'homeworkTitle' => $row[0],
                'homeworkScript' => $row[1],
                'homeworkDeadline' => $row[2]
            )
            );
     }

     echo json_encode(array("ClassHomework"=>$response));
     mysqli_close($conn);
?>