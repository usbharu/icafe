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

import com.icafe4j.util.Reader;

/**
 * PNG PLTE chunk reader
 *
 * @author Wen Yu, yuwen_66@yahoo.com
 * @version 1.0 04/26/2013
 */
public class PLTEReader implements Reader {

  private final Chunk chunk;
  private byte[] redMap;
  private byte[] greenMap;
  private byte[] blueMap;

  public PLTEReader(Chunk chunk) {
    if (chunk == null) {
      throw new IllegalArgumentException("Input chunk is null");
    }

    if (chunk.getChunkType() != ChunkType.PLTE) {
      throw new IllegalArgumentException("Not a valid PLTE chunk.");
    }

    this.chunk = chunk;

    read();
  }

  public byte[] getRedMap() {
    return redMap;
  }

  public byte[] getGreenMap() {
    return greenMap;
  }

  public byte[] getBlueMap() {
    return blueMap;
  }

  public void read() {

    byte[] colorMap = chunk.getData();
    int mapLen = colorMap.length;

    if ((mapLen % 3) != 0) {
      throw new IllegalArgumentException("Invalid colorMap length: " + mapLen);
    }

    redMap = new byte[mapLen / 3];
    greenMap = new byte[mapLen / 3];
    blueMap = new byte[mapLen / 3];

    for (int i = mapLen - 1, j = redMap.length - 1; j >= 0; j--) {
      blueMap[j] = colorMap[i--];
      greenMap[j] = colorMap[i--];
      redMap[j] = colorMap[i--];
    }
  }
}