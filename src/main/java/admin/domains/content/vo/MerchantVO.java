package admin.domains.content.vo;

import admin.domains.content.entity.Merchant;
import lombok.Data;

@Data
public class MerchantVO {
    private Integer id;
    private String nickname;
    private String code;
    private String name;
    private Double balance;
    private Integer status;
    private Integer userNumber;
    private String createTime;
    private String loginTime;

    public MerchantVO() {
    }

    public MerchantVO(Merchant merchant) {
        this.id=merchant.getId();
        this.nickname = merchant.getNickname();
        this.code = merchant.getCode();
        this.name = merchant.getAccount();
        this.balance = 0.0;
        this.status = merchant.getStatus();
        this.userNumber = 0;
        this.createTime = merchant.getRegistTime();
        this.loginTime = merchant.getLoginTime();
    }
}
