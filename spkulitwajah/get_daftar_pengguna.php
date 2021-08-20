<?php
include 'koneksi.php';

$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE);
$response = array();

$q = mysqli_query($con, "SELECT id_pengguna,nama_lengkap,password,username FROM pengguna ORDER BY id_pengguna DESC");

$response["pengguna"] = array();
while ($r = mysqli_fetch_array($q)) {
    $pengguna = array();
    $pengguna["id_pengguna"] = $r['id_pengguna'];
    $pengguna["nama_lengkap"] = $r['nama_lengkap'];
    $pengguna["password"] = $r['password'];
    $pengguna["username"] = $r['username'];
    array_push($response["pengguna"], $pengguna);
}
$response["status"] = 0;
$response["message"] = "Get list pengguna yang ada";

echo json_encode($response);
