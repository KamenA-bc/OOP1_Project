package netpbm.session;

import netpbm.image.ImageMemento;
import netpbm.image.NetpbmImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Represents a single image editing session.
 * <p>
 * A session groups together one or more loaded {@link NetpbmImage} objects
 * and tracks all operations applied to them. It also stores a history of
 * image states to support undo functionality and logs each transformation
 * as a textual description.
 */
public class Session {

    private final int id;
    private final List<NetpbmImage> images = new ArrayList<>();
    private final List<String> alternations = new ArrayList<>();
    private final Stack<ImageMemento> history = new Stack<>();

    /**
     * Constructs a session with the given unique ID.
     *
     * @param id The session identifier.
     */
    public Session(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public void addImage(NetpbmImage image) {
        images.add(image);
    }

    public List<NetpbmImage> getImages() {
        return images;
    }

    /**
     * Returns the first image in the session, or {@code null} if none are loaded.
     *
     * @return The first loaded image or {@code null}.
     */
    public NetpbmImage getFirstImage() {
        return images.isEmpty() ? null : images.getFirst();
    }

    /**
     * Removes an image from the session whose file name matches the given name,
     * ignoring case sensitivity.
     * <p>
     * Iterates through all loaded images and removes the first or multiple matches
     * if any exist. Uses an iterator to safely remove items during iteration.
     *
     * @param fileName The name of the file to remove.
     * @return {@code true} if at least one image was removed; {@code false} if no match was found.
     */
    public boolean removeImageByName(String fileName) {
        Iterator<NetpbmImage> iterator = images.iterator();
        boolean removed = false;

        while (iterator.hasNext()) {
            NetpbmImage img = iterator.next();
            if (fileName.equalsIgnoreCase(img.getFileName())) {
                iterator.remove();
                removed = true;
            }
        }

        return removed;
    }

    /**
     * Records an image operation  applied to this session.
     *
     * @param operation The name of the operation to log.
     */
    public void addAlternation(String operation) {
        alternations.add(operation);
    }

    public List<String> getAlternations() {
        return alternations;
    }

    /**
     * Saves the current state of all images for undo purposes.
     * A deep copy of the images is pushed onto a history stack.
     */
    public void saveState() {
        history.push(new ImageMemento(images));
    }

    /**
     * Restores the most recently saved image state, if available.
     * Also removes the last logged operation.
     *
     * @return {@code true} if undo was successful; {@code false} if history is empty.
     */
    public boolean undo() {
        if (history.isEmpty()) return false;

        List<NetpbmImage> previousState = history.pop().getRestoredImages();

        images.clear();
        for (NetpbmImage img : previousState) {
            images.add(img);
        }

        if (!alternations.isEmpty()) {
            alternations.remove(alternations.size() - 1);
        }

        return true;
    }
}

