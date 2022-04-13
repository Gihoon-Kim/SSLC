<?php
     $db_name = "hoonyhosting";
     $username = "hoonyhosting";
     $password = "wjsghkrl1!";
     $servername = "localhost";
 
     $conn = mysqli_connect($servername, $username, $password, $db_name);

     $classTitle = $_POST["classTitle"];

     $result = mysqli_query(
         $conn,
         "SELECT newsTitle, newsDescription, CreatedAt
            FROM SSLC_ClassNews
            WHERE className = '$classTitle'
            ORDER BY createdAt DESC"
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

     echo json_encode(array("ClassNews"=>$response));
     mysqli_close($conn);
?>