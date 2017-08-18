<html>
<head>
	<title>Create Paste</title>
</head>
<body>

<div style="test-align:center;">
<h2>Create Paste</h2>
<br>
<br>
<form id="createForm" action="/PersonalPaste/create" method="post">
	<p>Paste Name: <input type="text" name="pasteName" value="${ pasteName }" /></p>
	<br>
	<p>
	Paste Content:<br>
	<textarea form="createForm" name="pasteContent" style="width:80%;height:60vh"></textarea><br>
	</p>
	<input type="submit" value="Save" />
</form>
</div>

</body>
</html>