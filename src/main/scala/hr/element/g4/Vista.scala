package hr.element.g4

import swing._
import javax.swing.JDialog
import javax.swing.JFrame
import java.awt.Toolkit
import javax.swing.JComponent
import java.awt.Graphics
import java.awt.Color
import java.awt.image.BufferedImage

class Vista(w: Int, h: Int) extends Component {
  val fB = new FireBuffer(w, h)
  val bI = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB)

  def doTick() {
    bI.setRGB(0, 0, w, h, fB.update(), 0, w)
    this.repaint()
  }

  override def paint(g: Graphics2D) {
    super.paintComponent(g)

    g.drawImage(bI, 0, 0, null)
  }
}
