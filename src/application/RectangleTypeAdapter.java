package application;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

/**
 * Support class to for json  (reading and writing) to access Rectangle objects
 */

public class RectangleTypeAdapter extends TypeAdapter<Rectangle> {
	
	/**
	 * Method to write a rectangle value in a json file
	 */
	
    @Override
    public void write(JsonWriter out, Rectangle value) throws IOException {
        out.beginObject();
        out.name("id").value(value.getId());
        out.name("x").value(value.getX());
        out.name("y").value(value.getY());
        out.name("width").value(value.getWidth());
        out.name("height").value(value.getHeight());
        out.endObject();
    }

    /**
     * Method to read and deserialize the information in order to create a rectangle object form json file
     */
    
    @Override
    public Rectangle read(JsonReader in) throws IOException {
        String id=null;
    	int x = 0;
        int y = 0;
        int width = 0;
        int height = 0;

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "x":
                    x = in.nextInt();
                    break;
                case "y":
                    y = in.nextInt();
                    break;
                case "width":
                    width = in.nextInt();
                    break;
                case "height":
                    height = in.nextInt();
                    break;
                default:
                    in.skipValue(); // Ignore unknown fields
                    break;
            }
        }
        in.endObject();
        // Create a new Rectangle object using the deserialized information
        Rectangle rectangle = new Rectangle(width, height);
        rectangle.setId(id); // Set the ID or other relevant info
        rectangle.setX(x);
        rectangle.setY(y);
        return rectangle;
    }
}