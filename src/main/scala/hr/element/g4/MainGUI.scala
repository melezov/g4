package hr.element.g4

import swing._
import javax.swing.JDialog
import javax.swing.JFrame
import java.awt.Toolkit
import javax.swing.JComponent
import java.awt.Graphics
import java.awt.Color
import scala.concurrent.ops.spawn

object MainGUI extends SimpleSwingApplication {
  private def setLookAndFeel(dlf: Boolean) {
    Toolkit.getDefaultToolkit.setDynamicLayout(dlf)
    System.setProperty("sun.awt.noerasebackground", dlf.toString)

    JFrame.setDefaultLookAndFeelDecorated(dlf)
    JDialog.setDefaultLookAndFeelDecorated(dlf)
  }

  var running = true
  var vista = new Vista(600, 300)

  override def startup(args: Array[String]) {
    setLookAndFeel(true)
    top.visible = true

    spawn {
      println("ICH STARTE")
      while (running) {
        vista.doTick()
        Thread.sleep(10)
      }
      println("ICH DIE")
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
