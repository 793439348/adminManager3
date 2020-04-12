package lottery.domains.content.dao;

import lottery.domains.content.entity.Room;
import java.util.List;

public interface RoomDao {
	List<Room> query(int lottery, int type);
	boolean add(int merchant, int lottery, int type, String name, int maxPlayer, int minBalance, int minBet, String desc);
	boolean update(int id, int lottery, int type, String name, int maxPlayer, int minBalance, int minBet, String desc, int state);
}
