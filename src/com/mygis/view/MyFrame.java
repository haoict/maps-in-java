package com.mygis.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import com.mygis.data.io.WKTParser;
import com.mygis.model.crs.transform.AffineTransform;
import com.mygis.model.geom.Geometry;
import com.mygis.model.geom.Point;
import com.mygis.model.layer.Layer;

public class MyFrame extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private MyPanel myPanel;
    private Layer layer;
    private List<Point> listP;
    
    private int width;
    private int height;

    private int leftButtonX;
    private int leftButtonY;
    private boolean leftButtonDraged = false;

    private JProgressBar progressBar;
    private JLabel statusLabel;

    public MyFrame(int width, int height) {
        this.width = width;
        this.height = height;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setSize(width, height);

        createMenuBar();

        layer = new Layer();
        myPanel = new MyPanel(width - 5, height - 125, layer);

        this.add(myPanel);

        addButton(myPanel);

        createStatusBar();
    }

    private void createMenuBar() {

        JMenuBar menubar = new JMenuBar();

        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);

        JMenuItem eMenuItem1 = new JMenuItem("Open");
        eMenuItem1.setMnemonic(KeyEvent.VK_E);
        eMenuItem1.setToolTipText("Open map file");
        eMenuItem1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                statusLabel.setText("Loading map");
                final JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    try {
                        layer = getGraph(file.getPath());
                        myPanel.setLayer(layer);
                        myPanel.getMap().setListP(listP);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                    statusLabel.setText("Done.");
                } else {
                    statusLabel.setText("Click File -> Open to open a map ");
                }
            }
        });
        
        //Tien
        JMenuItem eMenuItem2 = new JMenuItem("Read Request");
        eMenuItem2.setMnemonic(KeyEvent.VK_E);
        eMenuItem2.setToolTipText("Open request file");
        eMenuItem2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                statusLabel.setText("Loading request");
                final JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    try {
                        myPanel.readRequest(file);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                    statusLabel.setText("Done.");
                } else {
                    statusLabel.setText("Click File -> Open to open a map ");
                }
            }
        });
        
        JMenuItem eMenuItem3 = new JMenuItem("Import solution path");
        eMenuItem3.setMnemonic(KeyEvent.VK_E);
        eMenuItem3.setToolTipText("Import solution path");
        eMenuItem3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                statusLabel.setText("Loading solution file");
                final JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    try {
                        myPanel.readSolution(file);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                    statusLabel.setText("Done.");
                } else {
                    statusLabel.setText("Click File -> Open to open a map ");
                }
            }
        });


        JMenuItem eMenuItem = new JMenuItem("Exit");
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit application");
        eMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        file.add(eMenuItem1);
        file.add(eMenuItem2);
        file.add(eMenuItem3);
        file.add(eMenuItem);
        menubar.add(file);

        setJMenuBar(menubar);
    }

    private void createStatusBar() {
        // create the status bar panel and shove it down the bottom of the frame
        progressBar = new JProgressBar();
        progressBar.setVisible(false);
        progressBar.setIndeterminate(true);
        //progressBar.setStringPainted(true);

        JPanel statusPanel = new JPanel();
        statusPanel.setBorder((Border) new BevelBorder(BevelBorder.LOWERED));
        add(statusPanel, BorderLayout.SOUTH);
        statusPanel.setPreferredSize(new Dimension(this.getWidth() - 20, 20));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        statusLabel = new JLabel("Click File -> Open to open a map ");
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusPanel.add(statusLabel);
        statusPanel.add(progressBar);
    }

    /* Example
     * WKTParser wkt = new WKTParser(); Geometry g1 = wkt.read(
     * "Polygon(1.0 6.0,50.999998414659173 10.0039816335536662,100.0079632645824341 -300.999993658637697,-300.9999857319385903 100.9880551094385923,1.0 6.0)"
     * );
     *
     * Geometry g2 = wkt.read("Polygon(10 10,100 300,400 10,10 10)");
     *
     * Geometry g3 = wkt.read("LineString(400 400,500 500)");
     *
     * Geometry g4 = wkt.read("POINT(523.09 234)");
     *
     * Geometry g5 = wkt.read("MULTIPOINT(1230.09 234,334 23)");
     *
     * Geometry g6 = wkt
     * .read("MULTILINESTRING((10 20,1230.09 234,334 23,10 20)" +
     * ",(30 20,45 33,23 10,30 20))");
     *
     * Geometry g7 =
     * wkt.read("MULTIPOLYGON(((110 120,140 150,160 170,110 120))," +
     * "((100 100,0 200,400 300,200 100,100 100)))");
     *
     * Geometry g8 = wkt.read(
     * "MULTIPOLYGON(((0 0,0 100,100 100,100 0,0 0),(10 10,10 40,90 90, 90 10, 10 10)))"
     * );
     *
     * Layer layer = new Layer(); layer.addGeometry(g1);
     * layer.addGeometry(g2); layer.addGeometry(g3); layer.addGeometry(g4);
     * layer.addGeometry(g5); layer.addGeometry(g6); layer.addGeometry(g7);
     * layer.addGeometry(g8);
     */
    @SuppressWarnings("resource")
    public Layer getGraph(String namedata) throws Exception {
        Layer layer = new Layer();
        WKTParser wkt = new WKTParser();
        listP = new ArrayList<Point>();
        
        Scanner sc = new Scanner(new File(namedata));
        String s;
        String step = "get point";
        System.out.println("LOAD GRAPH");

        while (sc.hasNext()) {

            s = sc.nextLine();
            //System.out.println(s);
            String[] ss = s.split("\\s+");
            if (s.equals("-1")) {
                step = "get edge";
                continue;
            }
            if (step.equals("get point")) {
                try {
                    int id = Integer.parseInt(ss[0]);
                    double x = Double.parseDouble(ss[1]);
                    double y = -Double.parseDouble(ss[2]);

                    Geometry geopoint = wkt.read("POINT(" + x + " " + y + ")");
                    layer.addGeometry(geopoint);
                    listP.add(new Point(id, x, y));
                } catch (Exception ex) {
                    throw new Exception("Invalid map file");
                }
            }
            //Collections.sort(listP, c);

            if (step.equals("get edge")) {
                try {
                    int id1 = Integer.parseInt(ss[0]);
                    int id2 = Integer.parseInt(ss[1]);
                    Geometry geoEdge = wkt.read("LineString("
                            + listP.get(Collections.binarySearch(listP, new Point(id1), Point.getComparator())).getX()
                            + " "
                            + listP.get(Collections.binarySearch(listP, new Point(id1), Point.getComparator())).getY()
                            + ","
                            + listP.get(Collections.binarySearch(listP, new Point(id2), Point.getComparator())).getX()
                            + " "
                            + listP.get(Collections.binarySearch(listP, new Point(id2), Point.getComparator())).getY()
                            + ")");

                    layer.addGeometry(geoEdge);
                } catch (Exception e) {
                    continue;
                }
            }
        }
        System.out.println("END GRAPH.");
        sc.close();
        return layer;
    }

    private void addButton(final MyPanel myPanel) {

        final Map map = myPanel.getMap();

        JButton fullViewButton = new JButton();
        fullViewButton.setText("Full");
        fullViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                map.fullView();
                map.refreshMap();
            }
        });
        this.add(fullViewButton);

        myPanel.addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                double scale = 1 - e.getWheelRotation() / 3d;
                map.transform(AffineTransform.scale(e.getX(), e.getY(), scale, scale));
                map.refreshMap();
            }
        });

        myPanel.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                if (leftButtonDraged) {
                    map.transform(AffineTransform.pan(e.getX() - leftButtonX, e.getY() - leftButtonY));
                    map.refreshMap();
                    leftButtonX = e.getX();
                    leftButtonY = e.getY();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
        myPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    leftButtonDraged = true;
                    leftButtonX = e.getX();
                    leftButtonY = e.getY();
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {

                    map.transform(AffineTransform.pan(e.getX() - leftButtonX, e.getY() - leftButtonY));
                    map.refreshMap();
                    leftButtonDraged = false;
                    leftButtonX = e.getX();
                    leftButtonY = e.getY();
                }
            }
        });

        JButton button1 = new JButton();
        button1.setText("Left");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                map.transform(AffineTransform.pan(-50, 0));
                map.refreshMap();
            }
        });
        this.add(button1);

        JButton button2 = new JButton();
        button2.setText("Right");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                map.transform(AffineTransform.pan(50, 0));
                map.refreshMap();
            }
        });
        this.add(button2);
        
        JButton button6 = new JButton();
        button6.setText("Up");
        button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                map.transform(AffineTransform.pan(0, -50));
                map.refreshMap();
            }
        });
        this.add(button6);
        
        JButton button7 = new JButton();
        button7.setText("Down");
        button7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                map.transform(AffineTransform.pan(0, 50));
                map.refreshMap();
            }
        });
        this.add(button7);

        JButton button3 = new JButton();
        button3.setText("Zoom int");
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                double scale = 1.5;
                map.transform(AffineTransform.scale(width / 2, height / 2, scale, scale));
                map.refreshMap();
            }
        });
        this.add(button3);

        JButton button4 = new JButton();
        button4.setText("Zoom out");
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                double scale = 0.5;
                map.transform(AffineTransform.scale(width / 2, height / 2, scale, scale));
                map.refreshMap();
            }
        });
        this.add(button4);
    }

}
