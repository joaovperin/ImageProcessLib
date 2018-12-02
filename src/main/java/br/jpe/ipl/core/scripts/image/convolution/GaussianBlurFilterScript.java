/*
 * Copyright (C) 2018 joaovperin
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
package br.jpe.ipl.core.scripts.image.convolution;

import br.jpe.ipl.core.scripts.image.ConvolutionTransformScript;

/**
 * Apply a gaussian blur filter
 *
 * @author joaovperin
 */
public class GaussianBlurFilterScript extends ConvolutionTransformScript {

    private int sum;

    public GaussianBlurFilterScript() {
        super(new double[][]{
            new double[]{1, 2, 1},
            new double[]{2, 4, 2},
            new double[]{1, 2, 1}
        });
    }

    @Override
    protected void forEachColorStart(double[][][] src, int i, int j, int c) {
        sum = 0;
    }

    @Override
    protected void forEachConvolutedPixel(double[][][] mtz, int i, int j, int c, int kI, int kJ) {
        double pixelValue = mtz[i + kI - 1][j + kJ - 1][c];
        sum += pixelValue * kernel[0][kI][kJ];
    }

    @Override
    protected void forEachColorEnd(double[][][] mtz, int i, int j, int c) {
        mtz[i][j][c] = Math.min(Math.round(sum / 16), 255);
    }

}
