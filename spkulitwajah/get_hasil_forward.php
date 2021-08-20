<?php
include 'koneksi.php';

$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE);
$response = array();

if (isset($input['hasil'])) {

    $hasil = $input['hasil'];
    $id_pengguna = $input['id_pengguna'];
    $metode = $input['metode'];
    $tanggal = date('Y-m-d');

    $arr_gejala_terpilih = explode("#", $hasil);

    $id_penyakit_hasil = '';
    $nama_penyakit_hasil = '';
    $sql1 = mysqli_query($con, "select id_penyakit,nama_penyakit from penyakit order by id_penyakit");
    while ($r = mysqli_fetch_array($sql1)) {
        $id_penyakit = $r['id_penyakit'];
        $nama_penyakit = $r['nama_penyakit'];
        $arr_gejala_penyakit = array();
        $sql_at = mysqli_query($con, "select id_gejala from aturan where id_penyakit='$id_penyakit' order by id_gejala");
        while ($r_at = mysqli_fetch_array($sql_at)) {
            $id_gejala = $r_at['id_gejala'];
            $arr_gejala_penyakit[] = $id_gejala;
        }
        if (arrays_are_equal($arr_gejala_terpilih, $arr_gejala_penyakit)) {
            $id_penyakit_hasil = $id_penyakit;
            $nama_penyakit_hasil = $nama_penyakit;
        }
    }

    if ($nama_penyakit_hasil != '') {
        $q = "insert into riwayat(id_pengguna,id_penyakit,tanggal,metode) values ('" . $id_pengguna . "','" . $id_penyakit_hasil . "','" . $tanggal . "','" . $metode . "')";
        mysqli_query($con, $q);
    } else {
        $nama_penyakit_hasil = 'Tidak ada jenis penyakit yang sesuai dengan gejala terpilih';
        $q = "insert into riwayat(id_pengguna,tanggal,metode) values ('" . $id_pengguna . "','" . $tanggal . "','" . $metode . "')";
        mysqli_query($con, $q);
    }

    $response["status"] = 0;
    $response["id_penyakit"] = $id_penyakit_hasil;
    $response["nama_penyakit"] = $nama_penyakit_hasil;
} else {
    $response["status"] = 2;
    $response["message"] = "Parameter ada yang kosong";
}

function arrays_are_equal($array1, $array2)
{
    array_multisort($array1);
    array_multisort($array2);
    return (serialize($array1) === serialize($array2));
}

echo json_encode($response);
