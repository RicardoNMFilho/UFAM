"""
    Introdução a Computação
    03/03/2022
    Ricardo Nogueira Miranda Filho
    Atividade Teste
"""

def dig(a,b):
    
    def rv(z):
        rvlist = []
        for i in range(len(z) - 1,-1,-1):
            rvlist.append(z[i])
        return rvlist
    
    def tr(z):
        for i in range(5 - len(z)):
            z.append("0")
        return z
    
    x = rv(tr(rv(list(str(a)))))
    y = rv(tr(rv(list(str(b)))))
    eq = []
    
    for i in range(0,5):
        if x[i] == y[i]:
            eq.append(x[i])

    return len(eq)
            
    
