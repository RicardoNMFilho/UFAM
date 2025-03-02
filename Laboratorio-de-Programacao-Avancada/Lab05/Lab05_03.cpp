#include <iostream>
#include "broken_passwords.h"

int main() {
    int tamanhoMaximo = 4;
    std::string caracteres = "0123456789";
    
    char *senhaCriptografada = "$6$LrSF5BAseToYYHJ0$SYY1avj8FRoRGpn.1kPXuZ6Xn5WTl2kL3"
                                "hxc3yMWdDUyz4c/Ac3Av3WO8Q9LciP8o4c9WaeLcgxIXWaHpJMFb.";

    BrokenPasswords(tamanhoMaximo, caracteres, senhaCriptografada);

    return 0;
}
