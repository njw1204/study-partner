<?php
    header('Content-Type: text/html; charset=utf-8');
    $conn = mysqli_connect("localhost", "", "", "");
    $conn->set_charset("utf8mb4");

    $id_len = mb_strlen($_POST["id"], "UTF8");
    $pw_len = mb_strlen($_POST["pw"], "UTF8");
    $school_len = mb_strlen($_POST["school"], "UTF8");
    $nick_len = mb_strlen($_POST["nick"], "UTF8");
    if ($id_len < 5 || $id_len > 30 || $pw_len < 5 || $pw_len > 30 ||
        ($school_len != 0 && ($school_len < 5 || $school_len > 20)) || $nick_len < 2 || $nick_len > 10) {
            mysqli_close($conn);
            die("error");
    }

    $id = mysqli_real_escape_string($conn, $_POST["id"]);
    $pw = mysqli_real_escape_string($conn, hash("sha256", $_POST["pw"]));
    $school = mysqli_real_escape_string($conn, $_POST["school"]);
    $nick = mysqli_real_escape_string($conn, $_POST["nick"]);
    $query = "SELECT COUNT(*) FROM studypartner_user_info WHERE id='$id'";

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

    if ($school_len)
        $query = "INSERT INTO studypartner_user_info (id, pw, school, nick) VALUES ('$id', '$pw', '$school', '$nick')";
    else
        $query = "INSERT INTO studypartner_user_info (id, pw, nick) VALUES ('$id', '$pw', '$nick')";

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