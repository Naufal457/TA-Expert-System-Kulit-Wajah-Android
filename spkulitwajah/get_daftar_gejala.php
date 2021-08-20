<?php
include 'koneksi.php';

$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE);
$response = array();

$q = mysqli_query($con, "SELECT id_gejala,nama_gejala,nilai_cf FROM gejala ORDER BY kode_gejala");

$response["gejala"] = array();
while ($r = mysqli_fetch_array($q)) {
    $gejala = array();
    $gejala["id_gejala"] = $r['id_gejala'];
    $gejala["nama_gejala"] = $r['nama_gejala'];
    $gejala["nilai_cf"] = $r['nilai_cf'];
    array_push($response["gejala"], $gejala);
}
$response["status"] = 0;
$response["message"] = "Get list gejala berhasil";

echo json_encode($response);
