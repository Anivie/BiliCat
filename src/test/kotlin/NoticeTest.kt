import java.awt.Image
import java.awt.SystemTray
import java.awt.Toolkit
import java.awt.TrayIcon
import java.awt.TrayIcon.MessageType

fun main() {
    //Obtain only one instance of the SystemTray object
    val tray = SystemTray.getSystemTray()
    Thread.currentThread().contextClassLoader.getResourceAsStream("")
    //If the icon is a file
    val image: Image = Toolkit.getDefaultToolkit().createImage("icon.png")
    //Alternative (if the icon is on the classpath):
    //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));
    val trayIcon = TrayIcon(image, "Java AWT Tray Demo")
    //Let the system resize the image if needed
    trayIcon.isImageAutoSize = true
    //Set tooltip text for the tray icon
    trayIcon.toolTip = "System tray icon demo"
    tray.add(trayIcon)
    trayIcon.displayMessage("Hello, World", "Java Notification Demo", MessageType.INFO)
}
