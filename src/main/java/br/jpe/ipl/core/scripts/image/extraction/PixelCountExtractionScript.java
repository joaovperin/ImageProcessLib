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
package br.jpe.ipl.core.scripts.image.extraction;

import br.jpe.ipl.core.ImageColor;
import br.jpe.ipl.core.ImageInfo;
import br.jpe.ipl.core.ImageInfoConstants;
import br.jpe.ipl.core.scripts.InfoExtractorScript;

/**
 * An script to extract the number of pixels of a color in an image
 *
 * @author joaovperin
 */
public class PixelCountExtractionScript implements InfoExtractorScript, ImageInfoConstants {

    private final ImageColor target;
    private final String label;

    public PixelCountExtractionScript(ImageColor target) {
        this(target, PIXEL_COUNT);
    }

    public PixelCountExtractionScript(ImageColor target, String label) {
        this.target = target;
        this.label = label;
    }

    @Override
    public final void run(double[][][] mtz, ImageInfo info) {
        int count = 0;

        int iLen = mtz.length;
        int jLen = mtz[0].length;

        for (int i = 0; i < iLen; i++) {
            for (int j = 0; j < jLen; j++) {
                ImageColor pixelColor = ImageColor.fromArray(mtz[i][j]);
                if (pixelColor.equals(target)) {
                    count++;
                }
            }
        }
        info.put(label, count);
    }

}
