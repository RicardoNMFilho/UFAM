function counter(x){
  let contador = 0;
  return function () {
    contador += 1;
    return contador + x;
  }
}

const incrementar = counter(10);

console.log('Primeira chamada ' + incrementar());
console.log('Segunda chamada ' + incrementar());
console.log('Terceira chamada ' + incrementar());
