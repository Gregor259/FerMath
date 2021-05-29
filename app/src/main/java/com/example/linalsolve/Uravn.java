package com.example.linalsolve;

public class Uravn {
    public double[] x;
    public int roots;
    public Boolean IsValid;

    public Uravn() {
        this.IsValid = false;
        this.roots = 0;
        this.x = new double[4];

    }
}
