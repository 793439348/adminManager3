package lottery.domains.content.biz;

import java.util.List;

import lottery.domains.content.vo.lottery.RoomVO;

public interface RoomService {
	List<RoomVO> query(int lottery, int type);
	boolean add(int merchant, int lottery, int type, String name, int maxPlayer, int minBalance, int minBet, String desc);
	boolean update(int id, int lottery, int type, String name, int maxPlayer, int minBalance, int minBet, String desc, int state);
}
