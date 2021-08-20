<?php
$response = array();
include 'koneksi.php';

$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE);

if (isset($input['kode_gejala'])) {

    $kode_gejala = $input['kode_gejala'];
    $nama_gejala = empty($input['nama_gejala']) ? "" : $input['nama_gejala'];
    $nilai_cf = empty($input['nilai_cf']) ? "" : $input['nilai_cf'];

    $q = "INSERT INTO gejala(kode_gejala,nama_gejala,nilai_cf) VALUES ('$kode_gejala','$nama_gejala','$nilai_cf')";
    mysqli_query($con, $q);

    $response["status"] = 0;
    $response["message"] = "Data berhasil disimpan";
} else {
    $response["status"] = 2;
    $response["message"] = "Parameter ada yang kosong";
}
echo json_encode($response);
