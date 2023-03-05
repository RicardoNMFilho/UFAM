let multi;

document.write('<div class="tabelas">');

for(cont = 1; cont <= 10; cont++){
	document.write('<table border="1"><tr><td colspan="2"><strong>Produtos de ' + cont + '</strong></td></tr>');
	for(cont2 = 1; cont2 <= 10; cont2++){
		multi = cont * cont2;
		document.write('<tr class="text">' + '<td>' + cont + 'x' + cont2 + '</td>' + '<td>' + multi + '</td>' + '</tr>');
	}
	document.write('</table> <br>');
}

document.write('</div>');