/**
 * COPYRIGHT (C) 2014-2019 WEN YU (YUWEN_66@YAHOO.COM) ALL RIGHTS RESERVED.
 * <p>
 * This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * <p>
 * Any modifications to this file must keep this entire header intact.
 */

package com.icafe4j.image.meta.iptc;

import com.icafe4j.string.StringUtils;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Defines DataSet tags for IPTC Envelope Record - Record number 1.
 * * 
 * @author Wen Yu, yuwen_66@yahoo.com
 * @version 1.0 07/02/2013
 */
public enum IPTCEnvelopeTag implements IPTCTag {
  RECORD_VERSION(0, "Envelope Record Version"),
  DESTINATION(5, "Destination") {
    @Override
    public boolean allowMultiple() {
      return true;
    }
  },
  FILE_FORMAT(20, "File Format"),
  FILE_VERSION(22, "File Version"),
  SERVICE_IDENTIFIER(30, "ServiceIdentifier"),
  ENVELOPE_NUMBER(40, "Envelope Number"),
  PRODUCT_ID(50, "Product ID") {
    @Override
    public boolean allowMultiple() {
      return true;
    }
  },
  ENVELOPE_PRIORITY(60, "Envelope Priority"),
  DATE_SENT(70, "Date Sent"),
  TIME_SENT(80, "Time Sent"),
  CODED_CHARACTER_SET(90, "Coded Character Set"),
  UNIQUE_OBJECT_NAME(100, "Unique Object Name"),
  ARM_IDENTIFIER(120, "ARM Identifier"),
  ARM_VERSION(122, "ARM Version"),

  UNKNOWN(999, "Unknown IPTCEnvelopeTag");

  private static final Map<Integer, IPTCEnvelopeTag> recordMap =
      new HashMap<>();

  static {
    for (IPTCEnvelopeTag record : values()) {
      recordMap.put(record.getTag(), record);
    }
  }

  private final int tag;
  private final String name;

  IPTCEnvelopeTag(int tag, String name) {
    this.tag = tag;
    this.name = name;
  }

  public static IPTCEnvelopeTag fromTag(int value) {
    IPTCEnvelopeTag record = recordMap.get(value);
    if (record == null) {
      return UNKNOWN;
    }
    return record;
  }

  public boolean allowMultiple() {
    return false;
  }

  // Default implementation. Could be replaced by individual ENUM
  public String getDataAsString(byte[] data) {
    String strVal = new String(data, StandardCharsets.UTF_8).trim();
    if (strVal.length() > 0) {
      return strVal;
    }
    // Hex representation of the data
    return StringUtils.byteArrayToHexString(data, 0, IPTCTag.MAX_STRING_REPR_LEN);
  }

  public String getName() {
    return name;
  }

  public int getRecordNumber() {
    return IPTCRecord.ENVELOP.getRecordNumber();
  }

  public int getTag() {
    return tag;
  }

  @Override
  public String toString() {
    return name;
  }
}