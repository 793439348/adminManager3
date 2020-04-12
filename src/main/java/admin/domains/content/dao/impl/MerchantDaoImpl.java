package admin.domains.content.dao.impl;

import admin.domains.content.dao.MerchantDao;
import admin.domains.content.entity.Merchant;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 商户
 * </p>
 *
 * @author: zeng
 * @since: 2020-03-26
 */
@Repository
public class MerchantDaoImpl implements MerchantDao {

    @Autowired
    private HibernateSuperDao<Merchant> superDao;

    @Override
    public Merchant getByCode(String code) {
        String hql = "from Merchant where code = ?0";
        Object[] obj = {code};
        return (Merchant)superDao.unique(hql, obj);
    }

    @Override
    public Merchant exists(String code, String account) {
        if (code!=null)
            return getByCode(code);
        if (account!=null)
            return getByAccount(account);
        return null;
    }

    @Override
    public Merchant getByAccount(String account) {
        String hql = "from Merchant where account = ?0";
        Object[] obj = {account};
        return (Merchant)superDao.unique(hql, obj);
    }

    public boolean add(Merchant merchant) {
        return superDao.save(merchant);
    }

    public boolean delete(Merchant merchant) {
        return superDao.delete(merchant);
    }

    public boolean update(Merchant merchant) {
        String hql = "update Merchant set nickname = ?0, status = ?1,roleId = ?2," +
                "phone = ?3,email=?4,qq=?5,wechat=?6 where id=?7";
        Object[] os = {merchant.getNickname(), merchant.getStatus(), merchant.getRoleId()
                , merchant.getPhone(), merchant.getEmail(), merchant.getQq(), merchant.getWechat(), merchant.getId()};
        return superDao.update(hql, os);
    }

    @Override
    public boolean updateType(Integer id, Integer status) {
        String hql = "update Merchant set status = ?0 where id = ?1";
        Object[] obj = {status, id};
        return superDao.update(hql,obj);
    }

    @Override
    public Merchant getBean(Integer id) {
        final String hql = "from " + "Merchant" + " where id = ?0";
        final Object[] values = { id };
        return (Merchant) superDao.unique(hql,values);
    }

    public PageList find(List<Criterion> condition, List<Order> sort, int start, int limit) {
        final String propertyName = "id";
        return superDao.findPageList(Merchant.class,propertyName,condition,sort,start,limit);
    }

    @Override
    public List<Merchant> findAll() {
        String hql = "from Merchant";
        return superDao.list(hql);
    }


}
