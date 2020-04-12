package admin.domains.content.biz;

import admin.domains.content.entity.Merchant;
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
public interface MerchantService {

    Merchant getbyCode(String code);

    Merchant getbyAccount(String account);

    Merchant exists(String code, String account);

    boolean add(Merchant merchant);

    PageList search(String name, String code, Integer status, Integer start, Integer limit);

    boolean updateMerchant(Merchant merchant);

    Merchant getBean(Integer id);

    List<Merchant> getListAll();

    boolean updateType(Integer id, Integer status);

}
