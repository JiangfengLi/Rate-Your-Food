package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Heading extends ImageView{ 
   
  private Image heading;
  
  public Heading() {
   heading = new Image("file:src/main/resources/images/jpro.png", false);
 
   this.setImage(heading);
   
   this.setFitWidth(1400);
   this.setPreserveRatio(true);
   this.setSmooth(true);
   this.setCache(true);
  }
}