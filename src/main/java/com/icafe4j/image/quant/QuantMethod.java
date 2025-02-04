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
 * QuantMethod.java
 * <p>
 * Who   Date       Description ====  =========  ====================================================
 * WY    12Sep2015  Initial creation
 */

package com.icafe4j.image.quant;

// Quantization method supported by ICAFE
public enum QuantMethod {
  POPULARITY, // Popularity
  WU_QUANT, // Xiaolin Wu
  NEU_QUANT // Neural network
}