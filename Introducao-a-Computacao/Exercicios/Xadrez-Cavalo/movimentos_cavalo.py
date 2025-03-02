"""
    Atividade 04 (Movimento do Cavalo) - Introdução a Ciência
    Aluno: Ricardo Nogueira Miranda Filho
    Data: 01/02/2022
"""

def possivel(x1,y1,x2,y2):
    if x1 > 8 or x1 < 1 or y1 > 8 or y1 < 1: return False
    elif x2 - 1 == x1 and y2 - 2 == y1: return (x1 < 8 and y1 < 7)
    elif x2 + 1 == x1 and y2 - 2 == y1: return (x1 > 1 and y1 < 7)
    elif x2 - 1 == x1 and y2 + 2 == y1: return  (x1 < 8 and y1 > 2)
    elif x2 + 1 == x1 and y2 + 2 == y1: return  (x1 > 1 and y1 > 2)
    elif x2 - 2 == x1 and y2 - 1 == y1: return (x1 < 7 and y1 < 8)
    elif x2 + 2 == x1 and y2 - 1 == y1: return (x1 > 2 and y1 < 8)
    elif x2 - 2 == x1 and y2 + 1 == y1: return (x1 < 7 and y1 > 1)
    elif x2 + 2 == x1 and y2 + 1 == y1: return (x1 > 2 and y1 > 1)
    else: return False
