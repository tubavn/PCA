import Automata.GeneralAutomata;
import Automata.PowerAutomata;
import Visualization.Visualization;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import edu.uci.ics.jung.graph.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyToolWindowFactory implements ToolWindowFactory {
  private ParseCode currentCode;
  //private JTabbedPane tabs = new JTabbedPane();
  private JTabbedPane tabs = new com.intellij.ui.components.JBTabbedPane();
  public void createToolWindowContent(Project project, ToolWindow toolWindow) {
//    System.out.print(project.getBasePath());
    LoadGraph(project, toolWindow);
  }

  private void LoadGraph(Project project, ToolWindow toolWindow) {
    currentCode = new ParseCode(project.getBasePath());
//    frame = new JFrame( "PSA Form Demo" );
//    PowerAutomata audioAutomata = new PowerAutomata("D:/Dropbox/Code/Plugin1/src/Data/Audio.txt");
//    PowerAutomata GPSAutomata = new PowerAutomata("D:/Dropbox/Code/Plugin1/src/Data/GPS.txt");
//    PowerAutomata LCDAutomata = new PowerAutomata("D:/Dropbox/Code/Plugin1/src/Data/LCD.txt");
//    PowerAutomata cellularAutomata = new PowerAutomata("D:/Dropbox/Code/Plugin1/src/Data/Cellular.txt");
//    PowerAutomata wifiAutomata = new PowerAutomata("D:/Dropbox/Code/Plugin1/src/Data/wifi.txt");

    PowerAutomata audioAutomata = new PowerAutomata("C:/Users/MyPC/Desktop/Code/Plugin1/src/Data/Audio.txt").simplifyAutomata(currentCode.audioCommands);
    PowerAutomata GPSAutomata = new PowerAutomata("C:/Users/MyPC/Desktop/Code/Plugin1/src/Data/GPS.txt").simplifyAutomata(currentCode.GPSCommands);
    PowerAutomata LCDAutomata = new PowerAutomata("C:/Users/MyPC/Desktop/Code/Plugin1/src/Data/LCD.txt").simplifyAutomata(currentCode.LCDCommands);
    PowerAutomata cellularAutomata = new PowerAutomata("C:/Users/MyPC/Desktop/Code/Plugin1/src/Data/Cellular.txt").simplifyAutomata(currentCode.cellularCommands);
    PowerAutomata wifiAutomata = new PowerAutomata("C:/Users/MyPC/Desktop/Code/Plugin1/src/Data/wifi.txt").simplifyAutomata(currentCode.wifiCommands);
    GeneralAutomata ga = new GeneralAutomata(audioAutomata, GPSAutomata, LCDAutomata, cellularAutomata, wifiAutomata);

    tabs.removeAll();
    JButton b = new JButton("Analysis");
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        LoadGraph(project, toolWindow);
      }
    });
    JPanel panel = AddPanel(ga);
    panel.add(b,BorderLayout.SOUTH);
    tabs.add("General", panel);
    tabs.add("Audio", AddPanel(audioAutomata));
    tabs.add("LCD", AddPanel(LCDAutomata));
    tabs.add("Wifi", AddPanel(wifiAutomata));
    tabs.add("Cellular", AddPanel(cellularAutomata));
    tabs.add("GPS", AddPanel(GPSAutomata));

    //
    Component component = toolWindow.getComponent();
    component.getParent().add(tabs);
  }



  private JPanel AddPanel(PowerAutomata pa) {
    JPanel tab = new JPanel();
    Visualization d = new Visualization();
    d.g = (Graph) pa.ToGrahp();
    JPanel panel = d.VisualizationGraph();
    tab.add(panel);
    return tab;
  }
}
