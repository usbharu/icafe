/**
 * COPYRIGHT (C) 2014-2019 WEN YU (YUWEN_66@YAHOO.COM) ALL RIGHTS RESERVED.
 * <p>
 * This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * <p>
 * Any modifications to this file must keep this entire header intact.
 */

package com.icafe4j.image.jpeg;

import com.icafe4j.io.IOUtils;
import com.icafe4j.util.Reader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * JPEG DQT segment reader
 *
 * @author Wen Yu, yuwen_66@yahoo.com
 * @version 1.0 10/11/2013
 */
public class DQTReader implements Reader {

  private final Segment segment;
  private final List<QTable> qTables = new ArrayList<>(4);

  public DQTReader(Segment segment) throws IOException {
    //
    if (segment.getMarker() != Marker.DQT) {
      throw new IllegalArgumentException("Not a valid DQT segment!");
    }

    this.segment = segment;
    read();
  }

  public List<QTable> getTables() {
    return qTables;
  }

  public void read() {
    //
    byte[] data = segment.getData();
    int len = segment.getLength();
    len -= 2;//

    int offset = 0;

    int[] de_zig_zag_order = JPGConsts.getDeZigzagMatrix();

    while (len > 0) {
      int QT_info = data[offset++];
      len--;
      int QT_precision = (QT_info >> 4) & 0x0f;
      int QT_index = (QT_info & 0x0f);
      int numOfValues = 64 << QT_precision;

      int[] out = new int[64];

      // Read QT tables
      // 8 bit for precision value of 0
      if (QT_precision == 0) {
        for (int j = 0; j < 64; j++) {
          out[j] = data[de_zig_zag_order[j] + offset] & 0xff;
        }
      } else { // 16 bit big-endian for precision value of 1
        for (int j = 0; j < 64; j++) {
          out[j] = (IOUtils.readUnsignedShortMM(data, offset + de_zig_zag_order[j] << 1));
        }
      }

      qTables.add(new QTable(QT_precision, QT_index, out));

      len -= numOfValues;
      offset += numOfValues;
    }
  }
}