package lottery.domains.content.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "room", catalog = "ecai")
public class Room implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1921788722708834491L;
	
	private int id;
    private int merchantId;
    private int lotteryId;
    private String name;
    private int type;
    private int state;
    private int maxPlayer;
    private int minBalance;
    private int minBet;
    private String desc;
    private String icon;
    
    public Room() {}
    
    public Room(int merchant, int lottery, int type, String name, int maxPlayer, int minBalance, int minBet, String desc) {
    	this.merchantId = merchant;
    	this.lotteryId = lottery;
    	this.type = type;
    	this.state = 0;
    	this.name = name;
    	this.maxPlayer = maxPlayer;
    	this.minBalance = minBalance;
    	this.minBet = minBet;
    	this.desc = desc;
    	this.icon = "";
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }
    public void setId(final int id) {
        this.id = id;
    }
    
    @Column(name = "merchant_id", nullable = false)
    public int getMerchantId() {
        return this.merchantId;
    }
    public void setMerchantId(final int v) {
        this.merchantId = v;
    }
    
    @Column(name = "lottery_id", nullable = false)
    public int getLotteryId() {
        return this.lotteryId;
    }
    public void setLotteryId(final int v) {
        this.lotteryId = v;
    }
    
    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return this.name;
    }
    public void setName(final String v) {
        this.name = v;
    }
    
    @Column(name = "type", nullable = false)
    public int getType() {
        return this.type;
    }
    public void setType(final int type) {
        this.type = type;
    }
    
    @Column(name = "state", nullable = false)
    public int getState() {
        return this.state;
    }
    public void setState(final int v) {
        this.state = v;
    }
    
    @Column(name = "max_player", nullable = false)
    public int getMaxPlayer() {
        return this.maxPlayer;
    }
    public void setMaxPlayer(final int v) {
        this.maxPlayer = v;
    }
    
    @Column(name = "min_balance", nullable = false)
    public int getMinBalance() {
        return this.minBalance;
    }
    public void setMinBalance(final int v) {
        this.minBalance = v;
    }
    
    @Column(name = "min_bet", nullable = false)
    public int getMinBet() {
        return this.minBet;
    }
    public void setMinBet(final int v) {
        this.minBet = v;
    }
    
    @Column(name = "desc", nullable = false, length = 200)
    public String getDesc() {
        return this.desc;
    }
    public void setDesc(final String v) {
        this.desc = v;
    }
    
    @Column(name = "icon", nullable = false, length = 200)
    public String getIcon() {
        return this.icon;
    }
    public void setIcon(final String v) {
        this.icon = v;
    }
}
