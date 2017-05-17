
<?php


function isValidInput() {
	if (isset($_POST['x']) && isset($_POST['y']) && isset($_POST['r'])) {
		$x = $_POST['x'];
		$y = str_replace(',',  '.', $_POST['y']);
        $r = str_replace(',',  '.', $_POST['r']);

	
		if (is_numeric($x) && is_numeric($y) && is_numeric($r)) 
			if ($x >= -4 & $x <= 4 && $y >= -5 && $y <= 5 && $r >= 2 && $r <= 5)
				return true;
	}		
	return false;
}

function isPointInsideShape($x, $y, $r) {
    if ($x >= 0) {
        if ($y >= 0) {
            if ($x <= $r/2 && $y <= $r/2)
                return true;
            else
                return false;
        }
    } else {
    	  if ($y >= 0) {
            if ($x >= -$r +$y)
                return true;
            else
                return false;
        }

        if ($y <= 0) {
            if ($x*$x + $y*$y < $r*$r) {
                return true;
            } else
                return false;
        } 
    }
}

if (isValidInput()) {
    $startupTime = new DateTime();
    $x = $_POST['x'];
    $y = str_replace(',',  '.', $_POST['y']);
    $r = str_replace(',',  '.', $_POST['r']);

    require_once ("resulthtml.html");


 } else {
    echo "Bad input";
 }
?>
