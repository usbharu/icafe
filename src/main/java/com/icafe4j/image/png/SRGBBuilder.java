/**
 * COPYRIGHT (C) 2014-2019 WEN YU (YUWEN_66@YAHOO.COM) ALL RIGHTS RESERVED.
 * <p>
 * This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * <p>
 * Any modifications to this file must keep this entire header intact.
 */

package com.icafe4j.image.png;

import com.icafe4j.util.Builder;

/**
 * PNG sRGB chunk builder
 *
 * @author Wen Yu, yuwen_66@yahoo.com
 * @version 1.0 10/16/2013
 */
public class SRGBBuilder extends ChunkBuilder implements Builder<Chunk> {

  private byte renderingIntent;

  public SRGBBuilder() {
    super(ChunkType.SRGB);
  }

  public SRGBBuilder renderingIntent(byte renderingIntent) {
    if (renderingIntent < 0 || renderingIntent > 3) {
      throw new IllegalArgumentException("Invalid rendering intent: " + renderingIntent);
    }
    this.renderingIntent = renderingIntent;
    return this;
  }

  @Override
  protected byte[] buildData() {
    // 1 bytes

    return new byte[] {renderingIntent};
  }
}