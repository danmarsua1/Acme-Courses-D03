package acme.features.administrator.configuration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.Configuration;
import acme.framework.controllers.AbstractController;
import acme.framework.roles.Administrator;

@Controller
public class AdministratorConfigurationController extends AbstractController<Administrator,Configuration>{
	@Autowired
	protected AdministratorConfigurationShowService showService;
	
	@PostConstruct
	protected void initialise(){
		super.addCommand("show",this.showService);
	}
}
