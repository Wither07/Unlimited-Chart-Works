package edition2;

public class Test {
	public static void main(String args[]) {
		DataEngine de = new DataEngine();
		Root r = de.loadData("C:\\Users\\Wither\\Desktop\\_.txt");
		Student curr = r.gg;
		System.out.println("Start------");
		while(curr!=null) {
			System.out.print(curr.toString());
			curr = curr.nextDep;
		}
	}
}
