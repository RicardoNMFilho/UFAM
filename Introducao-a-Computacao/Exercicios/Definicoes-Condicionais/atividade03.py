"""
    Atividade 03 - Introdução a Computação
    Aluno: Ricardo Nogueira Miranda Filho
    Data: 11/02/2022
"""


def conta(c):
    if c <= 5 and c >= 0 : return 50
    elif c > 5 and c <= 10 : return 50 + (c - 5) * 10
    elif c > 10 : return c * 5
    else : return False
