/*
 * Copyright (C) 2018 Perin
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package br.jpe.ipl.core;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * An image bean
 *
 * @author joaovperin
 */
public class Image {

    private final double[][][] mtz;
    private final int width;
    private final int height;
    private final int bands;

    public Image(double[][][] mtz) {
        this.mtz = mtz;
        this.width = mtz.length;
        this.height = mtz[0].length;
        this.bands = mtz[0][0].length;
    }

    public double[][][] getMatrix() {
        return mtz;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getBands() {
        return bands;
    }

    public BufferedImage toBufferedImage() {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color c = new Color(rgbToPixel(mtz[i][j][0]), rgbToPixel(mtz[i][j][1]), rgbToPixel(mtz[i][j][2]));
                image.setRGB(i, j, c.getRGB());
            }
        }
        return image;
    }

    private static float rgbToPixel(double rgb) {
        return ((float) rgb / 255);
    }

}
