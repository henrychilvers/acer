package com.chilvers.henry.acer;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * ASCII Art Generator in Java.
 * Prints a given text as an ASCII text art on the console.
 * This code is licensed under - CC Attribution CC BY 4.0.
 * @author www.quickprogrammingtips.com
 *
 */
public class ASCIIArtGenerator {
    private static final String DEFAULT_ART_SYMBOL = "*";

    /**
     * Prints ASCII art for the specified text. For size, you can use predefined sizes or a custom size.
     * Usage - printTextArt("Hi",30,ASCIIArtFont.ART_FONT_SERIF,"@");
     * @param artText
     * @param textHeight - Use a predefined size or a custom type
     * @param fontType - Use one of the available fonts
     * @param artSymbol - Specify the character for printing the ascii art
     */
    public void printTextArt(String artText, int textHeight, ASCIIArtFont fontType, String artSymbol) {
        var fontName = fontType.getValue();
        var imageWidth = findImageWidth(textHeight, artText, fontName);

        var image = new BufferedImage(imageWidth, textHeight, BufferedImage.TYPE_INT_RGB);
        var g = image.getGraphics();
        var font = new Font(fontName, Font.BOLD, textHeight);
        g.setFont(font);

        var graphics = (Graphics2D) g;
        graphics.drawString(artText, 0, getBaselinePosition(g, font));

        for (var y = 0; y < textHeight; y++) {
            var sb = new StringBuilder();

            for (var x = 0; x < imageWidth; x++) {
                sb.append(image.getRGB(x, y) == Color.WHITE.getRGB() ? artSymbol : " ");
            }

            if (sb.toString().trim().isEmpty()) {
                continue;
            }

            System.out.println(sb);
        }
    }

    /**
     * Convenience method for printing ascii text art.
     * Font default - Dialog,  Art symbol default - *
     * @param artText
     * @param textHeight
     */
    public void printTextArt(String artText, ASCIIArtFontSize textHeight) {
        printTextArt(artText, textHeight.getValue(), ASCIIArtFont.ART_FONT_DIALOG, DEFAULT_ART_SYMBOL);
    }

    /**
     * Using the Current font and current art text find the width of the full image
     * @param textHeight
     * @param artText
     * @param fontName
     * @return
     */
    private int findImageWidth(int textHeight, String artText, String fontName) {
        var im = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        var g = im.getGraphics();

        g.setFont(new Font(fontName, Font.BOLD, textHeight));

        return g.getFontMetrics().stringWidth(artText);
    }

    /**
     * Find where the text baseline should be drawn so that the characters are within image
     * @param g
     * @param font
     * @return
     */
    private int getBaselinePosition(Graphics g, Font font) {
        var metrics = g.getFontMetrics(font);

        return metrics.getAscent() - metrics.getDescent();
    }
}