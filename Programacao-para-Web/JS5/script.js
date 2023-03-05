function calculaArea(x){
  return Math.PI * x * x;
}

function calculaCircu(x){
  return Math.PI * 2 * x;
}

function enviarDados(){
  raio = document.querySelector("#raio").value;
  area = document.querySelector("#area");
  circu = document.querySelector("#circu");
  
  area.value = calculaArea(raio).toFixed(2);
  circu.value = calculaCircu(raio).toFixed(2);
}
