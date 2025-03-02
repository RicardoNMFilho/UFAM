"""
    RICARDO NOGUEIRA MIRANDA FILHO
    INTRODUÇÃO A COMPUTAÇÃO
    PLACAS MERCOSUL
"""

# PLACAS MERCOSUL TEM LETRAS MAIUSCULAS

def placa(x):
    def moto_carro(li):
        if "".join(li) == 'LLLNLNN': return 'carro'
        elif "".join(li) == 'LLLNNLN': return 'moto'
        else: return 'nada'
    def ordem_placa(x):
        return ['L' if 64 < ord(alg) < 91 else 'N' if 47 < ord(alg) < 58 else 'X' for alg in x]

    return moto_carro(ordem_placa(x))
