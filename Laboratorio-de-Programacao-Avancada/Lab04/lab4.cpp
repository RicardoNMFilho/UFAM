#include "grafo.h"
#include <fstream>

int main(int argc, char const *argv[]) {
    std::ifstream arq(argv[1]);
    if (!arq) return 1;

    int num_nos;
    double raio_comunicacao;
    arq >> num_nos >> raio_comunicacao;

    No *grafo = grafo_criar(num_nos);
    for (int i = 0; i < num_nos; i++) {
        int id;
        double pos_x, pos_y;
        arq >> id >> pos_x >> pos_y;

        grafo[i].id = id;
        grafo[i].posX = pos_x;
        grafo[i].posY = pos_y;
    }

    grafo_att_vizinhos(num_nos, raio_comunicacao, grafo);

    Evento *evento_inicial = new Evento;
    evento_inicial->tempo = 1.0;
    evento_inicial->alvo = 0;
    evento_inicial->tipo = 1;

    ListaEventos *lista_eventos = nullptr;
    lista_eventos_adicionar_ordenado(evento_inicial, &lista_eventos);

    simulacao_iniciar(&lista_eventos, grafo);

    delete[] grafo;
    while (lista_eventos != nullptr) {
        ListaEventos *evento = lista_eventos;
        lista_eventos = lista_eventos->proxEvento;
        delete evento->evento;
        delete evento;
    }

    arq.close();

    return 0;
}
