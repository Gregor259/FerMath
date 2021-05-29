package com.example.linalsolve;

import com.example.linalsolve.Uravn;

public class Solver {
    private String ish;
    private double a0, a1, a2, a3, a4;
    private double as, ac, at;

    public Solver(String s) {
        ish = s;
        a0 = 0;
        a1 = 0;
        a2 = 0;
        a3 = 0;
        a4 = 0;
    }

    public void Parse() {
        int i;
        String s = ish;
        i = s.indexOf("x^4");
        if (i != -1) {
            int j = i;
            while (true) {
                if (s.charAt(j) == '+') break;
                if (s.charAt(j) == '-') break;
                j--;
                if (j == -1) {
                    break;
                }
            }
            String a = s.substring(j + 1, i);
            if (j + 1 == i) a4 = 1;
            else a4 = Double.parseDouble(a);
            if (j != -1) {
                if (s.charAt(j) == '-') a4 = a4 * (-1);
                s = s.replace(s.charAt(j) + a + "x^4", "");
            }
            else s = s.replace(a + "x^4", "");
        }
        i = s.indexOf("x^3");
        if (i != -1) {
            int j = i;
            while (true) {
                if (s.charAt(j) == '+') break;
                if (s.charAt(j) == '-') break;
                j--;
                if (j == -1) {
                    break;
                }
            }
            String a = s.substring(j + 1, i);
            if (j + 1 == i) a3 = 1;
            else a3 = Double.parseDouble(a);
            if (j != -1) {
                if (s.charAt(j) == '-') a3 = a3 * (-1);
                s = s.replace(s.charAt(j) + a + "x^3", "");
            }
            else s = s.replace(a + "x^3", "");
        }
        i = s.indexOf("x^2");
        if (i != -1) {
            int j = i;
            char c = s.charAt(0);
            while (true) {
                if (s.charAt(j) == '+') break;
                if (s.charAt(j) == '-') break;
                j--;
                if (j == -1) {
                    break;
                }
            }
            String a = s.substring(j + 1, i);
            if (j + 1 == i) a2 = 1;
            else a2 = Double.parseDouble(a);
            if (j != -1) {
                if (s.charAt(j) == '-') a2 = a2 * (-1);
                s = s.replace(s.charAt(j) + a + "x^2", "");
            }
            else
                s = s.replace(a + "x^2", "");
        }
        i = s.indexOf("x");
        if (i != -1) {
            int j = i;
            while (true) {
                if (s.charAt(j) == '+') break;
                if (s.charAt(j) == '-') break;
                j--;
                if (j == -1) {
                    break;
                }
            }
            String a = s.substring(j + 1, i);
            if (j + 1 == i) a1 = 1;
            else a1 = Double.parseDouble(a);
            if (j != -1) {
                if (s.charAt(j) == '-') a1 = a1 * (-1);
                s = s.replace(s.charAt(j) + a + "x", "");
            }
            else s = s.replace(a + "x", "");
        }
        i = 0;
        if (s.length() != 0) {
            if (s.charAt(0) == '-' | s.charAt(0) == '+') i = 1;
            String a = s.substring(i);
            a0 = Double.parseDouble(a);
            if (s.charAt(0) == '-') a0 = a0 * (-1);
        }
    }

    public Uravn Solve() {
        if (a0 == 0) {
            Uravn res = new Uravn();
            a0 = a1;
            a1 = a2;
            a2 = a3;
            a3 = a4;
            a4 = 0;
            res = Solve();
            for(int i = res.roots; i > 0; i--) res.x[i] = res.x[i - 1];
            res.IsValid = true;
            res.roots++;
            res.x[0] = 0;
            return res;
        }
        if (a4 == 0 & a3 == 0 & a2 == 0) {
            return SolveFirstLevel(a1, a0);
        }
        if (a4 == 0 & a3 == 0) {
            return SolveSecondLevel(a2, a1, a0);
        }
        if (a4 == 0) {
            return SolveThirdLevel(a3, a2, a1, a0);
        }
        if (a1 == 0 & a3 == 0) {
            Uravn res2 = new Uravn();
            res2 = SolveSecondLevel(a4, a2, a0);
            Uravn res = new Uravn();
            res.IsValid = res2.IsValid;
            for (int i = 0; i < 2; i++)
                if (res2.x[i] >= 0) {
                    res.x[res.roots] = Math.sqrt(res2.x[i]);
                    res.x[res.roots + 1] = -Math.sqrt(res2.x[i]);
                    res.roots += 2;
                }
            return res;
        }
        return SolveFourthLevel(a4, a3, a2, a1, a0);
    }

