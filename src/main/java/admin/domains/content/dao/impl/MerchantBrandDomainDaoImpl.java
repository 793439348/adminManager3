package admin.domains.content.dao.impl;

import admin.domains.content.dao.MerchantBrandDomainDao;
import admin.domains.content.entity.MerchantBrandDomain;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 商户域名
 * </p>
 *
 * @author: zeng
 * @since: 2020-03-26
 */
@Repository
public class MerchantBrandDomainDaoImpl implements MerchantBrandDomainDao {

    @Autowired
    private HibernateSuperDao<MerchantBrandDomain> superDao;

    @Override
    public MerchantBrandDomain getBean(Integer id) {
        String hql = "from MerchantBrandDomain where id = ?0";
        Object[] objs = {id};
        return (MerchantBrandDomain) superDao.unique(hql, objs);
    }

    public boolean add(MerchantBrandDomain merchantBrandDomain) {
        return superDao.save(merchantBrandDomain);
    }

    public boolean delete(MerchantBrandDomain merchantBrandDomain) {
        String hql = "delete from MerchantBrandDomain where id = ?0";
        Object[] obj = {merchantBrandDomain.getId()};
        return superDao.delete(hql,obj);
    }

    public boolean update(MerchantBrandDomain merchantBrandDomain) {
        String hql = "update MerchantBrandDomain set brandId=?0,domain=?1 where id=?2";
        Object[] objs = {merchantBrandDomain.getBrandId(), merchantBrandDomain.getDomain(), merchantBrandDomain.getId()};
        return superDao.update(hql, objs);
    }

    @Override
    public List<MerchantBrandDomain> findAll() {
        String hql = "from MerchantBrandDomain";
        return superDao.list(hql);
    }

    public PageList find(List<Criterion> condition, List<Order> sort, int start, int limit) {
        final String propertyName = "id";
        return superDao.findPageList(MerchantBrandDomain.class, propertyName, condition, sort, start, limit);
    }
}
