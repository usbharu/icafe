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

public class PNGDescriptor {

  private PNGDescriptor() {
  }

  //
  public static String getCompressionTypeDescrition(int compressionType) {
    //
    String description = "";

    if (compressionType == 0) {
      description = "Deflate/inflate compression with a 32K sliding window";
    } else {
      description = "Invalid compression value";
    }

    return description;
  }

  public static String getFilterDescription(int filter) {
    //
    String description = "";

    switch (filter) {
      case 0:
        description = "No filter";
        break;
      case 1:
        description = "SUB filter";
        break;
      case 2:
        description = "UP filter";
        break;
      case 3:
        description = "AVERAGE filter";
        break;
      case 4:
        description = "PAETH filter";
        break;
      default:
        description = "Invalid filter type";
        break;
    }

    return description;
  }

  public static String getFilterTypeDescription(int filterType) {
    //
    String description = "";

    if (filterType == 0) {
      description = "Adaptive filtering with five basic filter types";
    } else {
      description = "Invalid filter type";
    }

    return description;
  }

  public static String getInterlaceTypeDescription(int interlaceType) {
    //
    String description = "";

    switch (interlaceType) {
      case 0:
        description = "No interlace";
        break;
      case 1:
        description = "Adam7 interlace";
        break;
      default:
        description = "Invalid interlace type";
        break;
    }

    return description;
  }
}