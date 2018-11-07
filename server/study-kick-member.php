<?php
    header('Content-Type: text/html; charset=utf-8');
    $conn = mysqli_connect("localhost", "", "", "");
    $conn->set_charset("utf8mb4");
    
    $id = mysqli_real_escape_string($conn, $_POST["id"]);
    $pw = mysqli_real_escape_string($conn, hash("sha256", $_POST["pw"]));
    $study_no = mysqli_real_escape_string($conn, $_POST["study_no"]);
    $apply_id = mysqli_real_escape_string($conn, $_POST["apply_id"]);

    $query = "SELECT COUNT(*), staff FROM studypartner_user_study WHERE id='$apply_id' AND study_no='$study_no'";
    $res = mysqli_query($conn, $query);
    if ($res) {
        if ($row = mysqli_fetch_array($res)) {
            if ((int)$row[0] != 1) {
                mysqli_close($conn);
                die("error");
            }
            if ((int)$row[1] != 0) {
                mysqli_close($conn);
                die("staff");
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

    $query = "SELECT staff FROM studypartner_user_study WHERE id='$id' AND study_no='$study_no'";
    $res = mysqli_query($conn, $query);
    if ($res) {
        if ($row = mysqli_fetch_assoc($res)) {
            if ((int)$row['staff'] == 0) {
                mysqli_close($conn);
                die("no staff");
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

    $query = "DELETE FROM studypartner_user_study WHERE id='$apply_id' AND study_no='$study_no'";
    $res = mysqli_query($conn, $query);
    if ($res) {
        $query = "UPDATE studypartner_study_list SET cnt=(SELECT COUNT(*) FROM studypartner_user_study WHERE studypartner_user_study.study_no='$study_no') WHERE study_no='$study_no'";
        mysqli_query($conn, $query);
        mysqli_close($conn);
        echo "OK";
    }
    else {
        mysqli_close($conn);
        http_response_code(503);
        die("error");
    }
?>