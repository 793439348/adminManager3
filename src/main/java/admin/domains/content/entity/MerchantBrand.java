package admin.domains.content.entity;

import lombok.ToString;

import javax.persistence.*;

/**
 * <p>
 * 商户品牌
 * </p>
 *
 * @author: zeng
 * @since: 2020-03-26
 */
@ToString
@Entity
@Table(name = "merchant_brand", schema = "ecai", catalog = "")
public class MerchantBrand {
    private int id;
    private int merchantId;
    private String name;
    private String code;
    private String templete;
    private String mtemplete;
    private int status;

    public MerchantBrand() {
    }

    public MerchantBrand(int merchantId, String name, String code, String templete, String mtemplete, int status) {
        this.merchantId = merchantId;
        this.name = name;
        this.code = code;
        this.templete = templete;
        this.mtemplete = mtemplete;
        this.status = status;
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
    @Column(name = "merchant_id")
    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Column(name = "templete")
    public String getTemplete() {
        return templete;
    }

    public void setTemplete(String templete) {
        this.templete = templete;
    }

    @Basic
    @Column(name = "mtemplete")
    public String getMtemplete() {
        return mtemplete;
    }

    public void setMtemplete(String mtemplete) {
        this.mtemplete = mtemplete;
    }

    @Basic
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MerchantBrand that = (MerchantBrand) o;

        if (id != that.id) return false;
        if (merchantId != that.merchantId) return false;
        if (status != that.status) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (templete != null ? !templete.equals(that.templete) : that.templete != null) return false;
        if (mtemplete != null ? !mtemplete.equals(that.mtemplete) : that.mtemplete != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + merchantId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (templete != null ? templete.hashCode() : 0);
        result = 31 * result + (mtemplete != null ? mtemplete.hashCode() : 0);
        result = 31 * result + status;
        return result;
    }
}
