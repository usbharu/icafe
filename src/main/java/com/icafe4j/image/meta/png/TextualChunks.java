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
 * TextualChunk.java
 * <p>
 * Who   Date       Description ====  =========  =================================================
 * WY    04Nov2015  Added chunk type check WY    09Jul2015  Rewrote to work with multiple textual
 * chunks WY    05Jul2015  Added write support WY    05Jul2015  Initial creation
 */

package com.icafe4j.image.meta.png;

import com.icafe4j.image.meta.Metadata;
import com.icafe4j.image.meta.MetadataEntry;
import com.icafe4j.image.meta.MetadataType;
import com.icafe4j.image.png.Chunk;
import com.icafe4j.image.png.ChunkType;
import com.icafe4j.image.png.TextReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class TextualChunks extends Metadata {

  /* This queue is used to keep track of the unread chunks
   * After it's being read, all of it's elements will be moved
   * to chunks list
   */
  private final Queue<Chunk> queue;
  // We keep chunks and keyValMap in sync
  private final List<Chunk> chunks;
  private final Map<String, String> keyValMap;

  public TextualChunks() {
    super(MetadataType.PNG_TEXTUAL);
    this.queue = new LinkedList<>();
    this.chunks = new ArrayList<>();
    this.keyValMap = new HashMap<>();
  }

  public TextualChunks(Collection<Chunk> chunks) {
    super(MetadataType.PNG_TEXTUAL);
    validateChunks(chunks);
    this.queue = new LinkedList<>(chunks);
    this.chunks = new ArrayList<>();
    this.keyValMap = new HashMap<>();
  }

  private static void validateChunks(Collection<Chunk> chunks) {
    for (Chunk chunk : chunks) {
      validateChunkType(chunk.getChunkType());
    }
  }

  private static void validateChunkType(ChunkType chunkType) {
    if ((chunkType != ChunkType.TEXT) && (chunkType != ChunkType.ITXT)
        && (chunkType != ChunkType.ZTXT)) {
      throw new IllegalArgumentException("Expect Textual chunk!");
    }
  }

  public List<Chunk> getChunks() {
    ArrayList<Chunk> chunkList = new ArrayList<>(chunks);
    chunkList.addAll(queue);
    return chunkList;
  }

  public Map<String, String> getKeyValMap() {
    ensureDataRead();
    return Collections.unmodifiableMap(keyValMap);
  }

  public Iterator<MetadataEntry> iterator() {
    ensureDataRead();
    List<MetadataEntry> entries = new ArrayList<>();

    for (Map.Entry<String, String> entry : keyValMap.entrySet()) {
      entries.add(new MetadataEntry(entry.getKey(), entry.getValue()));
    }

    return Collections.unmodifiableCollection(entries).iterator();
  }

  public void addChunk(Chunk chunk) {
    validateChunkType(chunk.getChunkType());
    queue.offer(chunk);
  }

  public void read() {
    if (queue.size() > 0) {
      TextReader reader = new TextReader();
      for (Chunk chunk : queue) {
        reader.setInput(chunk);
        String key = reader.getKeyword();
        String text = reader.getText();
        String oldText = keyValMap.get(key);
        keyValMap.put(key, (oldText == null) ? text : oldText + "; " + text);
        chunks.add(chunk);
      }
      queue.clear();
    }
  }
}