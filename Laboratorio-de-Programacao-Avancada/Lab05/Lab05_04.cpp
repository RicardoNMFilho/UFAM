#include <iostream>
#include "broken_passwords.h"

int main() {
    int tamanhoMaximo = 3;
    std::string caracteres = "abcdefghijklmnopqrstuvwxyz";
    
    char *senhaCriptografada = "$6$rMAk28dVkWjpYoA3$SkWbPYqEB8O/10ryvvjm1qN9BOrkeBOXp"
"JScVSGDL5L88OIs0UCBuP.pnd9TQ6SBx60dLKwR9WAzfnLtvjGvj.";

    BrokenPasswords(tamanhoMaximo, caracteres, senhaCriptografada);

    return 0;
}
