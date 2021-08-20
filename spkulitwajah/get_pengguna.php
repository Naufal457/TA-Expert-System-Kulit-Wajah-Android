<?php
include 'koneksi.php';

$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE);
$response = array();

if (isset($input['id_pengguna'])) {

    $id_pengguna = $input['id_pengguna'];

    $q = mysqli_query($con, "SELECT id_pengguna,nama_lengkap,username FROM pengguna WHERE id_pengguna='$id_pengguna'");
    $r = mysqli_fetch_array($q);

    $response["status"] = 0;
    $response["message"] = "Get pengguna berhasil";
    $response["id_pengguna"] = $r['id_pengguna'];
    $response["nama_lengkap"] = $r['nama_lengkap'];
    $response["username"] = $r['username'];

    
} else {
    $response["status"] = 2;
    $response["message"] = "Parameter ada yang kosong";
}
echo json_encode($response);
