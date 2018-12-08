package gsihome.reyst.tt1.data.datasources.local.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

@Entity(tableName = "albums")
public class AlbumDB {

    @NonNull
    @PrimaryKey
    private String id = "";

    @NonNull
    private String artistId = "";

    private String name;
    private int count;

    @Ignore
    private Bitmap image;
    private String imageFileName;

    public AlbumDB() {}

    public AlbumDB(@NonNull String id, @NonNull String artistId, String name, int count, Bitmap image) {
        this.id = id;
        this.artistId = artistId;
        this.name = name;
        this.count = count;
        this.image = image;

        updateImageFileName();
    }

    private void updateImageFileName() {
        if (image != null)
            this.imageFileName = String.valueOf(image.hashCode());
        else
            this.imageFileName = "";
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(@NonNull String artistId) {
        this.artistId = artistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
        updateImageFileName();
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
}
