<?php
    header('Content-Type: application/json; charset=utf-8');
    $conn = mysqli_connect("localhost", "", "", "");
    $conn->set_charset("utf8mb4");
    $kind = mysqli_real_escape_string($conn, $_GET["kind"]);
    $area = mysqli_real_escape_string($conn, $_GET["area"]);
    $school = mysqli_real_escape_string($conn, $_GET["school"]);
    $query = "SELECT study_no, title, kind, school, cnt FROM studypartner_study_list WHERE 1=1";

    if (strlen($kind)) {
        $query = $query." AND kind='$kind'";
    }
    if (strlen($area)) {
        $query = $query." AND area='$area'";
    }
    if (strlen($school)) {
        $query = $query." AND school LIKE '%$school%'";
    }

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