package admin.domains.content.biz.impl;

import admin.domains.content.biz.MerchantBrandService;
import admin.domains.content.dao.MerchantBrandDao;
import admin.domains.content.dao.MerchantDao;
import admin.domains.content.dao.SiteTemplateDao;
import admin.domains.content.entity.Merchant;
import admin.domains.content.entity.MerchantBrand;
import admin.domains.content.entity.SiteTemplate;
import admin.domains.content.vo.MerchantBrandVO;
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
public class MerchantBrandServiceImpl implements MerchantBrandService {

    @Autowired
    private AdminDataFactory adminDataFactory;

    @Autowired
    private MerchantBrandDao brandDao;

    @Autowired
    private MerchantDao merchantDao;

    @Autowired
    private SiteTemplateDao templateDao;

    @Override
    public PageList search(Integer merchantId, String name, Integer start, Integer limit) {
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
            criterions.add((Criterion) Restrictions.like("name", name, MatchMode.ANYWHERE));
        if (merchantId != null && merchantId != 0 )
            criterions.add((Criterion) Restrictions.eq("merchantId", merchantId));
        orders.add(Order.desc("id"));

        PageList pList = brandDao.find(criterions, orders, start, limit);
        List<MerchantBrandVO> list = new ArrayList<>();
        for (Object o : pList.getList()) {
            Merchant merchant = merchantDao.getBean(((MerchantBrand)o).getMerchantId());
            SiteTemplate template = templateDao.getBeanByCode(((MerchantBrand)o).getTemplete(),1);
            SiteTemplate mtemplate = templateDao.getBeanByCode(((MerchantBrand)o).getMtemplete(),2);
            MerchantBrandVO merchantBrandVO = new MerchantBrandVO(((MerchantBrand)o));
            merchantBrandVO.setMerchantCode(merchant.getCode());
            merchantBrandVO.setTemplete(template);
            merchantBrandVO.setMtemplete(mtemplate);
            list.add(merchantBrandVO);
        }
        pList.setList(list);
        return pList;
    }

    @Override
    public boolean updateType(Integer id, Integer status) {
        return brandDao.updateType(id,status);
    }

    @Override
    public MerchantBrand getBeanByCode(String code) {
        return brandDao.exists(code);
    }

    @Override
    public MerchantBrandVO getBean(Integer id) {
        MerchantBrand bean = brandDao.getBean(id);
        SiteTemplate template = templateDao.getBeanByCode(bean.getTemplete(),1);
        SiteTemplate mtemplate = templateDao.getBeanByCode(bean.getMtemplete(),2);
        MerchantBrandVO brandVO = new MerchantBrandVO(bean);
        Merchant merchant = merchantDao.getBean(bean.getMerchantId());
        brandVO.setMerchantCode(merchant.getCode());
        brandVO.setTemplete(template);
        brandVO.setMtemplete(mtemplate);
        return brandVO;
    }

    @Override
    public List<MerchantBrandVO> listAll() {
        List<MerchantBrand> brands = brandDao.findAll();
        List<MerchantBrandVO> list = new ArrayList<>();
        for (MerchantBrand brand : brands) {
            Merchant merchant = merchantDao.getBean(brand.getMerchantId());
            SiteTemplate template = templateDao.getBeanByCode(brand.getTemplete(),1);
            SiteTemplate mtemplate = templateDao.getBeanByCode(brand.getMtemplete(),2);
            MerchantBrandVO merchantBrandVO = new MerchantBrandVO(brand);
            merchantBrandVO.setMerchantCode(merchant.getCode());
            merchantBrandVO.setTemplete(template);
            merchantBrandVO.setMtemplete(mtemplate);
            list.add(merchantBrandVO);
        }
        return list;
    }

    @Override
    public boolean add(MerchantBrand merchantBrand) {
        return brandDao.add(merchantBrand);
    }

    @Override
    public boolean update(MerchantBrand merchantBrand) {
        return brandDao.update(merchantBrand);
    }
}
