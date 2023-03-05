Código correto, mas não imprimou o que foi requsitado na questão.
Nota: 1.5

class Venda {
    constructor(ID, quantidade, preco){
        this.ID = ID;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    setId(id){
        this.ID = id;
    }

    getId(){
        return this.ID;
    }

    setQuantidade(qnt){
        this.quantidade = qnt;
    }

    getQuantidade(){
        return this.quantidade;
    }

    setPreco(preco){
        this.preco = preco;
    }

    getPreco(){
        return this.preco;
    }

    getValorTotal() {
        console.log(this.quantidade * this.preco);
    }

}

let presunto = new Venda(1010, 100, 2.5);
let queijo = new Venda(2121, 50, 1.25);
