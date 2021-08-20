<?php
$response = array();
include 'koneksi.php';

$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE);

if (isset($input['id_pengguna'])) {

    $id_pengguna = $input['id_pengguna'];
    mysqli_query($con, "DELETE FROM riwayat WHERE id_pengguna='$id_pengguna'");

    $response["status"] = 0;
    $response["message"] = "Data berhasil dihapus";
} else {
    $response["status"] = 2;
    $response["message"] = "Parameter ada yang kosong";
}
echo json_encode($response);
