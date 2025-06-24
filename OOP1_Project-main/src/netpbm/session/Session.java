package netpbm.session;

import netpbm.image.ImageMemento;
import netpbm.image.NetPBMImages;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;


/**
 * Represents a user session containing a set of NetPBM images.
 * Tracks image modifications and supports undo functionality.
 */
public class Session {

    private final int id;
    private final List<NetPBMImages> images = new ArrayList<>();
    private final List<String> alternations = new ArrayList<>();
    private final Stack<ImageMemento> history = new Stack<>();

    public Session(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void addImage(NetPBMImages image) {
        images.add(image);
    }

    public List<NetPBMImages> getImages() {
        return images;
    }

    /**
     * Returns the first loaded image in the session, or {@code null} if none exist.
     */
    public NetPBMImages getFirstImage() {
        return images.isEmpty() ? null : images.getFirst();
    }

    /**
     * Removes an image by file name, if it exists in the session.
     *
     * @param fileName the name of the image file to remove
     * @return {@code true} if the image was found and removed, otherwise {@code false}
     */
    public boolean removeImageByName(String fileName) {
        Iterator<NetPBMImages> iterator = images.iterator();
        boolean removed = false;

        while (iterator.hasNext()) {
            NetPBMImages img = iterator.next();
            if (fileName.equalsIgnoreCase(img.getFileName())) {
                iterator.remove();
                removed = true;
            }
        }

        return removed;
    }

    public void addAlternation(String operation) {
        alternations.add(operation);
    }

    public List<String> getAlternations() {
        return alternations;
    }

    /**
     * Saves the current state of the image list for potential restoration.
     */
    public void saveState() {
        history.push(new ImageMemento(images));
    }

    /**
     * Restores the last saved image state and removes the most recent alternation.
     *
     * @return {@code true} if undo was successful, {@code false} if no history is available
     */
    public boolean undo() {
        if (history.isEmpty()) return false;

        List<NetPBMImages> previousState = history.pop().getRestoredImages();

        images.clear();
        images.addAll(previousState);

        if (!alternations.isEmpty()) {
            alternations.remove(alternations.size() - 1);
        }

        return true;
    }
}



