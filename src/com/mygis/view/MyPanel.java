package com.mygis.view;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.mygis.model.layer.Layer;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Map map;
    private Layer layer;

    public Layer getLayer() {
        return layer;
    }

    public void setLayer(Layer layer) {
        this.layer = layer;
        map.setLayer(layer);
        map.refreshMap();
        map.fullView();
    }

    public MyPanel(int width, int height, Layer layer) {
        this.setSize(width, height);
        this.setPreferredSize(new Dimension(width, height));
        this.layer = layer;
        map = new Map(width, height, layer, this);
        map.fullView();
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(map.getImage(), 0, 0, this);
    }

    public Map getMap() {
        return map;
    }

    //Tien
    public void readRequest(File file) {
        map.setRequestReader(file);
        map.drawRequest();
    }
    
    public void readSolution(File file) {
        try {
            map.setSolutionReader(file);
            map.drawSolution();
        } catch (Exception ex) {
            Logger.getLogger(MyPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
