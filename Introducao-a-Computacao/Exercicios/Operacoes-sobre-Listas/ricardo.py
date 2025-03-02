def take(k,xs):
    return [xs[y] for y in range(0,len(xs)) if y < k]

def drop(k,xs):
    return [xs[y] for y in range(0,len(xs)) if y >= k]

def head(xs):
    return xs[0]

def tail(xs):
    return [xs[y] for y in range(1,len(xs))]

def last(xs):
    return xs[-1]

def init(xs):
    return [xs[y] for y in range(0,len(xs)) if y != len(xs) - 1]
