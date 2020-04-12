package admin.domains.content.biz;

import admin.domains.content.entity.MerchantBrand;
import admin.domains.content.vo.MerchantBrandVO;
import javautils.jdbc.PageList;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author: zeng
 * @since: 2020-03-27
 */
public interface MerchantBrandService {
    PageList search(Integer merchantId,String name, Integer start, Integer limit);

    boolean updateType(Integer id, Integer status);

    MerchantBrand getBeanByCode(String code);

    MerchantBrandVO getBean(Integer id);

    List<MerchantBrandVO> listAll();

    boolean add(MerchantBrand merchantBrand);

    boolean update(MerchantBrand merchantBrand);
}
