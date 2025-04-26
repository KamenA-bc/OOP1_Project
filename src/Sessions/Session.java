package Sessions;


import Netpbm.LoadedImage;
import java.util.ArrayList;
import java.util.List;

public class Session {
    private final int id;
    private final List<LoadedImage> images;
    private final List<String> alternations;
    private final List<List<int[][][]>> imageBackups;
    private final List<Integer> widthBackups;
    private final List<Integer> heightBackups;

    public Session(int id) {
        this.id = id;
        this.images = new ArrayList<>();
        this.alternations = new ArrayList<>();
        this.imageBackups = new ArrayList<>();
        this.widthBackups = new ArrayList<>();
        this.heightBackups = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public List<LoadedImage> getImages() {
        return images;
    }

    public List<String> getAlternations() {
        return alternations;
    }

    public List<List<int[][][]>> getImageBackups() {
        return imageBackups;
    }

    public List<Integer> getWidthBackups() {
        return widthBackups;
    }

    public List<Integer> getHeightBackups() {
        return heightBackups;
    }

    public void addImage(LoadedImage image) {
        images.add(image);
    }

    public void addAlternation(String alternation) {
        alternations.add(alternation);
    }
}

