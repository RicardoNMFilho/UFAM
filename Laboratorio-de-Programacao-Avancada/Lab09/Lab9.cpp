#include <iostream>
#include <fstream>
#include <string>
#include <vector>

using namespace std;

int main(int argc, char* argv[]) {
    if (argc < 3) {
        cerr << "Uso: programa <arquivo_entrada> <tamanho_max_MB>" << endl;
        return 1;
    }

    ifstream arq_entrada(argv[1], ios::binary);
    if (!arq_entrada) {
        cerr << "Falha ao abrir o arquivo de entrada." << endl;
        return 2;
    }

    int cont = 1;
    string nome_arq_saida = "video_parte_" + to_string(cont) + ".mpg";
    ofstream arq_saida(nome_arq_saida, ios::binary);
    if (!arq_saida) {
        cerr << "Falha ao abrir o arquivo de saída." << endl;
        return 3;
    }

    const int tamanho_maximo = stoi(argv[2]) * 1024 * 1024;
    int tamanho_atual = 0;
    vector<char> buffer(1024);

    while (!arq_entrada.eof()) {
        arq_entrada.read(buffer.data(), buffer.size());
        streamsize bytes_lidos = arq_entrada.gcount();

        if (bytes_lidos == 0) break;

        if (tamanho_atual + bytes_lidos > tamanho_maximo) {
            arq_saida.close();
            cont++;
            nome_arq_saida = "video_parte_" + to_string(cont) + ".mpg";
            arq_saida.open(nome_arq_saida, ios::binary);

            if (!arq_saida) {
                cerr << "Falha ao abrir o arquivo de saída." << endl;
                arq_entrada.close();
                return 4;
            }

            tamanho_atual = 0;
        }

        arq_saida.write(buffer.data(), bytes_lidos);
        tamanho_atual += static_cast<int>(bytes_lidos);
    }

    arq_entrada.close();
    arq_saida.close();

    return 0;
}
