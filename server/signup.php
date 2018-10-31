<?php
    header('Content-Type: application/json; charset=utf-8');
    $conn = mysqli_connect("localhost", "", "", "");
    $conn->set_charset("utf8mb4");
    $id = mysqli_real_escape_string($conn, $_POST["id"]);
    $pw = mysqli_real_escape_string($conn, hash("sha256", $_POST["pw"]));
    $school = mysqli_real_escape_string($conn, $_POST["school"]);
    $nick = mysqli_real_escape_string($conn, $_POST["nick"]);
    $query = "SELECT COUNT(*) FROM user_info WHERE id='$id'";

    $res = mysqli_query($conn, $query);
    if ($res) {
        if ($row = mysqli_fetch_array($res)) {
            if ((int)$row[0]) {
                mysqli_close($conn);
                die("error");
            }
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

    $query = "INSERT INTO user_info ('id', 'pw', 'school', 'nick') VALUES ('$id', '$pw', '$school', '$nick')";
    $res = mysqli_query($conn, $query);
    if ($res) {
        mysqli_close($conn);
        echo "OK";
    }
    else {
        mysqli_close($conn);
        echo "error";
    }
?>