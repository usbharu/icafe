/**
 * COPYRIGHT (C) 2014-2019 WEN YU (YUWEN_66@YAHOO.COM) ALL RIGHTS RESERVED.
 * <p>
 * This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * <p>
 * Any modifications to this file must keep this entire header intact.
 * <p>
 * Change History - most recent changes go on top of previous changes
 * <p>
 * FrameReader.java
 * <p>
 * Who   Date       Description ====  =========  ====================================================
 * WY    08Oct2015  Initial creation
 */

package com.icafe4j.image.gif;

import com.icafe4j.image.reader.GIFReader;
import java.awt.image.BufferedImage;
import java.io.InputStream;

// Helper class to read GIF frames one at a time
public class FrameReader extends GIFReader {

  // Get the next frame as a GIFFrame
  public GIFFrame getGIFFrame(InputStream is) throws Exception {
    BufferedImage bi = getFrameAsBufferedImage(is);
    return (bi != null) ? new GIFFrame(bi, image_x, image_y, delay, disposalMethod, userInputFlag,
        transparencyFlag, transparent_color) : null;
  }

  // Get the next frame as a GIFFrame
  public GIFFrame getGIFFrameEx(InputStream is) throws Exception {
    BufferedImage bi = getFrameAsBufferedImageEx(is);
    return (bi != null) ? new GIFFrame(bi, image_x, image_y, delay, disposalMethod, userInputFlag,
        transparencyFlag, transparent_color) : null;
  }

  public BufferedImage read(InputStream is) {
    return null; // NOOP
  }
}