<?php
    header('Content-Type: application/json; charset=utf-8');
    $conn = mysqli_connect("localhost", "", "", "");
    $conn->set_charset("utf8mb4");
    $id = mysqli_real_escape_string($conn, $_GET["id"]);
    $query = "SELECT study_no FROM studypartner_user_study WHERE id='$id'";

    $res = mysqli_query($conn, $query);
    $result = array();
    if ($res) {
        while ($row = mysqli_fetch_array($res)) {
            $result[] = $row[0];
        }
    } else {
        mysqli_close($conn);
        http_response_code(503);
        die("error");
    }

    if (count($result) == 0) {
        mysqli_close($conn);
        die("[]");
    }

    $query = "SELECT study_no, title, kind, school, cnt FROM studypartner_study_list WHERE study_no IN (" . implode(",", $result) . ")";
    $res = mysqli_query($conn, $query);
    $result = array();
    if ($res) {
        while ($row = mysqli_fetch_assoc($res)) {
            $row['cnt'] = (int)$row['cnt'];
            if (is_file("/studypartner/icon/" . $row['study_no'] . ".png")) {
                $row['icon'] = "http://" . $_SERVER['HTTP_HOST'] . "/studypartner/icon/" . $row['study_no'] . ".png";
            }
            else {
                $row['icon'] = "";
            }
            $result[] = $row;
        }
    } else {
        mysqli_close($conn);
        http_response_code(503);
        die("error");
    }

    mysqli_close($conn);
    $json = json_encode($result, JSON_UNESCAPED_UNICODE);
    echo $json;
?>