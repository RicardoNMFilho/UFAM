(function () {
    const TAMX = 600;
    const TAMY = 800;
    let PAUSE = 0;
    let FIM = 0;
    let COUNT = 0;
    let START = 0;
  
    const PROB_ENEMY_SHIP = 0.2;
    const PROB_ENEMY_UFO = 0.5;
    const PROB_ENEMY_BIG = 0.8;
    const PROB_ENEMY_SMALL = 0.13;
    let acelerarInimigos = 1;
  
    let clearRun;
  
    let MOV = 1;
  
    let menuStart = document.querySelector("#start-menu");
    let gameOver = document.querySelector("#over-menu");
    let gameRestart = document.querySelector("#play-again");
  
    let space, ship, stats;
    let enemies = [];
    let guns = [];
  
    function init() {
      space = new Space();
      ship = new Ship();
      stats = new ShipStats();
      
      // Inicia o Jogo
      window.addEventListener("keydown", (e) => {
        if (e.code === "Space" && START === 0){
          menuStart.style.visibility = "hidden";
          clearInterval(clearRun);
          clearRun = window.setInterval(run, 1000 / 100);
          START = 1;
        }
      });
  
    }
  
    // Movimentação da Nave
    window.addEventListener("keydown", (e) => {
        if (e.key === "ArrowLeft") ship.mudaDirecao(-1);
        else if (e.key === "ArrowRight") ship.mudaDirecao(+1);
      });
  
    // Sistema de Pause
    window.addEventListener("keydown", (e) => {
        if(e.key === 'p' || e.key === 'P' && FIM != 1) {
          PAUSE = !PAUSE;
          pause(PAUSE);
        }
      });
  
    // Disparos -> Quantidade de tiros em tela por vez é limitada
    window.addEventListener("keypress", (e) => {
        if(e.code === "Space" && START === 1){
          if(guns.length <= 3){
            guns.push(new ShipGun());
          }
        }
      });
  
    gameRestart.addEventListener("click", () => {
      gameOver.style.visibility = "hidden";
      menuStart.style.visibility = "visible";
      stats.menuPoint.innerHTML = "";
  
      limpezaDeTela();
  
      PAUSE = 0;
      START = 0;
      FIM = 0;
  
      init();
    });
  
    function pause(p){
        let menu = document.querySelector("#pause-menu");
        if(p == 1 && FIM == 0){
          menu.style.visibility = "visible";
        }else{
          menu.style.visibility = "hidden";
        }
      }
  
    function gerarInimigos(){
      let inimigosTela = document.querySelectorAll(".enemy");
      const random_enemy = Math.random() * 100;
        
        if (random_enemy <= PROB_ENEMY_SHIP) {
          enemies.push(new EnemyShip());
        }
  
        if(random_enemy <= PROB_ENEMY_UFO) {
          enemies.push(new EnemyUFO());
        }
  
        if(random_enemy <= PROB_ENEMY_BIG) {
          enemies.push(new EnemyMeteorBig());
        }
  
        if(random_enemy <= PROB_ENEMY_SMALL) {
          enemies.push(new EnemyMeteorSmall());
        }
    }
  
    function movimentacao(){
      guns.forEach((e) => e.move());
      guns.forEach((e) => e.destroy());
  
      enemies.forEach((e) => e.move());
      enemies.forEach((e) => e.destroy());
    }
  
    function dificuldade(){
      COUNT += 10; // Intervalo de tempo que a função run é chamada -> 10ms
      if(COUNT > 60 * 1000){
        acelerarInimigos += 1;
      }
    }
  
    function detectarColisao(elem1, elem2){
      const box1 = elem1.getBoundingClientRect();
      const box2 = elem2.getBoundingClientRect();
  
      let colisao = (box1.top < box2.bottom && box1.bottom > box2.top
                    && box1.left < box2.right && box1.right > box2.left);
  
      return colisao;
    }
  
    function colisaoDoJogo(){
      enemies.forEach((e) => {
  
        if(detectarColisao(ship.element, e.element) && e.element.style.visibility != "hidden"){
  
          stats.life = stats.life - 1;
          e.element.style.visibility = "hidden";
          let icon = document.querySelectorAll(".icon-life");
          MOV = 0;
          ship.assetAtingido();
  
          if(stats.life > -1){
            stats.menuLife.removeChild(icon[0]);
          }
  
        }
  
        guns.forEach((f) => {
          if(detectarColisao(e.element, f.element) == true && e.element.style.visibility != "hidden"){
            stats.pontuacao += e.point;
            e.element.style.visibility = "hidden";
          }
        });
      });
    }
  
    // Limpeza de array
    function remocaoGunsEnemies(){
      guns.forEach((e)=>{
          if(e.destroyed){
            let index = guns.indexOf(e); // Encontra o índice do elemento
            if (index > -1) {
              guns.splice(index, 1); // Remove o elemento do array
            }
          }
        });
        
      enemies.forEach((e)=>{
          if(e.destroyed){
            let index = enemies.indexOf(e); // Encontra o índice do elemento
            if (index > -1) {
              enemies.splice(index, 1); // Remove o elemento do array
            }
          }
        });
    }
  
    function fimJogo(vidas){
      if(vidas < 0){
        gameOver.style.visibility = "visible";
        FIM = 1;
      }
    }
  
    function limpezaDeTela(){
  
      guns.forEach((e) => {
        space.element.removeChild(e.element);
      });
  
      enemies.forEach((e) => {
        space.element.removeChild(e.element);
      });
  
      guns = [];
      enemies = [];
    }
    
    // Espaço
    class Space {
      constructor() {
        this.element = document.getElementById("space");
        this.element.style.width = `${TAMX}px`;
        this.element.style.height = `${TAMY}px`;
        this.element.style.backgroundPositionY = "0px";
  
      }
      move() {
        this.element.style.backgroundPositionY = `${
          parseInt(this.element.style.backgroundPositionY) + 1
        }px`;
      }
    }
  
    // Nave Espacial
    class Ship {
      constructor() {
        this.element = document.getElementById("ship");
        this.AssetsDirecoes = [
          "img/playerLeft.png",
          "img/player.png",
          "img/playerRight.png",
        ];
        this.direcao = 1;
        this.element.src = this.AssetsDirecoes[this.direcao];
        this.element.style.top = "650px";
        this.element.style.left = `${parseInt(TAMX / 2) - 50}px`;
  
      }
  
      mudaDirecao(giro) {
        if (this.direcao + giro >= 0 && this.direcao + giro <= 2) {
          this.direcao += giro;
          if(MOV == 1){
            this.element.src = this.AssetsDirecoes[this.direcao];
          }
        }
      }
  
      assetAtingido(){
        if(MOV == 0){
          this.element.src = "img/playerDamaged.png";
          setTimeout(() => {MOV = 1},5000);
        }
      }
  
      move() {
        if (this.direcao === 0)
          this.element.style.left = `${parseInt(this.element.style.left) - 2}px`;
          if(parseInt(this.element.style.left) < 0) this.element.style.left = `${parseInt(this.element.style.left) + 2}px`;
        if (this.direcao === 2)
          this.element.style.left = `${parseInt(this.element.style.left) + 2}px`;
          if(parseInt(this.element.style.left) + 98 > TAMX) this.element.style.left = `${parseInt(this.element.style.left) - 2}px`;
        space.move();
      }
      
    }
  
    class ShipStats {
      constructor() {
        this.life = 3;
        this.pontuacao = 0;
  
        this.menu = document.createElement("div");
        this.menuLife = document.createElement("div");
        this.menuPoint = document.createElement("div");
  
        this.menu.setAttribute("id", "menu-stats");
        this.menuLife.setAttribute("id", "menu-life");
        this.menuPoint.setAttribute("id", "menu-point");
  
        for(let i = 0; i < 3; i++){
          let iconLife = document.createElement("img");
          iconLife.src = 'img/life.png';
          iconLife.className = 'icon-life';
          this.menuLife.appendChild(iconLife); 
        }
  
        this.menuPoint.innerHTML = "000000";
  
        this.menu.appendChild(this.menuLife);
        this.menu.appendChild(this.menuPoint);
  
        space.element.appendChild(this.menu);
      }
  
      atualizar(){
        function tratamentoPontuacao(pontuacao){
          let telaPontuacao = String(pontuacao);
          for(let c = 6; c > String(pontuacao).length; c--){
            telaPontuacao = "0" + telaPontuacao;
          }
          return telaPontuacao;
        }
  
        this.menuPoint.innerHTML = `${tratamentoPontuacao(this.pontuacao)}`;
        this.menu.appendChild(this.menuLife);
        this.menu.appendChild(this.menuPoint);
  
        space.element.appendChild(this.menu);
      }
    }
  
    class ShipGun{
      constructor(){
        this.element = document.createElement("img");
        this.element.className = "gun-ship";
        this.element.src = "img/laserRed.png";
        this.element.style.top = "630px";
        this.element.style.left = `${parseInt(ship.element.style.left) + 50}px`;
        space.element.appendChild(this.element);
      }
      move() {
        this.element.style.top = `${parseInt(this.element.style.top) - 5}px`;
      }
  
      destroy() {
        if (parseInt(this.element.style.top) < 0) {
          if (!this.destroyed) {
            space.element.removeChild(this.element);
            this.destroyed = true;
          }
        }
      }
    }
    
    // Inimigos
    class EnemyShip {
      constructor() {
        this.element = document.createElement("img");
        this.element.classList.add("enemy");
        this.element.src = "img/enemyShip.png";
        this.element.style.top = "0px";
        this.element.style.left = `${Math.floor(Math.random() * TAMX)}px`;
        space.element.appendChild(this.element);
  
        this.point = 50;
        this.velocidade = Math.random() * 5 + 4 + acelerarInimigos;
  
      }
      move() {
        this.element.style.top = `${parseInt(this.element.style.top) + this.velocidade}px`;
      }
      destroy() {
        if (parseInt(this.element.style.top) > TAMY) {
          if (!this.destroyed) {
            space.element.removeChild(this.element);
            this.destroyed = true;
          }
        }
      }
    }
  
    class EnemyUFO{
      constructor() {
        this.element = document.createElement("img");
        this.element.classList.add("enemy");
        this.element.src = "img/enemyUFO.png";
        this.element.style.top = "0px";
        this.element.style.left = `${Math.floor(Math.random() * TAMX)}px`;
        space.element.appendChild(this.element);
  
        this.point = 20;
        this.velocidade = Math.random() * 3 + 4 + acelerarInimigos;
  
        
      }
      move() {
        this.element.style.top = `${parseInt(this.element.style.top) + this.velocidade}px`;
      }
      destroy() {
        if (parseInt(this.element.style.top) > TAMY) {
          if (!this.destroyed) {
            space.element.removeChild(this.element);
            this.destroyed = true;
          }
        }
      }
    }
  
    class EnemyMeteorBig{
      constructor() {
        this.element = document.createElement("img");
        this.element.classList.add("enemy");
        this.element.src = "img/meteorBig.png";
        this.element.style.top = "0px";
        this.element.style.left = `${Math.floor(Math.random() * TAMX)}px`;
  
        space.element.appendChild(this.element);
  
        this.point = 10;
        this.velocidade = Math.random() * 2 + 3 + acelerarInimigos;
  
        
      }
      move() {
        this.element.style.top = `${parseInt(this.element.style.top) + this.velocidade}px`;
      }
      destroy() {
        if (parseInt(this.element.style.top) > TAMY) {
          if (!this.destroyed) {
            space.element.removeChild(this.element);
            this.destroyed = true;
          }
        }
      }
    }
  
    class EnemyMeteorSmall{
      constructor() {
        this.element = document.createElement("img");
        this.element.classList.add("enemy");
        this.element.src = "img/meteorSmall.png";
  
        this.element.style.top = "0px";
        this.element.style.left = `${Math.floor(Math.random() * TAMX)}px`;
  
        space.element.appendChild(this.element);
  
        this.point = 100;
        this.velocidade = Math.random() * 5 + 7 + acelerarInimigos;
  
      }
      move() {
        this.element.style.top = `${parseInt(this.element.style.top) + this.velocidade}px`;
      }
      destroy() {
        if (parseInt(this.element.style.top) > TAMY) {
          if (!this.destroyed) {
            space.element.removeChild(this.element);
            this.destroyed = true;
          }
        }
      }
    }
  
    // Jogo
    function run() {
      if(!PAUSE && !FIM && START){
  
        ship.move();
  
        movimentacao();
  
        gerarInimigos();
  
        colisaoDoJogo();
  
        dificuldade();
  
        stats.atualizar();
  
        remocaoGunsEnemies();
  
        fimJogo(stats.life);
  
      }
    } 
  
    init();
  })();
  