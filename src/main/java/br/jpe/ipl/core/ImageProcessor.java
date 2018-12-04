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

import br.jpe.ipl.core.scripts.ImageScript;
import br.jpe.ipl.core.scripts.MaskPixelScript;
import br.jpe.ipl.core.scripts.PixelScript;

/**
 * A helper class to process an image
 *
 * @author joaovperin
 */
public class ImageProcessor {

    private static final int DEF_TIMES = 1;

    public static final void process(double[][][] src, ImageScript... scripts) {
        process(src, DEF_TIMES, scripts);
    }

    public static final void process(double[][][] src, int t, ImageScript... scripts) {
        for (ImageScript s : scripts) {
            while (t-- > 0) {
                s.run(src);
            }
        }
    }

    public static final void process(double[][][] src, PixelScript... pixelScripts) {
        process(src, DEF_TIMES, pixelScripts);
    }

    public static final void process(double[][][] src, int t, PixelScript... pixelScripts) {
        int iLen = src.length;
        int jLen = src[0].length;

        while (t-- > 0) {
            for (int i = 0; i < iLen; i++) {
                for (int j = 0; j < jLen; j++) {
                    ImageColor color = ImageColor.fromArray(src[i][j]);
                    for (PixelScript p : pixelScripts) {
                        p.run(src, color, i, j);
                    }
                }
            }
        }
    }

    public static final void process(double[][][] src, MaskPixelScript... scripts) {
        process(src, DEF_TIMES, scripts);
    }

    public static final void process(double[][][] src, int t, MaskPixelScript... scripts) {
        int iLen = src.length;
        int jLen = src[0].length;
        int cLen = src[0][0].length;

        while (t-- > 0) {
            for (int i = 0; i < iLen; i++) {
                for (int j = 0; j < jLen; j++) {
                    ImageColor color = ImageColor.fromArray(src[i][j]);
                    for (MaskPixelScript p : scripts) {
                        double value = p.run(color.getRed(), color.getGreen(), color.getBlue());
                        for (int c = 0; c < cLen; c++) {
                            src[i][j][c] = value;
                        }
                    }
                }
            }
        }
    }

}
