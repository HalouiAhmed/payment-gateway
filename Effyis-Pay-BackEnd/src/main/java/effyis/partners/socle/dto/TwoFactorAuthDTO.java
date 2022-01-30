package effyis.partners.socle.dto;

public class TwoFactorAuthDTO {

    private String login;
    private String password;
    private String otp;

    public TwoFactorAuthDTO(String login, String password, String otp) {
        this.login = login;
        this.password = password;
        this.otp = otp;
    }

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

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @Override
    public String toString() {
        return "TwoFactorAuthDTO{" +
                "email='" + login + '\'' +
                ", password='" + password + '\'' +
                ", otp='" + otp + '\'' +
                '}';
    }
}
