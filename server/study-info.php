<?php
    header('Content-Type: application/json; charset=utf-8');
    $conn = mysqli_connect("localhost", "", "", "");
    $conn->set_charset("utf8mb4");
    $study_no = mysqli_real_escape_string($conn, $_GET["study_no"]);
    $query = "SELECT title, kind, area, school, contact, info FROM studypartner_study_list WHERE study_no='$study_no'";

    $res = mysqli_query($conn, $query);
    if ($res) {
        if ($row = mysqli_fetch_assoc($res)) {
            
        } else {
            mysqli_close($conn);
            http_response_code(503);
            die("error");
        }
    } else {
        mysqli_close($conn);
        http_response_code(503);
        die("error");
    }

    mysqli_close($conn);
    $json = json_encode($row, JSON_UNESCAPED_UNICODE);
    echo $json;
?>