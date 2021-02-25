<?php
$file = fopen("SRD_01022018.txt", "r");
$content ="";
while (!feof($file)) 
{
	$content .=fgets($file,4096);
}
fclose($file);

$lines = file("SRD_01022018.txt");
foreach ($lines as $line) 
{
	list($id, $date, $heure, $prix, $z, $y, $x, $v) = explode(";", $line);
	$exist = SelectAction($id);
	/*if ($exist != null) 
	{
		AjouterAction($id);
	}*/
	$tableDate = explode("/", $date);
	//echo $tableDate[3];
	echo "qoenblk";
	//$dateF = ("2000"+$tableDate[3]+"-"+$tableDate[2]+"-"+$tableDate[1]+" "+$heure+":00");
	//echo $dateF;
	//AjouterPrixAction($id,$date,$heure,$prix,$v);
}
function AjouterAction($id)
{
    require("connect_baseboutique.php");
	try
    {
    	$pdo_options[PDO::ATTR_ERRMODE] = PDO::ERRMODE_EXCEPTION;
        $bdd = new PDO('mysql:host='.$host.';dbname='.$bdd, $util, $password, $pdo_options);
        $req = $bdd->prepare('INSERT INTO action(numAction) VALUES(?)');
            $req->execute(array($id));
			$req->closeCursor();
			return 0;
            
    }
    catch(Exception $e)
    {
        die('Erreur : '.$e->getMessage());
    }
    
}

function SelectAction($id)
{
    require("connect_baseboutique.php");
	try
    {
    	$pdo_options[PDO::ATTR_ERRMODE] = PDO::ERRMODE_EXCEPTION;
        $bdd = new PDO('mysql:host='.$host.';dbname='.$bdd, $util, $password, $pdo_options);
        $req = $bdd->prepare('select count(*) from action where numAction = ?');
            $req->execute(array($id));
			$req->closeCursor();
			return $req;
            
        }
        catch(Exception $e)
        {
            die('Erreur : '.$e->getMessage());
        }
    
}
function AjouterPrixAction($id,$date,$heure,$prix,$volume)
{
    require("connect_baseboutique.php");
	try
    {

    	$dateF = "";
    	$tableDate = explode("/", $date);
    	$dateF = ("2000"+$tableDate[3]+"-"+$tableDate[2]+"-"+$tableDate[1]+" "+$heure+":00");
    	$pdo_options[PDO::ATTR_ERRMODE] = PDO::ERRMODE_EXCEPTION;
        $bdd = new PDO('mysql:host='.$host.';dbname='.$bdd, $util, $password, $pdo_options);
        $req = $bdd->prepare('INSERT INTO prixaction(date,IDAction,prix,volume) VALUES(?,?,?,?)');
            $req->execute(array($dateF,$id,$prix,$volume));
			$req->closeCursor();
			return 0;
            
        }
        catch(Exception $e)
        {
            die('Erreur : '.$e->getMessage());
        }
    
}

?>

