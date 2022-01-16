package com.chilvers.henry.acer

import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.image.BufferedImage

/**
 * ASCII Art Generator in Java.
 * Prints a given text as an ASCII text art on the console.
 * This code is licensed under - CC Attribution CC BY 4.0.
 * @author www.quickprogrammingtips.com
 *
 */
class ASCIIArtGenerator {
    /**
     * Convenience method for printing ascii text art.
     * Font default - Dialog,  Art symbol default - *
     * @param artText
     * @param textHeight
     */
    fun printTextArt(artText: String, textHeight: ASCIIArtFontSize) {
        printTextArt(artText, textHeight.value, ASCIIArtFont.ART_FONT_DIALOG, DEFAULT_ART_SYMBOL)
    }

    /**
     * Prints ASCII art for the specified text. For size, you can use predefined sizes or a custom size.
     * Usage - printTextArt("Hi",30,com.chilvers.henry.acer.ASCIIArtFont.ART_FONT_SERIF,"@");
     * @param artText
     * @param textHeight - Use a predefined size or a custom type
     * @param fontType - Use one of the available fonts
     * @param artSymbol - Specify the character for printing the ascii art
     */
    private fun printTextArt(artText: String, textHeight: Int, fontType: ASCIIArtFont, artSymbol: String?) {
        val fontName = fontType.value
        val imageWidth = findImageWidth(textHeight, artText, fontName)
        val image = BufferedImage(imageWidth, textHeight, BufferedImage.TYPE_INT_RGB)
        val g = image.graphics
        val font = Font(fontName, Font.BOLD, textHeight)

        g.font = font

        val graphics = g as Graphics2D

        graphics.drawString(artText, 0, getBaselinePosition(g, font))

        for (y in 0 until textHeight) {
            val sb = StringBuilder()

            for (x in 0 until imageWidth) {
                sb.append(if (image.getRGB(x, y) == Color.WHITE.rgb) artSymbol else " ")
            }

            if (sb.toString().trim { it <= ' ' }.isEmpty()) {
                continue
            }

            println(sb)
        }
    }

    /**
     * Using the Current font and current art text find the width of the full image
     * @param textHeight
     * @param artText
     * @param fontName
     * @return
     */
    private fun findImageWidth(textHeight: Int, artText: String, fontName: String): Int {
        val im = BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB)
        val g = im.graphics

        g.font = Font(fontName, Font.BOLD, textHeight)

        return g.fontMetrics.stringWidth(artText)
    }

    /**
     * Find where the text baseline should be drawn so that the characters are within image
     * @param g
     * @param font
     * @return
     */
    private fun getBaselinePosition(g: Graphics, font: Font): Int {
        val metrics = g.getFontMetrics(font)

        return metrics.ascent - metrics.descent
    }

    companion object {
        private const val DEFAULT_ART_SYMBOL = "*"
    }
}