package admin.domains.content.dao;

import admin.domains.content.entity.MerchantBrandDomain;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import java.util.List;

/**
 * <p>
 * 商户域名
 * </p>
 *
 * @author: zeng
 * @since: 2020-03-26
 */
public interface MerchantBrandDomainDao {

    MerchantBrandDomain getBean(Integer id);

    boolean add(MerchantBrandDomain merchantBrandDomain);

    boolean delete(MerchantBrandDomain merchantBrandDomain);

    boolean update(MerchantBrandDomain merchantBrandDomain);

    List<MerchantBrandDomain> findAll();

    PageList find(final List<Criterion> condition, final List<Order> sort, final int start, final int limit);
}
