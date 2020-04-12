package admin.domains.content.biz;

import admin.domains.content.entity.MerchantBrandDomain;
import admin.domains.content.vo.MerchantDomainVO;
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
public interface MerchantBrandDomainService {


    MerchantDomainVO getBean(Integer id);

    PageList search(Integer brand, String domain, Integer start, Integer limit);

    List<MerchantDomainVO> findAll();

    boolean add(MerchantBrandDomain domain);

    boolean update(MerchantBrandDomain domain);

    boolean delete(Integer id);


}
