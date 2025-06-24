package netpbm.session;

import netpbm.image.ImageMemento;
import netpbm.image.NetPBMImages;
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

    public NetPBMImages getFirstImage() {
        return images.isEmpty() ? null : images.getFirst();
    }

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

    public void saveState() {
        history.push(new ImageMemento(images));
    }

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


