<?php
include 'koneksi.php';

$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE);
$response = array();

if (isset($input['username']) && isset($input['password'])) {

    $username = $input['username'];
    $password = $input['password'];

    $q = mysqli_query($con, "SELECT * FROM pengguna WHERE username='$username' AND password='$password'");
    if (mysqli_num_rows($q) == 0) {
        $response["status"] = 1;
        $response["message"] = "Username dan Password salah";
    } else {
        $r = mysqli_fetch_array($q);
        $response["status"] = 0;
        $response["message"] = "Login berhasil";
        $response["id_pengguna"] = $r['id_pengguna'];
        $response["nama_lengkap"] = $r['nama_lengkap'];
        $response["level"] = $r['level'];
    }
} else {
    $response["status"] = 2;
    $response["message"] = "Parameter ada yang kosong";
}
echo json_encode($response);
