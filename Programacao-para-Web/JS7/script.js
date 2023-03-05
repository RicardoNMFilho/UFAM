function criarPonto(x){
    let ponto = document.createElement("div");
    ponto.className = "ponto";
    ponto.style.left = (x.pageX) + "px";
    ponto.style.top = (x.pageY) + "px";
    document.body.appendChild(ponto);
}

function divsPonto(){
    let pontos = document.querySelectorAll(".ponto");
    return pontos;
}

document.addEventListener("mousemove", function(e) {
    if(divsPonto().length <= 8){
        criarPonto(e);
    }else{
        divsPonto()[0].remove();
        criarPonto(e);
    }
});