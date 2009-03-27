import ca.uwo.garage.AuthorizationController;
import ca.uwo.garage.AuthorizationView;
import ca.uwo.garage.Controller;
import ca.uwo.garage.View;

public class UWOGarage {
	public static void main(String[] args) {
		//Controller control = new AdminController();
		//View view = new AdminView(control);
		Controller control = new AuthorizationController();
		View view = new AuthorizationView(control);
	}
}
