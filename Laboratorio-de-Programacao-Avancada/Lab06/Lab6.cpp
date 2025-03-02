#include <iostream>
#include <cstdio>
#include <cstdlib>
#include <cstring>

// Defina a estrutura para o cabeçalho do arquivo ZIP como mostrado anteriormente
#pragma pack(push, 1)
struct zip_file_hdr {
    int signature;
    short version;
    short flags;
    short compression;
    short mod_time;
    short mod_date;
    int crc;
    int compressed_size;
    int uncompressed_size;
    short name_length;
    short extra_field_length;
};
#pragma pack(pop)

int main(int argc, char *argv[]) {

    int num = 1;

    if (argc != 2) {
        std::cout << "Uso: " << argv[0] << " <arquivo_zip>" << std::endl;
        return 1;
    }

    // Abra o arquivo ZIP para leitura binária
    FILE *zip_file = fopen(argv[1], "rb");
    if (!zip_file) {
        std::cerr << "Erro ao abrir o arquivo ZIP" << std::endl;
        return 1;
    }

    while (1) {
        // Aloque memória para a estrutura do cabeçalho
        struct zip_file_hdr *file_hdr = (struct zip_file_hdr *)malloc(sizeof(struct zip_file_hdr));

        // Leia o cabeçalho do arquivo ZIP
        size_t bytes_read = fread(file_hdr, sizeof(struct zip_file_hdr), 1, zip_file);

        // Verifique se chegou ao final do arquivo ZIP
        if (bytes_read != 1) {
            free(file_hdr); // Libere a memória da estrutura
            break;
        }

        // Verifique a assinatura do cabeçalho
        if (file_hdr->signature != 0x04034b50) {
            free(file_hdr); // Libere a memória da estrutura
            break;
        }

        // Aloque memória para o nome do arquivo
        char *file_name = (char *)malloc(file_hdr->name_length + 1);

        // Leia o nome do arquivo do arquivo ZIP
        fread(file_name, file_hdr->name_length, 1, zip_file);
        file_name[file_hdr->name_length] = '\0';

        // Imprima as informações do arquivo
        std::cout << "Arquivo " << num << " ..." << std::endl;
        std::cout << "--> Nome do Arquivo: " << file_name << std::endl;
        std::cout << "--> Tamanho Compactado: " << file_hdr->compressed_size << std::endl;
        std::cout << "--> Tamanho Descompactado: " << file_hdr->uncompressed_size << std::endl;

        // Libere a memória alocada para o nome do arquivo
        free(file_name);

        // Faça o ponteiro do arquivo ZIP apontar para o próximo arquivo dentro dele
        fseek(zip_file, file_hdr->extra_field_length + file_hdr->compressed_size, SEEK_CUR);

        num++;

        // Libere a memória da estrutura do cabeçalho
        free(file_hdr);
    }

    // Feche o arquivo ZIP
    fclose(zip_file);

    return 0;
}
