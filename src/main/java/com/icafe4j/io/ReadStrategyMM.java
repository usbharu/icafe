/**
 * COPYRIGHT (C) 2014-2019 WEN YU (YUWEN_66@YAHOO.COM) ALL RIGHTS RESERVED.
 * <p>
 * This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * <p>
 * Any modifications to this file must keep this entire header intact.
 */

package com.icafe4j.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Read strategy for Motorola byte order BIG-ENDIAN stream.
 *
 * @author Wen Yu, yuwen_66@yahoo.com
 * @version 1.0 12/27/2012
 */
public class ReadStrategyMM implements ReadStrategy {

  private static final ReadStrategyMM instance = new ReadStrategyMM();

  private ReadStrategyMM() {
  }

  public static ReadStrategyMM getInstance() {
    return instance;
  }

  public int readInt(byte[] buf, int start_idx) {
    return (((buf[start_idx++] & 0xff) << 24) | ((buf[start_idx++] & 0xff) << 16) |
        ((buf[start_idx++] & 0xff) << 8) | (buf[start_idx++] & 0xff));
  }

  public int readInt(InputStream is) throws IOException {
    byte[] buf = new byte[4];
    IOUtils.readFully(is, buf);

    return (((buf[0] & 0xff) << 24) | ((buf[1] & 0xff) << 16) | ((buf[2] & 0xff) << 8) | (buf[3]
        & 0xff));
  }

  public long readLong(byte[] buf, int start_idx) {
    return (((buf[start_idx++] & 0xffL) << 56) | ((buf[start_idx++] & 0xffL) << 48) |
        ((buf[start_idx++] & 0xffL) << 40) | ((buf[start_idx++] & 0xffL) << 32) | (
        (buf[start_idx++] & 0xffL) << 24) |
        ((buf[start_idx++] & 0xffL) << 16) | ((buf[start_idx++] & 0xffL) << 8) | (buf[start_idx]
        & 0xffL));
  }

  public long readLong(InputStream is) throws IOException {
    byte[] buf = new byte[8];
    IOUtils.readFully(is, buf);

    return (((buf[0] & 0xffL) << 56) | ((buf[1] & 0xffL) << 48) |
        ((buf[2] & 0xffL) << 40) | ((buf[3] & 0xffL) << 32) | ((buf[4] & 0xffL) << 24) |
        ((buf[5] & 0xffL) << 16) | ((buf[6] & 0xffL) << 8) | (buf[7] & 0xffL));
  }

  public float readS15Fixed16Number(byte[] buf, int start_idx) {
    short s15 = (short) (((buf[start_idx++] & 0xff) << 8) | (buf[start_idx++] & 0xff));
    int fixed16 = (((buf[start_idx++] & 0xff) << 8) | (buf[start_idx] & 0xff));

    return s15 + fixed16 / 65536.0f;
  }

  public float readS15Fixed16Number(InputStream is) throws IOException {
    byte[] buf = new byte[4];
    IOUtils.readFully(is, buf);

    short s15 = (short) ((buf[1] & 0xff) | ((buf[0] & 0xff) << 8));
    int fixed16 = ((buf[3] & 0xff) | ((buf[2] & 0xff) << 8));

    return s15 + fixed16 / 65536.0f;
  }

  public short readShort(byte[] buf, int start_idx) {
    return (short) (((buf[start_idx++] & 0xff) << 8) | (buf[start_idx] & 0xff));
  }

  public short readShort(InputStream is) throws IOException {
    byte[] buf = new byte[2];
    IOUtils.readFully(is, buf);

    return (short) (((buf[0] & 0xff) << 8) | (buf[1] & 0xff));
  }

  public float readU16Fixed16Number(byte[] buf, int start_idx) {
    int u16 = (((buf[start_idx++] & 0xff) << 8) | (buf[start_idx++] & 0xff));
    int fixed16 = (((buf[start_idx++] & 0xff) << 8) | (buf[start_idx] & 0xff));

    return u16 + fixed16 / 65536.0f;
  }

  public float readU16Fixed16Number(InputStream is) throws IOException {
    byte[] buf = new byte[4];
    IOUtils.readFully(is, buf);

    int u16 = ((buf[1] & 0xff) | ((buf[0] & 0xff) << 8));
    int fixed16 = ((buf[3] & 0xff) | ((buf[2] & 0xff) << 8));

    return u16 + fixed16 / 65536.0f;
  }

  public float readU8Fixed8Number(byte[] buf, int start_idx) {
    int u8 = (buf[start_idx++] & 0xff);
    int fixed8 = (buf[start_idx] & 0xff);

    return u8 + fixed8 / 256.0f;
  }

  public float readU8Fixed8Number(InputStream is) throws IOException {
    byte[] buf = new byte[2];
    IOUtils.readFully(is, buf);

    int u8 = (buf[0] & 0xff);
    int fixed8 = (buf[1] & 0xff);

    return u8 + fixed8 / 256.0f;
  }

  public long readUnsignedInt(byte[] buf, int start_idx) {
    return (((buf[start_idx++] & 0xff) << 24) | ((buf[start_idx++] & 0xff) << 16) |
        ((buf[start_idx++] & 0xff) << 8) | (buf[start_idx++] & 0xff)) & 0xffffffffL;
  }

  public long readUnsignedInt(InputStream is) throws IOException {
    byte[] buf = new byte[4];
    IOUtils.readFully(is, buf);

    return (((buf[0] & 0xff) << 24) | ((buf[1] & 0xff) << 16) |
        ((buf[2] & 0xff) << 8) | (buf[3] & 0xff)) & 0xffffffffL;
  }

  public int readUnsignedShort(byte[] buf, int start_idx) {
    return (((buf[start_idx++] & 0xff) << 8) | (buf[start_idx] & 0xff));
  }

  public int readUnsignedShort(InputStream is) throws IOException {
    byte[] buf = new byte[2];
    IOUtils.readFully(is, buf);

    return (((buf[0] & 0xff) << 8) | (buf[1] & 0xff));
  }
}