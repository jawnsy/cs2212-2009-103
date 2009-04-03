package ca.uwo.garage;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * This class represents a status bar
 *
 */
// Apparently Java has no native StatusBar class... so make one
public class StatusBar
  extends JPanel
{
  private static final long serialVersionUID = 1L;

  private Component m_left, m_right;

  /**
   * The constructor for this class
   */
  public StatusBar() {
    /* By default, make a StatusBar with JLabels on the left and right.
     * 
     * The JLabel default cannot be empty since the JPanel will be collapsed.
     * Since we set the PreferredSize, though, empty labels are okay.
     */
    this(new JLabel(" ", JLabel.LEFT), new JLabel(" ", JLabel.RIGHT));
  }
  /**
   * Another constructor for this class
   * @param left
   * @param right
   */
  public StatusBar(Component left, Component right) {
    super();

    // Use the BorderLayout so we can align the text to either left or right
    setLayout(new BorderLayout());
    
    // This is the "minimum" StatusBar height. Use this OR an empty label
    setPreferredSize(new Dimension(0, 18));

    m_left = left; // just blindly copy the Component
    add(m_left, BorderLayout.WEST);

    m_right = right;
    add(m_right, BorderLayout.EAST);
  }

  public void setLeft(String text) {
    // This method only works with JLabels
    if (!(m_left instanceof JLabel))
      return;

    // We can cast the Component to a JLabel and set the text appropriately
    ((JLabel)m_left).setText(text);
  }
  public void setLeft(Component component) {
    m_left = component;
  }
  public Component getLeft() {
    return m_left;
  }

  public void setRight(String text) {
    // This method only works with JLabels
    if (!(m_right instanceof JLabel))
      return;

    // We can cast the Component to a JLabel and set the text appropriately
    ((JLabel)m_right).setText(text);
  }
  public void setRight(Component component) {
    m_right = component;
  }
  public Component getRight() {
    return m_right;
  }
}
