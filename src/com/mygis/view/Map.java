package com.mygis.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import com.mygis.model.common.Envelop;
import com.mygis.model.crs.transform.AffineTransform;
import com.mygis.model.geom.Geometry;
import com.mygis.model.geom.Point;
import com.mygis.model.layer.Layer;
import com.mygis.view.style.Style;
import com.mygis.view.util.DrawHelper;

public class Map {

    private int width;
    private int height;

    private Layer layer;
    private AffineTransform geometryTransform;

    private BufferedImage bufferImage;
    private Graphics2D graphic;
    private Style style;

    private Component component;

    public Map(int width, int height, Layer layer, Component component) {
        this.width = width;
        this.height = height;
        this.layer = layer;
        this.component = component;
        geometryTransform = new AffineTransform();
        bufferImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        graphic = (Graphics2D) bufferImage.getGraphics();
        style = new Style();
    }

    public Layer getLayer() {
        return layer;
    }

    public void setLayer(Layer layer) {
        this.layer = layer;
    }

    public void fullView() {
        geometryTransform = new AffineTransform();
        Envelop envelop = getEnvelop();
        Point center = envelop.getCenterPoint();

        double scale = computeScale(envelop);

        geometryTransform = geometryTransform.accumulate(AffineTransform.scale(center.getX(), center.getY(), scale, scale));

        double dx = width / 2 - center.getX();
        double dy = height / 2 - center.getY();

        geometryTransform = geometryTransform.accumulate(AffineTransform.pan(dx, dy));

        drawMap();
    }

    public void drawMap() {
        graphic.clearRect(0, 0, width, height);

        graphic.setPaint(Color.white);
        graphic.fillRect(0, 0, width, height);

        for (int i = 0; i < layer.size(); i++) {
            Geometry geom = layer.getGeometry(i).transform(geometryTransform);
            DrawHelper.drawGeometry(graphic, style, geom);
        }

        bufferImage.flush();
    }

    public void refreshMap() {
        drawMap();
        component.repaint();
    }

    public Image getImage() {
        return bufferImage;
    }

    public void transform(AffineTransform otherTransform) {
        geometryTransform = geometryTransform.accumulate(otherTransform);
    }

    public Envelop getEnvelop() {
        return layer.getEnvelop();
    }

    private double computeScale(Envelop envelop) {
        double wScale = (width - 10) / envelop.getWidth();
        double hScale = (height - 10) / envelop.getHeight();
        return Math.min(wScale, hScale);
    }

}
