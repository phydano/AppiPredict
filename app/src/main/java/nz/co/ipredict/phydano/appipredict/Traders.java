package nz.co.ipredict.phydano.appipredict;

/**
 * Created by phydano on 19/01/2016.
 * This class constructs a trader
 */
public class Traders {

    private String rank; // rank of the trader
    private String traderName; // name of the trader
    private String roi; // the rate in return of the investment
    private String networth; // the trader networth
    private String networthChange; // the change in the networth

    public Traders (String rank, String traderName, String roi, String networth, String networthChange){
        this.rank = rank;
        this.traderName = traderName;
        this.roi = roi;
        this.networth = networth;
        this.networthChange = networthChange;
    }

    public String getRank(){
        return rank;
    }
    public String getTraderName(){
        return traderName;
    }
    public String getRoi(){
        return roi;
    }
    public String getNetworth(){
        return networth;
    }
    public String getNetworthChange(){
        return networthChange;
    }
}
