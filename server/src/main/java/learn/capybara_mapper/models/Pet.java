package learn.capybara_mapper.models;

public class Pet {
    private int petId;
    private int petTypeId;
    private int locationId;
    private String name;
    private String description;
    private String photoLink;
    private PetType petTypeObj;
    private Location locationObj;

    public Pet() {}

    public Pet(int petId, int petTypeId, int locationId, String name, String description, String photoLink) {
        this.petId = petId;
        this.petTypeId = petTypeId;
        this.locationId = locationId;
        this.name = name;
        this.description = description;
        this.photoLink = photoLink;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getPetTypeId() {
        return petTypeId;
    }

    public void setPetTypeId(int petTypeId) {
        this.petTypeId = petTypeId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public PetType getPetTypeObj() {
        return petTypeObj;
    }

    public void setPetTypeObj(PetType petTypeObj) {
        this.petTypeObj = petTypeObj;
    }
    public Location getLocation() {
        return locationObj;
    }
}
