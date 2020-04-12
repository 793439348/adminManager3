package admin.domains.content.entity;

import lombok.ToString;

import javax.persistence.*;

/**
 * <p>
 * 商户域名
 * </p>
 *
 * @author: zeng
 * @since: 2020-03-26
 */
@ToString
@Entity
@Table(name = "merchant_brand_domain", schema = "ecai", catalog = "")
public class MerchantBrandDomain {
    private int id;
    private int brandId;
    private String domain;

    public MerchantBrandDomain() {
    }

    public MerchantBrandDomain(int brandId, String domain) {
        this.brandId = brandId;
        this.domain = domain;
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
    @Column(name = "brand_id")
    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    @Basic
    @Column(name = "domain")
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MerchantBrandDomain that = (MerchantBrandDomain) o;

        if (id != that.id) return false;
        if (brandId != that.brandId) return false;
        if (domain != null ? !domain.equals(that.domain) : that.domain != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + brandId;
        result = 31 * result + (domain != null ? domain.hashCode() : 0);
        return result;
    }
}
