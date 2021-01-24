package milovanov.stc31.innopolis.checkuper.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserDto {
    @NotBlank
    @Email
    @Size(min = 3, max = 30)
    private String username;
    @NotBlank
    @Size(min = 1, max = 50)
    private String fullName;
    //@Pattern(regexp = "''|on")
    private String isCustomer;
    //@Pattern(regexp = "''|on")
    private String isExecutor;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIsCustomer() {
        return isCustomer;
    }

    public void setIsCustomer(String isCustomer) {
        this.isCustomer = isCustomer;
    }

    public String getIsExecutor() {
        return isExecutor;
    }

    public void setIsExecutor(String isExecutor) {
        this.isExecutor = isExecutor;
    }
}
