package com.mygis.view.style;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;

public class Style {

    public int pointWidth = 5;
    public int solutionPointWidth = 10;
    
    public Color pointColor = Color.blue;
    public Color requestColor = Color.yellow;
    
    public Color solutionPointColor = Color.red;

    public Paint lineColor = Color.ORANGE;
    public Paint solutionLineColor = Color.BLACK;
    public Paint fillColor = Color.green;

    public Stroke stroke = new BasicStroke();
}
