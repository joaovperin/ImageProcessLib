/*
 * Copyright (C) 2020 Perin
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
package br.jpe.ipl;

import br.jpe.ipl.core.Image;
import br.jpe.ipl.core.ImageBuilder;
import br.jpe.ipl.core.ImageLoader;
import br.jpe.ipl.core.ImageWriter;
import java.io.File;
import java.io.IOException;

/**
 * A class for testing stuff. Do not distribute this ;)
 *
 * @author joaovperin
 */
public class NewClass {

    public static void main(String[] args) throws IOException {
        Image img = ImageBuilder.create(
                ImageLoader.fromFile(new File(expandImageName("test.png")))
                        .asOriginal())
                .build();
        ImageWriter.save(expandImageName("output_test.png"), img);
    }

    private static String expandImageName(String string) {
        return System.getProperty("user.home") + "\\ImageTests\\" + string;
    }

}
