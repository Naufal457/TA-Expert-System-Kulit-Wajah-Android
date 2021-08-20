<?php
$response = array();
include 'koneksi.php';

$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE);

if (isset($input['id_pengguna'])) {

    $id_pengguna = $input['id_pengguna'];
    $id_pengguna = empty($input['id_pengguna']) ? "" : $input['id_pengguna'];
    $nama_lengkap = empty($input['nama_lengkap']) ? "" : $input['nama_lengkap'];
    $username = empty($input['username']) ? "" : $input['username'];
    $password = empty($input['password']) ? "" : $input['password'];

    $q = "UPDATE pengguna SET
            id_pengguna='$id_pengguna',
			nama_lengkap='$nama_lengkap',
			username='$username',
			password='$password'
		WHERE id_pengguna='$id_pengguna'";
    mysqli_query($con, $q);

    $response["status"] = 0;
    $response["message"] = "Data berhasil disimpan";
} else {
    $response["status"] = 2;
    $response["message"] = "Parameter ada yang kosong";
}
echo json_encode($response);
