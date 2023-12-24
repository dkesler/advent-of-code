from sympy import *

'''
Create a system of 9 equations with 9 unknowns using the first 3 hailstones.
If it is possible to hit all hailstones in one throw as the problem states,
then any solution to the first 3 hailstones must hit all possible hailstones

x1-3 and vx1-3 are constants. all other values are variables.  Each equation represents
the time at which the (unknown) starting postion and velocity for each dimension will
intersect with the known hailstone.  The time must be the same for each hailstone.  
        x0 + vx0 * t1 == x1 + vx1 * t1
        x0 + vx0 * t2 == x2 + vx2 * t2
        x0 + vx0 * t3 == x3 + vx3 * t3
        y0 + vy0 * t1 == y1 + vy1 * t1
        y0 + vy0 * t2 == y2 + vy2 * t2
        y0 + vy0 * t3 == y3 + vy3 * t3
        z0 + vz0 * t1 == z1 + vz1 * t1
        z0 + vz0 * t2 == z2 + vz2 * t2
        z0 + vz0 * t3 == z3 + vz3 * t3

        test: (substituting in the values for the first 3 hailstones and simplifying variable names
        in a failed attempt to do this in wolfram alpha)
        a + b * t = 19 - 2 * t
        c + d * t = 13 + 1 * t
        e + f * t = 30 - 2 * t
        a + b * u = 18 - 1 * u
        c + d * u = 19 - 1 * u
        e + f * u = 22 - 2 * u
        a + b * v = 20 - 2 * v
        c + d * v = 25 - 2 * v
        e + f * v = 34 - 4 * v

        real
        a+b*t=257520024329236+21*t
        c+d*t=69140711609471+351*t
        e+f*t=263886787577054+72*t
        a+b*u=227164924449606+70*u
        c+d*u=333280170830371-28*u
        e+f*u=330954002548352-35*u
        a+b*v=269125649340143+35*v
        c+d*v=131766988959682-337*v
        e+f*v=261281801543906-281*v
'''

a, b, c, d, e, f, t, u, v = symbols('a, b, c, d, e, f, t, u, v')
eq1 = Eq(a+b*t, 257520024329236+21*t)
eq2 = Eq(c+d*t, 69140711609471+351*t)
eq3 = Eq(e+f*t, 263886787577054+72*t)
eq4 = Eq(a+b*u, 227164924449606+70*u)
eq5 = Eq(c+d*u, 333280170830371-28*u)
eq6 = Eq(e+f*u, 330954002548352-35*u)
eq7 = Eq(a+b*v, 269125649340143+35*v)
eq8 = Eq(c+d*v, 131766988959682-337*v)
eq9 = Eq(e+f*v, 261281801543906-281*v)

sol = solve([eq1, eq2, eq3, eq4, eq5, eq6, eq7, eq8, eq9], [a, b, c, d, e, f, t, u, v])

print(sol)


print(sol[0][0]+sol[0][2]+sol[0][4])
