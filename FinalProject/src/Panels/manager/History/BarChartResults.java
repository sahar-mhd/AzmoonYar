package Panels.manager.History;

import java.util.ArrayList;
import javax.swing.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarChartResults extends JDialog {

    private static final long serialVersionUID = 1L;
    private ArrayList<Double> avarages;
    private ArrayList<String> exams;
    private ArrayList<Double> grades;
    private ArrayList<String> ids;
    private String exam;
    private int[] amount = new int[3];

    public BarChartResults(int[] amount,String exam) {
        this.amount = amount;
        this.exam=exam;

        // Create Dataset
        CategoryDataset dataset = createDatasetR();

        //Create chart
        JFreeChart chart = ChartFactory.createBarChart(
                "Results", //Chart Title
                "", // Category axis
                "avarage", // Value axis
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );
        ChartPanel panel = new ChartPanel(chart);
        add(panel);
    }

    public BarChartResults(ArrayList<Double> avarages, ArrayList<String> exams) {
        this.avarages = avarages;
        this.exams = exams;

        // Create Dataset
        CategoryDataset dataset = createDatasetA();

        //Create chart
        JFreeChart chart = ChartFactory.createBarChart(
                "Results", //Chart Title
                "", // Category axis
                "avarage", // Value axis
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );
        ChartPanel panel = new ChartPanel(chart);
        add(panel);
    }

    public BarChartResults(ArrayList<Double> grades, ArrayList<String> ids, String exam) {
        this.grades = grades;
        this.ids = ids;
        this.exam = exam;

        // Create Dataset
        CategoryDataset dataset = createDatasetF();

        //Create chart
        JFreeChart chart = ChartFactory.createBarChart(
                "Results", //Chart Title
                "", // Category axis
                "grades", // Value axis
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        ChartPanel panel = new ChartPanel(chart);
        add(panel);
    }

    private CategoryDataset createDatasetA() {
        //azmoon be azmoon
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < exams.size(); i++) {
            dataset.addValue(avarages.get(i), "exams", exams.get(i));
        }
        return dataset;
    }

    private CategoryDataset createDatasetF() {
        //fard be fard
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < ids.size(); i++) {
            dataset.addValue(grades.get(i), exam, ids.get(i));
        }
        return dataset;
    }

    private CategoryDataset createDatasetR() {
        //review results
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(amount[0], exam, "ساده");
        dataset.addValue(amount[1], exam, "متوسط");
        dataset.addValue(amount[2], exam, "سخت");
        return dataset;
    }
}
