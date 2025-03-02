#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>

typedef struct evento_t* evento_t;
typedef struct lista_eventos_t lista_eventos_t;

struct evento_t {
    double tempo;
    int alvo;
    int tipo;
};

struct lista_eventos_t {
    evento_t info;
    lista_eventos_t* prox;
};

bool lista_eventos_adicionar_inicio(evento_t evento, lista_eventos_t** lista) {
    lista_eventos_t* item_novo = malloc(sizeof(lista_eventos_t));
    if (item_novo == NULL) return false;
    item_novo->info = evento;
    item_novo->prox = *lista;
    *lista = item_novo;
    return true;
}

bool lista_eventos_adicionar_fim(evento_t evento, lista_eventos_t** lista) {
    lista_eventos_t* item_novo = malloc(sizeof(lista_eventos_t));
    if (item_novo == NULL) return false;
    item_novo->info = evento;
    item_novo->prox = NULL;

    if (*lista == NULL) {
        *lista = item_novo;
    } else {
        lista_eventos_t* ultimo = *lista;
        while (ultimo->prox != NULL) {
            ultimo = ultimo->prox;
        }
        ultimo->prox = item_novo;
    }

    return true;
}

bool lista_eventos_adicionar_ordenado(evento_t evento, lista_eventos_t** lista) {
    lista_eventos_t* item_novo = malloc(sizeof(lista_eventos_t));
    if (item_novo == NULL) return false;
    item_novo->info = evento;

    if (*lista == NULL || evento->tempo < (*lista)->info->tempo) {
        item_novo->prox = *lista;
        *lista = item_novo;
    } else {
        lista_eventos_t* atual = *lista;
        while (atual->prox != NULL && atual->prox->info->tempo <= evento->tempo) {
            atual = atual->prox;
        }
        item_novo->prox = atual->prox;
        atual->prox = item_novo;
    }

    return true;
}

void lista_eventos_listar(lista_eventos_t* lista) {
    printf("Eventos da lista:\n");
    while (lista != NULL) {
        printf("Tempo: %.2f, Alvo: %d, Tipo: %d\n", lista->info->tempo, lista->info->alvo, lista->info->tipo);
        lista = lista->prox;
    }
}

int main(int argc, char const *argv[]) {
    double tempo;
    int alvo;
    int tipo;

    lista_eventos_t* lista_eventos = NULL;

    FILE *fp = fopen(argv[1], "r");

    /*

    Testes realizados antes de fazer a leitura do arquivo. O objetivo foi verificar o funcionamento das funções.

    evento_t event1 = malloc(sizeof(struct evento_t));
    event1->tempo = 6.0;
    event1->alvo = 1;
    event1->tipo = 0;

    evento_t event2 = malloc(sizeof(struct evento_t));
    event2->tempo = 8.0;
    event2->alvo = 2;
    event2->tipo = 1;

    evento_t event3 = malloc(sizeof(struct evento_t));
    event3->tempo = 7.0;
    event3->alvo = 3;
    event3->tipo = 2;

    lista_eventos_adicionar_fim(event1, &lista_eventos);
    lista_eventos_adicionar_fim(event2, &lista_eventos);
    lista_eventos_adicionar_fim(event3, &lista_eventos);

    lista_eventos_listar(lista_eventos);

    */

    while(!feof(fp)){
        fscanf(fp,"%lf\t%d\t%d\n",&tempo,&alvo,&tipo);
        evento_t evento = malloc(sizeof(evento_t));
        evento->tempo = tempo;
        evento->alvo = alvo;
        evento->tipo = tipo;
        lista_eventos_adicionar_ordenado(evento, &lista_eventos); // Inserindo ordenado, pode mudar a função aqui
    }

    lista_eventos_listar(lista_eventos);

    while (lista_eventos != NULL) {
        lista_eventos_t* temp = lista_eventos;
        lista_eventos = lista_eventos->prox;
        free(temp->info);
        free(temp);
    }

    return 0;
}
