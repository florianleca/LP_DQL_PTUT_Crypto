<html>
	<head>
		<title>Cryptobot Sandbox</title>
	</head>
	<body>
		<div>____________________________________</div>
		<div>APPLICATION DE CRYPTO TROP COOL</div>
		<div>____________________________________</div>
		
		
		<form action="/" method="post">
      		
      		<label for="symbol">Symbole</label>
      		<select name="symbol">
        		<option value="btc">BTC</option>
       		 	<option value="eth">ETH</option>
      		</select>
      		<br>
      	<!--  
      	<input type="submit" value="Confirmer symbole" />
		</form>
		<form action="post">
		-->
		
      		<label for="interval">Intervalle</label>
      		<select name="interval">
        		<option value="1 heure">1 heure</option>
       		 	<option value="1 jour">1 jour</option>
      		</select>
      		<br>
      	
      	<!-- 
      	<input type="submit" value="Confirmer intervalle" />
		</form>
		<form action="post">
		-->
		
		
      		<label for="startdate">Date début :</label>
      		<input name="startdate" type="date"/>
      		<br>
		<!-- 
		</form>
		<form action="post">
		-->
      		<label for="enddate">Date fin :</label>
      		<input name="enddate" type="date"/>
      		<br>
      		
      		<input type="submit" value="Confirmer paramètres" />
		</form>
		
		<div>${confirmation}</div>
		
	</body>
</html>