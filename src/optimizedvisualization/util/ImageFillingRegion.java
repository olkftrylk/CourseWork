package optimizedvisualization.util;

import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class ImageFillingRegion {
    public static void setBackgroundWithImage(Region region, String imagePath) {
        Image image = new Image(imagePath, region.getWidth(), region.getHeight(), false, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(
                region.getWidth(), region.getHeight(), true, true, true, false
        ));
        region.setBackground(new Background(backgroundImage));
    }
}
