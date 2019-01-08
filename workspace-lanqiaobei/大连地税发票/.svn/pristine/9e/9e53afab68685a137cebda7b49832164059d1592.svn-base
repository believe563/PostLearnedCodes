
package com.example.ocrsearch.common;

public final class BitArray {

  private int[] bits;
  private int size;

  public BitArray() {
    this.size = 0;
    this.bits = new int[1];
  }

  public BitArray(int size) {
    this.size = size;
    this.bits = makeArray(size);
  }

  public int getSize() {
    return size;
  }

  public int getSizeInBytes() {
    return (size + 7) >> 3;
  }

  private void ensureCapacity(int size) {
    if (size > bits.length << 5) {
      int[] newBits = makeArray(size);
      System.arraycopy(bits, 0, newBits, 0, bits.length);
      this.bits = newBits;
    }
  }

  public boolean get(int i) {
    return (bits[i >> 5] & (1 << (i & 0x1F))) != 0;
  }

  public void set(int i) {
    bits[i >> 5] |= 1 << (i & 0x1F);
  }

  public void flip(int i) {
    bits[i >> 5] ^= 1 << (i & 0x1F);
  }

  public int getNextSet(int from) {
    if (from >= size) {
      return size;
    }
    int bitsOffset = from >> 5;
    int currentBits = bits[bitsOffset];
    currentBits &= ~((1 << (from & 0x1F)) - 1);
    while (currentBits == 0) {
      if (++bitsOffset == bits.length) {
        return size;
      }
      currentBits = bits[bitsOffset];
    }
    int result = (bitsOffset << 5) + Integer.numberOfTrailingZeros(currentBits);
    return result > size ? size : result;
  }

  public int getNextUnset(int from) {
    if (from >= size) {
      return size;
    }
    int bitsOffset = from >> 5;
    int currentBits = ~bits[bitsOffset];
    currentBits &= ~((1 << (from & 0x1F)) - 1);
    while (currentBits == 0) {
      if (++bitsOffset == bits.length) {
        return size;
      }
      currentBits = ~bits[bitsOffset];
    }
    int result = (bitsOffset << 5) + Integer.numberOfTrailingZeros(currentBits);
    return result > size ? size : result;
  }

  public void setBulk(int i, int newBits) {
    bits[i >> 5] = newBits;
  }

  public void setRange(int start, int end) {
    if (end < start) {
      throw new IllegalArgumentException();
    }
    if (end == start) {
      return;
    }
    end--; 
    int firstInt = start >> 5;
    int lastInt = end >> 5;
    for (int i = firstInt; i <= lastInt; i++) {
      int firstBit = i > firstInt ? 0 : start & 0x1F;
      int lastBit = i < lastInt ? 31 : end & 0x1F;
      int mask;
      if (firstBit == 0 && lastBit == 31) {
        mask = -1;
      } else {
        mask = 0;
        for (int j = firstBit; j <= lastBit; j++) {
          mask |= 1 << j;
        }
      }
      bits[i] |= mask;
    }
  }

  public void clear() {
    int max = bits.length;
    for (int i = 0; i < max; i++) {
      bits[i] = 0;
    }
  }

  public boolean isRange(int start, int end, boolean value) {
    if (end < start) {
      throw new IllegalArgumentException();
    }
    if (end == start) {
      return true; 
    }
    end--; 
    int firstInt = start >> 5;
    int lastInt = end >> 5;
    for (int i = firstInt; i <= lastInt; i++) {
      int firstBit = i > firstInt ? 0 : start & 0x1F;
      int lastBit = i < lastInt ? 31 : end & 0x1F;
      int mask;
      if (firstBit == 0 && lastBit == 31) {
        mask = -1;
      } else {
        mask = 0;
        for (int j = firstBit; j <= lastBit; j++) {
          mask |= 1 << j;
        }
      }

      if ((bits[i] & mask) != (value ? mask : 0)) {
        return false;
      }
    }
    return true;
  }

  public void appendBit(boolean bit) {
    ensureCapacity(size + 1);
    if (bit) {
      bits[size >> 5] |= 1 << (size & 0x1F);
    }
    size++;
  }

  public void appendBits(int value, int numBits) {
    if (numBits < 0 || numBits > 32) {
      throw new IllegalArgumentException("Num bits must be between 0 and 32");
    }
    ensureCapacity(size + numBits);
    for (int numBitsLeft = numBits; numBitsLeft > 0; numBitsLeft--) {
      appendBit(((value >> (numBitsLeft - 1)) & 0x01) == 1);
    }
  }

  public void appendBitArray(BitArray other) {
    int otherSize = other.size;
    ensureCapacity(size + otherSize);
    for (int i = 0; i < otherSize; i++) {
      appendBit(other.get(i));
    }
  }

  public void xor(BitArray other) {
    if (bits.length != other.bits.length) {
      throw new IllegalArgumentException("Sizes don't match");
    }
    for (int i = 0; i < bits.length; i++) {
      bits[i] ^= other.bits[i];
    }
  }

  public void toBytes(int bitOffset, byte[] array, int offset, int numBytes) {
    for (int i = 0; i < numBytes; i++) {
      int theByte = 0;
      for (int j = 0; j < 8; j++) {
        if (get(bitOffset)) {
          theByte |= 1 << (7 - j);
        }
        bitOffset++;
      }
      array[offset + i] = (byte) theByte;
    }
  }

  public int[] getBitArray() {
    return bits;
  }

  public void reverse() {
    int[] newBits = new int[bits.length];
    int size = this.size;
    for (int i = 0; i < size; i++) {
      if (get(size - i - 1)) {
        newBits[i >> 5] |= 1 << (i & 0x1F);
      }
    }
    bits = newBits;
  }

  private static int[] makeArray(int size) {
    return new int[(size + 31) >> 5];
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder(size);
    for (int i = 0; i < size; i++) {
      if ((i & 0x07) == 0) {
        result.append(' ');
      }
      result.append(get(i) ? 'X' : '.');
    }
    return result.toString();
  }

}