"""
    Introdução a Computação - Questões de prova
    Ricardo Nogueira Miranda Filho
    18/02/2022
"""

def orx(a,b):
    # Caso A == B, as opções são False-False, True-True. Caso contrário, False-True, True-False,
    if a == b: return False
    else: return True

def pag(a,b,c):
    # As fómulas de PA e PG foram simplificadas.
    def pa(x,y,z): return (x == y + z) or (y == z + x) or (z == x + y)
    def pg(x,y,z): return (x == y * z) or (y == z * x) or (z == x * y)
    return pg(a,b,c) or pa(a,b,c)

def pgp(vl,i):
    # Trabalhar com idades inteiras.
    if i >= 60: return (vl * 0.6)
    elif i <= 10 and i >= 2: return (vl * 0.5)
    elif i < 2: return (vl * 0.1)
    else: return vl
