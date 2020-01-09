package sample;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
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
    private RadioButton radioOriginal;

    @FXML
    private RadioButton radioModified;

    @FXML
    private Image originalImage;

    @FXML
    private Image modifiedImage;

    @FXML
    void exit(ActionEvent event) {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    @FXML
    private String filterFlag;

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
            originalImage = image;
        } catch (Exception ex) {
            System.out.println("Error");
        }
    }

    @FXML
    void restorniOriginal(){
        imageViewBig.setImage(originalImage);
    }

    @FXML
    void ukazInfo(ActionEvent event) {
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
        imageViewBig.setImage(img);
        originalImage = img;
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

    @FXML
    private void negativeFlag(){
        filterFlag = "negative";
    }

    @FXML
    private void aplikujFiltr(){
        switch (filterFlag){
            case "negative": vynegujObrazek(); break;
        }
    }

    @FXML
    private void vynegujObrazek(){
        try{
            Image originalImage = imageViewBig.getImage();
            BufferedImage img = SwingFXUtils.fromFXImage(originalImage, null);
            for (int x = 0; x < img.getWidth(); x++){
                for (int y = 0; y < img.getWidth(); y++){
                    int rgbOrig = img.getRGB(x,y);
                    Color c = new Color(rgbOrig);
                    int r = 255-c.getRed();
                    int b = 255-c.getBlue();
                    int g = 255-c.getGreen();
                    Color nc = new Color(r,g,b);
                    img.setRGB(x,y,nc.getRGB());
                }
            }
            imageViewBig.setImage(SwingFXUtils.toFXImage(img, null));
            modifiedImage = SwingFXUtils.toFXImage(img, null);
        }catch(Exception E){
            System.out.println("Image not selected or something else and I hope it's not the something else option.");
        }

    }

    @FXML
    private void ukazOriginal(){
        imageViewBig.setImage(originalImage);
        radioModified.setSelected(false);
        radioOriginal.setSelected(true);
    }

    @FXML
    private void ukazModified(){
        imageViewBig.setImage(modifiedImage);
        radioOriginal.setSelected(false);
        radioModified.setSelected(true);
    }
}
