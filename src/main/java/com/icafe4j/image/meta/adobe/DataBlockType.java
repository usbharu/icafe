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
 * DataBlockType.java - Adobe Photoshop Document Data Block Types
 * <p>
 * Who   Date       Description ====  =========  =================================================
 * WY    25Jul2015  Initial creation
 */

package com.icafe4j.image.meta.adobe;

import java.util.HashMap;
import java.util.Map;

public enum DataBlockType {
  Layr("Layer Data", 0x4c617972),
  LMsk("User Mask Same as Global layer mask info table", 0x4c4d736b),
  Patt("Pattern", 0x50617474),
  FMsk("Filter Mask", 0x464d736b),
  Anno("Annotations", 0x416e6e6f),

  UNKNOWN("Unknown Data Block", 0xFFFFFFFF);

  private static final Map<Integer, DataBlockType> typeMap = new HashMap<>();

  static {
    for (DataBlockType type : values()) {
      typeMap.put(type.getValue(), type);
    }
  }

  private final String description;
  private final int value;

  DataBlockType(String description, int value) {
    this.description = description;
    this.value = value;
  }

  public static DataBlockType fromInt(int value) {
    DataBlockType type = typeMap.get(value);
    if (type == null) {
      return UNKNOWN;
    }
    return type;
  }

  public String getDescription() {
    return description;
  }

  public int getValue() {
    return value;
  }
}