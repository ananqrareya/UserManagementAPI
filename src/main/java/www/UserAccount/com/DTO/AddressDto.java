package www.UserAccount.com.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AddressDto {
    @NotNull(message = "City cannot be null")
    @Size(min = 2, max = 50, message = "City must be between 2 and 50 characters")
    private String city;

    @NotNull(message = "Region cannot be null")
    @Size(min = 2, max = 50, message = "Region must be between 2 and 50 characters")
    private String region;

    @NotNull(message = "Street cannot be null")
    @Size(min = 2, max = 50, message = "Street must be between 2 and 50 characters")
    private String street;

    @Min(value = 1, message = "Building number must be greater than 0")
    private int buildingNumber;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(int buildingNumber) {
        this.buildingNumber = buildingNumber;
    }
}
