"""
    Atividade 02 - Introdução a Ciência
    Aluno: Ricardo Nogueira Miranda Filho
    Data: 01/02/2022
"""

from math import *

def xp(n):
    return (n%3 == 0 and n%5 == 0) and (n>=0 and n<=100)

def amarelo(x1,x2,y1,y2):
    return (((y1 - y2)**2) / 2) - (pi * (((y1 - y2) / sqrt(2)) / 2)**2)
