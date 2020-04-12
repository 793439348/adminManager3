package admin.web.content;

import admin.domains.content.biz.MerchantService;
import admin.domains.content.dao.AdminUserDao;
import admin.domains.content.entity.AdminUser;
import admin.domains.content.entity.Merchant;
import admin.domains.jobs.AdminUserActionLogJob;
import admin.web.WebJSONObject;
import admin.web.helper.AbstractActionController;
import com.alibaba.fastjson.JSON;
import javautils.date.DateUtil;
import javautils.encrypt.MD5Util;
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
import java.math.BigDecimal;
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
public class MerchantController extends AbstractActionController {

    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private AdminUserDao adminUserDao;

    @ResponseBody
    @RequestMapping(value = "/merchant/list", method = RequestMethod.POST)
    public void MERCHANT_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        final String actionKey = "/merchant/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                String name = request.getParameter("name");
                String code = request.getParameter("code");
                Integer status = HttpUtil.getIntParameter(request, "status");
                Integer start = HttpUtil.getIntParameter(request, "start");
                Integer limit = HttpUtil.getIntParameter(request, "limit");

                PageList pList = merchantService.search(name, code, status, start, limit);
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
    @RequestMapping(value = "/merchant/add", method = RequestMethod.POST)
    public void MERCHANT_ADD(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        final String actionKey = "/merchant/add";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        final long t1 = System.currentTimeMillis();
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                String code = request.getParameter("code");
                String account = request.getParameter("account");
                String pwd1 = request.getParameter("pwd1");
                String nickname = request.getParameter("nickname");
                Integer status = HttpUtil.getIntParameter(request, "status");
                Integer role_id = HttpUtil.getIntParameter(request, "role_id");
                String phone = request.getParameter("phone");
                String email = request.getParameter("email");
                String qq = request.getParameter("qq");
                String wechat = request.getParameter("wechat");
                pwd1 = MD5Util.getMD5Code(MD5Util.getMD5Code(pwd1).toUpperCase()).toUpperCase();

                Merchant merchant = new Merchant(code, account, pwd1, nickname, BigDecimal.ZERO, status,
                        role_id, phone, email, qq, wechat, DateUtil.getCurrentTime());
                AdminUser adminUser = new AdminUser(code + "." + account, pwd1, "notset", role_id, DateUtil.getCurrentTime());

                try {
                    boolean b = merchantService.add(merchant);
                    Merchant bean = merchantService.exists(code, account);
                    adminUser.setMerchant_id(bean.getId());
                    boolean add = adminUserDao.add(adminUser);
                    if (b && add)
                        json.set(0, "0-6");
                    else
                        json.set(1, "1-6");
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
    @RequestMapping(value = "/merchant/update", method = RequestMethod.POST)
    public void MERCHANT_UPDATE(Merchant merchant, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        final String actionKey = "/merchant/update";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        final long t1 = System.currentTimeMillis();
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                Merchant mCode = merchantService.getbyCode(merchant.getCode());
                Merchant mAccount = merchantService.getbyAccount(merchant.getAccount());

                if ((null == mCode || mCode.getId() == merchant.getId()) && (null == mAccount || mAccount.getId() == merchant.getId())) {
                    if (merchantService.updateMerchant(merchant))
                        json.set(0, "0-6");
                    else
                        json.set(1, "1-6");
                } else {
                    if (null != mCode && mCode.getId() != merchant.getId())
                        json.set(2, "2-9002");
                    else if (null != mAccount && mAccount.getId() != merchant.getId())
                        json.set(2, "2-9004");

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
    @RequestMapping(value = "/merchant/modify-type", method = RequestMethod.POST)
    public void modifyType(Integer id, Integer status, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        final String actionKey = "/merchant/modify-type";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        final long t1 = System.currentTimeMillis();
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                if (merchantService.updateType(id, status))
                    json.set(0, "0-5");
                else
                    json.set(1, "1-5");
            } else
                json.set(2, "2-4");
        } else
            json.set(2, "2-6");
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text");
    }

    @ResponseBody
    @RequestMapping(value = "/merchant/get", method = RequestMethod.POST)
    public void getMerchant(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        /*final String actionKey = "/merchant/get";
        final long t1 = System.currentTimeMillis();
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {*/
                final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());

                Integer id = HttpUtil.getIntParameter(request, "id");
                Merchant merchant = merchantService.getBean(id);
                json.accumulate("bean", merchant);

                json.set(0, "0-3");
            /*}
            else {
                json.set(2, "2-4");
            }
        }
        else {
            json.set(2, "2-6");
        }
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }*/
        HttpUtil.write(response, json.toString(), "text/json");
    }

    @ResponseBody
    @RequestMapping(value = "/merchant/getlist", method = RequestMethod.POST)
    public void getMerchantList(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        List<Merchant> listAll = merchantService.getListAll();
        HttpUtil.write(response, JSON.toJSONString(listAll), "text/json");
    }

    @ResponseBody
    @RequestMapping(value = "/merchant/notexists", method = RequestMethod.POST)
    public void notexists(String code, String account, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        boolean boo = false;
        if (null == merchantService.exists(code, account))
            boo = true;
        HttpUtil.write(response, String.valueOf(boo), "text");
    }
}