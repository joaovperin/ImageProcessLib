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
package br.jpe.ipl.core.scripts.image;

import br.jpe.ipl.core.ImageColor;
import static br.jpe.ipl.core.ImageInfoConstants.PIXEL_COUNT;
import br.jpe.ipl.core.ImageInfoExtractor;
import br.jpe.ipl.core.ImagePoint;
import br.jpe.ipl.core.ImageUtils;
import br.jpe.ipl.core.scripts.ImageScript;
import br.jpe.ipl.core.scripts.image.extraction.PixelCountExtractionScript;
import java.util.HashMap;
import java.util.Map;

/**
 * A script to replace a color with another, in the entire image
 *
 * @author joaovperin
 */
public class BinaryLabelingScript implements ImageScript {

    private static final int MIN_SIZE = 1;

    private final ImageColor bgColor;
    private final boolean fill;
    private final Map<ImageColor, ImagePoint> labels;

    private final int minimumSize;

    public BinaryLabelingScript(ImageColor bgColor) {
        this(bgColor, false);
    }

    public BinaryLabelingScript(ImageColor bgColor, boolean fill) {
        this(bgColor, MIN_SIZE, fill);
    }

    public BinaryLabelingScript(ImageColor bgColor, int minimumSize, boolean fill) {
        this.bgColor = bgColor;
        this.minimumSize = minimumSize;
        this.fill = fill;
        this.labels = new HashMap<>();
    }

    @Override
    public void run(double[][][] src) {

        int iLen = src.length;
        int jLen = src[0].length;

        for (int i = 0; i < iLen; i++) {
            for (int j = 0; j < jLen; j++) {
                ImageColor color = ImageColor.fromArray(src[i][j]);
                // If the color changed
                if (!color.equals(bgColor) && !labels.containsKey(color)) {
                    // Paint with a random color
                    ImageColor newColor = ImageColor.random(labels.keySet());
                    ImagePoint p = new ImagePoint(i, j);
                    new FloodfillEightDirectionsScript(p, newColor).run(src);
                    // If have to fill, fill it all
                    if (fill) {

                        // Paint it like the background
                        if (ImageColor.fromArray(src[p.x][p.y]).equals(bgColor) && !ImageColor.
                                fromArray(src[p.x - 1][p.y + 1]).equals(bgColor)) {
                            new FloodfillEightDirectionsScript(p, bgColor).run(src);
                            // Paint it with the new chosen color
                        } else if (ImageUtils.inBounds(src, new ImagePoint(p.x - 1, p.y + 1)) &&
                                !ImageColor.fromArray(src[p.x][p.y]).equals(bgColor) &&
                                ImageColor.fromArray(src[p.x - 1][p.y + 1]).equals(bgColor)) {
                            new FloodfillEightDirectionsScript(p.southeast(), newColor).run(src);
                        }
                    }

                    boolean putColor = false;
                    if (minimumSize > MIN_SIZE) {
                        if (ImageInfoExtractor.create(src).
                                applyScript(new PixelCountExtractionScript(newColor)).
                                extract().getInt(PIXEL_COUNT) >= minimumSize) {
                            putColor = true;
                        } else if (fill) {
                            putColor = false;
                            new PaintAllScript(newColor, bgColor).run(src);
                        }
                    } else {
                        putColor = true;
                    }
                    // Put the new label
                    if (putColor) {
                        labels.put(newColor, new ImagePoint(i, j));
                    } else if (fill) {
                        new PaintAllScript(newColor, bgColor).run(src);
                    }
                }
            }
        }
    }

    public Map<ImageColor, ImagePoint> getColors() {
        return (HashMap) ((HashMap<ImageColor, ImagePoint>) labels).clone();
    }

}
