#include <cstdio>
#include <cstring>

int main(int argc, char const *argv[]) {
    FILE *file;
    file = fopen(argv[1], "rb");
    if(file == NULL) return 1;

    unsigned char startCodePrefix[3];
    unsigned char tipoStream, byte1, byte2, byte3, byte4;
    unsigned int largura, altura, frameRate;
    int valor;

    float listaFR[] = {23.976, 24.000, 25.000, 29.970, 30.000, 50.000, 59.940, 60.000};
    char listaTF[] = {'I','P','B'};

    while(fread(&startCodePrefix, sizeof(unsigned char), 3, file) == 3) {

        if(startCodePrefix[0] == 0x00 && startCodePrefix[1] == 0x00 && startCodePrefix[2] == 0x01) {
            fread(&tipoStream, sizeof(unsigned char), 1, file);
            switch(tipoStream) {
            case 0xBA:
                printf("--> Código: ba -- Pack\n");
                break;

            case 0xBB:
                printf("--> Código: bb -- System\n");
                break;

            case 0xB3:
                byte1 = fgetc(file);
                byte2 = fgetc(file);
                byte3 = fgetc(file);
                byte4 = fgetc(file);
                largura = byte1*16 + (byte2 >> 4);
                altura = (byte2 & 0x0F) * 256+byte3;
                frameRate = byte4 & 0x0F;
                printf("--> Código: b3 -- Sequence Header -- Width = %d, Height = %d -- Frame rate = %.3ffps\n", largura, altura, listaFR[frameRate-1]);
                break;

            case 0xB8:
                printf("--> Código: b8 -- Group of Pictures\n");
                break;

            case 0x00:
                byte1 = fgetc(file);
                byte2 = fgetc(file);
                valor = (byte2 >> 3) & 0x07;
                printf("--> Código: 00 -- Picture -- Tipo = %c\n", listaTF[valor-1]);
                break;

            case 0xC0 ... 0xDF:
                printf("--> Código: %.2x -- Packet Video\n", tipoStream);
                break;

            case 0xE0 ... 0xEF:
                printf("--> Código: %.2x -- Packet Audio\n", tipoStream);
                break;

            default:
                if(tipoStream >= 0x01 && tipoStream <= 0xAF) {
                    printf("--> Código: %.2x -- Slice\n", tipoStream);
                } else {
                    printf("--> Código: %.2x -- Tipo de stream não implementado\n", tipoStream);
                }
            }

        } else {
            fseek(file, -2, SEEK_CUR);
        }
    }

    fclose(file);
    return 0;
}
