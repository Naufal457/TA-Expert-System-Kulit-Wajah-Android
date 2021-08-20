<?php
include 'koneksi.php';

$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE);
$response = array();

if (isset($input['id_pengguna'])) {
    $id_pengguna = $input['id_pengguna'];
    $q = mysqli_query($con, "SELECT id_penyakit,DATE_FORMAT(tanggal, '%d-%m-%Y') as tanggal,metode,nilai FROM riwayat WHERE id_pengguna='$id_pengguna' ORDER BY id_riwayat DESC");

    if (mysqli_num_rows($q) == 0) {
        $response["status"] = 1;
        $response["message"] = "Tidak ada riwayat diagnosa";
    } else {
        $response["riwayat"] = array();
        while ($r = mysqli_fetch_array($q)) {
            $riwayat = array();
            if (is_null($r['id_penyakit'])) {
                $riwayat["nama_penyakit"] = 'Penyakit tidak terdiagnosa';
            } else {
                $q2 = mysqli_query($con, "select nama_penyakit from penyakit where id_penyakit='" . $r['id_penyakit'] . "'");
                $r2 = mysqli_fetch_array($q2);
                $riwayat["nama_penyakit"] = $r2['nama_penyakit'];
            }
            $riwayat["metode"] = $r['metode'];
            $riwayat["nilai"] = $r['nilai'];
            $riwayat["tanggal"] = $r['tanggal'];
            $riwayat["id_penyakit"] = is_null($r['id_penyakit']) ? '' : $r['id_penyakit'];
            array_push($response["riwayat"], $riwayat);
        }
        $response["status"] = 0;
        $response["message"] = "Get list riwayat berhasil";
    }
} else {
    $response["status"] = 2;
    $response["message"] = "Parameter ada yang kosong";
}

echo json_encode($response);
