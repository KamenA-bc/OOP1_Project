package netpbm.image;

import netpbm.session.Session;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores a snapshot of image states for undo operations.
 * <p>
 * Implements the Memento pattern by capturing deep copies of images at a specific point in time.
 * Used by {@link Session} to restore previous states when an undo is requested.
 */
public class ImageMemento {

    private final List<NetpbmImage> savedImages;

    /**
     * Creates a memento by deep copying the given list of images.
     *
     * @param imagesToSave The images to capture in their current state.
     */
    public ImageMemento(List<NetpbmImage> imagesToSave) {
        savedImages = new ArrayList<>();
        for (NetpbmImage img : imagesToSave) {
            savedImages.add(img.clone());
        }
    }

    /**
     * Returns deep copies of the saved images to restore a previous state.
     *
     * @return A new list of cloned images from the saved snapshot.
     */
    public List<NetpbmImage> getRestoredImages() {
        List<NetpbmImage> restored = new ArrayList<>();
        for (NetpbmImage img : savedImages) {
            restored.add(img.clone());
        }
        return restored;
    }
}
