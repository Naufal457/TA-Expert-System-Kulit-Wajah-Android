<?php
include 'koneksi.php';

$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE);
$response = array();

$q = mysqli_query($con, "SELECT id_penyakit,nama_penyakit FROM penyakit ORDER BY id_penyakit");

$response["penyakit"] = array();
while ($r = mysqli_fetch_array($q)) {
    $penyakit = array();
    $penyakit["id_penyakit"] = $r['id_penyakit'];
    $penyakit["nama_penyakit"] = $r['nama_penyakit'];
    array_push($response["penyakit"], $penyakit);
}
$response["status"] = 0;
$response["message"] = "Get list penyakit berhasil";

echo json_encode($response);
