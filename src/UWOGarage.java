import ca.uwo.garage.AdminController;
import ca.uwo.garage.AdminView;
import ca.uwo.garage.Controller;
import ca.uwo.garage.View;

public class UWOGarage {
	public static void main(String[] args) {
		Controller control = new AdminController();
		View view = new AdminView(control);
	}
}
