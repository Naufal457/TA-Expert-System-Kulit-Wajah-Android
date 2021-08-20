<?php
$response = array();
include 'koneksi.php';

$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE);

if (isset($input['hasil']) && isset($input['id_pengguna'])) {

    $hasil = $input['hasil'];
    $id_pengguna = $input['id_pengguna'];
    $metode = $input['metode'];
    $tanggal = date('Y-m-d');

    $cf_user = explode("#", $hasil);
    $arr_hasil = array();

    $i = 0;
    $sql = mysqli_query($con, "SELECT id_gejala FROM gejala ORDER BY kode_gejala");
    while ($rgejala = mysqli_fetch_array($sql)) {
        $id_gejala = $rgejala['id_gejala'];
        $arr_hasil[] = array(
            "id_gejala" => $id_gejala,
            "cf_user" => $cf_user[$i],
        );
        $i++;
    }

    $hasil = array();
    $x = 0;

    // -------- perhitungan metode certainty factor (CF) ---------
    // --------------------- START ------------------------
    $sqlpenyakit = mysqli_query($con, "SELECT id_penyakit FROM penyakit order by id_penyakit");
    while ($rpenyakit = mysqli_fetch_array($sqlpenyakit)) {
        $id_penyakit = $rpenyakit['id_penyakit'];
        $cf_gabungan = 0;
        $i = 0;
        $sql = mysqli_query($con, "SELECT id_gejala FROM aturan where id_penyakit='$id_penyakit'");
        while ($rgejala = mysqli_fetch_array($sql)) {
            $id_gejala = $rgejala['id_gejala'];
            $r_gejala = mysqli_fetch_array(mysqli_query($con, "select nilai_cf from gejala where id_gejala='$id_gejala'"));
            $cf_gejala = $r_gejala['nilai_cf'];
            foreach ($arr_hasil as $row) {
                if ($id_gejala == $row['id_gejala']) {
                    $cf = $row['cf_user'] * $cf_gejala;
                    if ($i >= 0) {
                        $cf_gabungan = $cf_gabungan + ($cf * (1 - $cf_gabungan));
                    } else {
                        $cf_gabungan = $cf;
                    }
                    $i++;
                }
            }
        }
         // $persentase = round($cf_gabungan * 100);
         $persentase = $cf_gabungan * 100;
         $hasil[$x]["id_penyakit"] = $id_penyakit;
         $hasil[$x]["nilai"] = number_format($persentase, 2);
         $x++;
    }
    // --------------------- END -------------------------

    array_sort_by_column($hasil, 'nilai');

    $hasil_penyakit_id = $hasil[0]["id_penyakit"];
    $hasil_nilai = $hasil[0]["nilai"];

    $q = mysqli_query($con, "select nama_penyakit from penyakit where id_penyakit='$hasil_penyakit_id'");
    $r = mysqli_fetch_array($q);
    $nama_penyakit = $r['nama_penyakit'];

    $q = "insert into riwayat(id_pengguna,id_penyakit,tanggal,metode,nilai) values ('" . $id_pengguna . "','" . $hasil_penyakit_id . "','" . $tanggal . "','" . $metode . "','" . $hasil_nilai . "')";
    mysqli_query($con, $q);

    $response["status"] = 0;
    $response["id_penyakit"] = $hasil_penyakit_id;
    $response["nama_penyakit"] = $nama_penyakit;
    $response["nilai"] = $hasil_nilai;
} else {
    $response["status"] = 2;
    $response["message"] = "Parameter ada yang kosong";
}

// fungsi untuk mengurutkan nilai berdasarkan nilai terbesar
function array_sort_by_column(&$arr, $col, $dir = SORT_DESC)
{
    $sort_col = array();
    foreach ($arr as $key => $row) {
        $sort_col[$key] = $row[$col];
    }
    array_multisort($sort_col, $dir, $arr);
}

echo json_encode($response);
