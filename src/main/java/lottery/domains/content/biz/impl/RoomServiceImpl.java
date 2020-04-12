package lottery.domains.content.biz.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lottery.domains.content.biz.RoomService;
import lottery.domains.content.dao.RoomDao;
import lottery.domains.content.entity.Room;
import lottery.domains.content.vo.lottery.RoomVO;
import lottery.domains.pool.LotteryDataFactory;

@Service
public class RoomServiceImpl implements RoomService {
	
	@Autowired
    private LotteryDataFactory lotteryDataFactory;
	@Autowired
    private RoomDao rdao;
	
	@Override
	public List<RoomVO> query(int lottery, int type) {
		List<Room> rs = rdao.query(lottery, type);
		List<RoomVO> rms = new ArrayList<RoomVO>();
		for(Room r : rs) {
			rms.add(new RoomVO(r, lotteryDataFactory));
		}
		return rms;
	}
	
	@Override
	public boolean add(int merchant, int lottery, int type, String name, int maxPlayer, int minBalance, int minBet, String desc) {
		if (merchant < 1 || lottery < 1 || type < 1 || name.length() < 1)
			return false;
		
		return rdao.add(merchant, lottery, type, name, maxPlayer, minBalance, minBet, desc);
	}
	
	@Override
	public boolean update(int id, int lottery, int type, String name, int maxPlayer, int minBalance, int minBet, String desc, int state) {
		if (lottery < 1 || type < 1 || name.length() < 1)
			return false;
		if (state != 0 && state != -1)
			state = 0;
		
		return rdao.update(id, lottery, type, name, maxPlayer, minBalance, minBet, desc, state);
	}
}
