<?php
$response = array();
include 'koneksi.php';

$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE);

if (isset($input['nama_lengkap'])) {

    
    $nama_lengkap= empty($input['nama_lengkap']) ? "" : $input['nama_lengkap'];
    $username = empty($input['username']) ? "" : $input['username'];
    $password = empty($input['password']) ? "" : $input['password'];

    $q = "INSERT INTO pengguna(nama_lengkap,username,password) VALUES ('$nama_lengkap','$username','$password')";
    mysqli_query($con, $q);

    $response["status"] = 0;
    $response["message"] = "Data berhasil disimpan";
} else {
    $response["status"] = 2;
    $response["message"] = "Parameter ada yang kosong";
}
echo json_encode($response);
