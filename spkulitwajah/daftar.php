<?php
$response = array();
include 'koneksi.php';

$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE);

if (isset($input['username'])) {

    $nama_lengkap = $input['nama_lengkap'];
    $username = $input['username'];
    $password = $input['password'];

    if (mysqli_num_rows(mysqli_query($con, "select * from pengguna where username='" . $username . "'")) > 0) {
        $response["status"] = 1;
        $response["message"] = "Username sudah digunakan";
    } else {
        $q = "INSERT INTO pengguna(nama_lengkap,username,password,level) VALUES ('$nama_lengkap','$username','$password','User')";
        mysqli_query($con, $q);

        $response["status"] = 0;
        $response["message"] = "Pendaftaran berhasil";
    }
} else {
    $response["status"] = 2;
    $response["message"] = "Parameter ada yang kosong";
}
echo json_encode($response);
