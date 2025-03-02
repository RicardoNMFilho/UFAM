"""
    RICARDO NOGUEIRA MIRANDA FILHO
    INTRODUÇÃO A COMPUTAÇÃO
    DEFINIÇÃO RECURSIVA
"""

def head(xs): return [xs[e] for e in range(len(xs)) if e == 0]
def tail(xs): return [xs[e] for e in range(len(xs)) if e != 0]

def norep(xs):
    if len(tail(xs)) == 0: return head(xs)
    elif head(xs)[0] in tail(xs):
        return norep(tail(xs))
    else: return head(xs) + norep(tail(xs))
    
