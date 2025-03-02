def rfinal(nfs):
    def aprovados(x):
        return [x[y] for y in range(0,len(x)) if x[y][1] >= 5 and x[y][2] >= 75]
    def repn(x):
        return [x[y] for y in range(0,len(x)) if x[y][1] < 5 and x[y][2] >= 75]
    def repp(x):
        return [x[y] for y in range(0,len(x)) if x[y][1] >= 5 and x[y][2] < 75]
    def reppn(x):
        return [x[y] for y in range(0,len(x)) if x[y][1] < 5 and x[y][2] < 75]

    return (aprovados(nfs), repn(nfs), repp(nfs), reppn(nfs))
