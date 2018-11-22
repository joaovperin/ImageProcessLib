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
package br.jpe.ipl.core.scripts.pixel;

import br.jpe.ipl.core.ImageColor;
import br.jpe.ipl.core.scripts.PixelScript;

/**
 * A pixel script to apply a contrast in an image
 *
 * @author joaovperin
 */
public class ContrastPixelScript implements PixelScript {

    private final double contrast;

    public ContrastPixelScript(double contrast) {
        this.contrast = contrast;
    }

    @Override
    public void run(double[][][] mtz, ImageColor color, int i, int j) {
        mtz[i][j][0] = applyContrast(color.getRed(), contrast);
        mtz[i][j][1] = applyContrast(color.getGreen(), contrast);
        mtz[i][j][2] = applyContrast(color.getBlue(), contrast);
    }

    private static int applyContrast(int p, double gain) {
        return (int) Math.min(Math.max(gain * p, 0), 255);
    }

}
