package admin.domains.content.dao;

import admin.domains.content.entity.MerchantBrand;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import java.util.List;

/**
 * <p>
 * 商户品牌
 * </p>
 *
 * @author: zeng
 * @since: 2020-03-26
 */
public interface MerchantBrandDao {
    boolean updateType(Integer id, Integer status);

    MerchantBrand exists(String code);

    MerchantBrand getBean(Integer id);

    List<MerchantBrand> findAll();

    boolean add(MerchantBrand merchantBrand);

    boolean delete(MerchantBrand merchantBrand);

    boolean update(MerchantBrand merchantBrand);

    PageList find(final List<Criterion> condition, final List<Order> sort, final int start, final int limit);
}
