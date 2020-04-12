package admin.web.content;

import admin.domains.content.biz.MerchantBrandService;
import admin.domains.content.entity.AdminUser;
import admin.domains.content.entity.MerchantBrand;
import admin.domains.content.vo.MerchantBrandVO;
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
public class MerchantBrandController extends AbstractActionController {

    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private MerchantBrandService merchantBrandService;

    @ResponseBody
    @RequestMapping(value = "/merchant-brand/list", method = RequestMethod.POST)
    public void MERCHANT_BRAND_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        final String actionKey = "/merchant-brand/list";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        final long t1 = System.currentTimeMillis();
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                Integer merchantId = HttpUtil.getIntParameter(request, "merchant");
                String name = request.getParameter("name");
                Integer start = HttpUtil.getIntParameter(request, "start");
                Integer limit = HttpUtil.getIntParameter(request, "limit");

                PageList pList = merchantBrandService.search(merchantId, name, start, limit);
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
    @RequestMapping(value = "/merchant-brand/add", method = RequestMethod.POST)
    public void MERCHANT_BRAND_ADD(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        final String actionKey = "/merchant-brand/add";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        final long t1 = System.currentTimeMillis();
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                Integer merchantCode = HttpUtil.getIntParameter(request, "merchantCode");
                String name = request.getParameter("name");
                String code = request.getParameter("code");
                String templete = request.getParameter("templete");
                String mtemplete = request.getParameter("mtemplete");
                Integer status = HttpUtil.getIntParameter(request, "status");
                MerchantBrand merchantBrand = new MerchantBrand(merchantCode, name, code, templete, mtemplete, status);
                try {
                    boolean b = merchantBrandService.add(merchantBrand);
                    json.set(0, "0-6");
                } catch (Exception e) {
                    json.set(1, "1-6");
                }
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
    @RequestMapping(value = "/merchant-brand/update", method = RequestMethod.POST)
    public void MERCHANT_BRAND_UPDATE(MerchantBrand merchantBrand, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        final String actionKey = "/merchant-brand/update";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        final long t1 = System.currentTimeMillis();
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                MerchantBrand bean = merchantBrandService.getBeanByCode(merchantBrand.getCode());

                if (bean == null || bean.getId() == merchantBrand.getId()) {
                    if (merchantBrandService.update(merchantBrand))
                        json.set(0, "0-6");
                    else
                        json.set(1, "1-6");
                } else
                    json.set(2, "2-9003");
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
    @RequestMapping(value = "/merchant-brand/modify-type", method = RequestMethod.POST)
    public void modifyType(Integer id, Integer status, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        final String actionKey = "/merchant-brand/update";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        final long t1 = System.currentTimeMillis();
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                if (merchantBrandService.updateType(id, status))
                    json.set(0,"0-6");
                else
                    json.set(1,"1-6");
            }else
                json.set(2,"2-4");
        }else
            json.set(2,"2-6");
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text");
    }

    @ResponseBody
    @RequestMapping(value = "/merchant-brand/get", method = RequestMethod.POST)
    public void MERCHANT_BRAND_GET(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

        final String actionKey = "/merchant-brand/get";
//        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
//        final AdminUser uEntity = super.getCurrUser(session, request, response);
//        if (uEntity != null) {
//            if (super.hasAccess(uEntity, actionKey)) {

        Integer id = HttpUtil.getIntParameter(request, "id");
        MerchantBrandVO bean = merchantBrandService.getBean(id);
        json.accumulate("bean", bean);
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
    @RequestMapping(value = "/merchant-brand/getlist", method = RequestMethod.POST)
    public void getList(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        List<MerchantBrandVO> list = merchantBrandService.listAll();
        HttpUtil.write(response, JSON.toJSONString(list), "text/json");
    }

    @ResponseBody
    @RequestMapping(value = "/merchant-brand/notexists", method = RequestMethod.POST)
    public void notexists(String code, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        MerchantBrand bean = merchantBrandService.getBeanByCode(code);
        boolean boo = false;
        if (bean == null)
            boo = true;
        HttpUtil.write(response, String.valueOf(boo), "text");
    }


}
