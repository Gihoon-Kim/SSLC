<?php
     $db_name = "hoonyhosting";
     $username = "hoonyhosting";
     $password = "wjsghkrl1!";
     $servername = "localhost";
 
     $conn = mysqli_connect($servername, $username, $password, $db_name);

     $userID = trim($_POST["userID"]);
     $userPassword = trim($_POST["userPassword"]);

     $statement = mysqli_prepare(
         $conn,
         "SELECT userID
            FROM SSLC_Admin
            WHERE userID = ? AND userPassword = ?"
     );

     mysqli_stmt_bind_param(
         $statement,
         "ss",
         $userID, $userPassword
     );
     mysqli_stmt_execute($statement);
     mysqli_stmt_store_result($statement);
     mysqli_stmt_bind_result($statement, $userID);

     $response = array();
     $response["success"] = false;

     while (mysqli_stmt_fetch($statement)) {

        $response["success"] = true;
        $response["userID"] = $userID;
     }

     echo json_encode($response);
?>