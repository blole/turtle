package dev.blole.turtle;

public final record Tabletop(int minX, int minY, int maxX, int maxY) {

    public void report(String output) {
        System.out.println(output);
    }
}
