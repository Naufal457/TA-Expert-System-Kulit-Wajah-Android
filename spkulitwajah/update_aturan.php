<?php
$response = array();
include 'koneksi.php';

$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE);

if (isset($input['id_penyakit'])) {

    $id_penyakit = $input['id_penyakit'];
    $daftar_gejala = empty($input['daftar_gejala']) ? "" : $input['daftar_gejala'];

    mysqli_query($con, "delete from aturan where id_penyakit='" . $id_penyakit . "'");

    $hsl = explode("#", $daftar_gejala);
    foreach ($hsl as $val) {
        $q2 = mysqli_query($con, "select id_gejala from gejala where nama_gejala='$val'");
        if (mysqli_num_rows($q2) > 0) {
            $r2 = mysqli_fetch_array($q2);
            $id_gejala = $r2['id_gejala'];
            $q3 = "insert into aturan(id_penyakit,id_gejala) values ('" . $id_penyakit . "','" . $id_gejala . "')";
            mysqli_query($con, $q3);
        }
    }

    $response["status"] = 0;
    $response["message"] = "Data berhasil disimpan";
} else {
    $response["status"] = 2;
    $response["message"] = "Parameter ada yang kosong";
}
echo json_encode($response);
