<?php
    header('Content-Type: application/json; charset=utf-8');
    $conn = mysqli_connect("localhost", "", "", "");
    $conn->set_charset("utf8mb4");
    $id = mysqli_real_escape_string($conn, $_GET["id"]);
    $query = "SELECT study_no, staff FROM studypartner_user_study WHERE id='$id' ORDER BY staff DESC, study_no DESC";

    $res = mysqli_query($conn, $query);
    $result = array();
    $is_staff = array();
    if ($res) {
        while ($row = mysqli_fetch_array($res)) {
            $result[] = $row[0];
            $is_staff[(int)$row[0]] = (bool)$row[1];
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

    $r2q = implode(",", $result);
    $query = "SELECT study_no, title, kind, school, area, cnt FROM studypartner_study_list WHERE study_no IN (" . $r2q . ") ORDER BY FIELD (study_no, " . $r2q . ")";
    $res = mysqli_query($conn, $query);
    $result = array();
    if ($res) {
        while ($row = mysqli_fetch_assoc($res)) {
            $row['cnt'] = (int)$row['cnt'];
            if (is_file("icon/" . $row['study_no'] . ".png")) {
                $row['icon'] = "http://" . $_SERVER['HTTP_HOST'] . "/studypartner/icon/" . $row['study_no'] . ".png";
            }
            else {
                $row['icon'] = "";
            }
            $row['study_no'] = (int)$row['study_no'];
            $row['staff'] = $is_staff[$row['study_no']];
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