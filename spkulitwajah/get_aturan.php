<?php
include 'koneksi.php';

$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE);
$response = array();

if (isset($input['id_penyakit'])) {

    $id_penyakit = $input['id_penyakit'];

    $q = mysqli_query($con, "SELECT id_penyakit,nama_penyakit FROM penyakit WHERE id_penyakit='$id_penyakit'");
    $r = mysqli_fetch_array($q);

    $gejala = array();
    $qgejala = "select * from aturan where id_penyakit='$id_penyakit'";
    $qgejala = mysqli_query($con, $qgejala);
    while ($rgejala = mysqli_fetch_array($qgejala)) {
        $r_gejala = mysqli_fetch_array(mysqli_query($con, "select kode_gejala,nama_gejala from gejala where id_gejala='" . $rgejala['id_gejala'] . "'"));
        $gejala[] = $r_gejala['kode_gejala'] . ' - ' . $r_gejala['nama_gejala'];
    }
    $daftar_gejala = implode("\n", $gejala);

    $response["status"] = 0;
    $response["message"] = "Get aturan berhasil";
    $response["id_penyakit"] = $r['id_penyakit'];
    $response["nama_penyakit"] = $r['nama_penyakit'];
    $response["daftar_gejala"] = $daftar_gejala;
} else {
    $response["status"] = 2;
    $response["message"] = "Parameter ada yang kosong";
}
echo json_encode($response);
