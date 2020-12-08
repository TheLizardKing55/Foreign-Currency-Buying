package accountManager.view;
import accountManager.controller.Controller;
import accountManager.model.Model;
public interface View {
	Controller getController();
	public void setController(Controller aController);
	public Model getModel();
	public void setModel(Model aModel);
}
