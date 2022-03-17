<?php
     $db_name = "hoonyhosting";
     $username = "hoonyhosting";
     $password = "wjsghkrl1!";
     $servername = "localhost";
 
     $conn = mysqli_connect($servername, $username, $password, $db_name);

     $result = mysqli_query(
         $conn,
         "SELECT *
            FROM SSLC_News
            ORDER BY createdAt DESC"
     );
     $response = array();
     while ($row = mysqli_fetch_array($result)) {

        array_push(
            $response,
            array(
                'success' => true,
                'newsNumber' => $row[0],
                'newsTitle' => $row[1],
                'newsDescription' => $row[2],
                'newsCreatedAt' => $row[3]
            )
            );
     }

     echo json_encode(array("News"=>$response));
     mysqli_close($conn);
?>