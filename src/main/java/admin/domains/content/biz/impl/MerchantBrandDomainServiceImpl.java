package admin.domains.content.biz.impl;

import admin.domains.content.biz.MerchantBrandDomainService;
import admin.domains.content.dao.MerchantBrandDao;
import admin.domains.content.dao.MerchantBrandDomainDao;
import admin.domains.content.dao.MerchantDao;
import admin.domains.content.entity.Merchant;
import admin.domains.content.entity.MerchantBrand;
import admin.domains.content.entity.MerchantBrandDomain;
import admin.domains.content.vo.MerchantDomainVO;
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
public class MerchantBrandDomainServiceImpl implements MerchantBrandDomainService {

    @Autowired
    private AdminDataFactory adminDataFactory;

    @Autowired
    private MerchantBrandDomainDao domainDao;

    @Autowired
    private MerchantDao merchantDao;

    @Autowired
    private MerchantBrandDao merchantBrandDao;

    @Override
    public MerchantDomainVO getBean(Integer id) {
        MerchantBrandDomain domain = domainDao.getBean(id);
        MerchantBrand brand = merchantBrandDao.getBean(domain.getBrandId());
        Merchant merchant = merchantDao.getBean(brand.getMerchantId());
        MerchantDomainVO domainVO = new MerchantDomainVO(merchant,brand, domain);
        return domainVO;
    }

    @Override
    public PageList search(Integer brand, String domain, Integer start, Integer limit) {
        if ( start == null || start < 0 ) {
            start = 0;
        }
        if (limit == null || limit < 0 ) {
            limit = 10;
        }
        if (limit > 100) {
            limit = 100;
        }

        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();

        if (brand != null && brand != 0 )
            criterions.add((Criterion) Restrictions.eq("brandId", brand));

        if (StringUtil.isNotNull(domain))
            criterions.add((Criterion) Restrictions.like("domain", domain, MatchMode.ANYWHERE));

        /*if (status != null && status > -1 && status < 2)
            criterions.add((Criterion) Restrictions.eq("status", status));*/

        orders.add(Order.desc("id"));

        PageList pageList = domainDao.find(criterions, orders, start, limit);
        List<MerchantDomainVO> list = new ArrayList<>();
        for (Object o : pageList.getList()) {
            MerchantDomainVO domainVO = new MerchantDomainVO();
            domainVO.setDomain(((MerchantBrandDomain)o).getDomain());
            domainVO.setId(((MerchantBrandDomain)o).getId());
            MerchantBrand brandBean = merchantBrandDao.getBean(((MerchantBrandDomain) o).getBrandId());
            domainVO.setBrandCode(brandBean.getCode());
            Merchant bean = merchantDao.getBean(brandBean.getMerchantId());
            domainVO.setMerchantCode(bean.getCode());
            list.add(domainVO);
        }
        pageList.setList(list);
        return pageList;
    }

    @Override
    public List<MerchantDomainVO> findAll() {
        List<MerchantBrandDomain> all = domainDao.findAll();
        List<MerchantDomainVO> list = new ArrayList<>();
        for (MerchantBrandDomain domain : all) {
            MerchantBrand bean = merchantBrandDao.getBean(domain.getBrandId());
            Merchant merchant = merchantDao.getBean(bean.getMerchantId());
            MerchantDomainVO domainVO = new MerchantDomainVO(merchant,bean,domain);
            list.add(domainVO);
        }
        return list;
    }


    @Override
    public boolean add(MerchantBrandDomain domain) {
        return domainDao.add(domain);
    }

    @Override
    public boolean update(MerchantBrandDomain domain) {
        return domainDao.update(domain);
    }

    @Override
    public boolean delete(Integer id) {
        MerchantBrandDomain domain = new MerchantBrandDomain();
        domain.setId(id);
        return domainDao.delete(domain);
    }
}
