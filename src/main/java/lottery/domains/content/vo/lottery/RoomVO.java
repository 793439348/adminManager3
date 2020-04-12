package lottery.domains.content.vo.lottery;

import lottery.domains.content.entity.Lottery;
import lottery.domains.content.entity.Room;
import lottery.domains.pool.LotteryDataFactory;

public class RoomVO {
	private int id;
    private String lottery;
    private String name;
    private String type;
    private int state;
    private int maxPlayer;
    private int minBalance;
    private int minBet;
    private String desc;
    private String icon;

    public RoomVO(final Room bean, final LotteryDataFactory df) {
        final Lottery lbean = df.getLottery(bean.getLotteryId());
        if (lbean != null) {
            this.lottery = lbean.getShowName();
        }
        this.id = bean.getId();
        this.name = bean.getName();
        this.type = mapRoomType(bean.getType());
        this.state = bean.getState();
        this.maxPlayer = bean.getMaxPlayer();
        this.minBalance = bean.getMinBalance();
        this.minBet = bean.getMinBet();
        this.desc = bean.getDesc();
        this.icon = bean.getIcon();
    }
    
    private String mapRoomType(int type) {
    	switch(type) {
    	case 1:
    		return "普通";
    	case 2:
    		return "中级";
    	case 3:
    		return "高级";
    	case 4:
    		return "至尊";
    	}
    	return "未知";
    }
    
    public int getId() {
        return this.id;
    }
    public void setId(final int id) {
        this.id = id;
    }

    public String getLottery() {
        return this.lottery;
    }
    public void setLottery(final String v) {
        this.lottery = v;
    }

    public String getName() {
        return this.name;
    }
    public void setName(final String v) {
        this.name = v;
    }

    public String getType() {
        return this.type;
    }
    public void setType(final String v) {
        this.type = v;
    }

    public int getState() {
        return this.state;
    }
    public void setState(final int v) {
        this.state = v;
    }

    public int getMaxPlayer() {
        return this.maxPlayer;
    }
    public void setMaxPlayer(final int v) {
        this.maxPlayer = v;
    }

    public int getMinBalance() {
        return this.minBalance;
    }
    public void setMinBalance(final int v) {
        this.minBalance = v;
    }

    public int getMinBet() {
        return this.minBet;
    }
    public void setMinBet(final int v) {
        this.minBet = v;
    }

    public String getDesc() {
        return this.desc;
    }
    public void setDesc(final String v) {
        this.desc = v;
    }

    public String getIcon() {
        return this.icon;
    }
    public void setIcon(final String v) {
        this.icon = v;
    }
}
