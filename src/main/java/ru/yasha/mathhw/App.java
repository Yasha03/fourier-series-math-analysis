package ru.yasha.mathhw;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Arrays;

public class App extends Application {

    private final int HEIGHT = 720;
    private final int WIDTH = 1080;
    private final int K_X = 40;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group group = new Group();

        Color colorAxis = Color.rgb(0,0,0);

        Line lineX = new Line(0, HEIGHT/2, WIDTH, HEIGHT/2);
        lineX.setStrokeWidth(2);
        lineX.setStroke(colorAxis);

        Line lineY = new Line(WIDTH/2, 0, WIDTH/2, HEIGHT);
        lineY.setStrokeWidth(2);
        lineY.setFill(colorAxis);

        group.getChildren().addAll(
            lineX, lineY
        );

        Operation operation = x -> getResultFunction(x[0], 10) * 40 ;

        /*for(int i = -WIDTH/2; i < WIDTH/2 - 1; i++) {
            group.getChildren().add(
                    new Line(
                        i + WIDTH/2,
                        -operation.execute(i) + HEIGHT/2,
                        i+1 +WIDTH/2,
                        -operation.execute(i+1)+ HEIGHT/2
                    )
            );
        }*/

        // Вывод меток

        double[] labels = new double[(int) (1080/Math.PI)/(K_X)];
        double num = Math.PI * K_X / 2;
        for (int i = 0; i < labels.length; i++){
            labels[i] = num * i;
        }

        System.out.println(Arrays.toString(labels));

        for (int i = 0; i < labels.length; i ++){
            String labelText = "";
            if(i == 2){
                labelText = "π";
            }else{
                labelText = i/2+"π";
            }
            if(i % 2 != 0){
                if(i == 1){
                    labelText = "π/2";
                }else {
                    labelText = i + "π/2";
                }
            }
            Text currentTextPI = new Text(labelText);
            Text currentTextPI2 = new Text("-" + labelText);
            if(i != 0) {
                currentTextPI.setY(HEIGHT / 2 + 30);
                currentTextPI.setX(-10 + labels[i] + WIDTH / 2);

                currentTextPI2.setY(HEIGHT / 2 + 30);
                currentTextPI2.setX(-10 + -labels[i] + WIDTH / 2);
            }

            group.getChildren().addAll(
              new Line(
              labels[i] + WIDTH / 2,
              HEIGHT/2 - 10,
              labels[i] + WIDTH / 2,
              HEIGHT/2 + 10
              ),
              new Line(
                -labels[i] + WIDTH / 2,
                HEIGHT/2 - 10,
                -labels[i] + WIDTH / 2,
                HEIGHT/2 + 10),
                currentTextPI,
                currentTextPI2
            );

        }

        double myPI = Math.PI * K_X;

        Line line1 = new Line(
                -myPI + WIDTH / 2,
                myPI + HEIGHT / 2,
                WIDTH / 2,
                myPI + HEIGHT / 2
        );

        Line line2 = new Line(
                WIDTH / 2,
                myPI + HEIGHT / 2,
                myPI/2 + WIDTH / 2,
                HEIGHT / 2 - 3 * K_X
        );

        Line line3 = new Line(
                myPI/2 + WIDTH / 2,
                HEIGHT / 2 - 3 * K_X,
                myPI + WIDTH / 2,
                HEIGHT / 2 + myPI
        );

        line1.setStrokeWidth(2);
        line2.setStrokeWidth(2);
        line3.setStrokeWidth(2);

        Color colorInitialGraph = Color.rgb(0, 0, 0);

        line1.setStroke(colorInitialGraph);
        line2.setStroke(colorInitialGraph);
        line3.setStroke(colorInitialGraph);

        // initial graph
        group.getChildren().addAll(line1, line2, line3);


        // new graph

        for (int i = -WIDTH/2; i < WIDTH/2 - 1; i++){
            group.getChildren().addAll(
                    new Line(
                            i + WIDTH/2 - myPI/2+10,
                            -getResultFunction(i/K_X, 1000) * K_X + HEIGHT/2,
                            i + 1 + WIDTH/2 - myPI/2+10,
                            -getResultFunction((i+1)/K_X, 1000) * K_X + HEIGHT/2
                    )
            );
        }



        primaryStage.setTitle("График матанализ");
        Scene scene = new Scene(group, WIDTH, HEIGHT);


        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public double getResultFunction(double x, int nMax){
        double a0 = (-3*Math.PI+3)/2;
        double res = a0 / 2;
        for (int n = 1; n <= nMax; n++){
            res += calculateAn(n) * Math.cos(n*x) + calculateBn(n) * Math.sin(n*x);
        }
        return res;
    }

    public double calculateAn(int n){
        double res = ( ((-Math.PI*Math.sin(Math.PI*n))/(n)) + (  ((-(Math.pow(Math.PI, 2)-5*Math.PI)*Math.sin((Math.PI*n)/2))/(Math.PI*n))   + ((6*Math.cos((Math.PI*n)/2))/(Math.PI*(n*n))) - (6/(Math.PI*n*n)) ) + ( ((-1*(Math.PI*Math.PI+Math.PI)*Math.sin((Math.PI*n)/2))/(Math.PI*n)) + ((6*Math.cos((Math.PI*n)/2))/(Math.PI*n*n)) - ((6*Math.pow(-1, n))/(Math.PI*n*n)) )  ) / (Math.PI);
        return res;
    }

    public double calculateBn(int n){
        double pi = Math.PI;
        double res = ( ( (pi)/(n) - (Math.pow(-1, n)*pi)/(n) ) + ( ((6*Math.sin((pi*n)/2))/(pi*n*n)) + (((pi*pi-5*pi)*Math.cos((pi*n)/2))/(pi*n)) + ((2*pi-pi*pi)/(pi*n)) ) + (((6*Math.sin((pi*n)/2))/(pi*n*n)) + (((pi*pi+pi)*Math.cos((pi*n)/2))/(pi*n)) + ((2*Math.pow(-1, n)*pi - pi*pi * Math.pow(-1, n))/(pi*n))) ) / (Math.PI);
        return res;
    }

    @FunctionalInterface
    public interface Operation {
        double execute(double... nums);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
