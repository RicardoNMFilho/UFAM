#include <iostream>
#include <stdint.h>
#include <arpa/inet.h>

using namespace std;

typedef struct {
  uint8_t daddr[6]; // Endereco MAC de destino
  uint8_t saddr[6]; // Endereco MAC de origem (source)
  uint16_t protocol; // Protocolo da próxima camada (IP!)
} ethernet_hdr_t;

typedef struct {
  uint8_t hdr_len:4, // Tamanho do Cabeçalho
  version:4; // Versão do IP
  uint8_t tos; // Tipo de serviço
  uint16_t tot_len; // Tamanho total do IP
  uint16_t id; // Id do pacote
  uint16_t frag_off; // Fragmentado?
  uint8_t ttl; // Tempo de vida
  uint8_t protocol; // Protocolo próxima camada (TCP!)
  uint16_t check; // Checksum
  uint8_t saddr[4]; // Endereço IP de origem
  uint8_t daddr[4]; // Endereço IP de destino
} ip_hdr_t;

typedef struct {
  uint16_t sport; // Porta TCP de origem
  uint16_t dport; // Porta TCP de destino
  uint32_t seq; // Sequência
  uint32_t ack_seq; // Acknowledgement
  uint8_t reservado:4, // Não usado
  hdr_len:4; // Tamanho do cabecalho
  uint8_t flags; // Flags do TCP
  uint16_t window; // Tamanho da janela
  uint16_t check; // Checksum
  uint16_t urg_ptr; // Urgente
} tcp_hdr_t;

int main( int argc, char** argv ){

    ethernet_hdr_t ether;
    ip_hdr_t ip;
    tcp_hdr_t tcp;
  
    FILE *file;
    file = fopen(argv[1], "rb");
    if(file == NULL) return 1;

    fread(&ether, sizeof(ethernet_hdr_t), 1, file);

    cout << "Lendo Ethernet.." << endl;
    cout << "  --> MAC de Origem: ";
    for(int i = 0; i < 6; i++) {
        printf("%02x:", ether.saddr[i]);
    }
    cout << "\b " << endl; // Remove the last colon

    cout << "  --> MAC de Destino: ";
    for(int i = 0; i < 6; i++) {
        printf("%02x:", ether.daddr[i]);
    }
    cout << "\b " << endl; // Remove the last colon

    fread(&ip, sizeof(ip_hdr_t), 1, file);

    cout << "Lendo IP.." << endl;
    cout << "  --> Versão IP: " << (int)ip.version << endl;
    cout << "  --> Tamanho do cabeçalho: " << (ip.hdr_len * 4) << " bytes" << endl;
    cout << "  --> Tamanho do pacote: " << ntohs(ip.tot_len) << " bytes" << endl;
    cout << "  --> Endereço IP de Origem: " << (int)ip.saddr[0] << "." << (int)ip.saddr[1] << "." << (int)ip.saddr[2] << "." << (int)ip.saddr[3] << endl;
    cout << "  --> Endereço IP de Destino: " << (int)ip.daddr[0] << "." << (int)ip.daddr[1] << "." << (int)ip.daddr[2] << "." << (int)ip.daddr[3] << endl;

    fread(&tcp, sizeof(tcp_hdr_t), 1, file);

    cout << "Lendo TCP .." << endl;
    cout << "  --> Porta de Origem: " << ntohs(tcp.sport) << endl;
    cout << "  --> Porta de Destino: " << ntohs(tcp.dport) << endl;
    cout << "  --> Tamanho do cabeçalho: " << (tcp.hdr_len * 4) << " bytes" << endl;

    // Operações envolvendo o http

    int tam_dados = ntohs(ip.tot_len) - (ip.hdr_len * 4) - (tcp.hdr_len * 4);
    cout << "Lendo Dados (HTTP) .." << endl;
    cout << "  --> Tamanho dos dados: " << tam_dados << " bytes" << endl;
    cout << "  --> Dados:" << endl;
    for(int i = 0; i < tam_dados; i++) {
        char c = fgetc(file);
        cout << c;
    }
    cout << endl;

    fclose(file);
    return 0;
}