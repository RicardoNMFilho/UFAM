const botao = document.querySelector("#action");

botao.addEventListener("click", function(e){
	let alturas = document.getElementsByName("altu");
	let largu = document.getElementsByName("largu");

	let barras = document.querySelectorAll(".bar");

	for (var i = alturas.length - 1; i >= 0; i--) {
		barras[i].style.height = alturas[i].value + 'px';
		barras[i].style.width = largu[0].value + 'px';
	}
})