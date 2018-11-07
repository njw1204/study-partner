<?php
    header('Content-Type: text/html; charset=utf-8');
    $conn = mysqli_connect("localhost", "", "", "");
    $conn->set_charset("utf8mb4");
    
    $id = mysqli_real_escape_string($conn, $_POST["id"]);
    $pw = mysqli_real_escape_string($conn, hash("sha256", $_POST["pw"]));
    $study_no = mysqli_real_escape_string($conn, $_POST["study_no"]);
    $msg = mysqli_real_escape_string($conn, $_POST["msg"]);

    $query = "SELECT COUNT(*) FROM studypartner_user_info WHERE id='$id' AND pw='$pw'";
    $res = mysqli_query($conn, $query);
    if ($res) {
        if ($row = mysqli_fetch_array($res)) {
            if ((int)$row[0] != 1) {
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

    $query = "UPDATE studypartner_user_study SET msg='$msg' WHERE id='$id' AND study_no='$study_no'";
    $res = mysqli_query($conn, $query);
    if ($res) {
        mysqli_close($conn);
        echo "OK";
    }
    else {
        mysqli_close($conn);
        http_response_code(503);
        echo "error";
    }
?>