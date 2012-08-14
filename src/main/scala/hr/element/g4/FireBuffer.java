package hr.element.g4;

import java.util.Random;

public class FireBuffer {
  public final int w, h;

  private final Random rnd;
  private final int[] body;

  public FireBuffer(final int w, final int h) {
    this.w = w;
    this.h = h;

    rnd = new Random();
    body = new int[w * (h + 3)]; // extra three rows for ugly artifacts
  }

//=============================================================================

  public int[] update() {
    for (int x = w - 1; x >= 0; x --) {
      body[x + (h + 2) * w] = 0x80 + rnd.nextInt(0x80);
    }

    for (int y = h + 2; y >= 1; y --)
    for (int x = w - 2; x >= 1; x --) {
      final int index = x + y * w;

      final int avg =
        (body[index - 1]     ) +
        (body[index    ] << 1) +
        (body[index + 1]     ) >>> 2;

      final int height = avg
        - rnd.nextInt(12)
        - (rnd.nextInt(50) == 0 ? 50 : 0);

      final int burn = Math.max(height, 0);
      body[index - w] = burn;
    }

// -----------------------------------------------------------------------------

    final int[] snapshot = new int[w * h];
    for (int i = snapshot.length - 1; i >= 0; i --) {
      snapshot[i] = palette[body[i]];
    }
    return snapshot;
  }

//=============================================================================

  private static final int[] palette = new int[0x100]; static {
    for (int i = 0x100 - 1; i >= 0; i --) {
      palette[i] = 0xff000000 | Math.min(i << 18, 0xff0000) | (i << 8);
    }
  }
}
