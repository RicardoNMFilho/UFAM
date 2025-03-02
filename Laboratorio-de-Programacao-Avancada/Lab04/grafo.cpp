#include "grafo.h"
#include <fstream>
#include <cmath>

bool lista_eventos_adicionar_inicio(Evento *evento, ListaEventos **lista) {
    ListaEventos *item_novo = new ListaEventos;
    if (item_novo == nullptr) return false;

    item_novo->evento = evento;
    item_novo->proxEvento = *lista;

    *lista = item_novo;

    return true;
}

bool lista_eventos_adicionar_fim(Evento *evento, ListaEventos **lista) {
    ListaEventos *item_novo = new ListaEventos;
    if (item_novo == nullptr) return false;

    item_novo->evento = evento;
    item_novo->proxEvento = nullptr;

    if (*lista == nullptr)
        *lista = item_novo;
    else {
        ListaEventos *item_atual = *lista;
        while (item_atual->proxEvento != nullptr) {
            item_atual = item_atual->proxEvento;
        }

        item_atual->proxEvento = item_novo;
    }
    return true;
}

bool lista_eventos_adicionar_ordenado(Evento *evento, ListaEventos **lista) {
    ListaEventos *item_novo = new ListaEventos;
    ListaEventos *item_atual = *lista;
    if (item_novo == nullptr) return false;

    item_novo->evento = evento;

    if (*lista == nullptr) {
        *lista = item_novo;
        return true;
    } else {
        if (item_novo->evento->tempo < item_atual->evento->tempo)
            return lista_eventos_adicionar_inicio(evento, lista);
        else {
            while (item_atual->proxEvento != nullptr && item_atual->proxEvento->evento->tempo < evento->tempo) {
                item_atual = item_atual->proxEvento;
            }

            return lista_eventos_adicionar_fim(evento, lista);
        }
    }
}

bool lista_vizinhos_adicionar(int vizinho, ListaVizinhos **lista) {
    ListaVizinhos *novo_vizinho = new ListaVizinhos;
    if (novo_vizinho == nullptr) return false;

    novo_vizinho->vizinho = vizinho;
    novo_vizinho->prox = *lista;

    *lista = novo_vizinho;
    return true;
}

No *grafo_criar(int tam) {
    No *vetor_no = new No[tam];
    if (vetor_no == nullptr) return nullptr;

    for (int i = 0; i < tam; i++) {
        vetor_no[i].pacoteEnviado = false;
        vetor_no[i].listaVizinhos = nullptr;
    }
    return vetor_no;
}

void grafo_att_vizinhos(int tam, double raio_comunicacao, No *grafo) {
    for (int i = 0; i < tam; i++) {
        for (int j = 0; j < tam; j++) {
            if (i != j) {
                double distancia_euclidiana = sqrt(pow(grafo[i].posX - grafo[j].posX, 2) + pow(grafo[i].posY - grafo[j].posY, 2));
                if (distancia_euclidiana < raio_comunicacao)
                    lista_vizinhos_adicionar(i, &grafo[j].listaVizinhos);
            }
        }
    }
}

void simulacao_iniciar(ListaEventos **lista, No *grafo) {
    while (*lista != nullptr) {
        Evento *evento = (*lista)->evento;
        *lista = (*lista)->proxEvento;
        std::cout << "[" << evento->tempo << "] Nó " << evento->alvo << " recebeu pacote." << std::endl;

        No *no_atual = &grafo[evento->alvo];
        if (!no_atual->pacoteEnviado) {
            ListaVizinhos *vizinho_atual = no_atual->listaVizinhos;
            while (vizinho_atual != nullptr) {
                int vizinho_id = vizinho_atual->vizinho;
                std::cout << "\t--> Repassando pacote para o nó " << vizinho_id << " ..." << std::endl;

                Evento *novo_evento = new Evento;
                novo_evento->alvo = vizinho_id;
                novo_evento->tipo = 1;
                novo_evento->tempo = (evento->tempo) + (0.1 + (vizinho_id * 0.01));

                lista_eventos_adicionar_ordenado(novo_evento, lista);
                no_atual->pacoteEnviado = true;
                vizinho_atual = vizinho_atual->prox;
            }
        }
    }
}
