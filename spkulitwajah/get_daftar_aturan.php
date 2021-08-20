<?php
include 'koneksi.php';

$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE);
$response = array();

$q = mysqli_query($con, "SELECT id_penyakit,nama_penyakit FROM penyakit ORDER BY id_penyakit");

$response["aturan"] = array();
while ($r = mysqli_fetch_array($q)) {
    $id = $r['id_penyakit'];
    $gejala = array();
    $qgejala = "select * from aturan where id_penyakit='$id'";
    $qgejala = mysqli_query($con, $qgejala);
    while ($rgejala = mysqli_fetch_array($qgejala)) {
        $r_gejala = mysqli_fetch_array(mysqli_query($con, "select kode_gejala from gejala where id_gejala='" . $rgejala['id_gejala'] . "'"));
        $gejala[] = $r_gejala['kode_gejala'];
    }
    $daftar_gejala = implode(" - ", $gejala);
    $aturan = array();
    $aturan["id_penyakit"] = $id;
    $aturan["nama_penyakit"] = $r['nama_penyakit'];
    $aturan["daftar_gejala"] = $daftar_gejala;
    array_push($response["aturan"], $aturan);
}
$response["status"] = 0;
$response["message"] = "Get list aturan berhasil";

echo json_encode($response);
