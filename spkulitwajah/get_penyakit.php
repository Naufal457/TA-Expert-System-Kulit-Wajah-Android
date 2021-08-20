<?php
include 'koneksi.php';

$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE);
$response = array();

if (isset($input['id_penyakit'])) {

    $id_penyakit = $input['id_penyakit'];

    $q = mysqli_query($con, "SELECT kode_penyakit,nama_penyakit,deskripsi,solusi FROM penyakit WHERE id_penyakit='$id_penyakit'");
    $r = mysqli_fetch_array($q);

    $response["status"] = 0;
    $response["message"] = "Get penyakit berhasil";
    $response["kode_penyakit"] = $r['kode_penyakit'];
    $response["nama_penyakit"] = $r['nama_penyakit'];
    $response["deskripsi"] = $r['deskripsi'];
    $response["solusi"] = $r['solusi'];
} else {
    $response["status"] = 2;
    $response["message"] = "Parameter ada yang kosong";
}
echo json_encode($response);
