package admin.web.content;

import admin.domains.content.biz.SiteTemplateService;
import admin.domains.content.entity.AdminUser;
import admin.domains.content.entity.SiteTemplate;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
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
public class SiteTemplateController extends AbstractActionController {
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;

    @Autowired
    private SiteTemplateService siteTemplateService;

    @ResponseBody
    @RequestMapping(value = "/site-template/list", method = RequestMethod.POST)
    public void SITE_TEMPLATE_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        List<SiteTemplate> all = siteTemplateService.findAll();
        HttpUtil.write(response, JSON.toJSONString(all), "text/json");
    }

    @ResponseBody
    @RequestMapping(value = "/site-template/add", method = RequestMethod.POST)
    public void SITE_TEMPLATE_ADD(@RequestParam MultipartFile smallImage, MultipartFile bigImage, HttpSession session,
                                  HttpServletRequest request, HttpServletResponse response) {
        final String actionKey = "/site-template/add";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        final long t1 = System.currentTimeMillis();
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)){
                String code = request.getParameter("code");
                String name = request.getParameter("name");
                Integer type = HttpUtil.getIntParameter(request, "type");
                //验证code
                SiteTemplate beanByCode = siteTemplateService.getBeanByCode(code,0);
                if (null == beanByCode || code !=null){
                    /*文件上传start*/
                    String savePath = request.getSession().getServletContext().getRealPath("staticmedia/templateImg");
                    String imgFormat = "bmp,jpg,png,tif,gif,pcx,tga,exif,fpx,svg,psd,cdr,pcd,dxf,ufo,eps,ai,raw,webp";
                    String suf = smallImage.getOriginalFilename().substring(smallImage.getOriginalFilename().lastIndexOf(".") + 1);
                    String suf1 = bigImage.getOriginalFilename().substring(bigImage.getOriginalFilename().lastIndexOf(".") + 1);
                    InputStream is = null;
                    OutputStream os = null;
                    InputStream is1 = null;
                    OutputStream os1 = null;
                    try {
                        File f = new File(savePath);
                        if (!f.exists()) {
                            f.mkdir();
                        }
                        // 图片格式验证
                        if (imgFormat.contains(suf.toLowerCase()) && imgFormat.contains(suf1.toLowerCase())) {

                            String smallImageName = code + "-smallImage." + suf;
                            String bigImageName = code + "-bigImage." + suf;

                            File file = new File(savePath + "/" + smallImageName );
                            if (!file.exists())
                                file.createNewFile();
                            File file1 = new File(savePath + "/" + bigImageName);
                            if (!file1.exists())
                                file1.createNewFile();

                            is = smallImage.getInputStream();
                            os = new FileOutputStream(file);
                            byte[] bytes = new byte[1024];
                            int n = 0;
                            while ((n = is.read(bytes)) != -1) {
                                os.write(bytes);
                            }
                            is1 = bigImage.getInputStream();
                            os1 = new FileOutputStream(file1);
                            while ((n = is1.read(bytes)) != -1) {
                                os1.write(bytes);
                            }

                            SiteTemplate siteTemplate = new SiteTemplate(code, name, type, "templateImg/"+smallImageName,"templateImg/"+ bigImageName);

                            if (siteTemplateService.add(siteTemplate))
                                json.set(0,"0-6");
                            else
                                json.set(1,"1-6");
                        } else {
                            //图片格式验证失败，返回错误消息
                            json.set(2,"2-9000");
                        }
                    } catch (IOException e) {
                        log.error("add template error:[{}]",e.getMessage());
                        json.set(1,"1-6");
                    }finally {
                        try {
                            if (os1!=null)
                                os1.close();
                            if (is1!=null)
                                is1.close();
                            if (os!=null)
                                os.close();
                            if (is!=null)
                                is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    /*文件上传end*/
                }else {
                    //验证失败
                    json.set(2,"2-9001");
                }
            }else
                json.set(2,"2-4");
        }else
            json.set(2,"2-6");
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response,json.toString(),"text/json");
    }

    @ResponseBody
    @RequestMapping(value = "/site-template/delete", method = RequestMethod.POST)
    public void SITE_TEMPLATE_DELETE(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        final String actionKey = "/site-template/delete";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        final long t1 = System.currentTimeMillis();
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                Integer id = HttpUtil.getIntParameter(request, "id");
                if (siteTemplateService.delete(id))
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
    @RequestMapping(value = "/site-template/update", method = RequestMethod.POST)
    public void SITE_TEMPLATE_UPDATE(@RequestParam(required = false) MultipartFile smallImage, MultipartFile bigImage,
                                     HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        final String actionKey = "/site-template/update";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        final long t1 = System.currentTimeMillis();
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)){
                Integer id = HttpUtil.getIntParameter(request, "id");
                Integer type = HttpUtil.getIntParameter(request, "type");
                String name = request.getParameter("name");
                String code = request.getParameter("code");
                //验证code
                SiteTemplate beanByCode = siteTemplateService.getBeanByCode(code,0);
                if (beanByCode == null || beanByCode.getId() == id ) {
                    String savePath = request.getSession().getServletContext().getRealPath("staticmedia/templateImg");
                    File f = new File(savePath);
                    if (!f.exists()) {
                        f.mkdir();
                    }
                    boolean boo = true;
                    String imgFormat = "bmp,jpg,png,tif,gif,pcx,tga,exif,fpx,svg,psd,cdr,pcd,dxf,ufo,eps,ai,raw,webp";
                    InputStream is = null;
                    OutputStream os = null;
                    InputStream is1 = null;
                    OutputStream os1 = null;
                    String smallImageName = "";
                    String bigImageName = "";
                    try {
                        // 是否修改图片
                        byte[] bytes = new byte[1024];
                        int n = 0;
                        if (smallImage != null) {
                            String suf = smallImage.getOriginalFilename().substring(smallImage.getOriginalFilename().lastIndexOf(".") + 1);
                            if (imgFormat.contains(suf.toLowerCase())){
                                smallImageName = code + "-smallImage." + suf;
                                File file = new File(savePath + "/" + smallImageName );
                                if (!file.exists())
                                    file.createNewFile();
                                is = smallImage.getInputStream();
                                os = new FileOutputStream(file);
                                while ((n = is.read(bytes)) != -1) {
                                    os.write(bytes);
                                }
                                smallImageName = "templateImg/"+smallImageName;
                            }else {
                                json.set(2,"2-9000");
                                boo = false;
                            }
                        }
                        if (bigImage != null) {
                            String suf1 = bigImage.getOriginalFilename().substring(bigImage.getOriginalFilename().lastIndexOf(".") + 1);
                            if (imgFormat.contains(suf1.toLowerCase())){
                                bigImageName = code + "-bigImage." + suf1;
                                File file1 = new File(savePath + "/" + bigImage);
                                if (!file1.exists())
                                    file1.createNewFile();
                                is1 = bigImage.getInputStream();
                                os1 = new FileOutputStream(file1);
                                while ((n = is1.read(bytes)) != -1) {
                                    os1.write(bytes);
                                }
                                bigImageName = "templateImg/"+bigImage;
                            }else {
                                json.set(2,"2-9000");
                                boo = false;
                            }
                        }
                    } catch (IOException e) {
                        e.getMessage();
                    }finally {
                        try {
                            if (os1!=null)
                                os1.close();
                            if (is1!=null)
                                is1.close();
                            if (os!=null)
                                os.close();
                            if (is!=null)
                                is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (boo) {
                        SiteTemplate siteTemplate = new SiteTemplate(code, name, type, smallImageName, bigImageName);
                        siteTemplate.setId(id);
                        log.info("SITE_TEMPLATE_UPDATE :{}", siteTemplate);

                        boolean update = siteTemplateService.update(siteTemplate);
                        json.set(0,"0-6");
                    }
                }else {
                    // code 已存在
                    json.set(2,"2-9001");
                }
            }else
                json.set(2,"2-4");
        }else
            json.set(2,"2-6"); final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response,json.toString(),"text/json");
    }

    @ResponseBody
    @RequestMapping(value = "/site-template/get", method = RequestMethod.POST)
    public void getMerchant(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

//        final String actionKey = "/site-template/get";
//        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
//        final AdminUser uEntity = super.getCurrUser(session, request, response);
//        if (uEntity != null) {
//            if (super.hasAccess(uEntity, actionKey)) {
        Integer id = HttpUtil.getIntParameter(request, "id");
        SiteTemplate siteTemplate = siteTemplateService.getBean(id);
        json.accumulate("bean", siteTemplate);
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
    @RequestMapping(value = "/site-template/getbycode", method = RequestMethod.POST)
    public void getMerchantByCode(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        Integer type = HttpUtil.getIntParameter(request,"type");
        SiteTemplate siteTemplate = siteTemplateService.getBeanByCode(code,type);
        HttpUtil.write(response, JSON.toJSONString(siteTemplate), "text/json");
    }
    @ResponseBody
    @RequestMapping(value = "/site-template/notexistCode", method = RequestMethod.POST)
    public void notexistCode(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        SiteTemplate siteTemplate = siteTemplateService.getBeanByCode(code,0);
        boolean boo = false;
        if (siteTemplate == null)
            boo = true;
        HttpUtil.write(response, String.valueOf(boo), "text/json");
    }

    @ResponseBody
    @RequestMapping(value = "/site-template/search", method = RequestMethod.POST)
    public void search(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        final String actionKey = "/site-template/search";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        final long t1 = System.currentTimeMillis();
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)){
                String name = request.getParameter("name");
                Integer type = HttpUtil.getIntParameter(request, "type");
                Integer page = HttpUtil.getIntParameter(request, "start");
                Integer pageSize = HttpUtil.getIntParameter(request, "limit");
                PageList pList = siteTemplateService.search(type, name, page, pageSize);
                if (pList != null) {
                    json.accumulate("totalCount", pList.getCount());
                    json.accumulate("data", pList.getList());
                } else {
                    json.accumulate("totalCount", 0);
                    json.accumulate("data", "[]");
                }
                json.set(0, "0-3");
            }else
                json.set(2,"2-4");
        }else
            json.set(2,"2-6");
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response,json.toString(),"text/json");
    }

}
