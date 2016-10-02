<?php

 if($_SERVER['REQUEST_METHOD']=='POST'){

 	$disease = $_POST['disease'];
 	$cause = $_POST['cause'];
 	$prescription = $_POST['prescription'];
  $aadharnumber = $_POST['aadharnumber'];

 	require_once('dbconnect.php');


 	$sql ="SELECT id FROM medicalhistory ORDER BY id ASC";
 	$res = mysqli_query($con,$sql);
 	$id = 0;

 	while($row = mysqli_fetch_array($res)){
 		$id = $row['id'];

 	}



 $sql="SELECT id FROM registerpatient WHERE aadharnumber='$aadharnumber'";
 $res2=mysqli_query($con,$sql);
 $user_list=mysqli_fetch_assoc($res2);
 $uid = $user_list['id'];
  var_dump($res2);
 $sql = "INSERT INTO medicalhistory (user_id,disease,cause,prescription) VALUES ($uid,'{$disease}','{$cause}','{$prescription}')";
 //echo $sql;
 	if(mysqli_query($con,$sql)){
 	 	echo "Successfully Uploaded";

 	}
  else{
    echo "Oops!Something went wrong";
  }

 	mysqli_close($con);
 	}
 else{
 		echo "Error";
  }

 ?>
