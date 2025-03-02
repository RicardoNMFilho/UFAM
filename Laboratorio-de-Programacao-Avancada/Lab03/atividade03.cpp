#include <iostream>
#include <fstream>
#include <vector>
#include <cmath>

using namespace std;

struct lista_vizinhos_t {
    int vizinho;
    lista_vizinhos_t* proximo;
    lista_vizinhos_t(int _vizinho) : vizinho(_vizinho), proximo(nullptr) {}
};

struct no_t {
    int id;
    double pos_x;
    double pos_y;
    lista_vizinhos_t* lista_vizinhos;
    no_t(int _id, double _pos_x, double _pos_y) : id(_id), pos_x(_pos_x), pos_y(_pos_y), lista_vizinhos(nullptr) {}
};

using grafo_t = vector<no_t>;

bool lista_vizinhos_adicionar(int vizinho, lista_vizinhos_t*& lista) {
    lista_vizinhos_t* novo = new lista_vizinhos_t(vizinho);
    if (!novo) {
        return false;
    }
    novo->proximo = lista;
    lista = novo;
    return true;
}

void lista_vizinhos_imprimir(lista_vizinhos_t* lista) {
    while (lista) {
        cout << " " << lista->vizinho;
        lista = lista->proximo;
    }
}

void grafo_atualizar_vizinhos(int tam, double raio_comunicacao, grafo_t& grafo) {
    for (int i = 0; i < tam; ++i) {
        for (int j = 0; j < tam; ++j) {
            if (i != j) {
                double distancia = sqrt(pow(grafo[i].pos_x - grafo[j].pos_x, 2) + pow(grafo[i].pos_y - grafo[j].pos_y, 2));
                if (distancia <= raio_comunicacao) {
                    lista_vizinhos_adicionar(i, grafo[j].lista_vizinhos);
                }
            }
        }
    }
}

void grafo_imprimir(int tam, grafo_t& grafo) {
    for (int i = 0; i < tam; ++i) {
        cout << "NÓ " << i << ":";
        lista_vizinhos_imprimir(grafo[i].lista_vizinhos);
        cout << endl;
    }
}

int main(int argc, char** argv) {
    if (argc != 2) {
        cout << "Uso: " << argv[0] << " <arquivo_entrada>" << endl;
        return 1;
    }

    ifstream arquivo_entrada(argv[1]);
    if (!arquivo_entrada.is_open()) {
        cout << "Erro ao abrir o arquivo de entrada." << endl;
        return 1;
    }

    int num_nos;
    double raio_comunicacao;
    arquivo_entrada >> num_nos >> raio_comunicacao;

    grafo_t grafo;
    for (int i = 0; i < num_nos; ++i) {
        int id;
        double pos_x, pos_y;
        arquivo_entrada >> id >> pos_x >> pos_y;
        grafo.emplace_back(id, pos_x, pos_y);
    }

    grafo_atualizar_vizinhos(num_nos, raio_comunicacao, grafo);
    grafo_imprimir(num_nos, grafo);

    arquivo_entrada.close();
    for (no_t& no : grafo) {
        while (no.lista_vizinhos) {
            lista_vizinhos_t* temp = no.lista_vizinhos;
            no.lista_vizinhos = no.lista_vizinhos->proximo;
            delete temp;
        }
    }

    return 0;
}
