package sample.Data;//package sample.Data;
//
//import javafx.application.Application;
//import javafx.fxml.FXML;
//import javafx.scene.chart.*;
//import javafx.stage.Stage;
//
//public class LineCharts extends Application {
//
//    LineChart<String, Number> lineCharts;
//
//    @Override
//    public void LineCharts() {
//
//        XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
//        series1.setName("Gold");
//        series1.getData().add(new XYChart.Data("Jan", 5));
//        series1.getData().add(new XYChart.Data("Feb", 10));
//        series1.getData().add(new XYChart.Data("Mar", 15));
//        series1.getData().add(new XYChart.Data("Apr", 24));
//        series1.getData().add(new XYChart.Data("May", 34));
//        series1.getData().add(new XYChart.Data("Jun", 36));
//        series1.getData().add(new XYChart.Data("Jul", 36));
//        series1.getData().add(new XYChart.Data("Aug", 34));
//        series1.getData().add(new XYChart.Data("Sep", 24));
//        series1.getData().add(new XYChart.Data("Oct", 15));
//        series1.getData().add(new XYChart.Data("Nov", 10));
//        series1.getData().add(new XYChart.Data("Dec", 5));
//        lineCharts.getData().add(series1);
//
//        XYChart.Series series2 = new XYChart.Series();
//        series2.setName("Sample Data 2");
//        series2.getData().add(new XYChart.Data("Jan", 20));
//        series2.getData().add(new XYChart.Data("Feb", 25));
//        series2.getData().add(new XYChart.Data("Mar", 43));
//        series2.getData().add(new XYChart.Data("Apr", 21));
//        series2.getData().add(new XYChart.Data("May", 53));
//        series2.getData().add(new XYChart.Data("Jun", 75));
//        series2.getData().add(new XYChart.Data("Jul", 35));
//        series2.getData().add(new XYChart.Data("Aug", 23));
//        series2.getData().add(new XYChart.Data("Sep", 27));
//        series2.getData().add(new XYChart.Data("Oct", 35));
//        series2.getData().add(new XYChart.Data("Nov", 63));
//        series2.getData().add(new XYChart.Data("Dec", 19));
//        lineCharts.getData().add(series2);
//
//    }
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//
//    }
//}
////    @Override
////    public void chart (Stage stage) {
////        stage.setTitle("Baker Stock Market");
////        CategoryAxis xAxis = new CategoryAxis();
////        // NumberAxis xAxis = new NumberAxis();
////        NumberAxis yAxis = new NumberAxis();
////        xAxis.setLabel("Timeline");
////        yAxis.setLabel("Stocks");
////        // xAxis.setSide(Side.TOP);
////        LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);
////
////        // LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
////
////        lineChart.setCreateSymbols(true);
//////        lineChart.setTitle("Baker Stock Market");
////    XYChart.Series series1 = new XYChart.Series();
////       series1.setName ("Sample Data 1");
//
//
//
