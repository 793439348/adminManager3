package lottery.domains.content.vo.bill;

import lottery.domains.content.entity.UserLotteryDetailsReport;

public class HistoryUserLotteryDetailsReportVO
{
    private String name;
    private String key;
    private double spend;
    private double prize;
    private double spendReturn;
    private double proxyReturn;
    private double cancelOrder;
    private double billingOrder;
    
    public HistoryUserLotteryDetailsReportVO() {
    }
    
    public HistoryUserLotteryDetailsReportVO(final String name, final String key) {
        this.name = name;
        this.key = key;
    }
    
    public void addBean(final UserLotteryDetailsReport bean) {
        this.spend += bean.getSpend();
        this.prize += bean.getPrize();
        this.spendReturn += bean.getSpendReturn();
        this.proxyReturn += bean.getProxyReturn();
        this.cancelOrder += bean.getCancelOrder();
        this.billingOrder += bean.getBillingOrder();
    }
    
    public void addBean(final HistoryUserLotteryDetailsReportVO bean) {
        this.spend += bean.getSpend();
        this.prize += bean.getPrize();
        this.spendReturn += bean.getSpendReturn();
        this.proxyReturn += bean.getProxyReturn();
        this.cancelOrder += bean.getCancelOrder();
        this.billingOrder += bean.getBillingOrder();
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getKey() {
        return this.key;
    }
    
    public void setKey(final String key) {
        this.key = key;
    }
    
    public double getSpend() {
        return this.spend;
    }
    
    public void setSpend(final double spend) {
        this.spend = spend;
    }
    
    public double getPrize() {
        return this.prize;
    }
    
    public void setPrize(final double prize) {
        this.prize = prize;
    }
    
    public double getSpendReturn() {
        return this.spendReturn;
    }
    
    public void setSpendReturn(final double spendReturn) {
        this.spendReturn = spendReturn;
    }
    
    public double getProxyReturn() {
        return this.proxyReturn;
    }
    
    public void setProxyReturn(final double proxyReturn) {
        this.proxyReturn = proxyReturn;
    }
    
    public double getCancelOrder() {
        return this.cancelOrder;
    }
    
    public void setCancelOrder(final double cancelOrder) {
        this.cancelOrder = cancelOrder;
    }
    
    public double getBillingOrder() {
        return this.billingOrder;
    }
    
    public void setBillingOrder(final double billingOrder) {
        this.billingOrder = billingOrder;
    }
}
