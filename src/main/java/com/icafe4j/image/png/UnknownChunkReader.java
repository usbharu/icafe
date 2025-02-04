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
 * Special chunk reader for UnknownChunk.
 *
 * @author Wen Yu, yuwen_66@yahoo.com
 * @version 1.0 01/01/2013
 */
public class UnknownChunkReader implements Reader {

  private final Chunk chunk;
  private int chunkValue;
  private byte[] data;

  public UnknownChunkReader(Chunk chunk) {
    if (chunk == null) {
      throw new IllegalArgumentException("Input chunk is null");
    }

    this.chunk = chunk;

    read();
  }

  public int getChunkValue() {
    return this.chunkValue;
  }

  public byte[] getData() {
    return data.clone();
  }

  public void read() {
    if (chunk instanceof UnknownChunk) {
      UnknownChunk unknownChunk = (UnknownChunk) chunk;
      this.chunkValue = unknownChunk.getChunkValue();
      this.data = unknownChunk.getData();
    } else {
      throw new IllegalArgumentException("Expect UnknownChunk.");
    }
  }
}