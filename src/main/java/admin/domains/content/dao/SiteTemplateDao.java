package admin.domains.content.dao;

import admin.domains.content.entity.SiteTemplate;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import java.util.List;

/**
 * <p>
 * 商户模板
 * </p>
 *
 * @author: zeng
 * @since: 2020-03-26
 */
public interface SiteTemplateDao {

    SiteTemplate getBeanByCode(String code,Integer type);

    SiteTemplate getBean(Integer id);

    List<SiteTemplate> findAll();

    boolean add(SiteTemplate siteTemplate);

    boolean delete(Integer id);

    boolean update(SiteTemplate siteTemplate);

    PageList find(final List<Criterion> condition, final List<Order> sort, final int page, final int pageSize);
}
