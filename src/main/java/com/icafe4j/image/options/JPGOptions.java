/**
 * COPYRIGHT (C) 2014-2019 WEN YU (YUWEN_66@YAHOO.COM) ALL RIGHTS RESERVED.
 * <p>
 * This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * <p>
 * Any modifications to this file must keep this entire header intact.
 */

package com.icafe4j.image.options;

import com.icafe4j.image.ImageType;
import com.icafe4j.image.tiff.TiffFieldEnum.PhotoMetric;

public class JPGOptions extends ImageOptions {

  // Color space constants
  public static final int COLOR_SPACE_RGB = PhotoMetric.RGB.getValue(); // RGB
  public static final int COLOR_SPACE_YCbCr = PhotoMetric.YCbCr.getValue(); // YCbCr
  public static final int COLOR_SPACE_CMYK = PhotoMetric.SEPARATED.getValue(); // CMYK
  public static final int COLOR_SPACE_YCCK = PhotoMetric.UNKNOWN.getValue();
      // JPGWriter will interpret this as YCCK

  private int quality = 80;
  private boolean includeTables = true;
  private int colorSpace = COLOR_SPACE_YCbCr;
  private boolean isTiffFlavor;
  private boolean writeICCProfile;

  public int getColorSpace() {
    return colorSpace;
  }

  public void setColorSpace(int colorSpace) {
    this.colorSpace = colorSpace;
  }

  public ImageType getImageType() {
    return ImageType.JPG;
  }

  public int getQuality() {
    return quality;
  }

  public void setQuality(int quality) {
    this.quality = quality;
  }

  public boolean includeTables() {
    return includeTables;
  }

  public boolean isTiffFlavor() {
    return isTiffFlavor;
  }

  public void setTiffFlavor(boolean isTiffFlavor) {
    this.isTiffFlavor = isTiffFlavor;
  }

  public boolean writeICCProfile() {
    return writeICCProfile;
  }

  public void setIncludeTables(boolean includeTables) {
    this.includeTables = includeTables;
  }

  public void setWriteICCProfile(boolean writeICCProfile) {
    this.writeICCProfile = writeICCProfile;
  }
}