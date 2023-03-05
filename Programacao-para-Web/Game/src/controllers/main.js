let conteudo;

const index = (req,res) => {
    res.render('main/index', {
        
    })
}

const about = (req,res) => {
    conteudo = "Space Shooter é um jogo de nave em JavaScript onde o jogador precisa enfrentar vários obstáculos enquanto tenta destruir os inimigos. O jogo conta com uma série de desafios, incluindo naves inimigas e asteroides que surgem no caminho do jogador. O objetivo do jogo é destruir o maior número de inimigos possível, acumulando pontos a cada adversário eliminado. O jogador tem três vidas no jogo e precisa ter habilidade e agilidade para desviar dos obstáculos enquanto atira nos inimigos. Quanto mais inimigos o jogador destruir, mais pontos serão acumulados. O jogo pode ser uma ótima opção para quem gosta de desafios e aventuras espaciais."
    res.render("main/about", {
        texto: conteudo, 
    })
}

const rank = (req,res) => {
    res.render("main/rank"), {

    }
}

const areas = (req,res) => {
    res.render("main/areas"), {
        
    }
}

const ui = (req,res) => {
    res.render("main/ui");
}

const game = (req,res) => {
    res.render("main/game");
}

module.exports = {index, about, ui, game, rank, areas};