package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class MainApp extends Application {

    private BufferedImage originalImage;

    @Override
    public void start(Stage stage) {
        ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(600);
        imageView.setFitHeight(400);

        Button openButton = new Button("画像を開く");
        openButton.setOnAction(e -> openImage(stage, imageView));

        BorderPane root = new BorderPane();
        root.setTop(openButton);
        root.setCenter(imageView);

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("SVG Vectorizer - カラー表示");
        stage.setScene(scene);
        stage.show();
    }

    private void openImage(Stage stage, ImageView imageView) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("画像を選択");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("画像ファイル", "*.png", "*.jpg", "*.jpeg")
        );

        File file = chooser.showOpenDialog(stage);
        if (file != null) {
            try {
                originalImage = ImageIO.read(file);
                imageView.setImage(processImage(originalImage));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // カラー画像そのまま表示
    private Image processImage(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        WritableImage wImage = new WritableImage(w, h);

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int rgb = img.getRGB(x, y);
                wImage.getPixelWriter().setArgb(x, y, rgb);
            }
        }
        return wImage;
    }
} // ← クラスの閉じ括弧
