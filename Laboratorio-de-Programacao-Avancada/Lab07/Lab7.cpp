#include <iostream>
#include <fstream>
#include <cstring>
#include <arpa/inet.h>

#pragma pack(1)

struct Chunk {
    int length;
    char type[4];
};

struct ChunkIHDR {
    int width;
    int height;
    char depth;
    char color;
    char compression;
    char filter;
    char interlace;
};

int main(int argc, char *argv[]) {
    int i = 1; // contador
    std::ifstream png(argv[1], std::ios::binary);
    Chunk png_chunk;
    ChunkIHDR png_chunk_IHDR;

    if (!png) {
        std::cerr << "Erro ao abrir o arquivo PNG." << std::endl;
        return 1;
    }

    png.seekg(8, std::ios::cur); // pula 8 bytes da posição atual do ponteiro

    while (png.read(reinterpret_cast<char*>(&png_chunk), sizeof(Chunk))) {
        std::cout << "Lendo chunk " << i << ":\n";
        std::cout << " --> Tamanho: " << ntohl(png_chunk.length) << "\n";
        std::cout << " --> Tipo: " << png_chunk.type[0] << png_chunk.type[1] << png_chunk.type[2] << png_chunk.type[3] << "\n";

        if (std::memcmp(png_chunk.type, "IHDR", 4) == 0) {
            png.read(reinterpret_cast<char*>(&png_chunk_IHDR), sizeof(ChunkIHDR));
            std::cout << "     --> Largura: " << ntohl(png_chunk_IHDR.width) << "\n";
            std::cout << "     --> Altura: " << ntohl(png_chunk_IHDR.height) << "\n";
            png.seekg(4, std::ios::cur); // pula 4 bytes da posição atual do ponteiro (CRC)
        } else if (std::memcmp(png_chunk.type, "IEND", 4) == 0) { // Tipo fim da imagem
            break;
        } else {
            png.seekg(ntohl(png_chunk.length) + 4, std::ios::cur); // pula o campo data e CRC a partir da posição atual do ponteiro
        }
        i++;
    }

    png.close();
    return 0;
}
