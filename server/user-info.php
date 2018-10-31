<?php
    header('Content-Type: application/json; charset=utf-8');
    $conn = mysqli_connect("localhost", "", "", "");
    $conn->set_charset("utf8mb4");
    $id = mysqli_real_escape_string($conn, $_GET["id"]);
    $query = "SELECT id, nick, school FROM user_info WHERE id='$id'";

    $res = mysqli_query($conn, $query);
    if ($res) {
        if ($row = mysqli_fetch_assoc($res)) {
            mysqli_close($conn);
            $json = json_encode($row, JSON_UNESCAPED_UNICODE);
            echo $json;
        }
        else {
            mysqli_close($conn);
            http_response_code(503);
            die("error");
        }
    } else {
        mysqli_close($conn);
        http_response_code(503);
        die("error");
    }
?>