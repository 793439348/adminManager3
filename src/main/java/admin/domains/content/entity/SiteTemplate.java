package admin.domains.content.entity;

import lombok.ToString;

import javax.persistence.*;

/**
 * <p>
 * 商户模板
 * </p>
 *
 * @author: zeng
 * @since: 2020-03-26
 */
@ToString
@Entity
@Table(name = "site_template", schema = "ecai", catalog = "")
public class SiteTemplate {
    private int id;
    private String code;
    private String name;
    private int type;
    private String smallImage;
    private String bigImage;

    public SiteTemplate() {
    }

    public SiteTemplate(String code, String name, int type, String smallImage, String bigImage) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.smallImage = smallImage;
        this.bigImage = bigImage;
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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Basic
    @Column(name = "small_image")
    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    @Basic
    @Column(name = "big_image")
    public String getBigImage() {
        return bigImage;
    }

    public void setBigImage(String bigImage) {
        this.bigImage = bigImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SiteTemplate that = (SiteTemplate) o;

        if (id != that.id) return false;
        if (type != that.type) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (smallImage != null ? !smallImage.equals(that.smallImage) : that.smallImage != null) return false;
        if (bigImage != null ? !bigImage.equals(that.bigImage) : that.bigImage != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + type;
        result = 31 * result + (smallImage != null ? smallImage.hashCode() : 0);
        result = 31 * result + (bigImage != null ? bigImage.hashCode() : 0);
        return result;
    }
}
