<?php
include 'koneksi.php';

$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE);
$response = array();

$q = mysqli_query($con, "SELECT id_penyakit,nama_penyakit FROM penyakit ORDER BY id_penyakit");

$response['penyakit'] = array();
while ($r = mysqli_fetch_array($q)) {

    $penyakit = array();
    $penyakit['id_penyakit'] = $r['id_penyakit'];
    $penyakit['nama_penyakit'] = $r['nama_penyakit'];

    $gejala = array();
    $id = $r['id_penyakit'];
    $qgejala = "select * from aturan where id_penyakit='$id'";
    $qgejala = mysqli_query($con, $qgejala);
    while ($rgejala = mysqli_fetch_array($qgejala)) {
        $r_gejala = mysqli_fetch_array(mysqli_query($con, "select id_gejala,nama_gejala,nilaif_cf from gejala where id_gejala='" . $rgejala['id_gejala'] . "'"));
        $gejala[] = array(
            'id_gejala' => $r_gejala['id_gejala'],
            'nama_gejala' => $r_gejala['nama_gejala'],
            'nilaif_cf' => $r_gejala['nilaif_cf'],
        );
    }
    $penyakit['gejala'] = $gejala;

    array_push($response['penyakit'], $penyakit);
}

$response['status'] = 0;
$response['message'] = "Get list gejala berhasil";

echo json_encode($response);
