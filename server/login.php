<?php
    header('Content-Type: text/html; charset=utf-8');
    $conn = mysqli_connect("localhost", "", "", "");
    $conn->set_charset("utf8mb4");
    $id = mysqli_real_escape_string($conn, $_POST["id"]);
    $pw = mysqli_real_escape_string($conn, hash("sha256", $_POST["pw"]));
    $query = "SELECT COUNT(*) FROM studypartner_user_info WHERE id='$id' AND pw='$pw'";

    $res = mysqli_query($conn, $query);
    if ($res) {
        if ($row = mysqli_fetch_array($res)) {
            mysqli_close($conn);
            if ((int)$row[0] == 1) {
                echo "OK";
            }
            else {
                echo "error";
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
?>