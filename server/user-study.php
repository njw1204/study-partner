<?php
    header('Content-Type: application/json; charset=utf-8');
    $conn = mysqli_connect("localhost", "", "", "");
    $conn->set_charset("utf8mb4");
    $id = mysqli_real_escape_string($conn, $_GET["id"]);
    $query = "SELECT study_no FROM user_study WHERE id='$id'";

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

    $query = "SELECT icon, title, kind, school, cnt FROM study_list WHERE study_no IN (" . implode(",", $result)) . ")";
    $res = mysqli_query($conn, $query);
    $result = array();
    if ($res) {
        while ($row = mysqli_fetch_assoc($res)) {
            $row['cnt'] = (int)$row['cnt'];
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