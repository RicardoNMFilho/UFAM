#include <GL/glut.h>
#include <iostream>
#include <cstdlib>
#include <ctime>
#include <vector>

typedef struct Segmento {
    int initX, initY, endX, endY;
} Segmento;

int largura, altura, segmentos;
std::vector<Segmento> Segmentos;

void display(void) {
    srand(time(NULL)); 
    glClear(GL_COLOR_BUFFER_BIT);
    glColor3f(1.0, 0.0, 0.0);

    // Desenha os segmentos
    for (auto &seg : Segmentos) {
        glBegin(GL_LINES);
            glVertex2i(seg.initX, seg.initY);
            glVertex2i(seg.endX, seg.endY);
        glEnd();
    }

    glFlush();
}

bool seCruzam(Segmento a, Segmento b) {
    // Calcula os coeficientes da equação da reta para cada segmento
    float m1 = (float)(a.endY - a.initY) / (a.endX - a.initX);
    float m2 = (float)(b.endY - b.initY) / (b.endX - b.initX);
    float c1 = a.initY - m1 * a.initX;
    float c2 = b.initY - m2 * b.initX;

    // Se os segmentos têm a mesma inclinação, não se cruzam
    if (m1 == m2) {
        return false;
    }

    // Calcula o ponto de interseção
    float x = (c2 - c1) / (m1 - m2);
    float y = m1 * x + c1;

    // Verifica se o ponto de interseção está dentro de ambos os segmentos
    bool dentroA = (x >= std::min(a.initX, a.endX) && x <= std::max(a.initX, a.endX)) &&
                   (y >= std::min(a.initY, a.endY) && y <= std::max(a.initY, a.endY));
    bool dentroB = (x >= std::min(b.initX, b.endX) && x <= std::max(b.initX, b.endX)) &&
                   (y >= std::min(b.initY, b.endY) && y <= std::max(b.initY, b.endY));

    return dentroA && dentroB;
}

int main(int argc, char **argv) {
    if (argc != 4) {
        std::cerr << "Uso: " << argv[0] << " <largura> <altura> <segmentos>" << std::endl;
        return 1;
    }

    largura = atoi(argv[1]);
    altura = atoi(argv[2]);
    segmentos = atoi(argv[3]);

    srand(time(NULL));

    // Gera os segmentos aleatoriamente e os adiciona ao vetor
    while (Segmentos.size() < segmentos) {
        int x1 = rand() % largura;
        int y1 = rand() % altura;
        int x2 = rand() % largura;
        int y2 = rand() % altura;
        Segmento novoSegmento = {x1, y1, x2, y2};

        bool cruzamento = false;
        for (auto &seg : Segmentos) {
            if (seCruzam(novoSegmento, seg)) {
                cruzamento = true;
                break;
            }
        }

        if (!cruzamento) {
            Segmentos.push_back(novoSegmento);
        }
    }

    glutInit(&argc, argv);
    glutInitDisplayMode ( GLUT_SINGLE | GLUT_RGB | GLUT_DEPTH );
    glutInitWindowSize(largura, altura);
    glutCreateWindow ("Segmentos Aleatorios");
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    glOrtho(0.0, largura, 0.0, altura, -1.0, 1.0);
    glClearColor(1.0, 1.0, 1.0, 1.0);
    glutDisplayFunc(display);
    glutMainLoop();

    return 0;
}
