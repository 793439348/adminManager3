package admin.domains.content.dao.impl;

import admin.domains.content.dao.MerchantBrandDao;
import admin.domains.content.entity.MerchantBrand;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 商户品牌
 * </p>
 *
 * @author: zeng
 * @since: 2020-03-26
 */
@Repository
public class MerchantBrandDaoImpl implements MerchantBrandDao {
    @Autowired
    private HibernateSuperDao<MerchantBrand> superDao;

    @Override
    public boolean updateType(Integer id, Integer status) {
        String hql = "update MerchantBrand set status = ?0 where id = ?1";
        Object[] obj = {status, id};
        return superDao.update(hql,obj);
    }

    @Override
    public MerchantBrand exists(String code) {
        String hql = "from MerchantBrand where code = ?0";
        Object[] obj = {code};
        return (MerchantBrand)superDao.unique(hql,obj);
    }

    @Override
    public MerchantBrand getBean(Integer id) {
        String hql = "from MerchantBrand where id = ?0";
        Object[] objs = {id};
        return (MerchantBrand)superDao.unique(hql,objs);
    }

    @Override
    public List<MerchantBrand> findAll() {
        String hql = "from MerchantBrand";
        return superDao.list(hql);
    }

    public boolean add(MerchantBrand merchantBrand) {
        return superDao.save(merchantBrand);
    }

    public boolean delete(MerchantBrand merchantBrand) {
        return superDao.delete(merchantBrand);
    }

    public boolean update(MerchantBrand merchantBrand) {
        String hql = "update MerchantBrand set merchantId = ?0,name=?1,templete=?2,mtemplete=?3,status=?4 where id=?5";
        Object[] objs = {merchantBrand.getMerchantId(), merchantBrand.getName(), merchantBrand.getTemplete(), merchantBrand.getMtemplete()
                , merchantBrand.getStatus(), merchantBrand.getId()};
        return superDao.update(hql,objs);
    }

    public PageList find(List<Criterion> condition, List<Order> sort, int start, int limit) {
        /*主键，用于查询总条数*/
        final String propertyName = "id";
        return superDao.findPageList(MerchantBrand.class, propertyName, condition, sort, start, limit);
    }
}
