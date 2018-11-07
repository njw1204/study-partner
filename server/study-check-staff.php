<?php
    header('Content-Type: text/html; charset=utf-8');
    $conn = mysqli_connect("localhost", "", "", "");
    $conn->set_charset("utf8mb4");
    
    $id = mysqli_real_escape_string($conn, $_GET["id"]);
    $study_no = mysqli_real_escape_string($conn, $_GET["study_no"]);

    $query = "SELECT staff FROM studypartner_user_study WHERE id='$id' AND study_no='$study_no'";
    $res = mysqli_query($conn, $query);
    if ($res) {
        if ($row = mysqli_fetch_array($res)) {
            if ((int)$row[0] != 0) {
                mysqli_close($conn);
                echo "OK";
            }
            else {
                mysqli_close($conn);
                http_response_code(503);
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