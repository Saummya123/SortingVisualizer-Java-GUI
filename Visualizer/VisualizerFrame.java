package Visualizer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class VisualizerFrame extends JFrame {

    private final int MAX_SPEED = 1000;
    private final int MIN_SPEED = 1;
    private final int MAX_SIZE = 500;
    private final int MIN_SIZE = 5;
    private final int DEFAULT_SPEED = 20;
    private final int DEFAULT_SIZE = 20;

    private final String[] Sorts = {"Bubble", "Selection", "Insertion", "Merge", "Radix LSD", "Radix MSD", "Bubble(fast)", "Selection(fast)", "Insertion(fast)", "Quick", "Quick(fast)"};

    private int sizeModifier;

    private JPanel wrapper, arrayWrapper, buttonWrapper;
    private JPanel[] squarePanels;
    private JButton start;
    private JComboBox<String> selection;
    private JSlider speed, size;
    private JLabel speedVal, sizeVal, timerLabel;
    private GridBagConstraints c;
    private JCheckBox stepped;

    public VisualizerFrame() {
        super("Sorting Visualizer by Saummya Jalan");

        start = new JButton("Start Sorting");
        selection = new JComboBox<>(Sorts);
        speed = new JSlider(MIN_SPEED, MAX_SPEED, DEFAULT_SPEED);
        size = new JSlider(MIN_SIZE, MAX_SIZE, DEFAULT_SIZE);
        speedVal = new JLabel("Speed: 20 ms");
        sizeVal = new JLabel("Size: 20 values");
        timerLabel = new JLabel("⏱ Time: 0 ms");
        stepped = new JCheckBox("Stepped Mode");

        wrapper = new JPanel(new BorderLayout());
        buttonWrapper = new JPanel();
        arrayWrapper = new JPanel(new GridBagLayout());
        c = new GridBagConstraints();
        c.insets = new Insets(0, 1, 0, 1);
        c.anchor = GridBagConstraints.SOUTH;

        // UI Colors
        wrapper.setBackground(Color.DARK_GRAY);
        buttonWrapper.setBackground(Color.DARK_GRAY);
        arrayWrapper.setBackground(Color.BLACK);
        speedVal.setForeground(Color.WHITE);
        sizeVal.setForeground(Color.WHITE);
        timerLabel.setForeground(Color.WHITE);
        stepped.setForeground(Color.WHITE);

        // Speed Slider
        speed.setMinorTickSpacing(10);
        speed.setMajorTickSpacing(100);
        speed.setPaintTicks(true);
        speed.addChangeListener(e -> {
            speedVal.setText("Speed: " + speed.getValue() + " ms");
            SortingVisualizer.sleep = speed.getValue();
        });

        // Size Slider
        size.setMinorTickSpacing(10);
        size.setMajorTickSpacing(100);
        size.setPaintTicks(true);
        size.addChangeListener(e -> {
            sizeVal.setText("Size: " + size.getValue() + " values");
            SortingVisualizer.sortDataCount = size.getValue();
        });

        // Button Actions
        start.addActionListener(e -> {
            long startTime = System.currentTimeMillis();
            SortingVisualizer.startSort((String) selection.getSelectedItem());
            long endTime = System.currentTimeMillis();
            timerLabel.setText("⏱ Time: " + (endTime - startTime) + " ms");
        });

        stepped.addActionListener(e -> SortingVisualizer.stepped = stepped.isSelected());

        // Assemble UI
        buttonWrapper.add(stepped);
        buttonWrapper.add(speedVal);
        buttonWrapper.add(speed);
        buttonWrapper.add(sizeVal);
        buttonWrapper.add(size);
        buttonWrapper.add(start);
        buttonWrapper.add(selection);
        buttonWrapper.add(timerLabel);

        wrapper.add(buttonWrapper, BorderLayout.SOUTH);
        wrapper.add(arrayWrapper, BorderLayout.CENTER);

        add(wrapper);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                sizeModifier = (int) ((getHeight() * 0.9) / squarePanels.length);
            }
        });
    }

    public void preDrawArray(Integer[] squares) {
        squarePanels = new JPanel[SortingVisualizer.sortDataCount];
        arrayWrapper.removeAll();
        sizeModifier = (int) ((getHeight() * 0.9) / squarePanels.length);

        for (int i = 0; i < SortingVisualizer.sortDataCount; i++) {
            squarePanels[i] = new JPanel();
            squarePanels[i].setPreferredSize(new Dimension(SortingVisualizer.blockWidth, squares[i] * sizeModifier));
            squarePanels[i].setBackground(Color.CYAN);
            arrayWrapper.add(squarePanels[i], c);
        }
        repaint();
        validate();
    }

    public void reDrawArray(Integer[] x) {
        reDrawArray(x, -1);
    }

    public void reDrawArray(Integer[] x, int y) {
        reDrawArray(x, y, -1);
    }

    public void reDrawArray(Integer[] x, int y, int z) {
        reDrawArray(x, y, z, -1);
    }

    public void reDrawArray(Integer[] squares, int working, int comparing, int reading) {
        arrayWrapper.removeAll();
        for (int i = 0; i < squarePanels.length; i++) {
            squarePanels[i] = new JPanel();
            squarePanels[i].setPreferredSize(new Dimension(SortingVisualizer.blockWidth, squares[i] * sizeModifier));

            // Unique Colors
            if (i == working)
                squarePanels[i].setBackground(new Color(0x7CFC00)); // LimeGreen
            else if (i == comparing)
                squarePanels[i].setBackground(Color.MAGENTA);
            else if (i == reading)
                squarePanels[i].setBackground(Color.ORANGE);
            else
                squarePanels[i].setBackground(Color.CYAN);

            arrayWrapper.add(squarePanels[i], c);
        }
        repaint();
        validate();
    }
}
