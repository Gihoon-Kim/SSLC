<?php
    $db_name = "hoonyhosting";
    $hostname = "hoonyhosting";
    $password = "wjsghkrl1!";
    $servername = "localhost";

    $conn = mysqli_connect($servername, $hostname, $password, $db_name);

    $userName = $_POST["userName"];
    $isTeacher = $_POST["isTeacher"];
    $imageData = $_POST["imageData"];

    $root = $_SERVER["DOCUMENT_ROOT"];
    $path = $root . "/Practice/SSLC/profileImage";

    $imageName = $isTeacher."_".$userName;
    $imagePath = $path."/{$imageName}";

    $InsertSQL = 
        "INSERT INTO SSLC_Image (
            imagePath,
            imageName,
            imageData)
            VALUES ('$imagePath', '$imageName', '$imageData')";
    

    $response = array();

    if (mysqli_query($conn, $InsertSQL)) {
        file_put_contents($path . "/{$imageName}" . ".jpeg", base64_decode($imageData));
        $response["success"] = true;
        echo json_encode($response);
    } else {

        echo "Failure";
    }
    mysqli_close($conn);

?>