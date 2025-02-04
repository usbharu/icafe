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
 * QuantQuality.java
 * <p>
 * Who   Date       Description ====  =========  ====================================================
 * WY    06Feb2016  Initial creation
 */

package com.icafe4j.image.quant;

/**
 * Predefined quantization quality. This parameter has no effect when
 * QuantMethod is QuantMethod.POPULARITY.
 *
 * @author Wen Yu, yuwen_66@yahoo.com
 */
public enum QuantQuality {
  BEST, // Brute force search to map pixel to LUT entry
  GOOD, // Inverse color map to speed up pixel to LUT mapping
  POOR
}