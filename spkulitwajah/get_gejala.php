<?php
include 'koneksi.php';

$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE);
$response = array();

if (isset($input['id_gejala'])) {

    $id_gejala = $input['id_gejala'];

    $q = mysqli_query($con, "SELECT kode_gejala,nama_gejala,nilai_cf FROM gejala WHERE id_gejala='$id_gejala'");
    $r = mysqli_fetch_array($q);

    $response["status"] = 0;
    $response["message"] = "Get gejala berhasil";
    $response["kode_gejala"] = $r['kode_gejala'];
    $response["nama_gejala"] = $r['nama_gejala'];
    $response["nilai_cf"] = $r['nilai_cf'];
} else {
    $response["status"] = 2;
    $response["message"] = "Parameter ada yang kosong";
}
echo json_encode($response);
