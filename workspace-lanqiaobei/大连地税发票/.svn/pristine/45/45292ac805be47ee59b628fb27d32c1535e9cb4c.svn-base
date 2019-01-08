
package com.example.ocrsearch.common;

public final class BitMatrix {

  private final int width;
  private final int height;
  private final int rowSize;
  private final int[] bits;

  public BitMatrix(int dimension) {
    this(dimension, dimension);
  }

  public BitMatrix(int width, int height) {
    if (width < 1 || height < 1) {
      throw new IllegalArgumentException("Both dimensions must be greater than 0");
    }
    this.width = width;
    this.height = height;
    this.rowSize = (width + 31) >> 5;
    bits = new int[rowSize * height];
  }

  public boolean get(int x, int y) {
    int offset = y * rowSize + (x >> 5);
    return ((bits[offset] >>> (x & 0x1f)) & 1) != 0;
  }

  public void set(int x, int y) {
    int offset = y * rowSize + (x >> 5);
    bits[offset] |= 1 << (x & 0x1f);
  }

  public void flip(int x, int y) {
    int offset = y * rowSize + (x >> 5);
    bits[offset] ^= 1 << (x & 0x1f);
  }

  public void clear() {
    int max = bits.length;
    for (int i = 0; i < max; i++) {
      bits[i] = 0;
    }
  }

  public void setRegion(int left, int top, int width, int height) {
    if (top < 0 || left < 0) {
      throw new IllegalArgumentException("Left and top must be nonnegative");
    }
    if (height < 1 || width < 1) {
      throw new IllegalArgumentException("Height and width must be at least 1");
    }
    int right = left + width;
    int bottom = top + height;
    if (bottom > this.height || right > this.width) {
      throw new IllegalArgumentException("The region must fit inside the matrix");
    }
    for (int y = top; y < bottom; y++) {
      int offset = y * rowSize;
      for (int x = left; x < right; x++) {
        bits[offset + (x >> 5)] |= 1 << (x & 0x1f);
      }
    }
  }

  public BitArray getRow(int y, BitArray row) {
    if (row == null || row.getSize() < width) {
      row = new BitArray(width);
    }
    int offset = y * rowSize;
    for (int x = 0; x < rowSize; x++) {
      row.setBulk(x << 5, bits[offset + x]);
    }
    return row;
  }

  public void setRow(int y, BitArray row) {
    System.arraycopy(row.getBitArray(), 0, bits, y * rowSize, rowSize);
  }

  public int[] getEnclosingRectangle() {
    int left = width;
    int top = height;
    int right = -1;
    int bottom = -1;

    for (int y = 0; y < height; y++) {
      for (int x32 = 0; x32 < rowSize; x32++) {
        int theBits = bits[y * rowSize + x32];
        if (theBits != 0) {
          if (y < top) {
            top = y;
          }
          if (y > bottom) {
            bottom = y;
          }
          if (x32 * 32 < left) {
            int bit = 0;
            while ((theBits << (31 - bit)) == 0) {
              bit++;
            }
            if ((x32 * 32 + bit) < left) {
              left = x32 * 32 + bit;
            }
          }
          if (x32 * 32 + 31 > right) {
            int bit = 31;
            while ((theBits >>> bit) == 0) {
              bit--;
            }
            if ((x32 * 32 + bit) > right) {
              right = x32 * 32 + bit;
            }
          }
        }
      }
    }

    int width = right - left;
    int height = bottom - top;

    if (width < 0 || height < 0) {
      return null;
    }

    return new int[] {left, top, width, height};
  }

  public int[] getTopLeftOnBit() {
    int bitsOffset = 0;
    while (bitsOffset < bits.length && bits[bitsOffset] == 0) {
      bitsOffset++;
    }
    if (bitsOffset == bits.length) {
      return null;
    }
    int y = bitsOffset / rowSize;
    int x = (bitsOffset % rowSize) << 5;

    int theBits = bits[bitsOffset];
    int bit = 0;
    while ((theBits << (31-bit)) == 0) {
      bit++;
    }
    x += bit;
    return new int[] {x, y};
  }

  public int[] getBottomRightOnBit() {
    int bitsOffset = bits.length - 1;
    while (bitsOffset >= 0 && bits[bitsOffset] == 0) {
      bitsOffset--;
    }
    if (bitsOffset < 0) {
      return null;
    }

    int y = bitsOffset / rowSize;
    int x = (bitsOffset % rowSize) << 5;

    int theBits = bits[bitsOffset];
    int bit = 31;
    while ((theBits >>> bit) == 0) {
      bit--;
    }
    x += bit;

    return new int[] {x, y};
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof BitMatrix)) {
      return false;
    }
    BitMatrix other = (BitMatrix) o;
    if (width != other.width || height != other.height ||
        rowSize != other.rowSize || bits.length != other.bits.length) {
      return false;
    }
    for (int i = 0; i < bits.length; i++) {
      if (bits[i] != other.bits[i]) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hash = width;
    hash = 31 * hash + width;
    hash = 31 * hash + height;
    hash = 31 * hash + rowSize;
    for (int bit : bits) {
      hash = 31 * hash + bit;
    }
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder(height * (width + 1));
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        result.append(get(x, y) ? "X " : "  ");
      }
      result.append('\n');
    }
    return result.toString();
  }

}
