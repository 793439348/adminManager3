package admin.domains.content.biz.impl;

import admin.domains.content.biz.MerchantService;
import admin.domains.content.dao.MerchantDao;
import admin.domains.content.entity.Merchant;
import admin.domains.content.vo.MerchantVO;
import admin.domains.pool.AdminDataFactory;
import javautils.StringUtil;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author: zeng
 * @since: 2020-03-27
 */
@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private AdminDataFactory adminDataFactory;

    @Autowired
    private MerchantDao merchantDao;

    @Override
    public Merchant getbyCode(String code) {
        return merchantDao.getByCode(code);
    }

    @Override
    public Merchant getbyAccount(String account) {
        return merchantDao.getByAccount(account);
    }

    @Override
    public Merchant exists(String code, String account) {
        return merchantDao.exists(code, account);
    }

    @Override
    public boolean add(Merchant merchant) {
        return merchantDao.add(merchant);
    }

    @Override
    public PageList search(String name, String code, Integer status, Integer start, Integer limit) {
        if (start < 0) {
            start = 0;
        }
        if (limit < 0) {
            limit = 10;
        }
        if (limit > 100) {
            limit = 100;
        }
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();

        if (StringUtil.isNotNull(name))
            criterions.add((Criterion) Restrictions.like("account", name, MatchMode.ANYWHERE));
        if (StringUtil.isNotNull(code))
            criterions.add((Criterion) Restrictions.like("code", code,MatchMode.ANYWHERE));
        if (status != null /*&& status > 0 && status <= 4*/)
            criterions.add((Criterion) Restrictions.eq("status", status));

        orders.add(Order.desc("id"));
        PageList pageList = merchantDao.find(criterions, orders, start, limit);
        List<MerchantVO> list = new ArrayList<>();
        for (Object o : pageList.getList()) {
            list.add(new MerchantVO((Merchant) o));
        }
        pageList.setList(list);
        return pageList;
    }

    @Override
    public boolean updateMerchant(Merchant merchant) {
        return merchantDao.update(merchant);
    }

    @Override
    public Merchant getBean(Integer id) {
        return merchantDao.getBean(id);
    }

    @Override
    public List<Merchant> getListAll() {
        return merchantDao.findAll();
    }

    @Override
    public boolean updateType(Integer id, Integer status) {
        return merchantDao.updateType(id,status);
    }
}
