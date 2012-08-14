package hr.element.g4

import java.awt.image.BufferedImage

import scala.actors.Actor
import scala.actors.TIMEOUT
import scala.swing.Graphics2D

import hr.element.etb.img.geom.Dim

case object Tick

object Vista extends Actor {
  private var buf: Option[Vista] = None

  start()

  private def setDim(dim: Dim) {
    buf match {
      case Some(b) if dim.w == b.w && dim.h == b.h =>
      case _ =>
        buf = Some(new Vista(dim.w, dim.h))
    }
  }

  private def doBitBlt(g: Graphics2D) {
    for(b <- buf) {
      b.bitBlt(g)
    }
  }

  private def doTick() {
    for(b <- buf) {
      b.doTick()
      MainGUI.vista.repaint()
    }
  }

  private def readDim(dim: Dim) {
    var newDim = dim

    while(receiveWithin(0) {
      case newestDim: Dim =>
        newDim = newestDim
        true

      case TIMEOUT =>
        false
    }) {}

    setDim(newDim)
  }

  private def readTick() {
    while(receiveWithin(0) {
      case Tick =>
        true

      case TIMEOUT =>
        false
    }){}

    doTick()
  }

  private def readBitBlt(g: Graphics2D) {
    val newGraphics = g
    doBitBlt(g)
  }

  def act {
    while(true) {
      receiveWithin(0) {
        case dim: Dim =>
          readDim(dim)

        case TIMEOUT =>
          receiveWithin(0) {
            case dim: Dim =>
              readDim(dim)

            case g: Graphics2D =>
              readBitBlt(g)
              reply()

            case TIMEOUT =>
              receive {
                case dim: Dim =>
                  readDim(dim)

                case g: Graphics2D =>
                  readBitBlt(g)
                  reply()

                case Tick =>
                  readTick()
              }
          }
      }
    }
  }
}

private class Vista(val w: Int, val h: Int) {
  private val fB = new FireBuffer(w, h)
  private val bI = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB)

  private var lastUpdate: Array[Int] = _
  doTick()

  def doTick() {
    lastUpdate = fB.update()
  }

  def bitBlt(g: Graphics2D) = {
    bI.setRGB(0, 0, w, h, lastUpdate, 0, w)
    g.drawImage(bI, 0, 0, null)
  }
}

