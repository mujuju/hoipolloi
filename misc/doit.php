<?php
/* Convert a phpMyPeople mysql database into a Hoi Polloi sqlite database */
/* Jan 7, 2008, run this script as root inside phpmypeople directory: php -f doit.php */

// Include the ADOdb Class
include("adodb/adodb.inc.php");

// Connect to SQLite3 Database for Hoi Polloi
try {
	$hp = new PDO('sqlite:/pmp.sq3', null, null, array(PDO::ATTR_PERSISTENT => true));
} 
catch (PDOException $e) {
	echo 'Connection failed: ' . $e->getMessage();
	exit();
}

// Connect to MySQL Database for phpMyPeople
$dbhost	= "localhost";
$dbuser	= "root";
$dbpass	= "";
$dbname	= "phpmypeople";
$db = &ADONewConnection('mysql');
$db->PConnect($dbhost, $dbuser, $dbpass, $dbname);
$ADODB_FETCH_MODE = ADODB_FETCH_BOTH;

//Categories
echo "Importing Categories\n";
$rs = $db->Execute("SELECT * FROM pmp_catagories");
$hp->exec("DELETE FROM pmp_categories");
while (!$rs->EOF) {
	$catid = $rs->fields[0];
	$catname = $rs->fields[1];
	$hp->exec("INSERT INTO pmp_categories (catCategoryID, catCategoryName) VALUES (\"$catid\", \"$catname\")") or die(print_r($hp->errorInfo()));
	$rs->MoveNext();
}

//Category Links
echo "Importing Links\n";
$rs = $db->Execute("SELECT * FROM pmp_catagorylink");
$hp->exec("DELETE FROM pmp_categorylink");
while (!$rs->EOF) {
	$pid = $rs->fields[1];
	$cid = $rs->fields[2];
	$hp->exec("INSERT INTO pmp_categorylink (clkCategoryID, clkPersonID) VALUES (\"$cid\", \"$pid\")") or die(print_r($hp->errorInfo()));
	$rs->MoveNext();
}

//Contacts
echo "Importing Contacts\n";
$rs = $db->Execute("SELECT * FROM pmp_contacts");
$hp->exec("DELETE FROM pmp_contacts");
while (!$rs->EOF) {
	$pid = $rs->fields[1];
	$tid = $rs->fields[2];
	$ctn = $rs->fields[3];
	$hp->exec("INSERT INTO pmp_contacts (ctnPersonID, ctnContactTypeID, ctnContact) VALUES (\"$pid\", \"$tid\", \"$ctn\")") or die(print_r($hp->errorInfo()));
	$rs->MoveNext();
}

//Contact Types
echo "Importing Contact Types\n";
$rs = $db->Execute("SELECT * FROM pmp_ctypes");
$hp->exec("DELETE FROM pmp_ctypes");
while (!$rs->EOF) {
	$tid  = $rs->fields[0];
	$type = $rs->fields[1];
	$hp->exec("INSERT INTO pmp_ctypes (typContactTypeID, typContactType) VALUES (\"$tid\", \"$type\")") or die(print_r($hp->errorInfo()));
	$rs->MoveNext();
}

//Profiles
echo "Importing Profiles\n";
$rs = $db->Execute("SELECT * FROM pmp_profiles");
$hp->exec("DELETE FROM pmp_people");
while (!$rs->EOF) {
	$f1 = $rs->fields[0];  //Person ID
	$f2 = $rs->fields[1];  //First Name
	$f3 = $rs->fields[2];  //Last Name
	$f4 = $rs->fields[9];  //Nick Name
	$f5 = $rs->fields[10]; //Nationality
	$f6 = $rs->fields[11]; //Description
	$f7 = "";
	$f8 = "Male";

	// Get Nationality ID Number
	switch ($f5) {
		case "American":
			$f5 = 183;
			break;
		case "Thai":
			$f5 = 171;
			break;
		case "Singaporean":
			$f5 = 154;
			break;
		case "Chinese":
			$f5 = 36;
			break;
		default:
			$f5 = 193;
			break;
	}
			
	// See if they have a photo already
	if (file_exists("pictures/".$f1.".jpg")) {
		$f7 = "jpg";
	}
	
	$hp->exec("INSERT INTO pmp_people (psnPersonID, psnFirstName, psnLastName, psnDescription, psnNickName, psnDemonymID, psnPhotoFilename, psnGender) VALUES (\"$f1\", \"$f2\", \"$f3\", \"$f6\", \"$f4\", \"$f5\", \"$f7\", \"$f8\")") or die(print_r($hp->errorInfo()));
	$rs->MoveNext();
}

//Addresses
echo "Importing Addresses\n";
$rs = $db->Execute("SELECT * FROM pmp_profiles");
$hp->exec("DELETE FROM pmp_addresses");
while (!$rs->EOF) {
	$uid      = $rs->fields[0];
	$label    = $rs->fields[1]." ".$rs->fields[2];
	$address1 = $rs->fields[3];
	$address2 = $rs->fields[4];
	$city     = $rs->fields[5];
	$state    = $rs->fields[6];
	$zip      = $rs->fields[7];
	$country  = $rs->fields[8];
	$sql = "INSERT INTO pmp_addresses (adrAddressLabel, adrAddressTypeID, adrPersonID, adrAddressLine1, adrAddressLine2, adrCity, adrState, adrZip, adrCountryID) VALUES (\"$label\", \"1\", \"$uid\", \"$address1\", \"$address2\", \"$city\", \"$state\", \"$zip\", \"$country\")";
	$hp->exec($sql) or die(print_r($hp->errorInfo()));
	$rs->MoveNext();
}

//Birthdays
echo "Importing Birthdays\n";
$rs = $db->Execute("SELECT * FROM pmp_profiles");
while (!$rs->EOF) {
	$pid   = $rs->fields[0];  //Person ID
	$label = $rs->fields[1]." ".$rs->fields[2];
	$rs2   = $db->Execute("SELECT * FROM pmp_birthdays WHERE (btdProfileID = '$pid')");
	$bd    = $rs2->fields[3];
	if ($rs2->RecordCount() < 2) {
		//Update Person with Birthday
		$hp->exec("UPDATE pmp_people SET psnBirthday = '$bd' WHERE psnPersonID = '$pid'");
	}
	else {
		echo $label." ($pid) has more than one birthday...skipping.\n";
	}

	$rs->MoveNext();
}

// Close Connections
$hp = null;
$db->close();

?>

