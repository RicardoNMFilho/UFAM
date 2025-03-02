#ifndef GRAFO_H
#define GRAFO_H

#include <iostream>

struct Evento {
    double tempo;
    int alvo;
    int tipo;
};

struct ListaEventos {
    Evento *evento;
    ListaEventos *proxEvento;
};

struct ListaVizinhos {
    int vizinho;
    ListaVizinhos *prox;
};

struct No {
    int id;
    double posX;
    double posY;
    bool pacoteEnviado;
    ListaVizinhos *listaVizinhos;
};

bool lista_eventos_adicionar_inicio(Evento *evento, ListaEventos **lista);
bool lista_eventos_adicionar_fim(Evento *evento, ListaEventos **lista);
bool lista_eventos_adicionar_ordenado(Evento *evento, ListaEventos **lista);

bool lista_vizinhos_adicionar(int vizinho, ListaVizinhos **lista);

No *grafo_criar(int tam);
void grafo_att_vizinhos(int tam, double raio_comunicacao, No *grafo);
void simulacao_iniciar(ListaEventos **lista, No *grafo);

#endif // GRAFO_H
