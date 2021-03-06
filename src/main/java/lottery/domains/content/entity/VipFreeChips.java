package lottery.domains.content.entity;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "vip_free_chips", catalog = "ecai")
public class VipFreeChips implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private int userId;
    private int level;
    private double money;
    private String startTime;
    private String endTime;
    private String time;
    private int status;
    private int isReceived;
    
    public VipFreeChips() {
    }
    
    public VipFreeChips(final int userId, final int level, final double money, final String startTime, final String endTime, final String time, final int status, final int isReceived) {
        this.userId = userId;
        this.level = level;
        this.money = money;
        this.startTime = startTime;
        this.endTime = endTime;
        this.time = time;
        this.status = status;
        this.isReceived = isReceived;
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
    
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return this.userId;
    }
    
    public void setUserId(final int userId) {
        this.userId = userId;
    }
    
    @Column(name = "level", nullable = false)
    public int getLevel() {
        return this.level;
    }
    
    public void setLevel(final int level) {
        this.level = level;
    }
    
    @Column(name = "money", nullable = false, precision = 16, scale = 5)
    public double getMoney() {
        return this.money;
    }
    
    public void setMoney(final double money) {
        this.money = money;
    }
    
    @Column(name = "start_time", nullable = false, length = 10)
    public String getStartTime() {
        return this.startTime;
    }
    
    public void setStartTime(final String startTime) {
        this.startTime = startTime;
    }
    
    @Column(name = "end_time", nullable = false, length = 10)
    public String getEndTime() {
        return this.endTime;
    }
    
    public void setEndTime(final String endTime) {
        this.endTime = endTime;
    }
    
    @Column(name = "time", nullable = false, length = 19)
    public String getTime() {
        return this.time;
    }
    
    public void setTime(final String time) {
        this.time = time;
    }
    
    @Column(name = "status", nullable = false)
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(final int status) {
        this.status = status;
    }
    
    @Column(name = "is_received", nullable = false)
    public int getIsReceived() {
        return this.isReceived;
    }
    
    public void setIsReceived(final int isReceived) {
        this.isReceived = isReceived;
    }
}
