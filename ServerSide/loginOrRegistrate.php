<?php

require 'baza.class.php';

	$headers = apache_request_headers();
	
	$device = $headers['user-agent'];
	$apikey= $headers['Basic'];

if ($_SERVER["REQUEST_METHOD"] === "POST" && $device==="Android" && $apikey==="ovojenasAPIkljuc" ){
	
	$response="";
		
	$email = filter_input(INPUT_POST, 'email');
	$password = filter_input(INPUT_POST,'password') ;

	$hashedPassword=hash ("sha256", $password ,false);
	
	$randomNumber = rand(10000,99999);
	$userToken =hash ("sha256", $randomNumber ,false);
	
	$checkUser= "SELECT COUNT(*) FROM `User` WHERE email=? and password=?";
	$logReg= "INSERT INTO `User`(`email`, `password`) VALUES (?,?);";
	$addUserToken = 'UPDATE `User` SET `token`=? WHERE `email`=?;';
	
	$db=new Baza();
	
	$postoji= false;
	if($db->query($logReg,$email,$hashedPassword)){
		$db->query($addUserToken, $userToken,$email);
		$response="Registracija uspjesna";
		$postoji=true;
	}else{
		$selectValue = $db->selectQuery($checkUser,$email,$hashedPassword);
		if($selectValue=="1"){
			$db->query($addUserToken, $userToken,$email);
			$postoji = true;
			$response="Login uspjesan";
		}else $response = "invalid email or password";
	} 
	
	$db->closeDb();
	if($postoji){
	header('Content-Type: application/json');
	echo json_encode( array('email'=>$email,'token'=> $userToken ,'responseMessage'=>$response,'postoji'=>$postoji));
	}
}
else echo 'access denied';
