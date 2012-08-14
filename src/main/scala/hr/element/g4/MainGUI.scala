package hr.element.g4

import java.awt.Toolkit

import scala.actors.Actor.actor
import scala.swing.Component
import scala.swing.Graphics2D
import scala.swing.MainFrame
import scala.swing.SimpleSwingApplication
import scala.swing.event.UIElementResized

import hr.element.etb.img.geom.Dim
import javax.swing.JDialog
import javax.swing.JFrame

object MainGUI extends SimpleSwingApplication {
  private def setLookAndFeel(dlf: Boolean) {
    Toolkit.getDefaultToolkit.setDynamicLayout(dlf)
    System.setProperty("sun.awt.noerasebackground", dlf.toString)

    JFrame.setDefaultLookAndFeelDecorated(dlf)
    JDialog.setDefaultLookAndFeelDecorated(dlf)
  }

  var running = true

  override def startup(args: Array[String]) {
    setLookAndFeel(true)
    top.visible = true

    actor {
      while (running) {
        Vista ! Tick
        Thread.sleep(50)
      }
    }
  }

  var vista = new Component {
    listenTo(this)

    reactions += {
      case UIElementResized(x) =>
        Vista ! Dim(x.size.width, x.size.height)
    }

    override def paint(g: Graphics2D) {
      super.paint(g)
      Vista !? g
    }
  }

  def top = new MainFrame {
    title = "Gore gore gore gore"
    contents = vista
  }

  override def shutdown() {
    running = false
  }
}
