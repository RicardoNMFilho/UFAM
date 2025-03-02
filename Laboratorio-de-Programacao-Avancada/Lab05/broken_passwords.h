#ifndef BROKEN_PASSWORDS_H
#define BROKEN_PASSWORDS_H

#include <iostream>
#include <string>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <crypt.h>

void BrokenPasswords(int tamanhoMaximo, const std::string& caracteres, const char* senhaCriptografada) {
    int totalCaracteres = caracteres.size();
    int numCaracteres = totalCaracteres; // Evitar chamadas frequentes de caracteres.size()

    for (int size = 1; size <= tamanhoMaximo; ++size) {
        std::string pwd(size, caracteres[0]);
        
        while (true) {
            char *senhaTesteCriptografada = crypt(pwd.c_str(), senhaCriptografada);
            if (strcmp(senhaTesteCriptografada, senhaCriptografada) == 0) {
                std::cout << pwd << " --> sim! Senha encontrada!" << std::endl;
                return;
            }
            
            std::cout << pwd << " --> não" << std::endl;

            // Incrementar os caracteres da senha gerada da direita para a esquerda
            int pos = size - 1;
            while (pos >= 0 && pwd[pos] == caracteres[numCaracteres - 1]) {
                pwd[pos] = caracteres[0];
                --pos;
            }

            // Se todos os caracteres atingirem o último, adicionar mais um caractere à esquerda
            if (pos < 0) {
                pwd = caracteres[0] + pwd;
                ++size;
            } else {
                pwd[pos] = caracteres[caracteres.find(pwd[pos]) + 1];
            }
        }
    }
    
    std::cout << "Senha não encontrada." << std::endl;
}


#endif // BROKEN_PASSWORDS_H
