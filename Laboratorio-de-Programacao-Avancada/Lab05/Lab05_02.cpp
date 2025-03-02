#include <iostream>
#include "broken_passwords.h"

int main() {
    int tamanhoMaximo = 3;
    std::string caracteres = "abcdefghijklmnopqrstuvwxyz";
    
    char *senhaCriptografada = "$6$7yCakIXevncmT6se$m002Lkf2BK6Qgyhc.c/PxMTvcmBAXYtIo"
                                "kUKvWwvB5H5zCt5HhhPOlV8ygebOcSsgNqG74whVwN.8UF9WaGfs/";

    BrokenPasswords(tamanhoMaximo, caracteres, senhaCriptografada);

    return 0;
}
