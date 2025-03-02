#include <iostream>
#include "broken_passwords.h"

int main() {
    int tamanhoMaximo = 5;
    std::string caracteres = "0123456789";
    
    char *senhaCriptografada = "$6$l2xE4w9twgjtnZBz$9YK9krslZFraLffy5VNiahAfT.xZNvB54"
"j91DMCMIoVFvj335ZKxb11qgVMn.KzU2GqVPPyS2FTBqPSciYq761";

    BrokenPasswords(tamanhoMaximo, caracteres, senhaCriptografada);

    return 0;
}
