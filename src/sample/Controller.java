package sample;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Controller {
    @FXML
    private AnchorPane root;

    @FXML
    private ImageView imageViewBig;

    @FXML
    void exit(ActionEvent event) {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    @FXML
    void zvolObrazek(ActionEvent event) {
        System.out.println("Vyber obrázek");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load image");

        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        File file = fileChooser.showOpenDialog(null);
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            imageViewBig.setImage(image);
        } catch (Exception ex) {
            System.out.println("Error");
        }
    }

    @FXML
    void ukazInfo(ActionEvent event) {
        System.out.println("Ukaž info");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informace");
        alert.setHeaderText("Info");
        String s ="Oldřich Havelka";
        alert.setContentText(s);
        alert.show();
    }

    @FXML
    void ulozObrazek(ActionEvent event) {
        FileChooser stegoImageSaver = new FileChooser();
        stegoImageSaver.setTitle("Save Image File");
        File f1 = stegoImageSaver.showSaveDialog(null);
        System.out.println(f1.getAbsolutePath());
        BufferedImage bImage = SwingFXUtils.fromFXImage(imageViewBig.getImage(), null);
        try {
            ImageIO.write(bImage, "jpg", f1.getAbsoluteFile());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void vygenerujObrazek(ActionEvent event) {
        Image img=SwingFXUtils.toFXImage(udelejVybarvenejObrazek(),null);
        imageViewBig.setImage(img) ;
        }


    @FXML
    private BufferedImage udelejVybarvenejObrazek(){
        BufferedImage bImage = new BufferedImage(600, 600, BufferedImage.TYPE_3BYTE_BGR);
        for (int x = 2; x < bImage.getWidth(); x++){
            for (int y = 2; y < bImage.getHeight(); y++){
                bImage.setRGB(x, y, (new Color(x%220,y%220,(x+y)%220).getRGB()));
            }
        }
        return bImage;
    }

}
