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
 * FilterMask.java - Adobe Photoshop Document Data Block LMsk
 * <p>
 * Who   Date       Description ====  =========  =================================================
 * WY    28Jul2015  Initial creation
 */

package com.icafe4j.image.meta.adobe;

import com.icafe4j.image.meta.MetadataEntry;
import com.icafe4j.io.ReadStrategy;
import java.util.Arrays;

public class FilterMask extends DDBEntry {

  private final int[] colors = new int[4];
  private int colorSpaceId;
  private int opacity;

  public FilterMask(int size, byte[] data, ReadStrategy readStrategy) {
    super(DataBlockType.FMsk, size, data, readStrategy);
    read();
  }

  public int[] getColors() {
    return colors.clone();
  }

  public int getOpacity() {
    return opacity;
  }

  public int getColorSpace() {
    return colorSpaceId;
  }

  public ColorSpaceID getColorSpaceID() {
    return ColorSpaceID.fromInt(colorSpaceId);
  }

  protected MetadataEntry getMetadataEntry() {
    MetadataEntry root =
        new MetadataEntry(DataBlockType.FMsk.name(), DataBlockType.FMsk.getDescription(), true);
    root.addEntry(new MetadataEntry("Size", getSize() + ""));
    root.addEntry(new MetadataEntry("Color Space", getColorSpaceID().name()));
    root.addEntry(new MetadataEntry("Color Values", Arrays.toString(colors)));
    root.addEntry(new MetadataEntry("Opacity", opacity + ""));
    //
    return root;
  }

  private void read() {
    int i = 0;
    colorSpaceId = readStrategy.readShort(data, i);
    i += 2;
    colors[0] = readStrategy.readUnsignedShort(data, i);
    i += 2;
    colors[1] = readStrategy.readUnsignedShort(data, i);
    i += 2;
    colors[2] = readStrategy.readUnsignedShort(data, i);
    i += 2;
    colors[3] = readStrategy.readUnsignedShort(data, i);
    i += 2;
    opacity = readStrategy.readShort(data, i);
  }
}