package com.example.weatherver2;

public class Sinoptic {
    int[] deg;
    int[] clouds;
    int[] rain;

    public Sinoptic(int[] deg, int[] clouds) {
        this.deg = deg;
        this.clouds = clouds;
    }

    public int getDegId(int id) {
        return deg[id];
    }

    public void setDeg(int[] deg) {
        this.deg = deg;
    }

    public int[] getClouds() {
        return clouds;
    }

    public int getCloudsId(int id) {
        return clouds[id];
    }

    public int[] getRain() {
        return rain;
    }

    public void setRain(int[] rain) {
        this.rain = rain;
    }
}
