package lottery.domains.content.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.RoomDao;
import lottery.domains.content.entity.Room;

@Repository
public class RoomDaoImpl implements RoomDao {
	private final String tab;
    @Autowired
    private HibernateSuperDao<Room> superDao;
    
    public RoomDaoImpl() {
        this.tab = Room.class.getSimpleName();
    }
    
    @Override
	public List<Room> query(int lottery, int type) {
    	String prefix = " where ";
    	List<Object> vs = new ArrayList<>();
    	String hql = "from " + this.tab;
    	int idx = 0;
    	if (lottery > 0) {
    		hql = hql + prefix + "lottery_id = ?" + idx;
    		idx ++;
    		prefix = " and ";
    		vs.add(lottery);
    	}
    	if (type > 0) {
    		hql = hql + prefix + "type = ?" + idx;
    		vs.add(type);
    	}
        return this.superDao.list(hql, vs.toArray());
	}
    
    @Override
    public boolean add(int merchant, int lottery, int type, String name, int maxPlayer, int minBalance, int minBet, String desc) {
    	return this.superDao.save(new Room(merchant, lottery, type, name, maxPlayer, minBalance, minBet, desc));
    }
    
    @Override
    public boolean update(int id, int lottery, int type, String name, int maxPlayer, int minBalance, int minBet, String desc, int state) {
    	final String hql = "update " + this.tab + " set lotteryId = ?1,type = ?2, name = ?3, maxPlayer = ?4, minBalance = ?5, minBet = ?6, desc = ?7, state = ?8 where id = ?0";
        final Object[] values = { id, lottery, type, name, maxPlayer, minBalance, minBet, desc, state };
        return this.superDao.update(hql, values);
    }
}
