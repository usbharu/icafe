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
 * MetadataEntry.java
 */

package com.icafe4j.image.meta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MetadataEntry {

  private final String key;
  private final String value;
  private final boolean isMetadataEntryGroup;

  private final Collection<MetadataEntry> entries = new ArrayList<>();

  public MetadataEntry(String key, String value) {
    this(key, value, false);
  }

  public MetadataEntry(String key, String value, boolean isMetadataEntryGroup) {
    this.key = key;
    this.value = value;
    this.isMetadataEntryGroup = isMetadataEntryGroup;
  }

  public void addEntry(MetadataEntry entry) {
    entries.add(entry);
  }

  public void addEntries(Collection<MetadataEntry> newEntries) {
    entries.addAll(newEntries);
  }

  public String getKey() {
    return key;
  }

  public boolean isMetadataEntryGroup() {
    return isMetadataEntryGroup;
  }

  public Collection<MetadataEntry> getMetadataEntries() {
    return Collections.unmodifiableCollection(entries);
  }

  public String getValue() {
    return value;
  }
}