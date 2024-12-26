package ru.netology.graphics.image;

public class ColorSchema implements TextColorSchema {
    private final char[] colorsWindows = {'�', '@', '#', '©', '*', '+', '°', '·'};
    private final char[] colorsMac = {'▇', '●', '◉', '◍', '◎', '○', '☉', '◌', '-'};
    int indexConvertCoefficientMac = 29;
    int indexConvertCoefficientWindows = 32;

    @Override
    public char convert(int color) {
        return colorsWindows[color / indexConvertCoefficientWindows];
    }
}
