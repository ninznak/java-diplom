package ru.netology.graphics.image;

public class ColorSchema implements TextColorSchema{
    private final char[] colors = {'▇', '●', '◉', '◍', '◎', '○', '☉', '◌', '-'};

    @Override
    public char convert(int color) {
        int indexConvertCoefficient = 29;
        return colors[color / indexConvertCoefficient];
    }
}
