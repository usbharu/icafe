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
 * IPTC.java
 * <p>
 * Who   Date       Description ====  =========  =================================================
 * WY    09Apr2018  Added iterator() to traverse IPTC datasets WY    25Apr2015  Renamed getDataSet()
 * to getDataSets() WY    25Apr2015  Added addDataSets() WY    13Apr2015  Added write()
 */

package com.icafe4j.image.meta.iptc;

import com.icafe4j.image.meta.Metadata;
import com.icafe4j.image.meta.MetadataEntry;
import com.icafe4j.image.meta.MetadataType;
import com.icafe4j.io.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IPTC extends Metadata {

  // Obtain a logger instance
  private static final Logger LOGGER = LoggerFactory.getLogger(IPTC.class);
  private Map<IPTCTag, List<IPTCDataSet>> datasetMap;

  public IPTC() {
    super(MetadataType.IPTC);
    datasetMap = new TreeMap<>(new IPTCTagComparator());
    isDataRead = true;
  }

  public IPTC(byte[] data) {
    super(MetadataType.IPTC, data);
    ensureDataRead();
  }

  public static void showIPTC(byte[] data) throws IOException {
    if (data != null && data.length > 0) {
      IPTC iptc = new IPTC(data);
      iptc.read();
      for (MetadataEntry item : iptc) {
        LOGGER.info(item.getKey() + ": " + item.getValue());
        if (item.isMetadataEntryGroup()) {
          String indent = "    ";
          Collection<MetadataEntry> entries = item.getMetadataEntries();
          for (MetadataEntry e : entries) {
            LOGGER.info(indent + e.getKey() + ": " + e.getValue());
          }
        }
      }
    }
  }

  public static void showIPTC(InputStream is) {
    try {
      showIPTC(IOUtils.inputStreamToByteArray(is));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void addDataSet(IPTCDataSet dataSet) {
    if (datasetMap != null) {
      IPTCTag tag = dataSet.getTagEnum();
      if (datasetMap.get(tag) == null) {
        List<IPTCDataSet> list = new ArrayList<>();
        list.add(dataSet);
        datasetMap.put(tag, list);
      } else if (dataSet.allowMultiple()) {
        datasetMap.get(tag).add(dataSet);
      }
    } else {
      throw new IllegalStateException("DataSet Map is empty");
    }
  }

  public void addDataSets(Collection<? extends IPTCDataSet> dataSets) {
    if (datasetMap != null) {
      for (IPTCDataSet dataSet : dataSets) {
        IPTCTag tag = dataSet.getTagEnum();
        if (datasetMap.get(tag) == null) {
          List<IPTCDataSet> list = new ArrayList<>();
          list.add(dataSet);
          datasetMap.put(tag, list);
        } else if (dataSet.allowMultiple()) {
          datasetMap.get(tag).add(dataSet);
        }
      }
    } else {
      throw new IllegalStateException("DataSet Map is empty");
    }
  }

  /**
   * Get a string representation of the IPTCDataSet associated with the key
   *
   * @param key the IPTCTag for the IPTCDataSet
   * @return a String representation of the IPTCDataSet, separated by ";"
   */
  public String getAsString(IPTCTag key) {
    // Retrieve the IPTCDataSet list associated with this key
    // Most of the time the list will only contain one item
    List<IPTCDataSet> list = getDataSet(key);

    StringBuilder value = new StringBuilder();

    if (list != null) {
      if (list.size() == 1) {
        value = new StringBuilder(list.get(0).getDataAsString());
      } else {
        for (int i = 0; i < list.size() - 1; i++) {
          value.append(list.get(i).getDataAsString()).append(";");
        }
        value.append(list.get(list.size() - 1).getDataAsString());
      }
    }

    return value.toString();
  }

  /**
   * Get a list of IPTCDataSet associated with a key
   *
   * @param key IPTCTag of the DataSet
   * @return a list of IPTCDataSet associated with the key
   */
  public List<IPTCDataSet> getDataSet(IPTCTag key) {
    return getDataSets().get(key);
  }

  /**
   * Get all the IPTCDataSet as a map for this IPTC data
   *
   * @return a map with the key for the IPTCDataSet tag and a list of IPTCDataSet as the value
   */
  public Map<IPTCTag, List<IPTCDataSet>> getDataSets() {
    ensureDataRead();
    return datasetMap;
  }

  public Iterator<MetadataEntry> iterator() {
    ensureDataRead();
    if (datasetMap != null) {
      // Print multiple entry IPTCDataSet
      Set<Map.Entry<IPTCTag, List<IPTCDataSet>>> entries = datasetMap.entrySet();
      Iterator<Entry<IPTCTag, List<IPTCDataSet>>> iter = entries.iterator();
      return new Iterator<>() {
        public MetadataEntry next() {
          Entry<IPTCTag, List<IPTCDataSet>> entry = iter.next();
          String key = entry.getKey().getName();
          String value = "";

          for (IPTCDataSet item : entry.getValue()) {
            value += ";" + item.getDataAsString();
          }

          return new MetadataEntry(key, value.replaceFirst(";", ""));
        }

        public boolean hasNext() {
          return iter.hasNext();
        }

        public void remove() {
          throw new UnsupportedOperationException(
              "Removing MetadataEntry is not supported by this Iterator");
        }
      };
    }
    return Collections.emptyIterator();
  }

  public void read() throws IOException {
    if (!isDataRead) {
      int i = 0;
      int tagMarker = data[i];
      datasetMap = new TreeMap<>(new IPTCTagComparator());
      while (tagMarker == 0x1c) {
        i++;
        int recordNumber = data[i++] & 0xff;
        int tag = data[i++] & 0xff;
        int recordSize = IOUtils.readUnsignedShortMM(data, i);
        i += 2;

        if (recordSize > 0) {
          IPTCDataSet dataSet = new IPTCDataSet(recordNumber, tag, recordSize, data, i);

          IPTCTag tagEnum = dataSet.getTagEnum();
          if (datasetMap.get(tagEnum) == null) {
            List<IPTCDataSet> list = new ArrayList<>();
            list.add(dataSet);
            datasetMap.put(tagEnum, list);
          } else {
            datasetMap.get(tagEnum).add(dataSet);
          }
        }

        i += recordSize;
        // Sanity check
        if (i >= data.length) {
          break;
        }
        tagMarker = data[i];
      }
      // Remove possible duplicates
      for (Map.Entry<IPTCTag, List<IPTCDataSet>> entry : datasetMap.entrySet()) {
        entry.setValue(
            new ArrayList<>(new LinkedHashSet<>(entry.getValue())));
      }

      isDataRead = true;
    }
  }

  public void write(OutputStream os) throws IOException {
    for (List<IPTCDataSet> datasets : getDataSets().values()) {
      for (IPTCDataSet dataset : datasets) {
        dataset.write(os);
      }
    }
  }
}