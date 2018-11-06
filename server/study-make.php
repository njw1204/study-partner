<?php
    header('Content-Type: text/html; charset=utf-8');
    $conn = mysqli_connect("localhost", "", "", "");
    $conn->set_charset("utf8mb4");

    $id = mysqli_real_escape_string($conn, $_POST["id"]);
    $pw = mysqli_real_escape_string($conn, hash("sha256", $_POST["pw"]));
    $title = mysqli_real_escape_string($conn, $_POST["title"]);
    $kind = mysqli_real_escape_string($conn, $_POST["kind"]);
    $area = mysqli_real_escape_string($conn, $_POST["area"]);
    $school = mysqli_real_escape_string($conn, $_POST["school"]);
    $contact = mysqli_real_escape_string($conn, $_POST["contact"]);
    $info = mysqli_real_escape_string($conn, $_POST["info"]);

    $query = "SELECT COUNT(*), school FROM studypartner_user_info WHERE id='$id' AND pw='$pw'";
    $res = mysqli_query($conn, $query);
    if ($res) {
        if ($row = mysqli_fetch_array($res)) {
            if ((int)$row[0] != 1) {
                mysqli_close($conn);
                die("error");
            }
            if ($school == "T" && $row[1]) {
                $school = $row[1];
            }
            else if ($school == "T") {
                mysqli_close($conn);
                die("no school");
            }
            else {
                $school = null;
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

    $query = "SELECT COUNT(*) FROM studypartner_study_list WHERE title='$title'";
    $res = mysqli_query($conn, $query);
    if ($res) {
        if ($row = mysqli_fetch_array($res)) {
            if ((int)$row[0] != 0) {
                mysqli_close($conn);
                die("already");
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

    $query = "INSERT INTO studypartner_study_list (title, kind, area, school, contact, info, cnt) VALUES ('$title', '$kind', '$area', '$school', '$contact', '$info', '1')";
    $res = mysqli_query($conn, $query);
    if ($res) {
    }
    else {
        mysqli_close($conn);
        die("error");
    }

    $query = "SELECT study_no FROM studypartner_study_list WHERE title='$title'";
    $res = mysqli_query($conn, $query);
    if ($res) {
        if ($row = mysqli_fetch_array($res)) {
            $now_study_no = (int)$row[0];
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

    $query = "INSERT INTO studypartner_user_study (study_no, id, msg, staff) VALUES ('$now_study_no', '$id', '스터디 개설자', '1')";
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