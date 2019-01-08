
package com.example.ocrsearch.common;

import com.example.ocrsearch.Binarizer;
import com.example.ocrsearch.LuminanceSource;

public final class HybridBinarizer extends GlobalHistogramBinarizer {

  private static final int BLOCK_SIZE_POWER = 3;
  private static final int BLOCK_SIZE = 1 << BLOCK_SIZE_POWER; // ...0100...00
  private static final int BLOCK_SIZE_MASK = BLOCK_SIZE - 1;   // ...0011...11
  private static final int MINIMUM_DIMENSION = BLOCK_SIZE * 5;
  private static final int MIN_DYNAMIC_RANGE = 24;

  private BitMatrix matrix;

  public HybridBinarizer(LuminanceSource source) {
    super(source);
  }

  @Override
  public BitMatrix getBlackMatrix() throws android.content.res.Resources.NotFoundException {
    if (matrix != null) {
      return matrix;
    }
    LuminanceSource source = getLuminanceSource();
    int width = source.getWidth();
    int height = source.getHeight();
    if (width >= MINIMUM_DIMENSION && height >= MINIMUM_DIMENSION) {
      byte[] luminances = source.getMatrix();
      int subWidth = width >> BLOCK_SIZE_POWER;
      if ((width & BLOCK_SIZE_MASK) != 0) {
        subWidth++;
      }
      int subHeight = height >> BLOCK_SIZE_POWER;
      if ((height & BLOCK_SIZE_MASK) != 0) {
        subHeight++;
      }
      int[][] blackPoints = calculateBlackPoints(luminances, subWidth, subHeight, width, height);

      BitMatrix newMatrix = new BitMatrix(width, height);
      calculateThresholdForBlock(luminances, subWidth, subHeight, width, height, blackPoints, newMatrix);
      matrix = newMatrix;
    } else {
      matrix = super.getBlackMatrix();
    }
    return matrix;
  }

  @Override
  public Binarizer createBinarizer(LuminanceSource source) {
    return new HybridBinarizer(source);
  }

  private static void calculateThresholdForBlock(byte[] luminances,
                                                 int subWidth,
                                                 int subHeight,
                                                 int width,
                                                 int height,
                                                 int[][] blackPoints,
                                                 BitMatrix matrix) {
    for (int y = 0; y < subHeight; y++) {
      int yoffset = y << BLOCK_SIZE_POWER;
      int maxYOffset = height - BLOCK_SIZE;
      if (yoffset > maxYOffset) {
        yoffset = maxYOffset;
      }
      for (int x = 0; x < subWidth; x++) {
        int xoffset = x << BLOCK_SIZE_POWER;
        int maxXOffset = width - BLOCK_SIZE;
        if (xoffset > maxXOffset) {
          xoffset = maxXOffset;
        }
        int left = cap(x, 2, subWidth - 3);
        int top = cap(y, 2, subHeight - 3);
        int sum = 0;
        for (int z = -2; z <= 2; z++) {
          int[] blackRow = blackPoints[top + z];
          sum += blackRow[left - 2] + blackRow[left - 1] + blackRow[left] + blackRow[left + 1] + blackRow[left + 2];
        }
        int average = sum / 25;
        thresholdBlock(luminances, xoffset, yoffset, average, width, matrix);
      }
    }
  }

  private static int cap(int value, int min, int max) {
    return value < min ? min : value > max ? max : value;
  }

  private static void thresholdBlock(byte[] luminances,
                                     int xoffset,
                                     int yoffset,
                                     int threshold,
                                     int stride,
                                     BitMatrix matrix) {
    for (int y = 0, offset = yoffset * stride + xoffset; y < BLOCK_SIZE; y++, offset += stride) {
      for (int x = 0; x < BLOCK_SIZE; x++) {
        if ((luminances[offset + x] & 0xFF) <= threshold) {
          matrix.set(xoffset + x, yoffset + y);
        }
      }
    }
  }

  private static int[][] calculateBlackPoints(byte[] luminances,
                                              int subWidth,
                                              int subHeight,
                                              int width,
                                              int height) {
    int[][] blackPoints = new int[subHeight][subWidth];
    for (int y = 0; y < subHeight; y++) {
      int yoffset = y << BLOCK_SIZE_POWER;
      int maxYOffset = height - BLOCK_SIZE;
      if (yoffset > maxYOffset) {
        yoffset = maxYOffset;
      }
      for (int x = 0; x < subWidth; x++) {
        int xoffset = x << BLOCK_SIZE_POWER;
        int maxXOffset = width - BLOCK_SIZE;
        if (xoffset > maxXOffset) {
          xoffset = maxXOffset;
        }
        int sum = 0;
        int min = 0xFF;
        int max = 0;
        for (int yy = 0, offset = yoffset * width + xoffset; yy < BLOCK_SIZE; yy++, offset += width) {
          for (int xx = 0; xx < BLOCK_SIZE; xx++) {
            int pixel = luminances[offset + xx] & 0xFF;
            sum += pixel;
            if (pixel < min) {
              min = pixel;
            }
            if (pixel > max) {
              max = pixel;
            }
          }
          if (max - min > MIN_DYNAMIC_RANGE) {
            for (yy++, offset += width; yy < BLOCK_SIZE; yy++, offset += width) {
              for (int xx = 0; xx < BLOCK_SIZE; xx++) {
                sum += luminances[offset + xx] & 0xFF;
              }
            }
          }
        }

        int average = sum >> (BLOCK_SIZE_POWER * 2);
        if (max - min <= MIN_DYNAMIC_RANGE) {
          average = min >> 1;

          if (y > 0 && x > 0) {
            int averageNeighborBlackPoint = (blackPoints[y - 1][x] + (2 * blackPoints[y][x - 1]) +
                blackPoints[y - 1][x - 1]) >> 2;
            if (min < averageNeighborBlackPoint) {
              average = averageNeighborBlackPoint;
            }
          }
        }
        blackPoints[y][x] = average;
      }
    }
    return blackPoints;
  }

}
