let ganhando = true;
let opcoes = ["papel", "pedra", "tesoura"]
let vitorias = 0;
let jogada;
let maquina;

while(ganhando){
	console.log("Escolha sua jogada: \n1 - Papel\n2 - Pedra\n3 - Tesoura");
	jogada = parseInt(prompt("Digite sua jogada"));
	maquina = Math.floor(Math.random() * 3) + 1;

	if(jogada == "1"|| jogada == "2" || jogada == "3"){

		console.log("A máquina jogou " + opcoes[maquina - 1]);

		if(jogada == maquina){
			console.log("Você empatou");
		}

		else if((jogada == 1 && maquina == 2) || (jogada == 2 && maquina == 3)){
			console.log("Você ganhou");
			vitorias = vitorias + 1;
		}

		else{
			console.log("Você perdeu");
			ganhando = false;
		}
	}

	else {
		ganhando = false;
	}
}

console.log("Você fez " + vitorias + " pontos");