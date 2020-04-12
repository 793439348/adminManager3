package lottery.web.content;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import admin.domains.content.entity.AdminUser;
import admin.web.WebJSONObject;
import admin.web.helper.AbstractActionController;
import javautils.http.HttpUtil;
import lottery.domains.content.biz.RoomService;
import lottery.domains.content.vo.lottery.RoomVO;
import net.sf.json.JSONArray;

@Controller
public class RoomController extends AbstractActionController {
	@Autowired
    private RoomService rSrv;
    
	@RequestMapping(value = { "/room/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void RoomList(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
        	Integer lotteryId = HttpUtil.getIntParameter(request, "lottery");
        	if (lotteryId == null)
        		lotteryId = 0;
        	Integer roomType = HttpUtil.getIntParameter(request, "type");
        	if (roomType == null)
        		roomType = 0;
            final List<RoomVO> list = this.rSrv.query(lotteryId, roomType);
            final JSONArray json = JSONArray.fromObject((Object)list);
            HttpUtil.write(response, json.toString(), "text/json");
        }
        else {
            HttpUtil.write(response, "[]", "text/json");
        }
    }
	
	@RequestMapping(value = { "/room/add" }, method = { RequestMethod.POST })
    @ResponseBody
    public void RoomAdd(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
		final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
        	Integer lotteryId = HttpUtil.getIntParameter(request, "lottery");
        	if (lotteryId == null)
        		lotteryId = 0;
        	Integer roomType = HttpUtil.getIntParameter(request, "type");
        	if (roomType == null)
        		roomType = 0;
        	String name = HttpUtil.getStringParameterTrim(request, "name");
        	if (name == null)
        		name = "";
        	Integer maxPlayer = HttpUtil.getIntParameter(request, "maxPlayer");
        	if (maxPlayer == null)
        		maxPlayer = 0;
        	Integer minBalance = HttpUtil.getIntParameter(request, "minBalance");
        	if (minBalance == null)
        		minBalance = 0;
        	Integer minBet = HttpUtil.getIntParameter(request, "minBet");
        	if (minBet == null)
        		minBet = 0;
        	String desc = HttpUtil.getStringParameterTrim(request, "desc");
        	if (desc == null)
        		desc = "";
        	
        	if (!this.rSrv.add(uEntity.getMerchant_id(), lotteryId, roomType, name, maxPlayer, minBalance, minBet, desc))
        		json.set(2, "2-2");
        }
        else {
        	json.set(2, "2-6");
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
	
	@RequestMapping(value = { "/room/edit" }, method = { RequestMethod.POST })
    @ResponseBody
    public void RoomEdit(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
		final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
        	Integer id = HttpUtil.getIntParameter(request, "id");
        	if (id == null)
        		id = 0;
        	Integer lotteryId = HttpUtil.getIntParameter(request, "lottery");
        	if (lotteryId == null)
        		lotteryId = 0;
        	Integer roomType = HttpUtil.getIntParameter(request, "type");
        	if (roomType == null)
        		roomType = 0;
        	String name = HttpUtil.getStringParameterTrim(request, "name");
        	if (name == null)
        		name = "";
        	Integer maxPlayer = HttpUtil.getIntParameter(request, "maxPlayer");
        	if (maxPlayer == null)
        		maxPlayer = 0;
        	Integer minBalance = HttpUtil.getIntParameter(request, "minBalance");
        	if (minBalance == null)
        		minBalance = 0;
        	Integer minBet = HttpUtil.getIntParameter(request, "minBet");
        	if (minBet == null)
        		minBet = 0;
        	String desc = HttpUtil.getStringParameterTrim(request, "desc");
        	if (desc == null)
        		desc = "";
        	Integer state = HttpUtil.getIntParameter(request, "state");
        	if (state == null)
        		state = 0;
        	
        	if (!this.rSrv.update(id, lotteryId, roomType, name, maxPlayer, minBalance, minBet, desc, state))
        		json.set(2, "2-2");
        }
        else {
        	json.set(2, "2-6");
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
}
