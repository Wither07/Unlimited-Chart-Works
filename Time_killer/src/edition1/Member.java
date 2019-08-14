package edition1;
/**
 * @author Wither
 *
 *
 */
import java.util.Vector;

public class Member {
	public int No = 0;
	public String name;
	public int period_amount;
	public Vector store_period;
	
	public Member() { }
	public Member(Member x) { 
		this.name = x.name;
		this.period_amount = x.period_amount;
		this.store_period = x.store_period;
	}
}
