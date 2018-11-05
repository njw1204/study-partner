<?php
    header('Content-Type: application/json; charset=utf-8');
    $conn = mysqli_connect("localhost", "", "", "");
    $conn->set_charset("utf8mb4");
    $study_no = mysqli_real_escape_string($conn, $_GET["study_no"]);
    $query = "SELECT s.id, u.nick, s.msg, s.staff FROM studypartner_user_study AS s INNER JOIN studypartner_user_info AS u ON s.id=u.id WHERE s.study_no='$study_no' ORDER BY s.staff DESC, s.id";

    $res = mysqli_query($conn, $query);
    $result = array();
    if ($res) {
        while ($row = mysqli_fetch_assoc($res)) {
            $row['staff'] = (int)$row['staff'];
            $row['staff'] = (bool)$row['staff'];
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