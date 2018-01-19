<?php

class Baza {

    private $server = "localhost";
    private $user = "id4293559_mihmarici";
	private $password = 'Q$bBvvw6';
    private $database = "id4293559_sisprojekt";

    private $conn = null;

    function  __construct() {
        $this->conn = new mysqli($this->server, $this->user,$this->password, $this->database);
        if (!$this->conn) {
            die ("Neuspješno spajanje na bazu");
        }
        $this->conn->set_charset("utf8");

        return $this->conn;
    }
 	
    function query($upit,$value1,$value2){	 
		$stmt = $this->conn->prepare($upit);
		$stmt->bind_param("ss", $value1, $value2);
		if ($stmt->execute())return true;
		else return false;	 
    }
	
	function selectQuery($upit,$email,$password) {
		$stmt = $this->conn->prepare($upit);
		$stmt->bind_param("ss", $email, $password);
		
        $postoji = $stmt->execute();
		$rezultat = "0";
		
        $stmt->bind_result($rezultat);
        $stmt->fetch();
		
		$stmt->close();
        if (!$postoji) {
            echo "Greška kod upita: $upit";
        }
        return $rezultat;
    } 
	
	function closeDb() {
        $this->conn->close();
		
    }	
}

