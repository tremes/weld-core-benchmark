/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.weld.benchmark.charts;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.text.G2TextMeasurer;
import org.jfree.text.TextBlock;
import org.jfree.text.TextUtilities;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;

/**
 * @author Kirill Gaevskii
 */
public class Chart {

    public final String NAME;

    private final String Y_AXIS_NAME;

    private final String X_AXIS_NAME;

    private static final int GRAPH_HEIGHT = 550;
    private static final int ONE_PART_WIDTH = 250;

    private final DefaultCategoryDataset lineChartDataset;

    public Chart(String name, String xAxisName, String yAxisName) {
        this.NAME = toCamelCase(name);
        this.Y_AXIS_NAME = toCamelCase(yAxisName);
        this.X_AXIS_NAME = toCamelCase(xAxisName);
        lineChartDataset = new DefaultCategoryDataset();
    }

    public Boolean saveImageTo(String path) {
        try {
            JFreeChart lineChartObject = ChartFactory.createBarChart(NAME, X_AXIS_NAME, Y_AXIS_NAME, lineChartDataset, PlotOrientation.VERTICAL, true, true, false);

            CategoryPlot plot = (CategoryPlot) lineChartObject.getPlot();

            plot.setDomainAxis(new CategoryAxis() {
                private static final long serialVersionUID = 1L;

                @Override
                protected TextBlock createLabel(@SuppressWarnings("rawtypes") Comparable category, float width, RectangleEdge edge, Graphics2D g2) {
                    TextBlock label = TextUtilities.createTextBlock(category.toString(), getTickLabelFont(category), getTickLabelPaint(category),
                            ONE_PART_WIDTH - 30, 2, new G2TextMeasurer(g2));

                    label.setLineAlignment(HorizontalAlignment.LEFT);
                    return label;
                }
            });

            File lineChart = new File(path, NAME + ".png");
            ChartUtilities.saveChartAsPNG(lineChart, lineChartObject, ONE_PART_WIDTH * lineChartDataset.getColumnCount(), GRAPH_HEIGHT);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void addValue(double value, String rowKey, String columnKey) {
        lineChartDataset.setValue(value, rowKey, columnKey);
    }

    private static String toCamelCase(String value) {
        return (value != null && value.length() > 0) ? value.substring(0, 1).toUpperCase() + value.substring(1) : value;
    }
}
