<?php
    header('Content-Type: text/html; charset=utf-8');
    $conn = mysqli_connect("localhost", "", "", "");
    $conn->set_charset("utf8mb4");
    
    $study_no = mysqli_real_escape_string($conn, $_GET["study_no"]);

    $query = "SELECT notice FROM studypartner_study_list WHERE study_no='$study_no'";
    $res = mysqli_query($conn, $query);
    if ($res) {
        if ($row = mysqli_fetch_array($res)) {
            mysqli_close($conn);
            echo $row[0];
        }
        else {
            mysqli_close($conn);
            http_response_code(503);
            die("");
        }
    } else {
        mysqli_close($conn);
        http_response_code(503);
        die("");
    }
?>