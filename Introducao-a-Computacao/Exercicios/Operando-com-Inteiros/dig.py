"""
    Introdução a Computação
    03/03/2022
    Ricardo Nogueira Miranda Filho
    Atividade Principal
"""


def dig(a,b):
    def um(x,y):
        if x // 10000 == y // 10000: return 1
        else: return 0
    def dois(x,y):
        if (x - (x // 10000) * 10000) // 1000 == (y - (y // 10000) * 10000) // 1000: return 1
        else: return 0
    def tres(x,y):
        if ((x - (x // 10000) * 10000) - ((x - (x // 10000) * 10000) // 1000) * 1000) // 100 == ((y - (y // 10000) * 10000) - ((y - (y // 10000) * 10000) // 1000) * 1000) // 100: return 1
        else: return 0
    def quatro(x,y):
        if (((x - (x // 10000) * 10000) - ((x - (x // 10000) * 10000) // 1000) * 1000)-(((x - (x // 10000) * 10000) - ((x - (x // 10000) * 10000) // 1000) * 1000)//100)*100) // 10 == (((y - (y // 10000) * 10000) - ((y - (y // 10000) * 10000) // 1000) * 1000)-(((y - (y // 10000) * 10000) - ((y - (y // 10000) * 10000) // 1000) * 1000)//100)*100) // 10: return 1
        else: return 0
    def cinco(x,y):
        if ((((x - (x // 10000) * 10000) - ((x - (x // 10000) * 10000) // 1000) * 1000)-(((x - (x // 10000) * 10000) - ((x - (x // 10000) * 10000) // 1000) * 1000)//100)*100)-((((x - (x // 10000) * 10000) - ((x - (x // 10000) * 10000) // 1000) * 1000)-(((x - (x // 10000) * 10000) - ((x - (x // 10000) * 10000) // 1000) * 1000)//100)*100)//10)*10) // 1 == ((((y - (y // 10000) * 10000) - ((y - (y // 10000) * 10000) // 1000) * 1000)-(((y - (y // 10000) * 10000) - ((y - (y // 10000) * 10000) // 1000) * 1000)//100)*100)-((((y - (y // 10000) * 10000) - ((y - (y // 10000) * 10000) // 1000) * 1000)-(((y - (y // 10000) * 10000) - ((y - (y // 10000) * 10000) // 1000) * 1000)//100)*100)//10)*10) // 1: return 1
        else: return 0   
    
    return um(a,b) + dois(a,b) + tres(a,b) + quatro(a,b) + cinco(a,b)
