<html>
<head>
	<title>Personal Paste</title>
</head>
<body>
	<div style="text-align: center">
	<h1>Personal Paste</h1>
	${ creationResult }<br>
	<br>
	Enter the name of the paste you want to view or create:<br>
		<form action="/PersonalPaste/submit" method="post">
			<input type="text" name="pasteName" style="width:70%" />
			<br>
			<input type="submit" name="submitBtn" value="View" />
			<input type="submit" name="submitBtn" value="Create" />
		</form>
		
	</div>
	<hr>
	Server Time: ${ time }<br>
	<a href="/PersonalPaste/about">Developed by Philip Rodriguez in 2017</a><br>
</body>
</html>