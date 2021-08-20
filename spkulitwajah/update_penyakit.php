<?php
$response = array();
include 'koneksi.php';

$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE);

if (isset($input['id_penyakit'])) {

    $id_penyakit = $input['id_penyakit'];
    $kode_penyakit = empty($input['kode_penyakit']) ? "" : $input['kode_penyakit'];
    $nama_penyakit = empty($input['nama_penyakit']) ? "" : $input['nama_penyakit'];
    $deskripsi = empty($input['deskripsi']) ? "" : $input['deskripsi'];
    $solusi = empty($input['solusi']) ? "" : $input['solusi'];

    $q = "UPDATE penyakit SET
			kode_penyakit='$kode_penyakit',
			nama_penyakit='$nama_penyakit',
			deskripsi='$deskripsi',
			solusi='$solusi'
		WHERE id_penyakit='$id_penyakit'";
    mysqli_query($con, $q);

    $response["status"] = 0;
    $response["message"] = "Data berhasil disimpan";
} else {
    $response["status"] = 2;
    $response["message"] = "Parameter ada yang kosong";
}
echo json_encode($response);
