<html>
	<head>
		<title>Cryptobot Sandbox</title>
	</head>
	<body>
		<h1>CRYPTOBOT SANDBOX</h1>
		
		<form action="/" method="post">
      		
      		<label for="symbol">Symbole</label>
      		<select name="symbol">
        		<option value="btc">BTC</option>
       		 	<option value="eth">ETH</option>
      		</select>
      		<br>
		
      		<label for="interval">Intervalle</label>
      		<select name="interval">
        		<option value="1 heure">1 heure</option>
       		 	<option value="1 jour">1 jour</option>
      		</select>
      		<br>
      	
      		<label for="startdate">Date début :</label>
      		<input name="startdate" type="date"/>
      		<br>

      		<label for="enddate">Date fin :</label>
      		<input name="enddate" type="date"/>
      		<br>
      		
      		<input type="submit" value="Confirmer paramètres" />
		</form>
		
		<div>${confirmation}</div>
		
	</body>
</html>