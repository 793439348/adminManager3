package admin.web.content;

import admin.domains.content.biz.MerchantBrandDomainService;
import admin.domains.content.entity.AdminUser;
import admin.domains.content.entity.MerchantBrandDomain;
import admin.domains.content.vo.MerchantDomainVO;
import admin.domains.jobs.AdminUserActionLogJob;
import admin.web.WebJSONObject;
import admin.web.helper.AbstractActionController;
import com.alibaba.fastjson.JSON;
import javautils.http.HttpUtil;
import javautils.jdbc.PageList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author: zeng
 * @since: 2020-03-27
 */
@Slf4j
@Controller
public class MerchantBrandDomainController extends AbstractActionController {

    @Autowired
    private MerchantBrandDomainService domainService;
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;


    @ResponseBody
    @RequestMapping(value = "/merchant-brand-domain/search",method = RequestMethod.POST)
    public void searchDomain(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        final String actionKey = "/merchant-brand-domain/search";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        final long t1 = System.currentTimeMillis();
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                Integer brand = HttpUtil.getIntParameter(request,"brand");
                String domain = request.getParameter("domain");
                Integer start = HttpUtil.getIntParameter(request,"start");
                Integer limit = HttpUtil.getIntParameter(request,"limit");

                PageList pList = domainService.search(brand, domain, start, limit);
                if (pList != null) {
                    json.accumulate("totalCount", pList.getCount());
                    json.accumulate("data", pList.getList());
                } else {
                    json.accumulate("totalCount", 0);
                    json.accumulate("data", "[]");
                }
                json.set(0, "0-3");
            } else
                json.set(2, "2-4");
        } else
            json.set(2, "2-6");
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }


    @ResponseBody
    @RequestMapping(value = "/merchant-brand-domain/add", method = RequestMethod.POST)
    public void MERCHANT_BRAND_DOMAIN_ADD(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        final String actionKey = "/merchant-brand-domain/add";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        final long t1 = System.currentTimeMillis();
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                Integer brand = HttpUtil.getIntParameter(request, "brandId");
                String domain = request.getParameter("domain");
                MerchantBrandDomain brandDomain = new MerchantBrandDomain(brand,domain);
                if (domainService.add(brandDomain))
                    json.set(0,"0-6");
                else
                    json.set(1,"1-6");
            } else
                json.set(2, "2-4");
        } else
            json.set(2, "2-6");
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    @ResponseBody
    @RequestMapping(value = "/merchant-brand-domain/delete", method = RequestMethod.POST)
    public void MERCHANT_BRAND_DOMAIN_DELETE(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        final String actionKey = "/merchant-brand-domain/delete";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        final long t1 = System.currentTimeMillis();
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                Integer id = HttpUtil.getIntParameter(request,"id");
                if (domainService.delete(id))
                    json.set(0,"0-5");
                else
                    json.set(1,"1-5");
            } else
                json.set(2, "2-4");
        } else
            json.set(2, "2-6");
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    @ResponseBody
    @RequestMapping(value = "/merchant-brand-domain/update", method = RequestMethod.POST)
    public void MERCHANT_BRAND_DOMAIN_UPDATE(MerchantBrandDomain brandDomain, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        final String actionKey = "/merchant-brand-domain/update";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        final long t1 = System.currentTimeMillis();
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                if (domainService.update(brandDomain))
                    json.set(0,"0-6");
                else
                    json.set(1,"1-6");
            } else
                json.set(2, "2-4");
        } else
            json.set(2, "2-6");
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    @ResponseBody
    @RequestMapping(value = "/merchant-brand-domain/get", method = RequestMethod.POST)
    public void MERCHANT_BRAND_DOMAIN_GET(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
//        final String actionKey = "/merchant-brand-domain/get";
//        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
//        final AdminUser uEntity = super.getCurrUser(session, request, response);
//        if (uEntity != null) {
//            if (super.hasAccess(uEntity, actionKey)) {

        Integer id = HttpUtil.getIntParameter(request, "id");
        MerchantDomainVO domain = domainService.getBean(id);
        json.accumulate("bean", domain);
        json.set(0, "0-3");
//            }
//            else {
//                json.set(2, "2-4");
//            }
//        }
//        else {
//            json.set(2, "2-6");
//        }
//        final long t2 = System.currentTimeMillis();
//        if (uEntity != null) {
//            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
//        }
        HttpUtil.write(response, json.toString(), "text/json");
    }

    @ResponseBody
    @RequestMapping(value = "/merchant-brand-domain/list", method = RequestMethod.POST)
    public void MERCHANT_BRAND_DOMAIN_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        List<MerchantDomainVO> all = domainService.findAll();
        HttpUtil.write(response, JSON.toJSONString(all), "text/json");
    }
}
