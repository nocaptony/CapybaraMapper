package learn.capybara_mapper.models;

public class Adoption {
    private int AdoptionId;
    private int petId;
    private int userId;
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;
    private String email;
    private String phoneNumber;
    private String requestDescription;
    private Pet petObj;
    private User userObj;

    public Adoption() {}

    public Adoption(int adoptionId, int petId, int userId, String streetAddress, String city, String state, String zipCode, String email, String phoneNumber, String requestDescription) {
        AdoptionId = adoptionId;
        this.petId = petId;
        this.userId = userId;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.requestDescription = requestDescription;
    }

    public int getAdoptionId() {
        return AdoptionId;
    }

    public void setAdoptionId(int adoptionId) {
        AdoptionId = adoptionId;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }

    public Pet getPetObj(){return petObj;}
    public void setPet(Pet pet) {this.petObj = pet;}

    public User getUserObj(){return userObj;}
    public void setUser(User user) {this.userObj = user;}
}
