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
 * _8BIM.java
 * <p>
 * Who   Date       Description ====  =========  =================================================================
 * WY    24Jan2015  initial creation
 */

package com.icafe4j.image.meta.adobe;

import com.icafe4j.image.meta.MetadataEntry;
import com.icafe4j.io.IOUtils;
import com.icafe4j.string.StringUtils;
import java.io.IOException;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class _8BIM {

  // Obtain a logger instance
  private static final Logger LOGGER = LoggerFactory.getLogger(_8BIM.class);
  private final short id;
  private final String name;
  protected int size;
  protected byte[] data;

  public _8BIM(short id, String name, byte[] data) {
    this(id, name, (data == null) ? 0 : data.length, data);
  }

  public _8BIM(short id, String name, int size, byte[] data) {
    this.id = id;
    this.name = name;
    this.size = size;
    this.data = data;
  }

  public _8BIM(ImageResourceID eId, String name, byte[] data) {
    this(eId.getValue(), name, data);
  }

  public byte[] getData() {
    return data.clone();
  }

  // Default implementation to be override by sub-classes for iteration purpose
  protected MetadataEntry getMetadataEntry() {
    //
    ImageResourceID eId = ImageResourceID.fromShort(id);

    if ((id >= ImageResourceID.PATH_INFO0.getValue()) && (id
        <= ImageResourceID.PATH_INFO998.getValue())) {
      return new MetadataEntry("PATH_INFO [" + StringUtils.shortToHexStringMM(id) + "]",
          eId.getDescription());
    } else if ((id >= ImageResourceID.PLUGIN_RESOURCE0.getValue()) && (id
        <= ImageResourceID.PLUGIN_RESOURCE999.getValue())) {
      return new MetadataEntry("PLUGIN_RESOURCE [" + StringUtils.shortToHexStringMM(id) + "]",
          eId.getDescription());
    } else if (eId == ImageResourceID.UNKNOWN) {
      return new MetadataEntry("UNKNOWN [" + StringUtils.shortToHexStringMM(id) + "]",
          eId.getDescription());
    } else {
      return new MetadataEntry("" + eId, eId.getDescription());
    }
  }

  public String getName() {
    return name;
  }

  public short getID() {
    return id;
  }

  public int getSize() {
    return size;
  }

  public void print() {
    ImageResourceID eId = ImageResourceID.fromShort(id);

    if ((id >= ImageResourceID.PATH_INFO0.getValue()) && (id
        <= ImageResourceID.PATH_INFO998.getValue())) {
      LOGGER.info("PATH_INFO [Value: {}] - Path Information (saved paths).",
          StringUtils.shortToHexStringMM(id));
    } else if ((id >= ImageResourceID.PLUGIN_RESOURCE0.getValue()) && (id
        <= ImageResourceID.PLUGIN_RESOURCE999.getValue())) {
      LOGGER.info("PLUGIN_RESOURCE [Value: {}] - Plug-In resource.",
          StringUtils.shortToHexStringMM(id));
    } else if (eId == ImageResourceID.UNKNOWN) {
      LOGGER.info("{} [Value: {}]", eId, StringUtils.shortToHexStringMM(id));
    } else {
      LOGGER.info("{}", eId);
    }

    LOGGER.info("Type: 8BIM");
    LOGGER.info("Name: {}", name);
    LOGGER.info("Size: {}", size);
  }

  public void write(OutputStream os) throws IOException {
    // Write IRB id
    os.write("8BIM".getBytes());
    // Write resource id
    IOUtils.writeShortMM(os, id);
    // Write name (Pascal string - first byte denotes length of the string)
    byte[] temp = name.trim().getBytes();
    os.write(temp.length); // Size of the string, may be zero
    os.write(temp);
    if (temp.length % 2 == 0) {
      os.write(0);
    }
    // Now write data size
    IOUtils.writeIntMM(os, size);
    os.write(data); // Write the data itself
    if (data.length % 2 != 0) {
      os.write(0); // Padding the data to even size if needed
    }
  }
}