package admin.domains.content.entity;

import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * <p>
 * 商户
 * </p>
 *
 * @author: zeng
 * @since: 2020-03-26
 */
@ToString
@Entity
@Table(name = "merchant", schema = "ecai", catalog = "")
public class Merchant {
    private int id;
    private String code;
    private String account;
    private String password;
    private String nickname;
    private BigDecimal balance;
    private int status;
    private int roleId;
    private String secretKey;
    private String registTime;
    private String loginTime;
    private String loginIp;
    private String loginPlat;
    private String ips;
    private String phone;
    private String email;
    private String qq;
    private String wechat;

    public Merchant() {
    }

    public Merchant(String code, String account, String password, String nickname, BigDecimal balance, int status, int roleId, String phone, String email, String qq, String wechat,String registTime) {
        this.registTime = registTime;
        this.code = code;
        this.account = account;
        this.password = password;
        this.nickname = nickname;
        this.balance = balance;
        this.status = status;
        this.roleId = roleId;
        this.phone = phone;
        this.email = email;
        this.qq = qq;
        this.wechat = wechat;
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "account")
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "nickname")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Basic
    @Column(name = "balance")
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Basic
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "role_id")
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "secret_key")
    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Basic
    @Column(name = "regist_time")
    public String getRegistTime() {
        return registTime;
    }

    public void setRegistTime(String registTime) {
        this.registTime = registTime;
    }

    @Basic
    @Column(name = "login_time")
    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    @Basic
    @Column(name = "login_ip")
    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    @Basic
    @Column(name = "login_plat")
    public String getLoginPlat() {
        return loginPlat;
    }

    public void setLoginPlat(String loginPlat) {
        this.loginPlat = loginPlat;
    }

    @Basic
    @Column(name = "ips")
    public String getIps() {
        return ips;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "qq")
    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Basic
    @Column(name = "wechat")
    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Merchant that = (Merchant) o;

        if (id != that.id) return false;
        if (status != that.status) return false;
        if (roleId != that.roleId) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (account != null ? !account.equals(that.account) : that.account != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (nickname != null ? !nickname.equals(that.nickname) : that.nickname != null) return false;
        if (balance != null ? !balance.equals(that.balance) : that.balance != null) return false;
        if (secretKey != null ? !secretKey.equals(that.secretKey) : that.secretKey != null) return false;
        if (registTime != null ? !registTime.equals(that.registTime) : that.registTime != null) return false;
        if (loginTime != null ? !loginTime.equals(that.loginTime) : that.loginTime != null) return false;
        if (loginIp != null ? !loginIp.equals(that.loginIp) : that.loginIp != null) return false;
        if (loginPlat != null ? !loginPlat.equals(that.loginPlat) : that.loginPlat != null) return false;
        if (ips != null ? !ips.equals(that.ips) : that.ips != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (qq != null ? !qq.equals(that.qq) : that.qq != null) return false;
        if (wechat != null ? !wechat.equals(that.wechat) : that.wechat != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + status;
        result = 31 * result + roleId;
        result = 31 * result + (secretKey != null ? secretKey.hashCode() : 0);
        result = 31 * result + (registTime != null ? registTime.hashCode() : 0);
        result = 31 * result + (loginTime != null ? loginTime.hashCode() : 0);
        result = 31 * result + (loginIp != null ? loginIp.hashCode() : 0);
        result = 31 * result + (loginPlat != null ? loginPlat.hashCode() : 0);
        result = 31 * result + (ips != null ? ips.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (qq != null ? qq.hashCode() : 0);
        result = 31 * result + (wechat != null ? wechat.hashCode() : 0);
        return result;
    }
}
