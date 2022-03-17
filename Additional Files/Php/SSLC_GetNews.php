<?php
     $db_name = "hoonyhosting";
     $username = "hoonyhosting";
     $password = "wjsghkrl1!";
     $servername = "localhost";
 
     $conn = mysqli_connect($servername, $username, $password, $db_name);

     $result = mysqli_query(
         $conn,
         "SELECT newsTitle, newsDescription, createdAt
            FROM SSLC_News"
     );
     $response = array();
     while ($row = mysqli_fetch_array($result)) {

        array_push(
            $response,
            array(
                'success' => true,
                'newsTitle' => $row[0],
                'newsDescription' => $row[1],
                'newsCreatedAt' => $row[2]
            )
            );
     }

     echo json_encode(array("News"=>$response));
     mysqli_close($conn);
?>