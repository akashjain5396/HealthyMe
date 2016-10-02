<?php
if($_SERVER['REQUEST_METHOD']=='POST'){

		$user = $_POST['username'];
		$age = $_POST['age'];
		$aadharnumber=$_POST['aadharnumber'];
		$city = $_POST['city'];
	  $pincode = $_POST['pincode'];
		$type = $_POST['type'];


    require_once('dbconnect.php');

		if($user == ''  || $age == ''|| $aadharnumber == '' || $city == ''|| $pincode == '' || $type == '') {
			echo 'please fill all values';
		}
		else{

      if($type == 'P'){

  			$sql = "SELECT * FROM registerpatient WHERE name='$user' AND aadharnumber='$aadharnumber'";
  			$res = mysqli_query($con,$sql);
        //var_dump($res);
  			$check = mysqli_fetch_assoc($res);
      //  echo $sql;

  			if($check){
  				echo 'already exist';
  			}else{

          $sql = "INSERT INTO registerpatient (name,age,aadharnumber,city,pincode) VALUES ('{$user}','{$age}','{$aadharnumber}','{$city}','{$pincode}') ";
          echo mysqli_error($con);
          //var_dump(mysqli_query($con,$sql));
        if(mysqli_query($con,$sql)){
					echo 'successfully registered';
				}else{
					echo 'oops! Please try again!';
				}

			}

     }
    elseif($type == 'D'){

      $sql = "SELECT * FROM registerdoctor WHERE name='$user' OR aadharnumber='$aadharnumber'";
      $res = mysqli_query($con,$sql);
      $check = mysqli_fetch_assoc($res);
      //echo $sql;
      if($check){
        echo 'already exist';
      }else{

        $sql = "INSERT INTO registerdoctor (name,aadharnumber,city,pincode) VALUES ('{$user}','{$aadharnumber}','{$city}','{$pincode}') ";
        echo $sql;
        var_dump(mysqli_query($con,$sql));
        if(/*mysqli_query($con,$sql)*/0){
        echo 'successfully registered';
      }else{
        echo 'oops! Please try again!';
      }

    }
  }

			$con->close();
		}
}
else{
echo 'error';
}
?>
