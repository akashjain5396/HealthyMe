<?php
  if($_SERVER['REQUEST_METHOD']=='POST'){
      $user = $_POST['username'];
      $aadharnumber = $_POST['aadharnumber'];

        require_once('dbconnect.php');

        $sql="SELECT * FROM registerpatient WHERE name = '$user' AND aadharnumber='$aadharnumber'";

        $result = $con->query($sql);

        $check = $result->fetch_assoc();
        if ($user == '' || $aadharnumber == '') {
          echo 'Please fill all the fields';
        }
        else{
             if($check){
             echo 'success';
             }else{
             echo 'failure';
             }
           }
      //$con->close();
  }
else{
   echo 'error';
}
?>
