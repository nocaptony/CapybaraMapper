package learn.capybara_mapper.models;

public class PetType {
    private int petTypeId;
    private String name;

    public PetType() {}
    public PetType(int petTypeId, String name) {
        this.petTypeId = petTypeId;
        this.name = name;
    }

    public int getPetTypeId() {
        return petTypeId;
    }

    public void setPetTypeId(int petTypeId) {
        this.petTypeId = petTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
