package admin.domains.content.biz;

import admin.domains.content.entity.SiteTemplate;
import javautils.jdbc.PageList;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author: zeng
 * @since: 2020-03-27
 */
public interface SiteTemplateService {

    SiteTemplate getBeanByCode(String code,Integer type);

    SiteTemplate getBean(Integer id);

    List<SiteTemplate> findAll();

    PageList search(Integer type, String name, Integer page, Integer pageSize);

    boolean add(SiteTemplate siteTemplate);

    boolean update(SiteTemplate siteTemplate);

    boolean delete(Integer id);
}
