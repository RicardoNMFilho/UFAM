#include <iostream>
#include <cstdio>
#include <cstdlib>
#include <cstring>

struct TipoPessoa {
    char nome[51];
    long long int cpf;
    int idade;
};

struct ListaTipoPessoa {
    TipoPessoa *pessoa;
    ListaTipoPessoa *prox;
};

struct TipoTabelaHash {
    ListaTipoPessoa **lista;
};

int ListaPessoasAdicionar(TipoPessoa *pessoa, ListaTipoPessoa **lista) {
    ListaTipoPessoa *novaPessoa = new ListaTipoPessoa;
    if (novaPessoa == nullptr) return 0;

    novaPessoa->pessoa = pessoa;
    novaPessoa->prox = *lista;

    *lista = novaPessoa;
    return 1;
}

void ListaPessoasListar(ListaTipoPessoa *lista) {
    if (lista != nullptr) {
        ListaTipoPessoa *aux = lista;
        std::cout << "- " << aux->pessoa->nome << "  " << aux->pessoa->cpf << "    " << aux->pessoa->idade << std::endl;

        aux = aux->prox;
        while (aux != nullptr) {
            std::cout << "- " << aux->pessoa->nome << "  " << aux->pessoa->cpf << "    " << aux->pessoa->idade << std::endl;
            aux = aux->prox;
        }
    }
}

TipoTabelaHash *TabelaHashCriarPessoas(int tamanho) {
    TipoTabelaHash *tabelaHash = new TipoTabelaHash;
    tabelaHash->lista = new ListaTipoPessoa*[tamanho];

    for (int i = 0; i < tamanho; i++) {
        tabelaHash->lista[i] = nullptr;
    }

    return tabelaHash;
}

int tamanhoHash = 0;

int TabelaHashFuncaoPessoas(TipoPessoa *pessoa) {
    return pessoa->cpf % tamanhoHash;
}

int TabelaHashAdicionarPessoas(TipoPessoa *pessoa, TipoTabelaHash *tabelaHash) {
    int local = TabelaHashFuncaoPessoas(pessoa);
    return ListaPessoasAdicionar(pessoa, &tabelaHash->lista[local]);
}

void TabelaHashListarPessoas(TipoTabelaHash *tabelaHash) {
    for (int i = 0; i < tamanhoHash; i++) {
        std::cout << "POSIÇÃO " << i << " DA TABELA HASH:" << std::endl;
        ListaPessoasListar(tabelaHash->lista[i]);
    }
}

int main(int argc, char const *argv[]) {
    TipoTabelaHash *tabelaHash = nullptr;
    sscanf(argv[1], "%d", &tamanhoHash);

    TipoPessoa *pessoa;
    char nome[51];
    long long int cpf;
    int idade;

    FILE* arquivo = fopen(argv[2], "r");
    tabelaHash = TabelaHashCriarPessoas(tamanhoHash);

    while (fscanf(arquivo, "%50[^\t]\t%lld\t%d\n", nome, &cpf, &idade) != -1) {
        pessoa = new TipoPessoa;
        strncpy(pessoa->nome, nome, sizeof(nome));

        pessoa->cpf = cpf;
        pessoa->idade = idade;

        TabelaHashAdicionarPessoas(pessoa, tabelaHash);
    }

    if (tabelaHash != nullptr) {
        TabelaHashListarPessoas(tabelaHash);
    } else {
        std::cout << "Erro." << std::endl;
    }

    return 0;
}
