package edition1;
/**
 * 
 * @author Wither
 * 
 *做不出来了先这样吧……
 *
 *结果最后还是自己试着做……
 *本模块大量参考网络资料
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class Auto_arrange {
	private static final String[] day = {
			"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
    private static final String[] time = {
    		"第一大节", "第二大节","第三大节","第四大节","第五大节"};

    final static int arraySize = 200;
    final static int inf = 1000000000;
    static int capacity[][], flow[][], max_flow[], pre[];//[arraySize]
    static int reflect[];//100标记值班的干事
    static int period_info[][];
    static int member_amount;
	static int period_amount;
	static int tmp;
	static int start_node;
	static int end_node;
    static Member store_member[];//100
    
    boolean cmp(Member a, Member b) {
    	return a.period_amount < b.period_amount;
    }
    
    static void Initialize() {
    	reflect = new int[100];
    	for (int element: reflect) {
    		element = 0;
    	}
    	period_info = new int[100][100];
    	for (int[] element: period_info) {
    		for (int e : element) {
    			e = 0;
    		}
    	}
    }
    
    static int Edmonds_Karp(int source, int target)//源点,汇点   
    {
        //初始化   
        Queue<Integer> store = new LinkedList<Integer>();
        int cur;
        //ans最大流 此处用于返回看能否保证每一班一个人  
        int ans=0;
        //cur当前节点   
        //flow存储的是两个点之间的流量，用于后续判断干事和班之间的关系  
        flow = new int [arraySize][arraySize];
        for (int[] r: flow) {
        	for (int c: r) {
        		c = 0;
        	}
        }
        while (true)//一直寻找增广路 
        {
        	max_flow = memset(arraySize, 0);
        	pre = memset(arraySize, 0);
            store.add(source);
            max_flow[source] = inf;
            while (!store.isEmpty())
            {
                cur = store.remove();
                for (int next = source; next <= target; next++)  
                {
                    //max_flow[next]恰可以用于标记是否访问过，同时要保证两点之间还有剩余流量   
                    //这个过程中，可能会出现多条可行路径，但因为汇点只有一个会被先到达的路径抢占，故每个过程只能找到一条  
                    if ((max_flow[next] == 0) && (capacity[cur][next]>flow[cur][next]))  
                    {
                        store.add(next);
                        //如果这两个点之间的值，比之前的最小值还小，则更新   
                        max_flow[next] = min(max_flow[cur], capacity[cur][next] - flow[cur][next]);
                        //记录前一个节点，用于后续更新   
                        pre[next] = cur;
                    }
                }
            }
            //说明已经找不到增广路了   
            if (max_flow[target] == 0)
                break;
            //更新操作   
            for (int u = target; u != source; u = pre[u]) {
                flow[pre[u]][u] += max_flow[target];
                //反向边    
                flow[u][pre[u]] -= max_flow[target];
            }
            ans += max_flow[target];
        }
        return ans;
    }
    
    private static int min(int i, int j) {
		return i < j ? i : j;
	}

	public static int[] memset(int size, int b) {
    	int[] ret = new int[size];
    	for (int c : ret) {
    		c = b;
    	}
    	return ret;
    }
    
    public static int[][] memset(int col, int row, int b) {
    	int[][] ret = new int[col][row];
    	for (int[] c : ret) {
    		for(int r : c) {
    			r = b;
    		}
    	}
    	return ret;
    }
    
	public static void reset(String filePath) throws IOException {
		//用于存储在EK算法之后，还没有被分配的干事  
	    Vector<Member> store_rest = new Vector<Member>();
	    Initialize();
	    int tmpstore;//下面临时存储一个干事可值班数的变量   
	    //模拟系统数据获取，如若需要每月排班一次可以随机获得干事的输入顺序，即可获得不同的结果  
	    //建议最后可以添加一个功能,当一个人需要手动调整位置时，可以实现他在哪些班次可以移动   
	    period_amount=26;
	    //值班数
	    member_amount=0;
	    //干事数通过计算有几次输入获得，初始值为0
	    int[] sch = {
	    		0,   1,   2,   3,   4,
	    		7,   8,   9,  10, 11,
	    		14, 15, 16, 17, 18,
	    		21, 22, 23, 24, 25,
	    		28, 29, 30, 31, 32, 33
	    };
	    store_member = new Member[100];
	    AvlTree t = Data_engine.load(filePath);
	    //本段处理输入
        //member的下标必须从1开始，因为还要设置一个超级源点
	    BufferedReader br =
				new BufferedReader(
						new InputStreamReader(
								new FileInputStream(filePath), "GBK"));
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			member_amount++;
			store_member[member_amount] = new Member();
			store_member[member_amount].name = new String(line.split("·")[0]);
			tmpstore = 0;
			store_member[member_amount].No = member_amount;
			store_member[member_amount].store_period = new Vector<Integer>();
			for (int i : sch) {
				if (line.split("·")[9].charAt(i) == '0') {
					tmpstore++;
					store_member[member_amount].store_period.add(Arrays.binarySearch(sch, i)+1);
				}
			}
			store_member[member_amount].period_amount=tmpstore;
		}
		br.close();
		
	    //设置源点为0，1-member_amount为干事,  
	    //member_amount+1到member_amount+period_amount即为班次   
	    start_node = 0;
	    end_node = member_amount + period_amount + 1;
	    //默认用序号代表班，从1到period_amount  
	    //建图  
	    //初始化，默认两点间有通路为1，无通路为0。  
	    capacity = memset(arraySize, arraySize, 0);
	    //超级源点为0，超级汇点为member_amount+period_amount+1  
	    //超级源点到每个干事间连一条权值为1的边，代表每个干事值1班  
	    for (int i = 1; i <= member_amount; i++)  
	    {
	        capacity[0][i] = 1;
	    }
	    //每班到超级汇点之间连一条权值为1的边，从而先确保每一班保证有一个干事值  
	    for (int i = member_amount+1; i <= end_node-1; i++)  
	    {
	        capacity[i][end_node] = 1;
	    }
	    for (int i = 1; i <= member_amount; i++)  
	    {
	        for (int j = 0; j < store_member[i].period_amount; j++)  
	        {
	            //干事和班之间权值置1，代表干事到班之间有一条路  
	            //store_member[i].store_period[j]存储的是第i个干事的第j班可以值哪个班加上member_amount的值就可以表示图中的点   
	            capacity[i][(int)store_member[i].store_period.get(j) + member_amount] = 1;
//	            System.out.println("i: " + i + "\nj: " + ((int)store_member[i].store_period.get(j) + member_amount));
	        }
	    }
	    //调用EK算法，先确保每一班有一人值。  
	    int check=Edmonds_Karp(start_node, end_node);
	    //check用来检验是否能够保证每班至少1人,若不等于班数则代表不可以确保，不过这种情况几乎不可能发生   
	    /*此处或许可以更加严密，如出现这种情况，就让某些干事值两班,出现这种情况，只要将源点到每个干事的权值设为2 
	     保留每个班次到汇点的权值为1，细节部分需要稍作修改*/  
	    if (check != period_amount)  
	    {
	    	System.out.println("无法保证每班一人");
	    	System.out.println(check + "\n" + period_amount);
	        return;
	    }
	    //给已经分配好的值班确认关系  
	    for (int i = member_amount + 1; i <= end_node - 1;i++)  
	    {
	        for (int j = 1; j <= member_amount; j++)  
	        {
	            //flow的值代表第j个干事是否值第(i-member_amount)个班,如果为1，则建立关系   
	            if (flow[j][i] == 1)  
	            {
	                reflect[j] = (i - member_amount);
	                break;
	            }
	        }
	    }
	    //处理已经分配好的干事和对应班的关系   
	    for (int i = 1; i <= member_amount; i++)  
	    {
	        if(reflect[i]!=0)//如果第i个干事已经被分配了1班   
	        	tmp = reflect[i];
	        else 
	        	continue; //否则跳过   
	        for (int j = 0; j < 10; j++)  
	        {
	            //找到一个空值，把这个干事放进去   
	            if (period_info[tmp][j] == 0)  
	            {
	                period_info[tmp][j] = i;
	                break;
	            }
	        }
	    }
	    //贪心阶段  
	    for (int i = 1; i <= member_amount; i++)  
	    {
	        //还未被安排  
	        if (reflect[i] == 0)  
	        {
	            store_rest.add(store_member[i]);
	        }
	    }
	    //按照自定义的贪心策略排序,已修改为冒泡
	    for(int i=1;i<store_rest.size();i++)  
	       {
	         for(int j=store_rest.size()-1;j>=i;j--)  
	         {
	           if(store_rest.get(j).period_amount<store_rest.get(j-1).period_amount)  
	            {
	        	    Collections.swap(store_rest, j, j-1);
	            }   
	         }
	       }
	    int p, minn,cnt;
	    //p用来记录该干事的哪个可值班中现有人数最少的那个班的序号  
	    //minn用来记录，该干事可值班中最小的现有人数   
	    for (int i = 0; i < store_rest.size(); i++)//循环剩下的干事   
	    {
	        p = 0;
	        minn = inf;
	        //循环该干事的可值班，找出他可值班中哪个班现有人数最少，并将他安排到那个班次  
	        //同时注意及时更新班次信息，否则会在贪心选择时出错   
	        for (int j = 0; j < store_rest.get(i).period_amount; j++)  
	        {
	            cnt = 0;
	            for (int k = 0; k < 10; k++)  
	            {
	                //store_rest[i].store_period[j][k]剩下的第i个干事，他可值的第j个班的班的序号   
	                if (period_info[(int)store_rest.get(i).store_period.get(j)][k] != 0)  
	                {
	                    cnt++;
	                }
	                else break;
	            }
	            if (cnt < minn)  
	            {
	                minn = cnt;
	                //更新最小班的位置   
	                p = (int) store_rest.get(i).store_period.get(j);
	            }
	        }
	        //更新   
	        reflect[store_rest.get(i).No] = p;
	        for (int k = 0; k < 10; k++)  
	            {
	                if (period_info[p][k] != 0)
	                	continue;
	                else
	                {
	                  //更新班次信息   
	                  period_info[p][k]=store_rest.get(i).No;
	                  break;
	                }
	            } 
	    }
	    //显示   
	    changeTree(t, filePath);
	    Data_engine.save(t, filePath);
	    return;
	}

	private static void changeTree(AvlTree t, String filePath) {
		for (int i = 1; i <= period_amount; i++)  
	    { 
			for (int j = 0; j < 10; j++)  
	        {
	            if (period_info[i][j] != 0) {
	            	Data_engine.change(t, store_member[period_info[i][j]].name, i-1);
	            }
	            else
	            	break;  
	        }
	    }
	}
}
