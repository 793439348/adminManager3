package admin.domains.content.vo;

import admin.domains.content.entity.MerchantBrand;
import admin.domains.content.entity.SiteTemplate;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author: zeng
 * @since: 2020-03-31
 */
@Data
public class MerchantBrandVO {
    private int id;
    private String merchantCode;
    private String name;
    private String code;
    private SiteTemplate templete;
    private SiteTemplate mtemplete;
    private int status;

    public MerchantBrandVO() {
    }
    public MerchantBrandVO( MerchantBrand merchantBrand) {
        this.id = merchantBrand.getId();
        this.name = merchantBrand.getName();
        this.code = merchantBrand.getCode();
        this.status = merchantBrand.getStatus();
    }


}
