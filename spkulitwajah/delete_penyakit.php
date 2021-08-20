<?php
$response = array();
include 'koneksi.php';

$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE);

if (isset($input['id_penyakit'])) {

    $id_penyakit = $input['id_penyakit'];
    mysqli_query($con, "DELETE FROM penyakit WHERE id_penyakit='$id_penyakit'");

    $response["status"] = 0;
    $response["message"] = "Data berhasil dihapus";
} else {
    $response["status"] = 2;
    $response["message"] = "Parameter ada yang kosong";
}
echo json_encode($response);
