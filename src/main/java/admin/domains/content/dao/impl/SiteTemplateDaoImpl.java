package admin.domains.content.dao.impl;

import admin.domains.content.dao.SiteTemplateDao;
import admin.domains.content.entity.SiteTemplate;
import javautils.StringUtil;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 商户模板
 * </p>
 *
 * @author: zeng
 * @since: 2020-03-26
 */
@Repository
public class SiteTemplateDaoImpl implements SiteTemplateDao {

    @Autowired
    private HibernateSuperDao<SiteTemplate> superDao;

    @Override
    public SiteTemplate getBeanByCode(String code, Integer type) {
        if (type != null && type != 0) {
            String hql = "from SiteTemplate where code = ?0 and type = ?1";
            Object[] objs = {code, type};
            return (SiteTemplate) superDao.unique(hql, objs);
        } else {
            String hql = "from SiteTemplate where code = ?0";
            Object[] objs = {code};
            return (SiteTemplate) superDao.unique(hql, objs);
        }
    }

    @Override
    public SiteTemplate getBean(Integer id) {
        String hql = "from SiteTemplate where id = ?0";
        Object[] objs = {id};
        return (SiteTemplate) superDao.unique(hql, objs);
    }

    @Override
    public List<SiteTemplate> findAll() {
        String hql = "from SiteTemplate";
        return superDao.list(hql);
    }

    public boolean add(SiteTemplate siteTemplate) {
        return superDao.save(siteTemplate);
    }

    public boolean delete(Integer id) {
        String hql = "delete from SiteTemplate where id = ?0";
        Object[] obj = {id};
        return superDao.delete(hql, obj);
    }

    public boolean update(SiteTemplate siteTemplate) {
        StringBuffer hql = new StringBuffer("update SiteTemplate set name = ?0,type = ?1,code = ?2");

        if (StringUtil.isNotNull(siteTemplate.getBigImage()) && StringUtil.isNotNull(siteTemplate.getSmallImage())) {
            hql.append(", smallImage = ?3 , bigImage = ?4 where id = ?5");
            Object[] objs = {siteTemplate.getName(), siteTemplate.getType(), siteTemplate.getCode(), siteTemplate.getSmallImage(),
                    siteTemplate.getBigImage(), siteTemplate.getId()};
            return superDao.update(hql.toString(), objs);
        } else if (StringUtil.isNotNull(siteTemplate.getSmallImage())) {
            hql.append(" ,  smallImage = ?3 where id = ?4");
            Object[] objs = {siteTemplate.getName(), siteTemplate.getType(), siteTemplate.getCode(), siteTemplate.getSmallImage(),
                    siteTemplate.getId()};
            return superDao.update(hql.toString(), objs);
        } else if (StringUtil.isNotNull(siteTemplate.getBigImage())) {
            hql.append(" ,bigImage = ?3 where id = ?4");
            Object[] objs = {siteTemplate.getName(), siteTemplate.getType(), siteTemplate.getCode(), siteTemplate.getBigImage(),
                    siteTemplate.getId()};
            return superDao.update(hql.toString(), objs);
        } else {
            hql.append(" where id = ?3");
            Object[] objs = {siteTemplate.getName(), siteTemplate.getType(), siteTemplate.getCode(), siteTemplate.getId()};
            return superDao.update(hql.toString(), objs);
        }
    }

    public PageList find(List<Criterion> condition, List<Order> sort, int page, int pageSize) {
        final String propertyName = "id";
        return superDao.findPageList(SiteTemplate.class, propertyName, condition, sort, page, pageSize);
    }
}
