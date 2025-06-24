package netpbm.image;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores a deep copy (snapshot) of a list of images,
 * allowing undo functionality via the Memento pattern.
 */
public class ImageMemento {

    private final List<NetPBMImages> savedImages;

    /**
     * Constructs a memento by deep copying the given list of images.
     *
     * @param imagesToSave The images to capture in their current state.
     */
    public ImageMemento(List<NetPBMImages> imagesToSave) {
        this.savedImages = new ArrayList<>();
        for (NetPBMImages image : imagesToSave) {
            savedImages.add(image.clone());
        }
    }

    /**
     * Returns a deep copy of the saved images.
     * Each image is cloned again so that restoring the state
     * does not mutate the original memento.
     *
     * @return A new list of cloned images.
     */
    public List<NetPBMImages> getRestoredImages() {
        List<NetPBMImages> restored = new ArrayList<>();
        for (NetPBMImages image : savedImages) {
            restored.add(image.clone());
        }
        return restored;
    }
}
