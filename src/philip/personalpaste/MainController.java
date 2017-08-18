package philip.personalpaste;

import java.io.File;
import java.util.Date;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	
	@PostConstruct
	public void init()
	{
		Scanner scan = new Scanner(System.in);
		while(true)
		{
			System.out.println("Please enter the data directory path:");
			String path = scan.nextLine();
			File dd = new File(path);
			try {
				DataHandler.setDataDirectory(dd);
				break;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	@RequestMapping("/")
	public ModelAndView homePage()
	{
		ModelAndView mv = new ModelAndView("Home");
		
		mv.addObject("time", new Date().toString());
		mv.addObject("creationResult", DataHandler.getPasteCount() + " pastes so far!");
		
		return mv;
	}
	
	@RequestMapping(value={"/view/{pasteName}", "/paste/{pasteName}"})
	public ModelAndView viewPasteLink(@PathVariable("pasteName") String pasteName)
	{
		ModelAndView mv = new ModelAndView("ViewPaste");

		
		try {
			mv.addObject("pasteTitle", "You are viewing " + pasteName);
			mv.addObject("pasteContent", DataHandler.getPaste(pasteName).replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;"));
		} catch (Exception e) {
			mv.addObject("pasteTitle", "You are viewing literally nothing.");
			mv.addObject("pasteContent", "This paste by the name \"" + pasteName + "\" does not exist. You are staring at nothing. Arguably, this is one of the truest nothings there is in the world, as the paste you tried to pull up simply does not exist in the universe even as some lost energy signature.");
		}
		
		return mv;
	}
	
	@RequestMapping(value="/submit", method = RequestMethod.POST)
	public ModelAndView viewPaste(@RequestParam("submitBtn") String submitBtn, @RequestParam("pasteName") String pasteName)
	{
		try
		{
			DataHandler.validateName(pasteName);
		}
		catch (Exception exc)
		{
			ModelAndView result = new ModelAndView("Home");
			result.addObject("time", new Date().toString());
			result.addObject("creationResult", "Your paste has an invalid name! Alphanumeric names only!");
			return result;
		}
		
		ModelAndView result = null;
		if (submitBtn.equals("Create"))
		{
			result = new ModelAndView("CreatePaste");
			result.addObject("pasteName", pasteName);
		}
		else
		{
			//Must have been view
			result = viewPasteLink(pasteName);
		}
		return result;
	}
	
	@RequestMapping("/about")
	public ModelAndView about()
	{
		return new ModelAndView("About");
	}
	
	@RequestMapping(value="/create", method = RequestMethod.POST)
	public ModelAndView createPaste(@RequestParam("pasteName") String pasteName, @RequestParam("pasteContent") String pasteContent)
	{
		ModelAndView mv = new ModelAndView("Home");
		
		mv.addObject("time", new Date().toString());
		
		try
		{
			DataHandler.setPaste(pasteName, pasteContent);
			mv.addObject("creationResult", "Your paste has been saved");
		}
		catch (Exception exc)
		{
			mv.addObject("creationResult", "Your paste could not be saved, sorry.");
		}
		
		return mv;
	}
}
