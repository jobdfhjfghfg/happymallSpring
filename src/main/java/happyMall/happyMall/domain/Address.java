package happyMall.happyMall.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@Setter
@ToString
public class Address {

    private String zipcode;

    private String fullAddr;

    private String extraAddr;

    public Address() {}

    public Address( String fullAddr, String extraAddr, String zipcode) {
        this.fullAddr = fullAddr;
        this.extraAddr = extraAddr;
        this.zipcode = zipcode;
    }
}
