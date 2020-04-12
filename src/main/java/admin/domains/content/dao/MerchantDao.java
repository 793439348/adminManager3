package admin.domains.content.dao;

import admin.domains.content.entity.Merchant;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import java.util.List;

/**
 * <p>
 * 商户
 * </p>
 *
 * @author: zeng
 * @since: 2020-03-26
 */
public interface MerchantDao {

    Merchant getByAccount(String account);

    Merchant getByCode(String code);

    Merchant exists(String code, String account);

    boolean add(Merchant merchant);

    boolean delete(Merchant merchant);

    boolean update(Merchant merchant);

    boolean updateType(Integer id, Integer status);

    Merchant getBean(Integer id);

    PageList find(final List<Criterion> condition, final List<Order> sort, final int start, final int limit);

    List<Merchant> findAll();
}
