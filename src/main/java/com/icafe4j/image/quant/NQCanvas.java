package com.icafe4j.image.quant;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;
import java.io.IOException;


public class NQCanvas extends Canvas {

  /**
   *
   */
  private static final long serialVersionUID = -5075876371476649415L;
  private Image image = null;
  private String url = null;

  public NQCanvas(String url) {
    this.url = url;
  }

  public static void main(String[] args) {
    NQCanvas canvas = new NQCanvas(args[0]);
    java.awt.Frame frame = new java.awt.Frame("NeuQuant Test");
    frame.setSize(500, 500);
    frame.add(canvas);
    frame.setVisible(true);
    frame.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent e) {
        System.out.println("Closing program");
        System.exit(0);
      }
    });
  }

  public void set() {
    try {
      System.out.println("Fetching " + url + " ...");
      Image img = null;
      try {
        java.net.URL u = new java.net.URL(url);
        img = Toolkit.getDefaultToolkit().getImage(u);
      } catch (Exception ee) {
        // filename
        img = Toolkit.getDefaultToolkit().getImage(url);
      }
      java.awt.MediaTracker tracker = new java.awt.MediaTracker(this);
      tracker.addImage(img, 0);
      try {
        tracker.waitForID(0);
      } catch (InterruptedException ignored) {
      }
      System.out.println("w = " + img.getWidth(this));
      System.out.println("h = " + img.getHeight(this));
      set(img);
    } catch (Exception ex) {
      System.out.println(ex);
    }
  }

  public void set(Image img) throws IOException {
    int w = img.getWidth(this);
    int h = img.getHeight(this);
    int[] pixels = new int[w * h];
    java.awt.image.PixelGrabber pg
        = new java.awt.image.PixelGrabber(img, 0, 0, w, h, pixels, 0, w);
    try {
      pg.grabPixels();
    } catch (InterruptedException ignored) {
    }
    if ((pg.getStatus() & java.awt.image.ImageObserver.ABORT) != 0) {
      throw new IOException("Image pixel grab aborted or errored");
    }
    NeuQuant nq = new NeuQuant(pixels);
    pixels = nq.quantize();
    this.image = this.createImage(new MemoryImageSource(w, h, pixels, 0, w));
  }

  public void paint(Graphics graphics) {
    if (image == null) {
      set();
    }
    if (image != null) {
      graphics.drawImage(image, 0, 0, this);
    }
  }
}