    public Uravn SolveFirstLevel(double b1, double b0) {
        Uravn res = new Uravn();
        res.IsValid = true;
        res.roots = 1;
        res.x[0] = b0 / b1 * (-1);
        return res;
    }
    public Uravn SolveSecondLevel(double b2, double b1, double b0) {
        Uravn res = new Uravn();
        double d = b1*b1;
        d -= 4 * b2 * b0;
        if (d >= 0) {
            res.roots = 2;
            res.IsValid = true;
            res.x[0] = (-1) * b1;
            res.x[1] = res.x[0];
            res.x[0] -= Math.sqrt(d);
            res.x[1] += Math.sqrt(d);
            res.x[0] = res.x[0] / 2 / b2;
            res.x[1] = res.x[1] / 2 / b2;
        }
        return res;
    }

    private Boolean CheckThird(double b2, double b1, double b0, int x) {
        double y = x * x * x;
        y += b2 * x * x;
        y += b1 * x;
        y += b0;
        return (y == 0);
    }

    public Uravn SolveThirdLevel(double b3, double b2, double b1, double b0) {
        Uravn res = new Uravn();
        double[] result;
        if (b0 != 1) {
            b3 = b3 / b0;
            b2 = b2 / b0;
            b1 = b1 / b0;
        }
        double p = (3 * b3 * b1 - b2 * b2) / 3 / b3 / b3;
        double q = (2 * b2 * b2 * b2 - 9 * b3 * b2 * b1 + 27 * b3 * b3) / 27 / b3 / b3/ b3;
        double D = p * p * p / 27 + q * q / 4;
        if (Double.compare(D, 0) >= 0) {
            if (Double.compare(D, 0) == 0) {
                double r = Math.cbrt(q / -2);
                res.roots = 2;
                res.IsValid = true;
                res.x[0] = 2 * r - b2 / 3 / b3;
                res.x[1] = -r - b2 / 3 / b3;
            } else {
                double r = Math.cbrt(-q / 2 + Math.sqrt(D));
                double s = Math.cbrt(-q / 2 - Math.sqrt(D));
                res.roots = 1;
                res.IsValid = true;
                res.x[0] = r + s - b2 / 3 / b3;
            }
        } else {
            double ac = q / 2 / Math.sqrt(-p * p * p / 27);
            double ang = Math.acos(ac) / 3;
            res.roots = 3;
            res.IsValid = true;
            for (int k = 0; k <= 2; k++) {
                double theta = ang;
                theta += 2 * Math.PI * k / 3;
                res.x[k] = Math.cos(theta) * Math.sqrt(-4 * p / 3);
                res.x[k] = res.x[k] + b2 / 3 / b3;
            }
        }
        return res;
    }

    public Uravn SolveFourthLevel(double b4, double b3, double b2, double b1, double b0) {
        Uravn res = new Uravn();
        double A = b3 / b4;
        double B = b2 / b4;
        double C = b1 / b4;
        double D = b0 / b4;
        double s2 = B * (-1);
        double s1 = A * C;
        s1 -= 4 * D;
        double s0 = 4 * B * D;
        s0 -= A * A * D;
        s0 -= C * C;
        a4 = 0;
        a3 = 1;
        a2 = s2;
        a1 = s1;
        a0 = s0;
        Uravn res3 = Solve();
        if (!res3.IsValid) return res;
        double c2 = A * A / 4;
        c2 -= B;
        c2 += res3.x[0];
        double c1 = A * res3.x[0] / 2;
        c1 -= C;
        double c0 = res3.x[0] * res3.x[0] / 4;
        c0 -= D;
        s1 = A / 2;
        s1 += Math.sqrt(c2);
        s0 = res3.x[0] / 2;
        if (c2 != 0) s0 += c1 / 2 / Math.sqrt(c2);
        Uravn res2 = SolveSecondLevel(1, s1, s0);
        int yk = 0;
        if (res2.IsValid) {
            res.IsValid = true;
            res.roots = 2;
            res.x[0] = res2.x[0];
            res.x[1] = res2.x[1];
            yk = 2;
        }
        s1 = A / 2;
        s1 -= Math.sqrt(c2);
        s0 = res3.x[0] / 2;
        if (c2 != 0) s0 -= c1 / 2 / Math.sqrt(c2);
        res2 = SolveSecondLevel(1, s1, s0);
        if (res2.IsValid) {
            res.IsValid = true;
            res.roots += 2;
            res.x[yk] = res2.x[0];
            res.x[yk + 1] = res2.x[1];
        }
        return res;
    }
}
