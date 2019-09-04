package rzepiszczak.damian.bankingapi.core.inputs;

import javax.validation.constraints.Min;

public class RegisterParams {

    @Min(value = 8, message = "Wrong first name")
    private String firstName;

    @Min(value = 8, message = "Wrong last name")
    private String lastName;

    private String login;
    private String password;
    private String confirmPassword;
    private String dayOfBirth;
    private boolean acceptedPersonalData;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirm_password(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(String dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public boolean isAcceptedPersonalData() {
        return acceptedPersonalData;
    }

    public void setAcceptedPersonalData(boolean acceptedPersonalData) {
        this.acceptedPersonalData = acceptedPersonalData;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "RegisterParams{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", dayOfBirth='" + dayOfBirth + '\'' +
                ", acceptedPersonalData=" + acceptedPersonalData +
                '}';
    }
}
